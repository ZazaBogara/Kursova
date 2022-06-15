package com.java.zakhar.Controllers;

import com.java.zakhar.Services.DataObject.Equipment;
import com.java.zakhar.Services.IEquipmentService;
import com.java.zakhar.Services.InvalidEntityIdException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentController {
    private final IEquipmentService service;

    public EquipmentController(IEquipmentService service) {
        this.service = service;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/equipments")
    List<Equipment> all() {
        return service.getEquipments();
    }
    // end::get-aggregate-root[]

    @PostMapping("/equipments")
    Equipment addEquipment(@RequestBody Equipment newEquipment) throws Exception {
        int id = service.addEquipment(newEquipment);
        return service.getEquipment(id);
    }

    // Single item

    @GetMapping("/equipments/{id}")
    Equipment one(@PathVariable int id) throws InvalidEntityIdException {
        return service.getEquipment(id);
    }

    @PutMapping("/equipments/{id}")
    Equipment replaceEquipment(@RequestBody Equipment newEquipment, @PathVariable int id) throws Exception {
        newEquipment.setID(id);
        service.updateEquipment(newEquipment);
        return service.getEquipment(id);
    }

    @DeleteMapping("/equipments/{id}")
    void deleteEquipment(@PathVariable int id) throws Exception {
        service.deleteEquipment(id);
    }
}
