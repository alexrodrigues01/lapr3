package lapr.project.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lapr.project.model.Farmacia;
import lapr.project.model.LugarDrone;
import lapr.project.model.LugarEstacionamento;

/**
 * The type Farmacia bd.
 */
public class FarmaciaBD {

    private static final String ERRO = "ERRO - falha na obtencao do nome da farmacia";

    /**
     * Este metodo retorna a lista de todas as farmácioas presentes na base de dados
     *
     * @return the farmacias
     */
    public List<Farmacia> getFarmacias() {
        ArrayList<Farmacia> listaFarmacias = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Farmacia")) {
                while (rs.next()) {
                    listaFarmacias.add(new Farmacia(rs.getInt("id"), rs.getString("nome"), rs.getInt("nif"), rs.getInt("telefone"), rs.getString("email"), rs.getString("emailGestor"), rs.getString("id_Morada")));
                }
            }
            BaseDados.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return listaFarmacias;
    }

    /**
     * Update ao id do veiculos do lugar de estacionamento
     *
     * @param lugarEstacionamento the lugar estacionamento
     * @return the boolean
     */
    public boolean updateLugar(LugarEstacionamento lugarEstacionamento) {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("UPDATE LugarEstacionamento SET LugarEstacionamento.id_veiculo=" + lugarEstacionamento.getScooterID() + " WHERE LugarEstacionamento.id=" + lugarEstacionamento.getId())) {
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
     * Update ao id do veiculos do lugar de estacionamento.
     *
     * @param lugarDrone the lugar drone
     * @return the boolean
     */
    public boolean updateLugarDrone(LugarDrone lugarDrone) {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("UPDATE LugarEstacionamento SET LugarEstacionamento.id_veiculo=" + lugarDrone.getDroneID() + " WHERE LugarEstacionamento.id=" + lugarDrone.getId())) {
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
     * Este metodo retorna a farmacia com um determinado email gestor.
     *
     * @param email the email
     * @return the farmaciaby gestor
     */
    public Farmacia getFarmaciabyGestor(String email) {
        try {
            Connection con = BaseDados.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Farmacia WHERE Farmacia.emailGestor= ?")) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Farmacia(rs.getInt("id"), rs.getString("nome"), rs.getInt("nif"), rs.getInt("telefone"), rs.getString("email"), rs.getString("emailGestor"), rs.getString("id_Morada"));
                    }
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
     * Este metodo retorna o lugar de estacionamento livre a partir da farmacia.
     *
     * @param farmID the farm id
     * @return the lugar estacionamentoby farm
     */
    public LugarEstacionamento getLugarEstacionamentobyFarm(int farmID) {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM LugarEstacionamento INNER JOIN ParqueEstacionamento ON LugarEstacionamento.id_parqueEstacionamento = ParqueEstacionamento.id WHERE ParqueEstacionamento.id_Farmacia = " + farmID + " AND ParqueEstacionamento.id_tipoEstacionamento = 1 AND LugarEstacionamento.id_veiculo is NULL")) {
                if (rs.next()) {
                    return new LugarEstacionamento(rs.getInt("id"));
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
     * Este metodo retorna o lugar de estacionamento para drone livre
     *
     * @param farmID the farm id
     * @return the lugar droneby farm
     */
    public LugarDrone getLugarDronebyFarm(int farmID) {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM LugarEstacionamento INNER JOIN ParqueEstacionamento ON LugarEstacionamento.id_parqueEstacionamento = ParqueEstacionamento.id WHERE ParqueEstacionamento.id_Farmacia=" + farmID + " AND ParqueEstacionamento.id_tipoEstacionamento = 2 AND LugarEstacionamento.id_veiculo is NULL ")) {
                if (rs.next()) {
                    return new LugarDrone(rs.getInt("id"));
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
     * Valida farmacia .
     *
     * @param email the email
     * @param nif   the nif
     * @return the boolean
     */
    public boolean validaFarmacia(String email, int nif) {
        try {
            Connection connection = BaseDados.getConnection();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Farmacia WHERE email like ? OR nif = ?")) {
                ps.setString(1, email);
                ps.setInt(2, nif);
                try (ResultSet result = ps.executeQuery()) {
                    if (result.next())
                        throw new IllegalArgumentException("Já existe uma farmacia com este email/nif");
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
     *  Este metodo cria um objeto farmacia validando os dados.
     *
     * @param nome        the nome
     * @param email       the email
     * @param telemovel   the telemovel
     * @param nif         the nif
     * @param morada      the morada
     * @param latitude    the latitude
     * @param longitude   the longitude
     * @param altitude    the altitude
     * @param emailGestor the email gestor
     * @return the int
     */
    public int novaFarmacia(String nome, String email, int telemovel, int nif, String morada, double latitude, double longitude, double altitude, String emailGestor) {
        try {
            Connection connection = BaseDados.getConnection();
            int moradaID = 0;
            try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM Morada WHERE  morada = ? AND latitude = ? AND longitude = ? AND altitude = ?")) {
                ps.setString(1, morada);
                ps.setDouble(2, latitude);
                ps.setDouble(3, longitude);
                ps.setDouble(4, altitude);

                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next())
                        throw new IllegalArgumentException("ERRO - morada invalida");

                    moradaID = result.getInt(1);
                }

            }

            int farmaciaID;
            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) FROM Farmacia")) {

                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next())
                        farmaciaID = 1;
                    else
                        farmaciaID = result.getInt(1) + 1;
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Farmacia (id, nome, nif, telefone, email, emailGestor, id_morada) values(?, ?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, farmaciaID);
                ps.setString(2, nome);
                ps.setInt(3, nif);
                ps.setInt(4, telemovel);
                ps.setString(5, email);
                ps.setString(6, emailGestor);
                ps.setInt(7, moradaID);

                ps.executeUpdate();
            }

            return farmaciaID;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     *  Este metodo retorna cria os lugares de estacionamento na base de dados.
     *
     * @param farmaciaID             the farmacia id
     * @param lugaresSemCarregamento the lugares sem carregamento
     * @param lugaresComCarregamento the lugares com carregamento
     * @param capacidadeEnergia      the capacidade energia
     * @return the lugares scooter
     */
    public boolean setLugaresScooter(int farmaciaID, int lugaresSemCarregamento, int lugaresComCarregamento, double capacidadeEnergia) {
        try {
            Connection connection = BaseDados.getConnection();

            int parqueID;
            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) FROM ParqueEstacionamento")) {

                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next())
                        parqueID = 1;
                    else
                        parqueID = result.getInt(1) + 1;
                }

            }

            try (PreparedStatement prepS = connection.prepareStatement("INSERT INTO ParqueEstacionamento (id,capacidadeEnergia,id_farmacia,id_tipoEstacionamento) values (?,?,?,1)")) {
                prepS.setInt(1, parqueID);
                prepS.setDouble(2, capacidadeEnergia);
                prepS.setInt(3, farmaciaID);
                prepS.executeUpdate();
            }

            int lugarID;
            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) FROM LugarEstacionamento")) {

                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next())
                        lugarID = 1;
                    else
                        lugarID = result.getInt(1) + 1;
                }

            }

            try (PreparedStatement ps = connection.prepareStatement("INSERT into LugarEstacionamento(id,id_suporteCarregamento,id_parqueEstacionamento) values (?,?,?) ")) {
                for (int i = 0; i < lugaresSemCarregamento; i++) {
                    ps.setInt(1, lugarID);
                    ps.setInt(2, 2);
                    ps.setInt(3, parqueID);
                    ps.executeUpdate();
                    lugarID++;
                }

                for (int i = 0; i < lugaresComCarregamento; i++) {
                    ps.setInt(1, lugarID);
                    ps.setInt(2, 2);
                    ps.setInt(3, parqueID);
                    ps.executeUpdate();
                    lugarID++;
                }
            }

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }

    }

    /**
     * Este metyodo associa os produtos as farmacias
     *
     * @param farmaciaID the farmacia id
     * @return the boolean
     */
    public boolean associaProdutos(int farmaciaID) {
        try {
            Connection connection = BaseDados.getConnection();

            try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM Produto")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        try (PreparedStatement psd = connection.prepareStatement("INSERT into Produto_Farmacia(id_produto,id_farmacia,stock) values(?,?,0)")) {
                            psd.setInt(1, rs.getInt(1));
                            psd.setInt(2, farmaciaID);
                            psd.executeUpdate();
                        }
                    }
                }

            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Gets nome farmacia.
     *
     * @param farmaciaID id da farmacia que queremos obter o nome
     * @return nome da farmacia
     */
    public String getNomeFarmacia(int farmaciaID) {
        try {
            Connection connection = BaseDados.getConnection();

            try (PreparedStatement ps = connection.prepareStatement("SELECT nome FROM Farmacia WHERE id = ?")) {
                ps.setInt(1, farmaciaID);

                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next())
                        throw new IllegalArgumentException(ERRO);

                    String nome = result.getString("nome");
                    if (nome == null) {
                        throw new IllegalArgumentException(ERRO);
                    }

                    return nome;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException(ERRO);
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Este metodo retorna cria os lugares de estacionamento na base de dados.
     *
     * @param farmaciaID             the farmacia id
     * @param lugaresSemCarregamento the lugares sem carregamento
     * @param lugaresComCarregamento the lugares com carregamento
     * @param capacidadeEnergia      the capacidade energia
     * @return the lugares drone
     */
    public boolean setLugaresDrone(int farmaciaID, int lugaresSemCarregamento, int lugaresComCarregamento, double capacidadeEnergia) {
        try {
            int lastIDParque = lastIDParque() + 1;
            int lastID = lastIDLugar() + 1;
            Connection connection = BaseDados.getConnection();
            try (PreparedStatement prepS = connection.prepareStatement("INSERT INTO ParqueEstacionamento (id,capacidadeEnergia,id_farmacia,id_tipoEstacionamento) values (?,?,?,2)")) {
                prepS.setInt(1, lastIDParque);
                prepS.setDouble(2, capacidadeEnergia);
                prepS.setInt(3, farmaciaID);
                prepS.executeUpdate();

                try (PreparedStatement ps = connection.prepareStatement("INSERT into LugarEstacionamento(id,id_suporteCarregamento,id_parqueEstacionamento) values (?,1,?) ")) {
                    for (int i = 0; i < lugaresSemCarregamento; i++) {
                        ps.setInt(1, lastID);
                        ps.setInt(2, lastIDParque);
                        ps.executeUpdate();
                        lastID++;
                    }

                    try (PreparedStatement preparedStatements = connection.prepareStatement("INSERT into LugarEstacionamento(id,id_suporteCarregamento,id_parqueEstacionamento) values (?,2,?) ")) {
                        for (int i = 0; i < lugaresComCarregamento; i++) {
                            preparedStatements.setInt(1, lastID);
                            preparedStatements.setInt(2, lastIDParque);
                            preparedStatements.executeUpdate();
                            lastID++;
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }

    }

    /**
     * Este metodo retorna o ultimo id da tabela lugarEstacionamento da base de dados.
     *
     * @return the int
     */
    public int lastIDLugar() {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet set = con.createStatement().executeQuery("SELECT MAX(id) FROM LugarEstacionamento")) {
                if (set.next()) {
                    return set.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao do ultimo id dos lugares de estacionamento");
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * Este metodo retorna o ultimo id da tabela parqueEstacionameto da base de dados.
     *
     * @return lastIDParque
     */
    public int lastIDParque() {
        try {
            Connection connec = BaseDados.getConnection();
            try (ResultSet sett = connec.createStatement().executeQuery("SELECT MAX(id) FROM ParqueEstacionamento")) {
                if (sett.next()) {
                    return sett.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao do ultimo id dos parques de estacionamento");
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }


    /**
     * Apaga toda as linhas das tabelas.
     */
    public void deleteRun() {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs45 = con.createStatement().executeQuery("delete nota")) {
                try (ResultSet rs = con.createStatement().executeQuery("delete produto_farmacia")) {
                    try (ResultSet rs2 = con.createStatement().executeQuery("delete linhafatura")) {
                        try (ResultSet rs3 = con.createStatement().executeQuery("delete fatura")) {
                            try (ResultSet rs4 = con.createStatement().executeQuery("delete lugarestacionamento")) {
                                try (ResultSet rs5 = con.createStatement().executeQuery("delete drone")) {
                                    try (ResultSet rs6 = con.createStatement().executeQuery("delete scooter")) {
                                        try (ResultSet rs7 = con.createStatement().executeQuery("delete caminho")) {
                                            try (ResultSet rs8 = con.createStatement().executeQuery("delete parqueestacionamento")) {
                                                try (ResultSet rs9 = con.createStatement().executeQuery("delete linhaencomendaproduto")) {
                                                    try (ResultSet rs10 = con.createStatement().executeQuery("delete encomenda")) {
                                                        try (ResultSet rs11 = con.createStatement().executeQuery("delete entrega")) {
                                                            try (ResultSet rs12 = con.createStatement().executeQuery("delete veiculo")) {
                                                                try (ResultSet joao = con.createStatement().executeQuery("delete cliente")) {
                                                                    try (ResultSet rs13 = con.createStatement().executeQuery("delete estafeta")) {
                                                                        try (ResultSet rs14 = con.createStatement().executeQuery("delete farmacia")) {
                                                                            try (ResultSet rs15 = con.createStatement().executeQuery("delete gestorfarmacia")) {
                                                                                try (ResultSet rs16 = con.createStatement().executeQuery("delete utilizador")) {
                                                                                    try (ResultSet rs17 = con.createStatement().executeQuery("delete morada")) {
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            BaseDados.closeConnection();
        }

    }
}
