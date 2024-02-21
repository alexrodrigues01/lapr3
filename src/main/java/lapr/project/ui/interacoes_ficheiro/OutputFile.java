package lapr.project.ui.interacoes_ficheiro;

import lapr.project.data.*;
import lapr.project.model.*;

import java.util.List;

public class OutputFile {

    /**
     * construtor privado do output file
     */
    private OutputFile() {
    }

    /**
     * escreve o output num text file
     */
    public static void writeOutput(){
        OutputFileWriter.novaUC("Farmácias");
        FarmaciaBD farmaciaBD= new FarmaciaBD();
        List<Farmacia> farmacias= farmaciaBD.getFarmacias();
        StringBuilder stringBuilder = new StringBuilder();
        for (Farmacia f: farmacias){
            stringBuilder.append(f.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Scooters");
        ScooterBD scooterBD= new ScooterBD();
        List<Scooter> scooters= scooterBD.getListaScooter();
        stringBuilder = new StringBuilder();
        for (Scooter f: scooters){
            stringBuilder.append(f.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Drones");
        DroneBD droneBD= new DroneBD();
        List<Drone> drones= droneBD.getListaDrones();
        stringBuilder = new StringBuilder();
        for (Drone f: drones){
            stringBuilder.append(f.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Clientes");
        ClienteBD clienteBD= new ClienteBD();
        List<Cliente> clientes= clienteBD.getListaClientes();
        stringBuilder = new StringBuilder();
        for (Cliente f: clientes){
            stringBuilder.append(f.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Produtos");
        ProdutoBD produtoBD= new ProdutoBD();
        List<Produto> produtos= produtoBD.getTodosProdutos();
        stringBuilder = new StringBuilder();
        for (Produto p: produtos){
            stringBuilder.append(p.toString()+String.format("%nPreço: %.2f€%nMassa: %d gramas%nIVA: %d%%%n%n",p.getPrecoUnitario(),p.getPesoUnitario(),p.getIva()));
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Estafetas");
        EstafetaBD estafetaBD= new EstafetaBD();
        List<Estafeta> estafetas= estafetaBD.getEstafetas();
        stringBuilder = new StringBuilder();
        for (Estafeta e: estafetas){
            stringBuilder.append(e.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Moradas");
        MoradaBD moradaBD= new MoradaBD();
        List<Morada> moradas= moradaBD.getMoradas();
        stringBuilder = new StringBuilder();
        for (Morada m: moradas){
            stringBuilder.append( String.format("Morada: %s%nLatitude: %.15f%nLongitude: %.15f%nAltitude: %.1f%n%n",m.getStringMorada(),m.getLatitude(),m.getLongitude(),m.getAltitude()));
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Encomendas");
        EncomendaBD encomendaBD= new EncomendaBD();
        List<Encomenda> encomendas= encomendaBD.getEncomendas();
        stringBuilder = new StringBuilder();
        for (Encomenda e: encomendas){
            stringBuilder.append(e.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Faturas");
        FaturaBD faturaBD= new FaturaBD();
        List<Fatura> faturas= faturaBD.getFaturas();
        stringBuilder = new StringBuilder();
        for (Fatura e: faturas){
            stringBuilder.append(e.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Notas");
        NotaBD notaBD= new NotaBD();
        List<String> notas= notaBD.getNotas();
        stringBuilder = new StringBuilder();
        for (String e: notas){
            stringBuilder.append(e+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());

        OutputFileWriter.novaUC("Entregas");
        EntregaBD entregaBD= new EntregaBD();
        List<Entrega> entregas= entregaBD.getEntregas();
        stringBuilder = new StringBuilder();
        for (Entrega e: entregas){
            stringBuilder.append(e.toString()+"\n");
        }
        OutputFileWriter.write(stringBuilder.toString());
    }
}
