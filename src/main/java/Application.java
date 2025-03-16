import controller.UserController;
import dao.UserDAO;
import service.UserService;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class Application {
    private static UserController userController;
    private static void di() {
        userController = new UserController(new UserService(new UserDAO()));
    }
    private static void setupControllers() {
        di();
        userController.setupRoutes();
    }
    public static void main(String[] args) {
        port(3000);
        staticFiles.location("/public");
        setupControllers();
    }
}
