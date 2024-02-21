package lapr.project.controller;

import java.util.List;
import lapr.project.model.Morada;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;

public class EstimarConsumoScooterTask {

    private static final double DENSIDADE_AR = 1.2041;                //(Kg/m^3) densidade do ar em condicoes CPTP (SI)
    private static final double RESISTENCIA_AR_COEFICIENTE = 1.1;      // Resistencia para um humano adulto em cima de uma scooter (SI)
    private static final double AREA_FRONTAL_SCOOTER_CONDUTOR = 0.3;      // (m^2) estimativa da area frontal do conjunto formado pela scooter e o estafeta (SI)
    private static final double MASSA_MEDIA_HUMANO = 80;               // (Kg) Peso medio para o estafeta (SI)
    private static final double ACELERACAO_GRAVITICA = 9.80665;       // (m/s^2) (SI)

    private StringBuilder printable;

    /**
     * Construtor
     */
    public EstimarConsumoScooterTask() {
        //vazio
    }

    /**
     * calcular o tempo de entrega com base na distancia e a velocidade media
     * das scooters
     *
     * @param distancia distancia a percorrer
     * @return tempo em segundos
     */
    protected double calcularTempoEntrega(double distancia) {
        return (distancia / ((double) (Constantes.VELOCIDADE_SCOOTER) * 1000 / 3600));
    }

    /**
     * calcular a velocidade da scooter em relacao a velocidade do vento
     *
     * @param m1 morada partida
     * @param m2 morada chegada
     * @param velocidadeSemVento velocidade do veiculo real
     * @return velocidade em relacao ao vento
     */
    public double calcularVelocidadeRelacaoVento(Morada m1, Morada m2, double velocidadeSemVento) {
        double ventoLatitude = Constantes.ventoyScooter;
        double ventoLongitude = Constantes.ventoxScooter;
        double cordY = m2.getLatitude() - m1.getLatitude();
        double cordX = m2.getLongitude() - m1.getLongitude();
        double anguloVetorMovimento = Math.atan2(cordY, cordX);
        double anguloVetorVento = Math.atan2(ventoLatitude, ventoLongitude);
        double moduloVetorVento = Math.sqrt(ventoLongitude * ventoLongitude + ventoLatitude * ventoLatitude);
        double cosAnguloEnTreVetores = Math.cos(anguloVetorVento - anguloVetorMovimento);
        return velocidadeSemVento - cosAnguloEnTreVetores * moduloVetorVento;
    }

    /**
     * metodo para calcular uma estimativa de consumo a partir de um caminho
     * dado
     *
     * @param estimativas caminho mais curto ou mais eficaz
     * @param massaScooter massa da scooter
     * @param massaEntrega massa das encomendas
     * @param caminho caminho a percorrer
     * @param print boolean se e para dar print ou nao aos calculos
     * @return estimativa de consumo
     */
    protected double calculoEstimativaConsumo(boolean estimativas, double massaScooter, double massaEntrega, List<Morada> caminho, boolean print) {
        double massa = massaScooter + MASSA_MEDIA_HUMANO + massaEntrega;
        double energiaEstimadaTotal = 0;
        double velocidadeSI = Constantes.VELOCIDADE_SCOOTER * (double) 1000 / 3600;
        Graph<Morada, Double> g;
        if (estimativas) {
            g = AplicacaoPOT.getInstance().getMapaTerrestreEstimativas();
        } else {
            g = AplicacaoPOT.getInstance().getMapaTerrestreDistancias();
        }
        for (int i = 0; i < caminho.size() - 1; i++) {
            Morada m1 = caminho.get(i);
            Morada m2 = caminho.get(i + 1);
            double velocidadeRelacaoVento = calcularVelocidadeRelacaoVento(m1, m2, velocidadeSI);
            double inclinacaoRuaRadianos = m1.getInclinacaoRuaRadianos(m2);
            double tempoEstimado = calcularTempoEntrega(m1.distanceFrom(m2));
            double forcaInclinacao = massa * ACELERACAO_GRAVITICA * Math.sin(inclinacaoRuaRadianos);
            double forcaAtrito = massa * ACELERACAO_GRAVITICA * g.getEdge(m1, m2).getElement() * Math.cos(inclinacaoRuaRadianos);
            double forcaResistenciaAr = (DENSIDADE_AR * RESISTENCIA_AR_COEFICIENTE * AREA_FRONTAL_SCOOTER_CONDUTOR * Math.pow(velocidadeRelacaoVento, 2)) / 2;
            double forcaTotal = forcaInclinacao + forcaAtrito + forcaResistenciaAr;
            double potenciaTotal = forcaTotal * velocidadeRelacaoVento;
            double energiaEstimada = potenciaTotal * tempoEstimado;
            if (print) {
                printable.append("Troço: " + m1.getStringMorada() + " -> " + m2.getStringMorada() + "\n");
                printable.append("Massa total: " + massa + " kg\n");
                printable.append("Velocidade Scooter: " + velocidadeSI + " m/s\n");
                printable.append("Velocidade em relação ao vento: " + velocidadeRelacaoVento + " m/s\n");
                printable.append("Inclinação da rua: " + inclinacaoRuaRadianos + " radianos\n");
                printable.append("Aceleração gravitica: " + ACELERACAO_GRAVITICA + "\n");
                printable.append("Densidade do ar: " + DENSIDADE_AR + " kg/m^3\n");
                printable.append("Coeficiente da resistência do ar: " + RESISTENCIA_AR_COEFICIENTE + "\n");
                printable.append("Area frontal da scooter: " + AREA_FRONTAL_SCOOTER_CONDUTOR + " m^2\n");
                printable.append("Força da inclinação: " + forcaInclinacao + " N\n");
                printable.append("Força da atrito: " + forcaAtrito + " N\n");
                printable.append("Força da resistência do Ar: " + forcaResistenciaAr + " N\n");
                printable.append("Força total: " + forcaTotal + " N\n");
                printable.append("Potência total: " + potenciaTotal + " W\n");
                printable.append("Tempo estimado: " + tempoEstimado + " s\n");
                printable.append("Energia Estimada: " + energiaEstimada + " J\n");
                printable.append("\n");
            }
            energiaEstimadaTotal += energiaEstimada;
        }
        if (print) {
            printable.append("Caminho: ");
            printable.append(caminho.get(0) + " -> ");
            for (int i = 1; i < caminho.size() - 1; i++) {
                Morada m1 = caminho.get(i);
                printable.append(m1.getStringMorada() + " -> ");
            }
            Morada m2 = caminho.get(caminho.size() - 1);
            printable.append(m2.getStringMorada());
            printable.append("\nEnergia Estimada Total: " + energiaEstimadaTotal + " J\n");
        }
        return energiaEstimadaTotal;
    }

    /**
     * estimativa do consumo energetico de uma scooter default para efeitos de
     * criação do grafo
     *
     * @param m1 morada partida
     * @param m2 morada chegada
     * @param coeficienteAtrito coeficiente de atrito
     * @return estimativa simples usada para o grafo
     */
    public double estimativaSimples(Morada m1, Morada m2, double coeficienteAtrito) {
        double distancia = m1.distanceFrom(m2);
        double tempoEstimado = calcularTempoEntrega(distancia);
        double inclinacaoRuaRadianos = m1.getInclinacaoRuaRadianos(m2);
        double forcaInclinacao = 90 * ACELERACAO_GRAVITICA * Math.sin(inclinacaoRuaRadianos);
        double forcaAtrito = 90 * ACELERACAO_GRAVITICA * coeficienteAtrito * Math.cos(inclinacaoRuaRadianos);
        double velocidadeSI = Constantes.VELOCIDADE_SCOOTER * (double) 1000 / 3600;
        double velocidadeRelacaoVento = calcularVelocidadeRelacaoVento(m1, m2, velocidadeSI);
        double forcaResistenciaAr = (DENSIDADE_AR * RESISTENCIA_AR_COEFICIENTE * AREA_FRONTAL_SCOOTER_CONDUTOR * Math.pow(velocidadeRelacaoVento, 2)) / 2;
        double forcaTotal = forcaInclinacao + forcaAtrito + forcaResistenciaAr;
        double potenciaTotal = forcaTotal * velocidadeRelacaoVento;
        return potenciaTotal * tempoEstimado;
    }

    /**
     * stringbuilder com as informações cientificas do percurso
     *
     * @param estimativas caminho mais curto ou mais eficaz
     * @param massaScooter massa da scooter
     * @param massaEntrega massa das encomendas
     * @param caminho caminho
     * @return info para dar print
     */
    public StringBuilder info(boolean estimativas, double massaScooter, double massaEntrega, List<Morada> caminho) {
        setPrintable(new StringBuilder());
        calculoEstimativaConsumo(estimativas, massaScooter, massaEntrega, caminho, true);
        return printable;
    }

    /**
     * printable setter
     *
     * @param printable variavel que guarda as informaçoes cientificas
     */
    protected void setPrintable(StringBuilder printable) {
        this.printable = printable;
    }
}
