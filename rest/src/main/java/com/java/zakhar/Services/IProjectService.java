package com.java.zakhar.services;

import com.java.zakhar.services.dataobject.Project;

import java.util.List;

public interface IProjectService {
    List<Project> getProjects();

    Project getProject(int ID) throws InvalidEntityIdException;

    int addProject(Project project) throws Exception;

    void updateProject(Project project) throws Exception;

    void deleteProject(int id) throws Exception;
}
