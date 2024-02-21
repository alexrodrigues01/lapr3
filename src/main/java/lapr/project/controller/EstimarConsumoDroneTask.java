package lapr.project.controller;

import java.util.List;
import lapr.project.model.Drone;
import lapr.project.model.Morada;
import lapr.project.utils.Constantes;

/**
 * classe responsável por estimar o consumo energetico de um drone para um determinado percurso
 */
public class EstimarConsumoDroneTask {
    /**
     * valor da aceleraçao gravitica
     */
    private static final double ACELERACAO_GRAVITICA = 9.80665;
    /**
     * drone para efeitos de estimativa de geração do grafo
     */
    private static final Drone droneEstimado = new Drone(1,10,10,1,10,2,5,3,10,100,3,0.5,3);
    /**
     * massa estimada de uma possível entrega para efeitos de estimativa simples
     */
    private static final double ENTREGA_MASSA_ESTIMADA = 3;
    /**
     * variável que armazena as informações cientificas do custo energetico de um determinado percurso para escrever em texto
     */
    private StringBuilder printable;

    /**
     * construtor vazio
     */
    public EstimarConsumoDroneTask() {
        //vazio
    }

    /**
     * metodo principal que estima o consumo energetico do drone em Joule
     * @param drone drone selecionado
     * @param massaEntrega massa da entrega selcionada
     * @param percurso percurso determinado
     * @param print variavél de controlo que determina se a informação é impressa ou nao
     * @return consumo energetico do drone em Joule
     */
    public double estimarConsumoPercurso(Drone drone, double massaEntrega, List<Morada> percurso, boolean print) {
        double massaEntregaAux = massaEntrega;
        double energiaEstimada = 0;
        for (int i = 0; i < percurso.size() - 1; i++) {
            massaEntregaAux = massaEntregaAux * (1 / (double) (percurso.size() - 1));
            Morada m1 = percurso.get(i);
            Morada m2 = percurso.get(i + 1);
            if(print){
                printable.append("Troço: "+m1.getStringMorada()+" -> "+m2.getStringMorada()+"\n");
            }
            energiaEstimada += estimarConsumoViagem(drone, m1, m2, massaEntregaAux, print);
        }
        if(print){
            printable.append("Caminho: ");
            printable.append(percurso.get(0)+" -> ");
            for (int i = 1; i < percurso.size() - 1; i++){
                Morada m1 = percurso.get(i);
                printable.append(m1.getStringMorada()+" -> ");
            }
            Morada m2 = percurso.get(percurso.size()-1);
            printable.append(m2.getStringMorada());
            printable.append("\nEnergia Estimada Total: "+energiaEstimada+" J\n");
        }
        return energiaEstimada;
    }

    /**
     * estimativa do consumo energetico de um drone default para efeitos de criação do grafo
     * @param m1 primeira morada
     * @param m2 segunda morada
     * @return stimativa do consumo energetico de um drone default
     */
    public double estimarConsumoSimples(Morada m1, Morada m2) {
        double distanciaComVento = distanciaComVento(droneEstimado, m1,m2, false);

        double calculo1 = ((droneEstimado.getMassa() + ENTREGA_MASSA_ESTIMADA) * ACELERACAO_GRAVITICA) / (droneEstimado.getRotors() * droneEstimado.getDrag());
        double calculo2 = droneEstimado.getAvionics() / droneEstimado.getVelocidade();
        double calculo3 = calculo1 + calculo2;

        double result = distanciaComVento * calculo3;

        if(Double.compare(result,0) == -1){
            return 0;
        }
        return result;
    }

    /**
     * consumo energético entre duas moradas em joule
     * @param drone drone selecionado
     * @param m1 primeira morada
     * @param m2 segunda morada
     * @param massaEntrega massa da entrega selecionada
     * @param print variavél de controlo que determina se a informação é impressa ou nao
     * @return consumo energético entre duas moradas em joule
     */
    protected double estimarConsumoViagem(Drone drone, Morada m1, Morada m2, double massaEntrega, boolean print) {
        double massaTotal = drone.getMassa() + massaEntrega;
        double distanciaComVento = distanciaComVento(drone, m1, m2, print);

        double calculo1 = (massaTotal * ACELERACAO_GRAVITICA) / (drone.getRotors() * drone.getDrag());
        double calculo2 = drone.getAvionics() / drone.getVelocidade();
        double calculo3 = calculo1 + calculo2;

        if(print){
            printable.append("Massa Total: "+massaTotal+" kg\n");
            printable.append("Aceleração gravitica: "+ACELERACAO_GRAVITICA+"\n");
            printable.append("Rotors: "+drone.getRotors()+"\n");
            printable.append("Drag: "+drone.getDrag()+"\n");
            printable.append("Avionics: "+drone.getAvionics()+"\n");
            printable.append("Velocidade do Drone: "+drone.getVelocidade()+" km/h\n");
            printable.append("Energia Estimada: "+distanciaComVento * calculo3+" J\n");
            printable.append("\n");
        }

        return distanciaComVento * calculo3;
    }

    /**
     * calculo representativo da distancia sobre 1 - racio do headwind
     * @param drone drone selecionado
     * @param m1 primeira morada
     * @param m2 segunda morada
     * @param print variavél de controlo que determina se a informação é impressa ou nao
     * @return distancia sobre 1 - racio do headwind
     */
    protected double distanciaComVento(Drone drone, Morada m1, Morada m2, boolean print) {
        double distancia = m1.distanceFromNoHeight(m2);

        double[] vetorVento = new double[2];
        vetorVento[0] = Constantes.ventoxDrone * 3.6;
        vetorVento[1] = Constantes.ventoyDrone * 3.6;

        double[] vetorVelocidade = calcularVetor(drone.getVelocidade(), m1, m2);

        double headwind = produtoEscalar(vetorVelocidade, vetorVento) / drone.getVelocidade();

        double ratioHeadwind = headwind / drone.getVelocidade();

        double divisor = 1 - ratioHeadwind;

        if(print){
            printable.append("Distância: "+distancia+" m\n");
            printable.append("Headwind: "+headwind+"\n");
            printable.append("Headwind Ratio: "+ratioHeadwind+"\n");
        }

        return distancia / divisor;
    }

    /**
     * produto escalar entre dois vetores
     * @param vetor1 primeiro vetor
     * @param vetor2 segundo vetor
     * @return produto escalar entre dois vetores
     */
    protected double produtoEscalar(double[] vetor1, double[] vetor2) {
        double resultado = 0;
        for (int i = 0; i < vetor1.length; i++) {
            resultado += vetor1[i] * vetor2[i];
        }
        return resultado;
    }

    /**
     * cálculo de um vetor através do modulo da velocidade e duas moradas
     * @param modulo modulo da velocidade
     * @param m1 primeira morada
     * @param m2 segunda morada
     * @return vetor calculado
     */
    protected double[] calcularVetor(double modulo, Morada m1, Morada m2) {
        double[] vetor = new double[2];

        vetor[0] = m2.getLongitude() - m1.getLongitude();
        vetor[1] = m2.getLatitude() - m1.getLatitude();

        double k = Math.pow(Math.abs(vetor[0]), 2) + Math.pow(Math.abs(vetor[1]), 2);
        double i = Math.sqrt((modulo * modulo) / k);

        vetor[0] *= i;
        vetor[1] *= i;

        return vetor;
    }

    /**
     * stringbuilder com as informações cientificas do percurso
     * @param drone drone selecionado
     * @param massaEntrega massa da entrega selecionada
     * @param percurso percurso determinado
     * @return informações cientificas do percurso
     */
    public StringBuilder info(Drone drone, double massaEntrega, List<Morada> percurso){
        setPrintable(new StringBuilder());
        estimarConsumoPercurso(drone, massaEntrega, percurso,true);
        return printable;
    }

    /**
     * printable setter
     * @param printable variavel que guarda as informaçoes cientificas
     */
    protected void setPrintable(StringBuilder printable) {
        this.printable = printable;
    }
}
