package com.example.viewservice.service;

import com.example.viewservice.dto.group.GroupRequest;
import com.example.viewservice.dto.group.GroupResponse;
import com.example.viewservice.dto.student.StudentRequest;
import com.example.viewservice.dto.student.StudentResponse;
import com.example.viewservice.dto.user.CreateUserRequest;
import com.example.viewservice.dto.user.LoginUserRequest;
import com.example.viewservice.dto.user.UpdateUserRequest;
import com.example.viewservice.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ViewService {
    private StudentService studentService;
    private GroupService groupService;
    private UserService userService;


    public List<GroupResponse> getAllGroups() {
        return groupService.getAllGroups();
    }
    public List<GroupResponse> getAllGroupsSorted(String sortBy, String order) {
        List<GroupResponse> groups = getAllGroups(); // Получение списка групп

        Comparator<GroupResponse> comparator;
        switch (sortBy) {
            case "course":
                comparator = Comparator.comparing(GroupResponse::getCourse);
                break;
            case "formOfEducation":
                comparator = Comparator.comparing(GroupResponse::getFormOfEducation);
                break;
            case "speciality":
                comparator = Comparator.comparing(GroupResponse::getSpeciality);
                break;
            default:
                comparator = Comparator.comparing(GroupResponse::getName);
                break;
        }

        // Если порядок "desc", меняем порядок сортировки
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return groups.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

    }
    public List<StudentResponse> getAllStudents(String groupId) {
        return studentService.getAllStudents(groupId);
    }
    public List<StudentResponse> getAllStudentsSorted(String groupName, String sortBy, String order) {
        List<StudentResponse> students = getAllStudents(groupName); // Получение списка студентов

        Comparator<StudentResponse> comparator;
        switch (sortBy) {
            case "firstName":
                comparator = Comparator.comparing(StudentResponse::getFirstName);
                break;
            case "patronymic":
                comparator = Comparator.comparing(StudentResponse::getPatronymic);
                break;
            case "gender":
                comparator = Comparator.comparing(StudentResponse::getGender);
                break;
            case "birthday":
                comparator = Comparator.comparing(StudentResponse::getBirthday);
                break;
            case "formOfEducation":
                comparator = Comparator.comparing(StudentResponse::getFormOfEducation);
                break;
            case "numberOfFailedExams":
                comparator = Comparator.comparingInt(StudentResponse::getNumberOfFailedExams);
                break;
            default:
                comparator = Comparator.comparing(StudentResponse::getSurname);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public StudentResponse createStudent(String groupName,
                                         StudentRequest studentRequest) {
        return studentService.create(groupName, studentRequest);
    }
    public StudentResponse getByIdAndGroupName(Long studentId,
                                               String groupName) {
        return studentService.getByIdAndGroupName(studentId, groupName);
    }
    public StudentResponse updateStudent(Long studentId,
                                         String groupName,
                                         StudentRequest studentRequest) {
        return studentService.update(studentId, groupName, studentRequest);
    }



    public GroupResponse createGroup(GroupRequest groupRequest) {
        return groupService.create(groupRequest);
    }
    public void deleteStudent(Long id,
                              Long groupId) {
        studentService.deleteById(id, groupId);
    }
    public GroupResponse getGroupByName(String groupName) {
        return groupService.getGroupByName(groupName);
    }
    public GroupResponse updateGroup(String groupName,
                                     GroupRequest groupRequest) {
        return groupService.update(groupName, groupRequest);
    }

    public void deleteGroup(String groupName) {
        groupService.delete(groupName);
    }



    public UserResponse register(CreateUserRequest createUserRequest) {
        return userService.register(createUserRequest);
    }
    public UserResponse getUserById(Long id) {
        return userService.getUserById(id);
    }
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }
    public UserResponse deleteUser(Long id) {
        return userService.deleteUser(id);
    }
    public UserResponse updateUser(Long id,
                                   UpdateUserRequest updateUserRequest) {
        return userService.updateUser(id, updateUserRequest);
    }
    public UserResponse login(LoginUserRequest loginUserRequest) {
        return userService.login(loginUserRequest);
    }

    public String getRoleForUser(String username) {
        if ("admin".equals(username)) {
            return "ADMIN";
        }
        return "USER";
    }

}
