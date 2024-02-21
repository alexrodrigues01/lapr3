package lapr.project.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lapr.project.model.Entrega;
import lapr.project.model.Morada;

/**
 * The type Entrega bd.
 */
public class EntregaBD {


    /**
     * Define o caminho , com a lista de moradas de uma entrega através do id da entrega
     *
     * @param id      the id da entrega
     * @param moradas lista de moradas da entrega
     * @return the caminho
     */
    public boolean setCaminho(int id, List<Morada> moradas) {
        try {
            Connection con = BaseDados.getConnection();
            int i = 1;
            for (Morada morada : moradas) {
                try(ResultSet rs = con.createStatement().executeQuery("INSERT INTO caminho(id_entrega,ordemEntrega,id_Morada) values (" + id + "," + i++ + "," + morada.getId() + ")")){}
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return false;
    }


    /**
     * Retorna a lista de entregas de um estafeta
     *
     * @param emailEstafeta the email estafeta
     * @return a lista de entregas
     */
    public List<Entrega> getEntregaByEstafeta(String emailEstafeta) {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM entrega where emailEstafeta=? and id_EstadoEntrega=1")){
                ps.setString(1,emailEstafeta);
                List<Entrega> lista= new ArrayList<>();
                try(ResultSet sr=ps.executeQuery()){
                    while(sr.next()) {
                        lista.add( new Entrega(sr.getInt("Id"), sr.getDouble("cargaTotal"), sr.getDouble("distanciaTotal"), sr.getDouble("consumoEstimado"),
                                sr.getString("emailEstafeta"), sr.getInt("id_EstadoEntrega"), sr.getInt("id_veiculo")));
                    }
                    return lista;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }

    /**
     * Retorna o id estado de uma entrega recebida por parâmetro
     *
     * @param id the id
     * @return the id estado entrega
     */
    public int getIdEstadoEntrega(int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet set = con.createStatement().executeQuery("SELECT idEstadoEntrega FROM entrega where id=" + id)){
                return set.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * Inicia uma entrega recebida por paramtetro
     *
     * @param idEntrega the id entrega
     */
    public void iniciarEntrega(int idEntrega) {
        try {
            Connection connection = BaseDados.getConnection();
            try(PreparedStatement ps = connection.prepareStatement("UPDATE Entrega SET id_EstadoEntrega= ? WHERE id=?")){
                ps.setDouble(1, 2);
                ps.setInt(2, idEntrega);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Cria uma entrega na base de dados com a informação recebida por parâmetro
     *
     * @param cargaTotal    the carga total
     * @param distancia     the distancia
     * @param consumo       the consumo
     * @param veiculo       the veiculo
     * @param emailEstafeta the email estafeta
     * @return the entrega
     */
    public Entrega criarEntrega(double cargaTotal, double distancia, double consumo, int veiculo, String emailEstafeta) {
        Entrega entrega= null;
        try {
            int idEntrega= lastIDEntrega()+1;
            entrega= new Entrega(idEntrega,cargaTotal,distancia,consumo,emailEstafeta,veiculo,3);
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps= con.prepareCall("INSERT INTO Entrega(id,cargaTotal,distanciaTotal,consumoEstimado,emailEstafeta,id_estadoEntrega,id_veiculo) values (?,?,?,?,?,?,?)")){
                ps.setInt(1,idEntrega);
                ps.setDouble(2,cargaTotal);
                ps.setDouble(3,distancia);
                ps.setDouble(4,consumo);
                ps.setString(5,emailEstafeta);
                ps.setInt(6,1);
                ps.setInt(7,veiculo);
                ps.executeUpdate();
                return entrega;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return entrega;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Retorna o id da ultima entrega adicionada
     *
     * @return id da entrega
     */
    public int lastIDEntrega() {
        try {
            Connection connect = BaseDados.getConnection();
            try(ResultSet rsett = connect.createStatement().executeQuery("SELECT MAX(id) FROM Entrega")){
                if (rsett.next()) {
                    return rsett.getInt(1);
                }else{
                    return 1;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * Cria uma entrega na base de dados com a informação recebida por parâmetro
     *
     * @param cargaTotal the carga total
     * @param distancia  the distancia
     * @param consumo    the consumo
     * @param veiculo    the veiculo
     * @return the entrega
     */
    public Entrega criarEntrega(double cargaTotal, double distancia, double consumo, int veiculo) {
        Entrega entregaa= null;
        try {
            int idEntregaa= lastIDEntrega()+1;
            entregaa= new Entrega(idEntregaa,cargaTotal,distancia,consumo,veiculo,3);
            Connection conn = BaseDados.getConnection();
            try(PreparedStatement ps= conn.prepareCall("INSERT INTO Entrega(id,cargaTotal,distanciaTotal,consumoEstimado,id_estadoEntrega,id_veiculo) values (?,?,?,?,?,?)")){
                ps.setInt(1,idEntregaa);
                ps.setDouble(2,cargaTotal);
                ps.setDouble(3,distancia);
                ps.setDouble(4,consumo);
                ps.setInt(5,1);
                ps.setInt(6,veiculo);
                ps.executeUpdate();
                return entregaa;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return entregaa;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     *Retorna a lista de entregas por fazer por drone de um estafeta
     *
     * @param email the email
     * @return a lista de entregas por fazer por drone
     */
    public List<Entrega> getEntregasToDoDrone(String email) {
        try {
            List<Entrega> lista = new ArrayList<>();
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps= con.prepareCall("SELECT * FROM entrega inner join encomenda on encomenda.id_entrega=entrega.id inner join farmacia on farmacia.id=encomenda.id_farmacia where emailEstafeta is null and id_EstadoEntrega=1 and farmacia.emailgestor like ?")){
                ps.setString(1,email);
                try(ResultSet rs =ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(new Entrega(rs.getInt("Id"), rs.getDouble("cargaTotal"), rs.getDouble("distanciaTotal"), rs.getDouble("consumoEstimado"),
                                rs.getString("emailEstafeta"), rs.getInt("id_EstadoEntrega"), rs.getInt("id_veiculo")));
                    }
                    return lista;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }

    /**
     * Valida o id de uma entrega por fazer por drone
     *
     * @param email the email
     * @param id    the id
     * @return true se sucesso, falso se insucesso
     */
    public boolean validaIdEntregaToDoDrone(String email,int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps= con.prepareCall("SELECT entrega.id FROM entrega inner join encomenda on encomenda.id_entrega=entrega.id inner join farmacia on farmacia.id=encomenda.id_farmacia where emailEstafeta is null and id_EstadoEntrega=1 and farmacia.emailgestor like ? and entrega.id=?")){
                ps.setString(1,email);
                ps.setInt(2,id);
                try(ResultSet rs =ps.executeQuery()) {
                    while (rs.next()) {
                        if(rs.getInt("id")==id){
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        throw new IllegalArgumentException("Id Entrega Invalido");
    }

    /**
     * Retorna a lista de moradas com id através do caminho
     *
     * @param caminho the caminho
     * @return a lista de moradas com id
     */
    public List<Morada> getMoradasByCaminho(List<Morada> caminho) {
        List<Morada> moradas= new ArrayList<>();
        try{
            Connection conn= BaseDados.getConnection();
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM Morada where morada like ?")){
                for(Morada m: caminho) {
                    ps.setString(1,m.getStringMorada());
                    try(ResultSet set =ps.executeQuery()){
                        if(!set.next())
                            throw new IllegalArgumentException("Morada não encontrada");
                        moradas.add(new Morada(set.getInt("id"),set.getString("morada"),set.getDouble("latitude"),set.getDouble("longitude"),set.getDouble("altitude")));
                    }

                }
            }
            return moradas;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDados.closeConnection();
        }
        return moradas;
    }

    /**
     * Valida id entrega estafeta
     *
     * @param email     the email
     * @param idEntrega the id entrega
     * @return true se sucesso, falso se insucesso
     */
    public boolean validaIdEntregaEstafeta(String email, int idEntrega) {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps= con.prepareCall("SELECT * FROM entrega where emailEstafeta=? and id_EstadoEntrega=1 and id=?")){
                ps.setString(1,email);
                ps.setInt(2,idEntrega);
                try(ResultSet rs=ps.executeQuery()){
                    while (rs.next()) {
                        if(rs.getInt("id")==idEntrega){
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        throw new IllegalArgumentException("Id Entrega Invalido");
    }

    /**
     * Retorna a lista de entregas por fazer por drone de um estafeta
     * @return a lista de entregas por fazer por drone
     */
    public List<Entrega> getEntregas() {
        try {
            List<Entrega> lista = new ArrayList<>();
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps= con.prepareCall("SELECT * FROM entrega")){
                try(ResultSet rs =ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(new Entrega(rs.getInt("id"), rs.getDouble("cargaTotal"), rs.getDouble("distanciaTotal"), rs.getDouble("consumoEstimado"),
                                rs.getString("emailEstafeta"), rs.getInt("id_EstadoEntrega"), rs.getInt("id_veiculo")));
                    }
                    return lista;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }
}
