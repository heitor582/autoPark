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

    public boolean create(final String name, final UUID owner_id){
        final Garage garage = Garage.create(name, owner_id);
        return garageDAO.insert(garage);
    }

    public List<Garage> getAllBy(final UUID ownerId){
        return garageDAO.getAllBy(ownerId);
    }
}
