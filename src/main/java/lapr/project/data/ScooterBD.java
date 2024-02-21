package lapr.project.data;

import com.google.zxing.NotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.zxing.WriterException;
import lapr.project.model.Scooter;
import lapr.project.utils.Pair;
import lapr.project.utils.QRCode;

/**
 * The type Scooter bd.
 */
public class ScooterBD {

    private static final String CARGA = "carga";
    private static final String CAPACIDADE = "capacidadeBateria";
    private static final String MASSA = "massa";
    private static final String POTENCIA = "potenciaMotor";

    /**
     * Insere uma scooter na base de dados e as informações relacionadas
     *
     * @param scooter scooter
     * @return true se sucesso, falso se insucesso
     * @throws NotFoundException excecao
     */
    public boolean updateScooter(Scooter scooter) throws NotFoundException {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps= con.prepareCall("insert into Veiculo (id,carga,capacidadeBateria,id_estadoVeiculo,consumoHoraBateria,tensaoBateria,massa,potenciaMotor) values (?,?,?,?,?,?,?,?)")){
                ps.setInt(1,scooter.getId());
                ps.setInt(2, scooter.getCarga());
                ps.setDouble(3, scooter.getCapacidadeBateria());
                ps.setInt(4, scooter.getEstadoScooterID());
                ps.setDouble(5, scooter.getConsumoHoraBateria());
                ps.setDouble(6, scooter.getTensaoBateria());
                ps.setDouble(7, scooter.getMassa());
                ps.setDouble(8, scooter.getPotenciaMotor());
                ps.executeUpdate();
                try(PreparedStatement statement=con.prepareCall("INSERT into Scooter (id,qrCode) values (?,?)")){
                    statement.setInt(1,scooter.getId());
                    statement.setString(2, QRCode.convertToText(scooter.getQrCode()));
                    statement.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }


    /**
     * Retorna a lista de scooters.
     *
     * @return a lista de scooters
     */
    public List<Scooter> getListaScooter() {
        List<Scooter> listaScooter = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            String sql="SELECT * FROM Veiculo inner join Scooter on Veiculo.id=Scooter.id";
            try(Statement stmt = con.createStatement()){
                try(ResultSet rs=stmt.executeQuery(sql)){
                    while (rs.next()) {
                       listaScooter.add(new Scooter(rs.getInt("id"),rs.getInt(CARGA),rs.getDouble(CAPACIDADE),1,rs.getDouble("consumoHoraBateria"),rs.getDouble("tensaoBateria"),rs.getDouble(MASSA),rs.getDouble(POTENCIA)));
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();

        }
        return listaScooter;
    }


    /**
     * Retorna informação de uma scooter por ID
     *
     * @param scooterID the scooter id
     * @return the scooter by id
     */
    public String getScooterByID(int scooterID) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Veiculo WHERE Veiculo.id=" + scooterID)){
                if (rs != null && rs.next()) {
                    return "ID da scooter: " + rs.getInt("id") + "\nCapacidade da bateria: " + rs.getDouble(CAPACIDADE) + "\nCarga: " + rs.getInt(CARGA);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * Remove uma scooter da base dados através do id da scooter
     *
     * @param scooterID the scooter id
     * @return true se sucesso, falso se insucesso
     */
    public  boolean removerScooter(int scooterID) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs=con.createStatement().executeQuery("UPDATE LugarEstacionamento SET id_veiculo= null WHERE id_veiculo="+scooterID)){
                try(ResultSet re = con.createStatement().executeQuery("DELETE FROM Scooter WHERE Scooter.id=" + scooterID)){
                    try(ResultSet resultSet=con.createStatement().executeQuery("DELETE FROM Veiculo WHERE Veiculo.id="+scooterID)){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Retorna uma lista com os dados da scooter através de um id
     *
     * @param scooterID the scooter id
     * @return lista de dados da scooter
     */
    public  List<String> getDadosScooterbyID(int scooterID) {
        List<String> dadosScooter = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Veiculo WHERE Veiculo.id=" + scooterID)){
                if (rs.next()) {
                    dadosScooter.add(rs.getInt("id") + "");
                    dadosScooter.add(rs.getInt(CARGA) + "");
                    dadosScooter.add(rs.getDouble(CAPACIDADE) + "");
                    dadosScooter.add(rs.getInt("id_estadoVeiculo") + "");
                    dadosScooter.add(rs.getDouble("consumoHoraBateria") + "");
                    dadosScooter.add(rs.getDouble("tensaoBateria") + "");
                    dadosScooter.add(rs.getDouble(MASSA) + "");
                    dadosScooter.add(rs.getDouble(POTENCIA) + "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return dadosScooter;
    }

    /**
     * Update dados da scooter
     *
     * @param id                 the id
     * @param carga              the carga
     * @param capacidadeBateria  the capacidade bateria
     * @param estadoScooterID    the estado scooter id
     * @param consumoHoraBateria the consumo hora bateria
     * @param tensaoBateria      the tensao bateria
     * @param peso               the peso
     * @param potenciaMotor      the potencia motor
     * @return true se sucesso, falso se insucesso
     */
    public boolean updateDados(String id, String carga, String capacidadeBateria, String estadoScooterID, String consumoHoraBateria, String tensaoBateria, String peso, String potenciaMotor) {
        try {
            Connection connection = BaseDados.getConnection();
            try(ResultSet r = connection.createStatement().executeQuery("UPDATE Veiculo SET Veiculo.carga=" + carga + ", Veiculo.capacidadeBateria=" + capacidadeBateria + ",Veiculo.id_estadoVeiculo=" + estadoScooterID + ",Veiculo.consumoHoraBateria=" + consumoHoraBateria + ",Veiculo.tensaoBateria=" + tensaoBateria + ",Veiculo.massa=" + peso + ",Veiculo.potenciaMotor=" + potenciaMotor + " WHERE Veiculo.id=" + id)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Retorna o id da ultima scooter adiciona à base dados
     *
     * @return the int
     */
    public int lastID() {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rset = con.createStatement().executeQuery("SELECT MAX(id) FROM Veiculo")){
                if (rset.next()) {
                    return rset.getInt(1);
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
     * Retorna a carga da bateria de uma scooter através do id
     *
     * @param idScooter the id scooter
     * @return carga da bateria da scooter
     */
    public static int getBateriaScooterById(int idScooter) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Veiculo where id=" + idScooter)){
                return rs.getInt(CARGA);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * Retorna a capacidade da bateria de uma scooter através do id da scooter
     *
     * @param idScooter the id scooter
     * @return capacidade da bateria da scooter
     */
    public int getCapacidadeScooterById(int idScooter) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Veiculo where Veiculo.id=" + idScooter)){
                if(rs.next())
                    return rs.getInt(CAPACIDADE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * Retorna as scooters disponíveis através de uma lista com id s de scooters
     *
     * @param listaScooterID the lista scooter id
     * @return lista de scooters disponiveis
     */
    public List<Scooter> getAvailableScooters(List<Integer> listaScooterID) {
        List<Scooter> listaScooter = new ArrayList<>();
        try {
            for (Integer scooterID : listaScooterID) {
                Connection connection = BaseDados.getConnection();
                try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM Veiculo WHERE id like ? AND id_estadoVeiculo=2")){
                    ps.setInt(1, scooterID);
                    try(ResultSet result = ps.executeQuery()){
                        while (result.next()) {
                            listaScooter.add(new Scooter(result.getInt("id"), result.getDouble(CAPACIDADE), result.getDouble(POTENCIA)));
                        }
                    }
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return listaScooter;
    }

    /**
     * Retorna a massa de uma scooter através do id
     *
     * @param id the id
     * @return Massa da scooter
     */
    public double getMassaScooterByID(int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet r = con.createStatement().executeQuery("SELECT massa FROM Veiculo where id=" + id)){
                return r.getDouble(1);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * Verifica , através de um id recebido, se esse veículo é uma scooter
     *
     * @param id the id
     * @return the boolean
     */
    public boolean isScooter(int id) {
        try{
            Connection con = BaseDados.getConnection();
            try(ResultSet r = con.createStatement().executeQuery("SELECT * FROM Veiculo inner join Scooter on Veiculo.id=Scooter.id where Veiculo.id=" + id)){
                if(r.next()){
                    return true;
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * Atualiza a carga de uma scooter através do id
     *
     * @param carga the carga
     * @param id    the id
     * @return true se sucesso, falso se insucesso
     */
    public boolean atualizarCarga(int carga,int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet s = con.createStatement().executeQuery("UPDATE Veiculo SET veiculo.carga="+carga +" WHERE id="+id)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Retorna a capacidade e o parque do veiculo através do id da scooter
     *
     * @param id the id
     * @return Pair com a capacidade e parque da scooter
     */
    public Pair<Double,Integer> getCapacidadeAndParqueByIdVeiculo(int id) {
        try{
            Connection con = BaseDados.getConnection();
            try(ResultSet re = con.createStatement().executeQuery("SELECT ParqueEstacionamento.capacidadeEnergia,ParqueEstacionamento.id FROM ParqueEstacionamento inner join LugarEstacionamento on ParqueEstacionamento.id=LugarEstacionamento.id_parqueEstacionamento where LugarEstacionamento.id_veiculo=" + id)){
                if(re.next()){
                    return new Pair<>(re.getDouble("capacidadeEnergia"),re.getInt("id"));
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * Retorna a lista de scooters de uma farmácia através do seu id
     *
     * @param idFarm the id farm
     * @return the scooters by farm id
     */
    public List<Scooter> getScootersByFarmID(int idFarm) {
        try {
            ArrayList<Scooter> lista=new ArrayList<>();
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("select * from veiculo inner join Scooter on Scooter.id=veiculo.id " +
                    "inner join LugarEstacionamento on LugarEstacionamento.id_veiculo=veiculo.id " +
                    "inner join ParqueEstacionamento on ParqueEstacionamento.id=LugarEstacionamento.id_parqueEstacionamento " +
                    "inner join Farmacia on ParqueEstacionamento.id_farmacia=Farmacia.id " +
                    "where Farmacia.id=" + idFarm)){
                while(rs.next()) {
                    lista.add( new Scooter(rs.getInt("id"),rs.getInt(CARGA),rs.getDouble(CAPACIDADE),rs.getInt("id_estadoveiculo")
                            ,rs.getDouble("consumohorabateria"),rs.getDouble("tensaobateria"),rs.getDouble(MASSA),rs.getDouble(POTENCIA)));
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }

            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }

    /**
     * Altera o estado do veículo (id recebido por parâmetro) para o recebido por parâmetro
     *
     * @param id     the id
     * @param estado the estado de carregamento
     * @return true se sucesso, falso se insucesso
     */
    public boolean setACarregar(int id,int estado) {
        try{
            Connection con = BaseDados.getConnection();
            try(ResultSet s = con.createStatement().executeQuery("UPDATE Veiculo SET veiculo.id_estadoVeiculo="+estado +" WHERE id="+id)){
                return true;
            }
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * Retorna a lista de scooters disponíves de uma farmácia através do id
     *
     * @param idFarm the id farm
     * @return lista de scooters disponíveis
     */
    public List<Scooter> getAvailableScootersByFarmID(int idFarm) {
        try {
            ArrayList<Scooter> listaS=new ArrayList<>();
            Connection connec = BaseDados.getConnection();
            try(ResultSet rs = connec.createStatement().executeQuery("select * from veiculo inner join Scooter on Scooter.id=veiculo.id " +
                    "inner join LugarEstacionamento on LugarEstacionamento.id_veiculo=veiculo.id " +
                    "inner join ParqueEstacionamento on ParqueEstacionamento.id=LugarEstacionamento.id_parqueEstacionamento " +
                    "inner join Farmacia on ParqueEstacionamento.id_farmacia=Farmacia.id " +
                    "where Farmacia.id=" + idFarm+ " AND Veiculo.id_estadoVeiculo=2 ")){
                while(rs.next()) {
                    listaS.add( new Scooter(rs.getInt("id"),rs.getInt(CARGA),rs.getDouble(CAPACIDADE),rs.getInt("id_estadoveiculo")
                            ,rs.getDouble("consumohorabateria"),rs.getDouble("tensaobateria"),rs.getDouble(MASSA),rs.getDouble(POTENCIA)));
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }

            return listaS;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }

    /**
     * Define o estado do veiculo (id recebido por parametro) para o estado recebido por parametro
     *
     * @param veiculo the veiculo
     * @param i       o estado do veiculo
     * @return true se sucesso, falso se insucesso
     */
    public boolean setEstadoVeiculo(int veiculo, int i) {
        try {
            Connection conn = BaseDados.getConnection();
            try(ResultSet rset = conn.createStatement().executeQuery("UPDATE Veiculo SET id_estadoVeiculo="+i+" WHERE id="+veiculo)){
            }
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return false;
    }
    }

