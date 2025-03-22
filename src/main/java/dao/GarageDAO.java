package dao;

import model.Garage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GarageDAO extends DAO{
    public GarageDAO() {
        super();
        connect();
    }

    public boolean insert(final Garage garage) {
        String sql = "INSERT INTO garages (id, name, owner_id, created_at, updated_at)"
                + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            Statement st = connection.createStatement();
            stmt.setObject(1, garage.getId());
            stmt.setString(2, garage.getName());
            stmt.setObject(3, garage.getOwnerId());
            stmt.setTimestamp(4, Timestamp.from(garage.getCreatedAt()));
            stmt.setTimestamp(5, Timestamp.from(garage.getUpdateAt()));

            stmt.executeUpdate();
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return true;
    }

    public List<Garage> getAllBy(final UUID ownerId) {
        List<Garage> garages = new ArrayList<>();
        String sql = "SELECT * FROM garages where owner_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setObject(1, ownerId);
            try(ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Garage garage = new Garage(
                            rs.getObject("id", UUID.class),
                            rs.getString("name"),
                            rs.getObject("owner_id", UUID.class),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant()
                    );
                    garages.add(garage);
                }
            }
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return garages;
    }

    public boolean delete(final UUID ownerId, final UUID garageId) {
        String sql = "DELETE FROM garages WHERE owner_id = ? AND id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            Statement st = connection.createStatement();
            stmt.setObject(1, ownerId);
            stmt.setObject(2, garageId);

            stmt.executeUpdate();
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return true;
    }

    public boolean update(UUID ownerId, UUID id, String name) {
        boolean status = false;
        String sql = "UPDATE garages SET name = ?, updated_at = ? WHERE id = ? and owner_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name);
            st.setTimestamp(2, Timestamp.from(Instant.now()));
            st.setObject(3, id);
            st.setObject(4, ownerId);

            int rowsAffected = st.executeUpdate();
            status = rowsAffected > 0;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}
