package org.example.groupservice.service;

import lombok.RequiredArgsConstructor;
import org.example.groupservice.dto.GroupResponse;
import org.example.groupservice.dto.StudentResponse;
import org.example.groupservice.exception.DuplicateGroupException;
import org.example.groupservice.exception.GroupNotFoundException;
import org.example.groupservice.mapper.GroupMapper;
import org.example.groupservice.repository.GroupRepository;
import org.example.groupservice.dto.GroupRequest;
import org.example.groupservice.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService
{
    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final GroupMapper groupMapper;

    public List<GroupResponse> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toResponse).toList();
    }

    public GroupResponse create(GroupRequest groupRequest)
    {
        Group group = groupMapper.toGroup(groupRequest);
        if (groupRepository.findByName(group.getName()).isPresent())
        {
            throw new DuplicateGroupException("Group with " + group.getName() + " already exists");
        }
        groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    public void delete(Long groupId)
    {
        groupRepository.findById(groupId).orElseThrow(() ->
                new GroupNotFoundException("Group not found with id: " + groupId));
        studentService.deleteAllStudents(groupId);
        groupRepository.deleteById(groupId);

    }

    public GroupResponse update(Long id, GroupRequest updatedGroup)
    {
        Group oldGroup = groupRepository.getReferenceById(id);
        groupMapper.copyFields(oldGroup, updatedGroup);
        return groupMapper.toResponse(groupRepository.save(oldGroup));
    }

    public GroupResponse findById(Long id) {
        return groupMapper.toResponse(groupRepository.findById(id).orElseThrow(() ->
                new GroupNotFoundException("Group not found with id: " + id)));
    }

    public List<StudentResponse> allStudents(Long groupId)
    {
        return studentService.getAllStudents(groupId);
    }
  }
