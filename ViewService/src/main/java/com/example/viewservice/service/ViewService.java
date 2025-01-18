package com.example.viewservice.service;

import com.example.viewservice.dto.GroupRequest;
import com.example.viewservice.dto.GroupResponse;
import com.example.viewservice.dto.StudentRequest;
import com.example.viewservice.dto.StudentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ViewService {
    private StudentService studentService;
    private GroupService groupService;

    public List<GroupResponse> getAllGroups() {
        return groupService.getAllGroups();
    }

    public List<StudentResponse> getAllStudents(String groupId) {
        return studentService.getAllStudents(groupId);
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

}
