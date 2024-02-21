package lapr.project.utils;

public class Constantes {

    private Constantes() {
    }

    /**
     * Constante ID papel admin
     */
    public static final int PAPEL_ADMIN = 0;

    /**
     * Constante ID papel gestor farmacia
     */
    public static final int PAPEL_GESTOR_FARMACIA = 1;

    /**
     * Constante ID papel cliente
     */
    public static final int PAPEL_CLIENTE = 2;

    /**
     * Constante ID papel estafeta
     */
    public static final int PAPEL_ESTAFETA = 3;

    /**
     * Constante velocidade da scooter usada para calculos
     */
    public static final int VELOCIDADE_SCOOTER = 30;

    /**
     * Constante taxa de entrega
     */
    public static final int TAXA_ENTREGA = 0;

    /**
     * Constante carga maxima do estafeta
     */
    public static int cargaEstafeta = 50;

    /**
     * Constante email da aplicacao
     */
    public static final String EMAIL = "g20.lapr3.2020.2021.testes@gmail.com";

    /**
     * Constante percentagem de ganhos de creditos de acordo com o dinheiro
     * gasto
     */
    public static final int PERCENTAGEM_CREDITOS = 10;

    /**
     * Constante vento longitudinal para o drone
     */
    public static double ventoxDrone;

    /**
     * Constante vento latitudinal para o drone
     */
    public static double ventoyDrone;

    /**
     * Constante vento longitudinal para a scooter
     */
    public static double ventoxScooter;

    /**
     * Constante vento latitudinal para a scooter
     */
    public static double ventoyScooter;

    /**
     * Constante path para o ficheiro com os parametros da aplicacao
     */
    public static final String PARAMS_FICHEIRO = "src/main/resources/application.properties";

    /**
     * Constante path para os parametros da base de dados url
     */
    public static final String PARAMS_BASEDADOS_URL = "database.url";

    /**
     * Constante path para o ficheiro com o username para a base de dados
     */
    public static final String PARAMS_BASEDADOS_USERNAME = "database.username";

    /**
     * Constante path para o ficheiro com a password para a base de dados
     */
    public static final String PARAMS_BASEDADOS_PASS = "database.password";

    /**
     * Constante path para o ficheiro com as moradas gerais
     */
    public static final String FICHEIRO_MORADA_NOME = "src/main/resources/moradas.txt";

    /**
     * Constante path para o ficheiro de testes com as moradas de teste
     */
    public static final String FICHEIRO_MORADA_NOME_TESTE = "src/test/resources/moradasTeste.txt";

    /**
     * Constante path para o ficheiro com as ligacoes terrestres gerais
     */
    public static final String FICHEIRO_LIGACOES_TERRESTRE_NOME = "src/main/resources/ligacoes_terrestre.txt";

    /**
     * Constante path para o ficheiro com as ligacoes terrestres de testes
     */
    public static final String FICHEIRO_LIGACOES_TERRESTRE_NOME_TESTE = "src/test/resources/ligacoes_terrestreTeste.txt";

    /**
     * Constante path para o ficheiro com as ligacoes aereas gerais
     */
    public static final String FICHEIRO_LIGACOES_AEREO_NOME = "src/main/resources/ligacoes_aereo.txt";

    /**
     * Constante path para o ficheiro com as ligacoes aereas de testes
     */
    public static final String FICHEIRO_LIGACOES_AEREO_NOME_TESTE = "src/test/resources/ligacoes_aereoTeste.txt";

    /**
     * Constante path para o ficheiro com a informacao dos ventos gerais
     */
    public static final String FICHEIRO_VENTO = "src/main/resources/ventos.txt";

    /**
     * Constante path para o ficheiro com a informacao dos ventos dos testes
     */
    public static final String FICHEIRO_VENTO_TESTE = "src/test/resources/ventosTeste.txt";

    /**
     * Constante path para o diretorio com a informacao dos veiculos
     */
    public static final String PATH = "src\\main\\resources\\VeiculoInfo";

    /**
     * Constante path para o diretorio com todos os ficheiros relacionados ao
     * programa desenvolvido em C
     */
    public static final String PATHC = "src\\main\\resources\\C";

    /**
     * Constante para o max excedente
     */
    public static final int MAX_EXCEDENTE = 5;

    /**
     * Constante para a potencia dos carregadores das scooters
     */
    public static final int POTENCIA_CARREGADOR_SCOOTER = 10000;

    /**
     * Constante para a potencia dos carregadores dos drones
     */
    public static final int POTENCIA_CARREGADOR_DRONE = 100;

    /**
     * Constante para a velocidade dos drones usada nas estimativas simples
     */
    public static final int VELOCIDADE_DRONE = 8;

    /**
     * atribui um valor à constante do vento longitudinal do drone
     *
     * @param ventoxDrone vento longitudinal
     */
    public static void setVentoxDrone(double ventoxDrone) {
        Constantes.ventoxDrone = ventoxDrone;
    }

    /**
     * atribui um valor à constante do vento latitudinal do drone
     *
     * @param ventoyDrone vento latitudinal
     */
    public static void setVentoyDrone(double ventoyDrone) {
        Constantes.ventoyDrone = ventoyDrone;
    }

    /**
     * atribui um valor à constante do vento longitudinal da scooter
     *
     * @param ventoxScooter vento longitudinal
     */
    public static void setVentoxScooter(double ventoxScooter) {
        Constantes.ventoxScooter = ventoxScooter;
    }

    /**
     * atribui um valor à constante do vento latitudinal da scooter
     *
     * @param ventoyScooter vento latitudinal
     */
    public static void setVentoyScooter(double ventoyScooter) {
        Constantes.ventoyScooter = ventoyScooter;
    }

    /**
     * atribui um valor à carga maxima do estafeta. metodo utilizado por causa
     * dos diversos cenários e da mudança da carga máxima dos estafetas
     *
     * @param cargaEstafeta carga maxima
     */
    public static void setCargaEstafeta(int cargaEstafeta) {
        Constantes.cargaEstafeta = cargaEstafeta;
    }
}
