package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Plane;
import airport.core.models.storage.Storage;
import airport.core.ports.IStorage;
import airport.core.ports.IValidator;
import airport.core.validators.CapacityValidator;
import airport.core.validators.PlaneIdValidator;
import airport.core.validators.StringNotBlankValidator;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author becer
 */
public class PlaneController {

   
    private final IStorage storage;

    private final IValidator<String> planeIdValidator;
    private final IValidator<String> stringValidator;
    private final IValidator<Integer> capacityValidator;

    /* ---------- Constructor por inyección (tests) ---------- */
    public PlaneController(IStorage storage,
            IValidator<String> planeIdValidator,
            IValidator<String> stringValidator,
            IValidator<Integer> capacityValidator) {

        this.storage = storage;
        this.planeIdValidator = planeIdValidator;
        this.stringValidator = stringValidator;
        this.capacityValidator = capacityValidator;
    }

    /* ---------- Constructor por defecto (aplicación) ---------- */
    public PlaneController() {
        this(Storage.getInstance(),
                new PlaneIdValidator(),
                new StringNotBlankValidator(),
                new CapacityValidator());
    }

   
    public Response createPlane(String id, String brand, String model,
            int maxCapacity, String airline) {

        if (!planeIdValidator.isValid(id)) {
            return new Response(planeIdValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!stringValidator.isValid(brand)
                || !stringValidator.isValid(model)
                || !stringValidator.isValid(airline)) {
            return new Response("Brand, model and airline are required", Status.BAD_REQUEST);
        }
        if (!capacityValidator.isValid(maxCapacity)) {
            return new Response(capacityValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (storage.getPlaneById(id) != null) {
            return new Response("A plane with this ID already exists", Status.BAD_REQUEST);
        }

        Plane p = new Plane(id, brand, model, maxCapacity, airline);
        storage.addPlane(p);
        return new Response("Plane created successfully", Status.CREATED);
    }


    public Response getAllPlanes() {
        List<Plane> planes = storage.getAllPlanesCopy();
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
        List<Plane> planes = storage.getAllPlanesCopy();
        planes.sort((a, b) -> a.getBrand()
                .compareToIgnoreCase(b.getBrand()));
        return new Response("Plane list ordered by brand", Status.OK, planes);
    }

    public Response getAllPlanesOrdered() {
        List<Plane> planes = storage.getAllPlanesCopy();
        planes.sort(Comparator.comparing(Plane::getId));
        return new Response("Plane list ordered by ID", Status.OK, planes);
    }
}
