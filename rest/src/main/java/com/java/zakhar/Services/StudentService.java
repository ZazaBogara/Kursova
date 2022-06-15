package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.IDataStorage;
import com.java.zakhar.DataStorage.ProjectItem;
import com.java.zakhar.DataStorage.StudentItem;
import com.java.zakhar.DataStorage.StudentProjectItem;
import com.java.zakhar.Services.DataObject.Student;
import com.java.zakhar.Services.DataObject.StudentProject;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class StudentService implements IStudentService {
    IDataStorage dataStorage;


    private List<StudentProject> loadStudentProjects(int studentID) {
        List<StudentProject> StudentProjects = new LinkedList<>();
        for (StudentProjectItem studentProjectItem : dataStorage.getStudentProjects().getItems()) {
            if (studentProjectItem.getStudentID() == studentID) {
                ProjectItem projectItem = dataStorage.getProjects().getItem(studentProjectItem.getProjectID());
                StudentProject StudentProject = new StudentProject(studentProjectItem.getId(), projectItem.getId(), projectItem.getName());
                StudentProjects.add(StudentProject);
            }
        }
        return StudentProjects;
    }

    private Student loadStudent(StudentItem studentItem) {
        List<StudentProject> studentProjects = loadStudentProjects(studentItem.getId());
        return new Student(studentItem.getId(), studentItem.getFirstName(), studentItem.getLastName(), studentItem.getCourse(), studentProjects);
    }


    public List<Student> getStudents() {
        List<Student> result = new LinkedList<>();
        for (StudentItem studentItem : dataStorage.getStudents().getItems()) {
            Student student = loadStudent(studentItem);
            result.add(student);
        }
        return result;
    }

    public Student getStudent(int ID) throws InvalidEntityIdException {
        StudentItem studentItem = dataStorage.getStudents().getItem(ID);
        if (studentItem == null)
            throw new InvalidEntityIdException(String.format("Student with ID %d is not found", ID));

        return loadStudent(studentItem);
    }


    private void updateStudentProjects(int studentID, List<StudentProject> projects) throws IOException {
        // get existing projects
        HashMap<Integer, StudentProjectItem> existingProjects = new HashMap<>();
        for (StudentProjectItem studentProjectItem : dataStorage.getStudentProjects().getItems()) {
            if (studentProjectItem.getStudentID() == studentID) {
                existingProjects.put(studentProjectItem.getId(), studentProjectItem);
            }
        }
        boolean wasModified = false;
        // update projects
        for (StudentProject proj : projects) {
            StudentProjectItem existingItem = existingProjects.get(proj.getID());
            if (existingItem != null) {
                existingProjects.remove(proj.getID());
                if (existingItem.getProjectID() != proj.getProjectID()) {
                    dataStorage.getStudentProjects().setItem(new StudentProjectItem(existingItem.getId(), studentID, proj.getProjectID()));
                    wasModified = true;
                }
            } else {
                dataStorage.getStudentProjects().setItem(new StudentProjectItem(dataStorage.getProjectEquipments().getNewID(), studentID, proj.getProjectID()));
                wasModified = true;
            }
        }
        // remove unused
        for (StudentProjectItem studentProjectItem : existingProjects.values()) {
            dataStorage.getStudentProjects().deleteItem(studentProjectItem.getId());
            wasModified = true;
        }
        //
        if (wasModified)
            dataStorage.getStudentProjects().save();

    }

    public int addStudent(Student student) throws Exception {
        if (student.getID() != 0)
            throw new InvalidEntityIdException("StudentID must be 0 for adding new equipment");

        int id = dataStorage.getStudents().getNewID();
        StudentItem studentItem = new StudentItem(id, student.getFirstName(), student.getLastName(), student.getCourse());
        dataStorage.getStudents().setItem(studentItem);
        dataStorage.getStudents().save();

        if (student.getProjects() != null) {
            updateStudentProjects(id, student.getProjects());
        }
        return id;
    }

    public void updateStudent(Student student) throws Exception {
        StudentItem existingStudentItem = dataStorage.getStudents().getItem(student.getID());
        if (existingStudentItem == null)
            throw new InvalidEntityIdException(String.format("Student with ID %d is not found", student.getID()));

        StudentItem studentItem = new StudentItem(student.getID(), student.getFirstName(), student.getLastName(), student.getCourse());
        dataStorage.getStudents().setItem(studentItem);
        dataStorage.getStudents().save();

        if (student.getProjects() != null) {
            updateStudentProjects(student.getID(), student.getProjects());
        }
    }


    public void deleteStudent(int id) throws Exception {
        StudentItem existingStudentItem = dataStorage.getStudents().getItem(id);
        if (existingStudentItem == null)
            throw new InvalidEntityIdException(String.format("Student with ID %d is not found", id));

        updateStudentProjects(id, new ArrayList<>());

        dataStorage.getStudents().deleteItem(id);
        dataStorage.getStudents().save();
    }
}
