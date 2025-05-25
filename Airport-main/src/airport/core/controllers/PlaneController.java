/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Plane;
import airport.core.models.storage.Storage;
import airport.core.services.Validator;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author becer
 */
public class PlaneController {

    private final Storage storage;

    public PlaneController() {
        this.storage = Storage.getInstance();
    }

    public Response createPlane(String id, String brand, String model, int maxCapacity, String airline) {

        if (!Validator.isValidPlaneId(id)) {
            return new Response("Invalid plane ID format (expected format: AA12345)", Status.BAD_REQUEST);
        }

        if (!Validator.isValidString(id)) {
            return new Response("Plane ID is required", Status.BAD_REQUEST);
        }

        if (!Validator.isValidString(brand) || !Validator.isValidString(model) || !Validator.isValidString(airline)) {
            return new Response("Brand, model and airline are required", Status.BAD_REQUEST);
        }

        if (!Validator.isValidCapacity(maxCapacity)) {
            return new Response("Max capacity must be greater than 0", Status.BAD_REQUEST);
        }

        if (storage.getPlaneById(id) != null) {
            return new Response("A plane with this ID already exists", Status.BAD_REQUEST);
        }

        Plane p = new Plane(id, brand, model, maxCapacity, airline);
        storage.addPlane(p);
        return new Response("Plane created successfully", Status.CREATED);
    }

    public Response getAllPlanes() {
        ArrayList<Plane> planes = storage.getAllPlanesCopy();
        return new Response("Plane list loaded", Status.OK, planes);
    }

    public Response getPlaneById(String id) {
        Plane p = storage.getPlaneById(id);
        if (p == null) {
            return new Response("Plane not found", Status.NOT_FOUND);
        }
        return new Response("Plane found", Status.OK, p.copy());
    }

    public Response getAllPlanesOrderedByBrand() {
        ArrayList<Plane> planes = storage.getAllPlanesCopy();

        planes.sort((a, b) -> a.getBrand().compareToIgnoreCase(b.getBrand()));

        return new Response("Plane list ordered by brand", Status.OK, planes);
    }

    public Response getAllPlanesOrdered() {
        ArrayList<Plane> planes = storage.getAllPlanesCopy();
        planes.sort(Comparator.comparing(Plane::getId));
        return new Response("Plane list ordered by ID", Status.OK, planes);
    }
}
