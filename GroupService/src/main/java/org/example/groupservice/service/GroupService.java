package org.example.groupservice.service;

import lombok.RequiredArgsConstructor;
import org.example.groupservice.exceptions.DuplicateGroupException;
import org.example.groupservice.exceptions.GroupNotFoundException;
import org.example.groupservice.mapper.GroupMapper;
import org.example.groupservice.repository.GroupRepository;
import org.example.groupservice.dto.GroupDto;
import org.example.groupservice.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService
{
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public List<GroupDto> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto).toList();
    }

    public GroupDto create(GroupDto groupDto)
    {
        Group group = groupMapper.toGroup(groupDto);
        if (groupRepository.findByName(group.getName()).isPresent())
        {
            throw new DuplicateGroupException("Group with " + group.getName() + " already exists");
        }
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    public void delete(Integer id)
    {
        groupRepository.findById(id).orElseThrow(() ->
                new GroupNotFoundException("Group not found with id: " + id));
        groupRepository.deleteById(id);
    }

    public GroupDto update(Integer id, GroupDto updatedGroup)
    {
        Group oldGroup = groupRepository.getReferenceById(id);
        groupMapper.copyFields(oldGroup, updatedGroup);

        if (groupRepository.findByName(updatedGroup.getName()).isPresent())
        {
            throw new DuplicateGroupException("Group with " + updatedGroup.getName() + " already exists");
        }

        return groupMapper.toDto(groupRepository.save(oldGroup));
    }

    public GroupDto findById(Integer id) {
        return groupMapper.toDto(groupRepository.findById(id).orElseThrow(() ->
                new GroupNotFoundException("Group not found with id: " + id)));
    }

  }
