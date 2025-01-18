package org.example.groupservice.service;

import jakarta.transaction.Transactional;
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
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final GroupMapper groupMapper;

    public List<GroupResponse> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toResponse).toList();
    }

    public GroupResponse create(GroupRequest groupRequest) {
        Group group = groupMapper.toGroup(groupRequest);
        if (groupRepository.findByName(group.getName()).isPresent()) {
            throw new DuplicateGroupException("Group with " + group.getName() + " already exists");
        }
        groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    public void delete(String groupName) {
        Group group = groupRepository.findByName(groupName).orElseThrow(() ->
                new GroupNotFoundException("Group not found with id: " + groupName));
        studentService.deleteAllStudents(group.getName());
        groupRepository.deleteByName(groupName);

    }

    public GroupResponse update(String groupName, GroupRequest updatedGroup) {
        Group oldGroup = groupRepository.getReferenceByName(groupName);
        groupMapper.copyFields(oldGroup, updatedGroup);
        return groupMapper.toResponse(groupRepository.save(oldGroup));
    }

    public GroupResponse findByName(String groupName) {
        return groupMapper.toResponse(groupRepository.findByName(groupName).orElseThrow(() ->
                new GroupNotFoundException("Group not found with name: " + groupName)));
    }

    public List<StudentResponse> allStudents(String groupName) {
        return studentService.getAllStudents(groupName);
    }
}
