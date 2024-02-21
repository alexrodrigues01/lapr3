/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lapr.project.data.BaseDados;
import lapr.project.data.MoradaBD;
import lapr.project.model.Morada;
import lapr.project.model.autorizacao.AutorizacaoFacade;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;
import lapr.project.utils.Pair;
import lapr.project.utils.PreencherGrafo;

public class AplicacaoPOT {

    private final AutorizacaoFacade autorizacaoFacade;
    private Graph<Morada, Double> mapaTerrestreEstimativas;
    private Graph<Morada, Double> mapaTerrestreDistancias;
    private Graph<Morada, Double> mapaAereoEstimativas;
    private Graph<Morada, Double> mapaAereoDistancias;

    private AplicacaoPOT() {
        Properties props = getProperties();
        this.autorizacaoFacade = new AutorizacaoFacade();
        BaseDados.setJdbcUrl(props.getProperty(Constantes.PARAMS_BASEDADOS_URL));
        BaseDados.setUsername(props.getProperty(Constantes.PARAMS_BASEDADOS_USERNAME));
        BaseDados.setPassword(props.getProperty(Constantes.PARAMS_BASEDADOS_PASS));
    }

    public SessaoUtilizador getSessaoAtual() {
        return this.autorizacaoFacade.getSessaoAtual();
    }

    public Graph<Morada, Double> getMapaTerrestreEstimativas() {
        return mapaTerrestreEstimativas;
    }

    public Graph<Morada, Double> getMapaTerrestreDistancias() {
        return mapaTerrestreDistancias;
    }

    public Graph<Morada, Double> getMapaAereoEstimativas() {
        return mapaAereoEstimativas;
    }

    public Graph<Morada, Double> getMapaAereoDistancias() {
        return mapaAereoDistancias;
    }

    public void setMapaAereoDistancias(Graph<Morada, Double> mapaAereoDistancias) {
        this.mapaAereoDistancias = mapaAereoDistancias;
    }

    public void setMapaAereoEstimativas(Graph<Morada, Double> mapaAereoEstimativas) {
        this.mapaAereoEstimativas = mapaAereoEstimativas;
    }

    public boolean doLogin(String email, String strPwd) {
        return this.autorizacaoFacade.doLogin(email, strPwd) != null;
    }

    public void doLogout() {
        this.autorizacaoFacade.doLogout();
    }
    private Properties getProperties() {
        Properties props = new Properties();

        // Lê as propriedades e valores definidas 
        try {
            InputStream in = new FileInputStream(Constantes.PARAMS_FICHEIRO);
            props.load(in);
            in.close();
        } catch (IOException ex) {

        }
        return props;
    }

    private static AplicacaoPOT singleton = null;

    public static AplicacaoPOT getInstance() {
        if (singleton == null) {
            synchronized (AplicacaoPOT.class) {
                singleton = new AplicacaoPOT();
            }
        }
        return singleton;
    }

    public AutorizacaoFacade getAutorizacaoFacade() {
        return autorizacaoFacade;
    }

    public void setMapaTerrestreEstimativas(Graph<Morada, Double> mapa) {
        this.mapaTerrestreEstimativas = mapa;
    }

    public void setMapaTerrestreDistancias(Graph<Morada, Double> mapa) {
        this.mapaTerrestreDistancias = mapa;
    }

    public void setVento() {
        PreencherGrafo.getVento(Constantes.FICHEIRO_VENTO);
    }

    public void setGrafos(MoradaBD moradaBD,String moradas, String ligacoesTerrestre, String ligacoesAereas) {
        PreencherGrafo.setMoradaBD(moradaBD);
        this.mapaTerrestreDistancias = PreencherGrafo.lerMoradas(moradas);
        this.mapaTerrestreEstimativas = mapaTerrestreDistancias.clone();
        this.mapaAereoDistancias = mapaTerrestreDistancias.clone();
        this.mapaAereoEstimativas = mapaTerrestreDistancias.clone();

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> grafos = PreencherGrafo.lerLigacoes(ligacoesTerrestre, mapaTerrestreEstimativas, mapaTerrestreDistancias, false);
        mapaTerrestreEstimativas = grafos.getKey();
        mapaTerrestreDistancias = grafos.getValue();

        grafos = PreencherGrafo.lerLigacoes(ligacoesAereas, mapaAereoEstimativas, mapaAereoDistancias, true);
        mapaAereoEstimativas = grafos.getKey();
        mapaAereoDistancias = grafos.getValue();

    }
}
