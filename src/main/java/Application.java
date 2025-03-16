import controller.GarageController;
import controller.UserController;
import dao.GarageDAO;
import dao.UserDAO;
import service.GarageService;
import service.UserService;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class Application {
    private static UserController userController;
    private static GarageController garageController;
    private static void di() {
        userController = new UserController(new UserService(new UserDAO()));
        garageController = new GarageController(new GarageService(new GarageDAO()));
    }
    private static void setupControllers() {
        di();
        userController.setupRoutes();
        garageController.setupRoutes();
    }
    public static void main(String[] args) {
        port(3000);
        staticFiles.location("/public");
        setupControllers();
    }
}
