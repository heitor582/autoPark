package dao;

import model.Price;
import model.User;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PriceDAO extends DAO{
    public PriceDAO() {
        super();
        connect();
    }
    public List<Price> getAllBy(final UUID garageId) {
        List<Price> prices = new ArrayList<>();
        String sql = "SELECT * FROM garage_prices WHERE garage_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setObject(1, garageId);
            try(ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Price price = new Price(
                            rs.getObject("id", UUID.class),
                            rs.getInt("above_time"),
                            rs.getObject("garage_id", UUID.class),
                            BigInteger.valueOf(rs.getLong("price")),
                            rs.getTimestamp("created_at").toInstant()
                    );
                    prices.add(price);
                }
            }
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return prices;
    }

    public boolean delete(final UUID garageId, final UUID priceId) {
        String sql = "DELETE FROM garage_prices WHERE garage_id = ? AND id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            Statement st = connection.createStatement();
            stmt.setObject(1, garageId);
            stmt.setObject(2, priceId);

            stmt.executeUpdate();
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return true;
    }

    public boolean create(final Price price) {
        String sql = "INSERT INTO garage_prices (id, above_time, garage_id, price, created_at)"
                + "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            Statement st = connection.createStatement();
            stmt.setObject(1, price.getId());
            stmt.setInt(2, price.getAboveTime());
            stmt.setObject(3, price.getGarageId());
            stmt.setLong(4, price.getPrice().longValueExact());
            stmt.setTimestamp(5, Timestamp.from(price.getCreatedAt()));

            stmt.executeUpdate();
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return true;
    }

    public boolean update(final UUID garageId, final UUID priceId, final int aboveTime, final BigInteger price) {
        boolean status = false;
        String sql = "UPDATE garage_prices SET above_time = ?, price = ? WHERE id = ? and garage_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, aboveTime);
            st.setLong(2, price.longValueExact());
            st.setObject(3, priceId);
            st.setObject(4, garageId);

            int rowsAffected = st.executeUpdate();
            status = rowsAffected > 0;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}
