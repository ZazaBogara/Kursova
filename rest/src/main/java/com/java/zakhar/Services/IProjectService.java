package com.java.zakhar.Services;

import com.java.zakhar.Services.DataObject.Project;

import java.util.List;

public interface IProjectService {
    List<Project> getProjects();

    Project getProject(int ID) throws InvalidEntityIdException;

    int addProject(Project project) throws Exception;

    void updateProject(Project project) throws Exception;

    void deleteProject(int id) throws Exception;
}
