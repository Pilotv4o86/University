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
public class ViewService
{
    private StudentService studentService;
    private GroupService groupService;

    public List<GroupResponse> getAllGroups()
    {
        return groupService.getAllGroups();
    }
    public List<StudentResponse> getAllStudents(Long groupId)
    {
        return studentService.getAllStudents(groupId);
    }

    public StudentResponse createStudent(Long groupId, StudentRequest studentRequest)
    {
        return studentService.create(groupId, studentRequest);
    }

    public StudentResponse findByIdAndGroupId(Long studentId, Long groupId){
        return studentService.findByIdAndGroupId(studentId, groupId);
    }

    public StudentResponse updateStudent(Long studentId, Long groupId, StudentRequest studentRequest)
    {
        return studentService.update(studentId, groupId, studentRequest);
    }

    public GroupResponse createGroup(GroupRequest groupRequest)
    {
        return groupService.create(groupRequest);
    }

    public void deleteStudent(Long id, Long groupId)
    {
        studentService.deleteById(id, groupId);
    }

    public GroupResponse findGroupById(Long groupId)
    {
        return groupService.getGroupById(groupId);
    }

    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest)
    {
        return groupService.update(groupId, groupRequest);
    }

    public void deleteGroup(Long groupId)
    {
        groupService.delete(groupId);
    }
}
