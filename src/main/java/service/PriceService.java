package service;

import dao.PriceDAO;
import model.Garage;
import model.Price;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class PriceService {
    private final PriceDAO priceDAO;

    public PriceService(final PriceDAO priceDAO) {
        this.priceDAO = priceDAO;
    }

    public List<Price> getAllBy(final UUID garageId) {
        return priceDAO.getAllBy(garageId);
    }

    public boolean delete(final UUID garageId, final UUID priceId) {
        return priceDAO.delete(garageId, priceId);
    }

    public boolean create(final UUID garageId, final int aboveTime, final BigInteger price) {
        Price newPrice = Price.create(aboveTime, garageId, price);
        return priceDAO.create(newPrice);
    }

    public boolean update(final UUID garageId, final UUID priceId, final int aboveTime, final BigInteger price) {
        return priceDAO.update(garageId, priceId, aboveTime, price);
    }
}
