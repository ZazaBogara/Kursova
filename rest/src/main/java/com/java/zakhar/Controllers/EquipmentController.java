package com.java.zakhar.controllers;

import com.java.zakhar.services.IEquipmentService;
import com.java.zakhar.services.InvalidEntityIdException;
import com.java.zakhar.services.dataobject.Equipment;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentController {
    private final IEquipmentService service;


    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public EquipmentController(IEquipmentService service) {
        this.service = service;
    }

    @GetMapping("/equipments")
    List<Equipment> all() {
        return service.getEquipments();
    }

    @PostMapping("/equipments")
    public Equipment addEquipment(@RequestBody Equipment newEquipment) throws Exception {
        int id = service.addEquipment(newEquipment);
        return service.getEquipment(id);
    }

    @GetMapping("/equipments/{id}")
    public Equipment one(@PathVariable int id) throws InvalidEntityIdException {
        return service.getEquipment(id);
    }

    @PutMapping("/equipments/{id}")
    public Equipment replaceEquipment(@RequestBody Equipment newEquipment, @PathVariable int id) throws Exception {
        newEquipment.setID(id);
        service.updateEquipment(newEquipment);
        return service.getEquipment(id);
    }

    @DeleteMapping("/equipments/{id}")
    public void deleteEquipment(@PathVariable int id) throws Exception {
        service.deleteEquipment(id);
    }
}
