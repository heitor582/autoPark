package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Garage;
import model.Price;
import service.PriceService;
import utils.Json;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class PriceController implements Routes{
    private final PriceService priceService;

    public PriceController(final PriceService priceService) {
        this.priceService = priceService;
    }

    private void create(){
        post("/user/:ownerId/garage/:garageId/price", (request, response) -> {
            UUID garageId = UUID.fromString(request.params(":garageId"));
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            int aboveTime = jsonObject.get("aboveTime").getAsInt();
            BigInteger price = BigInteger.valueOf(jsonObject.get("price").getAsLong());

            boolean success = priceService.create(garageId, aboveTime, price);
            response.status(success ? 201 : 400);
            return success ? "Create successfully" : "error for create price";
        });
    }

    private void getAll(){
        get("/user/:ownerId/garage/:garageId/price", (request, response) -> {
            UUID garageId = UUID.fromString(request.params(":garageId"));

            List<Price> prices = priceService.getAllBy(garageId);
            response.status(prices.isEmpty() ? 204 : 200);
            return new Gson()
                    .toJsonTree(Json.writeValueAsString(prices));
        });
    }
    private void deleteById(){
        delete("/user/:ownerId/garage/:garageId/price/:priceId", (request, response) -> {
            UUID garageId = UUID.fromString(request.params(":garageId"));
            UUID priceId = UUID.fromString(request.params(":priceId"));

            boolean success = priceService.delete(garageId, priceId);
            response.status(success ? 204 : 400);
            return success ? "Deleted successfully" : "error for delete garage";
        });
    }
    private void update(){
        put("/user/:ownerId/garage/:garageId/price/:id", (request, response) -> {
            UUID garageId = UUID.fromString(request.params(":garageId"));
            UUID priceId = UUID.fromString(request.params(":id"));

            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            int aboveTime = jsonObject.get("aboveTime").getAsInt();
            BigInteger price = BigInteger.valueOf(jsonObject.get("price").getAsLong());

            boolean success = priceService.update(garageId, priceId, aboveTime, price);
            response.status(success ? 200 : 400);
            return success ? "Update successfully" : "error for update price";
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
