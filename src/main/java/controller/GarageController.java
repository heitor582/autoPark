package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Garage;
import service.GarageService;
import spark.Spark;
import utils.Json;

import java.util.List;
import java.util.UUID;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class GarageController implements Routes{
    private final GarageService garageService;

    public GarageController(final GarageService garageService) {
        this.garageService = garageService;
    }

    private void create(){
        post("/user/:ownerId/garage", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String username = jsonObject.get("name").getAsString();
            UUID ownerId = UUID.fromString(request.params(":ownerId"));

            boolean success = garageService.create(username, ownerId);
            response.status(success ? 201 : 400);
            return success ? "Create successfully" : "error for create garage";
        });
    }

    private void getAll(){
        get("/user/:ownerId/garage", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));

            List<Garage> garages = garageService.getAllBy(ownerId);
            response.status(garages.isEmpty() ? 204 : 200);
            return new Gson()
                            .toJsonTree(Json.writeValueAsString(garages));
        });
    }

    private void deleteById(){
        delete("/user/:ownerId/garage/:garageId", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));

            boolean success = garageService.delete(ownerId, garageId);
            response.status(success ? 204 : 400);
            return success ? "Deleted successfully" : "error for delete garage";
        });
    }

    private void update(){
        put("/user/:ownerId/garage/:garageId", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID id = UUID.fromString(request.params(":garageId"));
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String name = jsonObject.get("name").getAsString();

            boolean success = garageService.update(ownerId, id, name);
            response.status(success ? 200 : 400);
            return success ? "Update successfully" : "error for update user";
        });
    }
    @Override
    public void setupRoutes() {
        create();
        getAll();
        deleteById();
        update();
    }
}
