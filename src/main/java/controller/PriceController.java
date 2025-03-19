package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Garage;
import utils.Json;

import java.util.List;
import java.util.UUID;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class PriceController implements Routes{
    private void create(){
        post("/user/:ownerId/garage/:garageId", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            //TODO
            boolean success = priceService.create();
            response.status(success ? 201 : 400);
            return success ? "Create successfully" : "error for create price";
        });
    }

    private void getAll(){
        get("/user/:ownerId/garage/:garageId/price", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));

            List<Garage> garages = priceService.getAllBy(ownerId, garageId);
            response.status(garages.isEmpty() ? 204 : 200);
            return new Gson()
                    .toJsonTree(Json.writeValueAsString(garages));
        });
    }
    private void deleteById(){
        delete("/user/:ownerId/garage/:garageId/price/:priceId", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));
            UUID priceId = UUID.fromString(request.params(":priceId"));

            //TODO
            boolean success = priceService.deleted(ownerId, garageId, priceId);
            response.status(success ? 204 : 400);
            return success ? "Deleted successfully" : "error for delete garage";
        });
    }
    private void update(){
        put("/user/:ownerId/garage/:garageId/price/:priceId", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));
            UUID priceId = UUID.fromString(request.params(":priceId"));

            //TODO
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
