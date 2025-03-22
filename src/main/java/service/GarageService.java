package service;

import dao.GarageDAO;
import model.Garage;
import java.util.List;
import java.util.UUID;

public class GarageService {
    private final GarageDAO garageDAO;


    public GarageService(final GarageDAO garageDAO) {
        this.garageDAO = garageDAO;
    }

    public boolean create(final String name, final UUID ownerId){
        final Garage garage = Garage.create(name, ownerId);
        return garageDAO.insert(garage);
    }

    public List<Garage> getAllBy(final UUID ownerId){
        return garageDAO.getAllBy(ownerId);
    }
    public boolean delete(final UUID ownerId, final UUID garageId){
        return garageDAO.delete(ownerId, garageId);
    }

    public boolean update(final UUID ownerId, final UUID id, final String name){
        return garageDAO.update(ownerId, id, name);
    }
}
