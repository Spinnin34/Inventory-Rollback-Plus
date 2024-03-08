package p.zestianstaff.database;
import com.google.inject.Inject;
import p.zestianstaff.ZestianStaff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    public final ConnectionPoolManager pool;

    @Inject
    public SQLManager(ConnectionPoolManager pool) {
        this.pool = pool;
        makeTable();
    }

    private void makeTable() {
        try (Connection conn = pool.getConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `DatosJugador` (" +
                            "Jugador VARCHAR(50) NOT NULL," +
                            "Horas INT NOT NULL DEFAULT 0," +
                            "Minutos INT NOT NULL DEFAULT 0," +
                            "Segundos INT NOT NULL DEFAULT 0," +
                            "PRIMARY KEY (Jugador)" +
                            ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarTiempoJugado(String jugador, int horas, int minutos, int segundos) {
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO `DatosJugador` (Jugador, Horas, Minutos, Segundos) VALUES (?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE Horas = Horas + VALUES(Horas), " +
                             "Minutos = Minutos + VALUES(Minutos), " +
                             "Segundos = Segundos + VALUES(Segundos)"
             )) {

            ps.setString(1, jugador);
            ps.setInt(2, horas);
            ps.setInt(3, minutos);
            ps.setInt(4, segundos);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTopStaff(int limit) {
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT Jugador, SUM(Horas) AS TotalHoras, SUM(Minutos) AS TotalMinutos, SUM(Segundos) AS TotalSegundos " +
                             "FROM `DatosJugador` " +
                             "WHERE Jugador IN (SELECT DISTINCT Jugador FROM `Permisos` WHERE Permiso = 'staff.top') " +
                             "GROUP BY Jugador " +
                             "ORDER BY TotalHoras DESC, TotalMinutos DESC, TotalSegundos DESC " +
                             "LIMIT ?"
             )) {

            ps.setInt(1, limit);
            ResultSet resultSet = ps.executeQuery();

            List<String> topStaffList = new ArrayList<>();
            while (resultSet.next()) {
                String jugador = resultSet.getString("Jugador");
                int totalHoras = resultSet.getInt("TotalHoras");
                int totalMinutos = resultSet.getInt("TotalMinutos");
                int totalSegundos = resultSet.getInt("TotalSegundos");

                topStaffList.add(jugador + " - " + totalHoras + " horas, " + totalMinutos + " minutos, " + totalSegundos + " segundos");
            }

            return topStaffList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

