package com.java.zakhar.controllers;

import com.java.zakhar.services.IProjectService;
import com.java.zakhar.services.InvalidEntityIdException;
import com.java.zakhar.services.dataobject.Project;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    private final IProjectService service;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public ProjectController(IProjectService service) {
        this.service = service;
    }

    @GetMapping("/projects")
    public List<Project> all() {
        return service.getProjects();
    }

    @PostMapping("/projects")
    public Project addProject(@RequestBody Project newProject) throws Exception {
        int id = service.addProject(newProject);
        return service.getProject(id);
    }

    @GetMapping("/projects/{id}")
    public Project one(@PathVariable int id) throws InvalidEntityIdException {
        return service.getProject(id);
    }

    @PutMapping("/projects/{id}")
    public Project replaceProject(@RequestBody Project newProject, @PathVariable int id) throws Exception {
        newProject.setID(id);
        service.updateProject(newProject);
        return service.getProject(id);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable int id) throws Exception {
        service.deleteProject(id);
    }
}
