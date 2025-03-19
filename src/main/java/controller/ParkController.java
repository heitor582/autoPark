package controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.math.BigInteger;
import java.util.UUID;

import static spark.Spark.delete;
import static spark.Spark.post;
import static spark.Spark.put;

public class ParkController implements Routes{
    private void park(){
        post("/user/:ownerId/garage/:garageId/park", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String plate = jsonObject.get("plate").getAsString();

            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));

            boolean success = parkService.create(ownerId, garageId, plate);
            response.status(success ? 201 : 400);
            return success ? "Parked successfully" : "error";
        });
    }
    private void parkOcr(){
        post("/user/:ownerId/garage/:garageId/park/ocr", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));

            String plate = ocrService.getPlate(file);

            boolean success = parkService.create(ownerId, garageId, plate);
            response.status(success ? 201 : 400);
            return success ? "Parked successfully" : "error";
        });
    }
    private void unpark(){
        delete("/user/:ownerId/garage/:garageId/unpark", (request, response) -> {
            String body = request.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String plate = jsonObject.get("plate").getAsString();

            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));

            BigInteger price = parkService.delete(ownerId, garageId, plate);
            return price.compareTo(BigInteger.valueOf(-1)) < 0? "error" : price;
        });
    }
    private void unparkOcr(){
        delete("/user/:ownerId/garage/:garageId/unpark/ocr", (request, response) -> {
            UUID ownerId = UUID.fromString(request.params(":ownerId"));
            UUID garageId = UUID.fromString(request.params(":garageId"));

            String plate = ocrService.getPlate(file);

            BigInteger price = parkService.delete(ownerId, garageId, plate);
            return price.compareTo(BigInteger.valueOf(-1)) < 0? "error" : price;
        });
    }
    @Override
    public void setupRoutes() {
        park();
        unpark();
    }
}
