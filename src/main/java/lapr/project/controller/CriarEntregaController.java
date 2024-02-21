package lapr.project.controller;

import java.util.ArrayList;
import java.util.List;
import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.ui.interacoes_ficheiro.OutputFileWriter;
import lapr.project.utils.Constantes;
import lapr.project.utils.Pair;

/**
 * The type Criar entrega controller.
 */
public class CriarEntregaController {

    private final FarmaciaBD farmaciaBD;
    private final EncomendaBD encomendaBD;
    private final MoradaBD moradaBD;
    private final ScooterBD scooterBD;
    private final DroneBD droneBD;
    private final EstafetaBD estafetaBD;
    private final EntregaBD entregaBD;
    private Morada moradaFarmacia;
    private final CaminhoTask caminhoTask;
    private final EstimarConsumoDroneTask estimarConsumoDroneTask;
    private final EstimarConsumoScooterTask estimarConsumoTask;

    private int idFarm;
    /**
     * The Pair drone.
     */
    Pair<List<Morada>, double[]> pairDrone;
    /**
     * The Pair scooter.
     */
    Pair<List<Morada>, double[]> pairScooter;
    private Scooter scooter;
    private Drone drone;
    private List<Encomenda> listaEncomendas;
    private List<Morada> listaMoradas;
    private List<Drone> listaDrones;
    private List<Drone> listaDronesCapazes;
    private List<Scooter> listaScooters;
    private double cargaTotal;
    private List<Morada> caminho;
    private double distancia;
    private double consumo;

    /**
     * Instantiates a new Criar entrega controller.
     *
     * @param farmaciaBD  the farmacia bd
     * @param encomendaBD the encomenda bd
     * @param moradaBD    the morada bd
     * @param droneBD     the drone bd
     * @param scooterBD   the scooter bd
     * @param estafetaBD  the estafeta bd
     * @param entregaBD   the entrega bd
     */
    public CriarEntregaController(FarmaciaBD farmaciaBD, EncomendaBD encomendaBD, MoradaBD moradaBD, DroneBD droneBD, ScooterBD scooterBD, EstafetaBD estafetaBD, EntregaBD entregaBD) {
        this.farmaciaBD = farmaciaBD;
        this.encomendaBD = encomendaBD;
        this.moradaBD = moradaBD;
        this.droneBD = droneBD;
        this.scooterBD = scooterBD;
        this.estafetaBD = estafetaBD;
        this.entregaBD = entregaBD;
        this.caminhoTask = new CaminhoTask();
        this.estimarConsumoDroneTask = new EstimarConsumoDroneTask();
        this.estimarConsumoTask = new EstimarConsumoScooterTask();
        List<Morada> lm = new ArrayList<>();
        listaScooters = new ArrayList<>();
        listaDrones = new ArrayList<>();
        listaDronesCapazes = new ArrayList<>();
        listaEncomendas = new ArrayList<>();
        listaMoradas = new ArrayList<>();
        double[] array = new double[3];
        array[0] = Double.MAX_VALUE;
        pairDrone = new Pair<>(lm, array);

        pairScooter = new Pair<>(lm, array);
    }

    /**
     * Retorna a lista de encomendas da farmácia do gestor que ainda não foram atribuidas.
     *
     * @return lista de encomendas
     */
    public List<Encomenda> getEncomendas() {
        String email = AplicacaoPOT.getInstance().getSessaoAtual().getEmailUtilizador();
        Farmacia farm = farmaciaBD.getFarmaciabyGestor(email);
        idFarm = farm.getId();
        moradaFarmacia = moradaBD.getMoradaById(Integer.parseInt(farm.getMoradaId()));
        return encomendaBD.getEncomendasByFarmID(idFarm);
    }

    /**
     * Valida os dados introduzidos, cria a entrega e guarda na base de dados, juntamente com o caminho.
     *
     * @param encomendas a lista de encomendas selecionadas
     * @param estimativa true- se pretende o caminho mais eficiente; false- se pretende o caminho mais curto
     * @return um pair com o veículo escolhido para entrega e a estimativa do consumo
     * @throws Exception the exception
     */
    public Pair<Veiculo, Double> validaDados(List<Integer> encomendas, boolean estimativa) throws Exception {
        listaEncomendas = encomendaBD.getEncomendasByID(encomendas);
        validaEncomendas();
        listaMoradas = encomendaBD.getMoradasClientesByEncomendas(listaEncomendas);
        cargaTotal = calcularCargaTotal();
        listaDrones = droneBD.getAvailableDronesByFarmID(idFarm);
        listaDronesCapazes = getDronesCapazes();
        if (Double.compare(Constantes.cargaEstafeta, cargaTotal) > -1) {
            listaScooters = scooterBD.getAvailableScootersByFarmID(idFarm);
        }
        if (!listaDronesCapazes.isEmpty()) {
            for (Drone drone1 : listaDronesCapazes) {
                Pair<List<Morada>, double[]> p = caminhoTask.caminho(estimativa, moradaFarmacia, new ArrayList<>(listaMoradas), cargaTotal, drone1, true, moradaBD);
                if (p.getValue()[0] < pairDrone.getValue()[0]) {
                    pairDrone = p;
                    this.drone = drone1;
                }
            }
        }
        if (!listaScooters.isEmpty()) {
            for (Scooter scooter1 : listaScooters) {
                Pair<List<Morada>, double[]> p2 = caminhoTask.caminho(estimativa, moradaFarmacia, new ArrayList<>(listaMoradas), cargaTotal, scooter1, false, moradaBD);
                if (p2.getValue()[0] < pairScooter.getValue()[0]) {
                    pairScooter = p2;
                    this.scooter = scooter1;
                }
            }
        }
        if (scooter == null && drone == null) {
            return null;
        }
        Veiculo veiculo = getVeiculoMaisEficiente();
        Entrega entrega;
        String emailEstafeta="";
        if (veiculo instanceof Scooter) {
            emailEstafeta = estafetaBD.getEstafetaAvailable(idFarm);
            entrega = entregaBD.criarEntrega(cargaTotal, distancia, consumo, veiculo.getId(), emailEstafeta);
        } else {
            entrega = entregaBD.criarEntrega(cargaTotal, distancia, consumo, veiculo.getId());
        }

        List<Morada> caminhoFinal=entregaBD.getMoradasByCaminho(caminho);
        entregaBD.setCaminho(entrega.getId(), caminhoFinal);
        encomendaBD.setIDEntrega(listaEncomendas, entrega);

        if (estimativa) {
            OutputFileWriter.novaUC("Estimar consumo para caminho mais eficiente");
        } else {
            OutputFileWriter.novaUC("Estimar consumo para caminho mais curto/rápido");
        }


        if (veiculo instanceof Drone) {
            droneBD.setEstadoVeiculo(veiculo.getId(),3);
            OutputFileWriter.write(estimarConsumoDroneTask.info((Drone) veiculo, cargaTotal, caminho).toString());
        } else {
            scooterBD.setEstadoVeiculo(veiculo.getId(),3);
            estafetaBD.setEstadoEstafeta(emailEstafeta,2);
            OutputFileWriter.write(estimarConsumoTask.info(estimativa, veiculo.getMassa(), cargaTotal, caminho).toString());
        }

        return new Pair<>(veiculo, consumo);

    }

    /**
     * Retorna o veiculo mais eficiente tendo em conta o consumo e no caso destes serem iguais compara o tempo que demora a fazer o percurso
     *
     *
     * @return the veiculo mais eficiente
     */
    protected Veiculo getVeiculoMaisEficiente() {
        if (pairDrone.getValue()[0] < pairScooter.getValue()[0]) {
            caminho = pairDrone.getKey();
            consumo = pairDrone.getValue()[0];
            distancia = pairDrone.getValue()[2];
            return drone;
        } else {
            if (pairDrone.getValue()[0] > pairScooter.getValue()[0]) {
                caminho = pairScooter.getKey();
                consumo = pairScooter.getValue()[0];
                distancia = pairScooter.getValue()[2];
                return scooter;
            } else {
                if (pairDrone.getValue()[1] < pairScooter.getValue()[1]) {
                    caminho = pairDrone.getKey();
                    consumo = pairDrone.getValue()[0];
                    distancia = pairDrone.getValue()[2];
                    return drone;
                } else {
                    if (pairDrone.getValue()[1] > pairScooter.getValue()[1]) {
                        caminho = pairScooter.getKey();
                        consumo = pairScooter.getValue()[0];
                        distancia = pairScooter.getValue()[2];
                        return scooter;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calcula a carga total da entrega
     *
     * @return the double
     */
    protected double calcularCargaTotal() {
        double cargaTotal1 = 0;
        for (Encomenda encomenda : listaEncomendas) {
            cargaTotal1 += encomenda.getCarga();
        }
        return cargaTotal1;
    }

    /**
     * Retorna os drones que conseguem fazer esta entrega
     *
     * @return the drones capazes
     */
    protected List<Drone> getDronesCapazes() {
        List<Drone> dronesCapazes = new ArrayList<>();
        for (Drone drone2 : listaDrones) {
            if (Double.compare(drone2.getCargaMaxima(), cargaTotal) > -1) {
                dronesCapazes.add(drone2);
            }
        }
        return dronesCapazes;
    }

    /**
     * Valida se as encomendas selecionadas pertencem à farmácia do gestor
     */
    protected void validaEncomendas(){
        for(Encomenda encomenda: listaEncomendas){
            if(encomenda.getIdFarmacia()!=idFarm){
                throw new IllegalArgumentException("Encomenda invalida- Esta encomenda pertence a outra farmacia");
            }
        }
    }

}
