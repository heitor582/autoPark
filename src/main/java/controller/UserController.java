package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;
import service.UserService;
import utils.Json;

import java.util.UUID;

import static spark.Spark.delete;
import static spark.Spark.post;
import static spark.Spark.put;

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

            User user = userService.login(username, password);
            response.status(user != null ? 200 : 401);
            return new Gson()
                    .toJsonTree(Json.writeValueAsString(user));
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
    private void update(){
        put("/user/:id", (request, response) -> {
            UUID id = UUID.fromString(request.params(":id"));
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();

            boolean success = userService.update(id, username, password);
            response.status(success ? 200 : 400);
            return success ? "Update successfully" : "error for update user";
        });
    }

    private void deleteById(){
        delete("/user/:id", (request, response) -> {
            UUID id = UUID.fromString(request.params(":id"));

            boolean success = userService.delete(id);
            response.status(success ? 204 : 400);
            return success ? "Deleted successfully" : "error for delete garage";
        });
    }

    @Override
    public void setupRoutes() {
        login();
        create();
        update();
        deleteById();
    }
}
