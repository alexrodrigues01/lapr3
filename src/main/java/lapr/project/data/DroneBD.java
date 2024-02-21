package lapr.project.data;

import lapr.project.model.Drone;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class dronebd
 */
public class DroneBD {
    private static final String CARGA = "carga";
    private static final String CAPACIDADE = "capacidadeBateria";
    private static final String MASSA = "massa";
    private static final String ROTORS = "rotors";
    private static final String VELOCIDADE = "velocidade";
    private static final String AVIONICS = "avionics";
    private static final String IDESTADO = "id_estadoveiculo";
    private static final String POTENCIA = "potenciamotor";
    private static final String TENSAO = "tensaobateria";
    private static final String CARGAMAXIMA = "cargamaxima";
    private static final String CONSUMO = "consumohorabateria";

    /**
     * update drone na base de dados
     * @param drone drone a dar update
     * @return boolean
     */
    public boolean updateDrone(Drone drone) {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareCall("insert into Veiculo (id,carga,capacidadeBateria,id_estadoVeiculo,consumoHoraBateria,tensaoBateria,massa,potenciaMotor) values (?,?,?,?,?,?,?,?)")){
                ps.setInt(1,drone.getId());
                ps.setInt(2, drone.getCarga());
                ps.setDouble(3, drone.getCapacidadeBateria());
                ps.setInt(4, drone.getEstadoDroneID());
                ps.setDouble(5, drone.getConsumoHoraBateria());
                ps.setDouble(6, drone.getTensaoBateria());
                ps.setDouble(7, drone.getMassa());
                ps.setDouble(8, drone.getPotenciaMotor());
                ps.executeUpdate();
                try(PreparedStatement prepareCall=con.prepareCall("INSERT into Drone (id,velocidade,avionics,drag,rotors,cargaMaxima) values (?,?,?,?,?,?)")){
                    prepareCall.setInt(1,drone.getId());
                    prepareCall.setDouble(2,drone.getVelocidade());
                    prepareCall.setDouble(3,drone.getAvionics());
                    prepareCall.setDouble(4,drone.getDrag());
                    prepareCall.setDouble(5,drone.getRotors());
                    prepareCall.setDouble(6,drone.getCargaMaxima());
                    prepareCall.executeUpdate();
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
     * retorna o maior id dos drones na base de dados
     * @return maior id dos drones na base de dados
     */
    public int lastIDdrone() {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet resultSet = con.createStatement().executeQuery("SELECT MAX(id) FROM Veiculo")){
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * retorna lista dos drones na base de dados
     * @return lista dos drones na base de dados
     */
    public List<String> getListaDrone() {
        List<String> listaDrone = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            String sql="SELECT * FROM Veiculo inner join Drone on Veiculo.id=Drone.id";
            try(Statement statement = con.createStatement()){
                try(ResultSet resultSet=statement.executeQuery(sql)){
                    while (resultSet.next()) {
                        listaDrone.add("ID do Drone: " + resultSet.getInt("id") + "\nCapacidade da bateria do Drone: " + resultSet.getDouble(CAPACIDADE) + "\nCarga do Drone: " + resultSet.getInt(CARGA));
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            BaseDados.closeConnection();

        }
        return listaDrone;
    }

    /**
     * retorna lista dos drones na base de dados
     * @return lista dos drones na base de dados
     */
    public List<Drone> getListaDrones() {
        List<Drone> listaDrone = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            String sql="SELECT * FROM Veiculo inner join Drone on Veiculo.id=Drone.id";
            try(Statement statement = con.createStatement()){
                try(ResultSet resultSet=statement.executeQuery(sql)){
                    while (resultSet.next()) {
                        listaDrone.add(new Drone(resultSet.getInt("id"),resultSet.getInt(CARGA),resultSet.getDouble(CAPACIDADE),resultSet.getInt(IDESTADO)
                                ,resultSet.getDouble(CONSUMO),resultSet.getDouble(TENSAO),resultSet.getDouble(MASSA),resultSet.getDouble(POTENCIA),
                                resultSet.getDouble(VELOCIDADE),resultSet.getDouble(AVIONICS),resultSet.getDouble("drag"),resultSet.getDouble(ROTORS),resultSet.getDouble(CARGAMAXIMA)));
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            BaseDados.closeConnection();

        }
        return listaDrone;
    }

    /**
     * lista dados drone com base num id de drone da base de dados
     * @param droneID id do drone
     * @return dados drone
     */
    public List<String> getDadosDronebyID(int droneID) {
        List<String> dadosDrone = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet set = con.createStatement().executeQuery("SELECT * FROM Veiculo WHERE Veiculo.id=" + droneID)){
                if (set.next()) {
                    dadosDrone.add(set.getInt("id") + "");
                    dadosDrone.add(set.getInt(CARGA) + "");
                    dadosDrone.add(set.getDouble(CAPACIDADE) + "");
                    dadosDrone.add(set.getInt(IDESTADO) + "");
                    dadosDrone.add(set.getDouble(CONSUMO) + "");
                    dadosDrone.add(set.getDouble(TENSAO) + "");
                    dadosDrone.add(set.getDouble(MASSA) + "");
                    dadosDrone.add(set.getDouble(POTENCIA) + "");
                }
                try(ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM Drone WHERE Drone.id=" + droneID)){
                    if (resultSet.next()) {
                        dadosDrone.add(resultSet.getInt(VELOCIDADE) + "");
                        dadosDrone.add(resultSet.getDouble(AVIONICS) + "");
                        dadosDrone.add(resultSet.getInt("drag") + "");
                        dadosDrone.add(resultSet.getDouble(ROTORS) + "");
                        dadosDrone.add(resultSet.getDouble("cargaMaxima") + "");
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return dadosDrone;
    }

    /**
     * update ao dados do drone na base de dados
     * @param id id do drone
     * @param cargaDrone cargaDrone do drone
     * @param capacidadeBateriaDrone capacidadeBateriaDrone do drone
     * @param estadoDroneID estadoDroneID do drone
     * @param consumoHoraBateriaDrone consumoHoraBateriaDrone do drone
     * @param tensaoBateriaDrone tensaoBateriaDrone do drone
     * @param massa massa do drone
     * @param potencia potencia do drone
     * @param velocidade velocidade do drone
     * @param avionics avionics do drone
     * @param drag drag do drone
     * @param rotors rotors do drone
     * @param cargaMaxima cargaMaxima do drone
     * @return boolean
     */
    public boolean updateDadosDrone(int id, int cargaDrone, double capacidadeBateriaDrone, int estadoDroneID, double consumoHoraBateriaDrone, double tensaoBateriaDrone, double massa, double potencia,double velocidade, double avionics, double drag, double rotors, double cargaMaxima) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet resultSet=con.createStatement().executeQuery("UPDATE Veiculo SET Veiculo.carga=" + cargaDrone + ", Veiculo.capacidadeBateria=" + capacidadeBateriaDrone + ",Veiculo.id_estadoVeiculo=" + estadoDroneID + ",Veiculo.consumoHoraBateria=" + consumoHoraBateriaDrone + ",Veiculo.tensaoBateria=" + tensaoBateriaDrone + ",Veiculo.massa=" + massa + ",Veiculo.potenciaMotor=" + potencia + " WHERE Veiculo.id=" + id)){
                try(PreparedStatement ps = con.prepareCall("UPDATE Drone SET Drone.velocidade = ?, Drone.avionics = ?, Drone.drag = ?, Drone.rotors = ?, Drone.cargaMaxima = ? WHERE Drone.id = ?")){
                    ps.setDouble(1,velocidade);
                    ps.setDouble(2,avionics);
                    ps.setDouble(3,drag);
                    ps.setDouble(4,rotors);
                    ps.setDouble(5,cargaMaxima);
                    ps.setDouble(6,id);
                    return true;
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * retorna drone com o id definido da base de dados
     * @param droneID id drone
     * @return drone
     */
    public String getDroneByID(int droneID) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet executeQuery = con.createStatement().executeQuery("SELECT * FROM Veiculo WHERE Veiculo.id=" + droneID)){
                if (executeQuery != null && executeQuery.next()) {
                    return "ID do Drone: " + executeQuery.getInt("id") + "\nCapacidade da bateria: " + executeQuery.getDouble(CAPACIDADE) + "\nCarga: " + executeQuery.getInt(CARGA);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * remove drone com o id da base de dados
     * @param droneID id do drone
     * @return boolean
     */
    public boolean removerDrone(int droneID) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("UPDATE LugarEstacionamento SET id_veiculo= null WHERE id_veiculo="+droneID)){
                try(ResultSet resultSet=con.createStatement().executeQuery("DELETE FROM Drone WHERE Drone.id=" + droneID)){
                    try(ResultSet resultSet1= con.createStatement().executeQuery("DELETE FROM Veiculo WHERE Veiculo.id="+droneID)){
                        return true;
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * verifica se o id do veiculo é drone na base de dados
     * @param id id veiculo
     * @return boolean
     */
    public boolean isDrone(int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet re = con.createStatement().executeQuery("SELECT * FROM Veiculo inner join Drone on Veiculo.id=Drone.id where Veiculo.id=" + id)){
                if (re.next()) {
                    return true;
                }
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * capacidade do drone by id na base de dados
     * @param id id drone
     * @return boolean
     */
    public int getCapacidadeDroneById(int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Veiculo where id=" + id)){
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
     * retorna email do admin da farmacia usando o drone
     * @param id id drone
     * @return email do admin da farmacia
     */
    public String getEmailAdministradorByDrone(int id) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Veiculo inner join LugarEstacionamento on Veiculo.id=LugarEstacionamento.id_Veiculo inner join ParqueEstacionamento on LugarEstacionamento.id_parqueestacionamento=ParqueEstacionamento.id inner join Farmacia on ParqueEstacionamento.id_farmacia=Farmacia.id where Veiculo.id=" + id)){
                if(rs.next())
                    return rs.getString("emailGestor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * lista de drones da farmacia atraves do id na base de dados
     * @param idFarm id farmacia
     * @return lista de drones da farmacia
     */
    public List<Drone> getDronesByFarmID(int idFarm) {
        try {
            List<Drone> lista=new ArrayList<>();
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("select * from veiculo inner join drone on drone.id=veiculo.id " +
                    "inner join LugarEstacionamento on LugarEstacionamento.id_veiculo=veiculo.id " +
                    "inner join ParqueEstacionamento on ParqueEstacionamento.id=LugarEstacionamento.id_parqueEstacionamento " +
                    "inner join Farmacia on ParqueEstacionamento.id_farmacia=Farmacia.id " +
                    "where Farmacia.id=" + idFarm)){
                while(rs.next()) {
                    lista.add( new Drone(rs.getInt("id"),rs.getInt(CARGA),rs.getDouble(CAPACIDADE),rs.getInt(IDESTADO)
                    ,rs.getDouble(CONSUMO),rs.getDouble(TENSAO),rs.getDouble(MASSA),rs.getDouble(POTENCIA),
                            rs.getDouble(VELOCIDADE),rs.getDouble(AVIONICS),rs.getDouble("drag"),rs.getDouble(ROTORS),rs.getDouble("cargamaxima")));
                }
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
     * lista de drones disponiveis da farmacia
     * @param idFarm id farmacia
     * @return lista de drones disponiveis da farmacia
     */
    public List<Drone> getAvailableDronesByFarmID(int idFarm) {
        try {
            List<Drone> listaa=new ArrayList<>();
            Connection conn = BaseDados.getConnection();
            try(ResultSet rset = conn.createStatement().executeQuery("select * from veiculo inner join drone on drone.id=veiculo.id " +
                    "inner join LugarEstacionamento on LugarEstacionamento.id_veiculo=veiculo.id " +
                    "inner join ParqueEstacionamento on ParqueEstacionamento.id=LugarEstacionamento.id_parqueEstacionamento " +
                    "inner join Farmacia on ParqueEstacionamento.id_farmacia=Farmacia.id " +
                    "where Farmacia.id=" + idFarm + " AND Veiculo.id_estadoVeiculo=2 ")){
                while(rset.next()) {
                    listaa.add( new Drone(rset.getInt("id"),rset.getInt(CARGA),rset.getDouble(CAPACIDADE),rset.getInt(IDESTADO)
                            ,rset.getDouble(CONSUMO),rset.getDouble(TENSAO),rset.getDouble(MASSA),rset.getDouble(POTENCIA),
                            rset.getDouble(VELOCIDADE),rset.getDouble(AVIONICS),rset.getDouble("drag"),rset.getDouble(ROTORS),rset.getDouble("cargamaxima")));
                }
            }
            return listaa;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }

    /**
     * setter estado veiculo na base de dados
     * @param veiculo id estado veiculo
     * @param i estado
     * @return set estado
     */
    public boolean setEstadoVeiculo(int veiculo, int i) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rse = con.createStatement().executeQuery("UPDATE Veiculo SET id_estadoVeiculo="+i+" WHERE id="+veiculo)){
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
