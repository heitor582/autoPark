package controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import service.UserService;

import static spark.Spark.post;

public class UserController implements Routes {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    private void login(){
        post("/user/login", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();

            boolean success = userService.login(username, password);
            response.status(success ? 200 : 401);
            return success ? "Successfully login" : "Unauthorized";
        });
    }

    private void create(){
        post("/user", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();
            boolean isAdmin = jsonObject.get("isAdmin").getAsBoolean();

            boolean success = userService.create(username, password, isAdmin);
            response.status(success ? 201 : 400);
            return success ? "Create successfully" : "error for create user";
        });
    }

    @Override
    public void setupRoutes() {
        login();
        create();
    }
}
