package com.java.zakhar.Controllers;

import com.java.zakhar.Services.DataObject.Project;
import com.java.zakhar.Services.IProjectService;
import com.java.zakhar.Services.InvalidEntityIdException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    private final IProjectService service;

    public ProjectController(IProjectService service) {
        this.service = service;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/projects")
    List<Project> all() {
        return service.getProjects();
    }
    // end::get-aggregate-root[]

    @PostMapping("/projects")
    Project addProject(@RequestBody Project newProject) throws Exception {
        int id = service.addProject(newProject);
        return service.getProject(id);
    }

    // Single item

    @GetMapping("/projects/{id}")
    Project one(@PathVariable int id) throws InvalidEntityIdException {
        return service.getProject(id);
    }

    @PutMapping("/projects/{id}")
    Project replaceProject(@RequestBody Project newProject, @PathVariable int id) throws Exception {
        newProject.setID(id);
        service.updateProject(newProject);
        return service.getProject(id);
    }

    @DeleteMapping("/projects/{id}")
    void deleteProject(@PathVariable int id) throws Exception {
        service.deleteProject(id);
    }
}
