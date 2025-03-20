package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Garage;
import service.GarageService;
import utils.Json;

import java.util.List;
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;

public class GarageController implements Routes{
    private final GarageService garageService;

    public GarageController(final GarageService garageService) {
        this.garageService = garageService;
    }

    private void create(){
        post("/garage", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String username = jsonObject.get("name").getAsString();
            String ownerId = jsonObject.get("ownerId").getAsString();

            boolean success = garageService.create(username, UUID.fromString(ownerId));
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
    @Override
    public void setupRoutes() {
        create();
        getAll();
    }
}
