import controller.GarageController;
import controller.PriceController;
import controller.UserController;
import dao.GarageDAO;
import dao.PriceDAO;
import dao.UserDAO;
import service.GarageService;
import service.PriceService;
import service.UserService;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class Application {
    private static UserController userController;
    private static GarageController garageController;
    private static PriceController priceController;
    private static void di() {
        userController = new UserController(new UserService(new UserDAO()));
        garageController = new GarageController(new GarageService(new GarageDAO()));
        priceController = new PriceController(new PriceService(new PriceDAO()));
    }
    private static void setupControllers() {
        di();
        userController.setupRoutes();
        garageController.setupRoutes();
        priceController.setupRoutes();
    }
    public static void main(String[] args) {
        port(3000);
        staticFiles.location("/public");
        setupControllers();
    }
}
