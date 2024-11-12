package org.example.groupservice.controllers;

import lombok.AllArgsConstructor;
import org.example.groupservice.dto.GroupDto;
import org.example.groupservice.dto.StudentResponse;
import org.example.groupservice.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController
{
    private GroupService groupService;

    @GetMapping("/all-groups")
    public ResponseEntity<List<GroupDto>> getAllGroups()
    {
        return ResponseEntity.ok(groupService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<GroupDto> create(@RequestBody GroupDto groupDto)
    {

        GroupDto savedGroupDto = groupService.create(groupDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGroupDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedGroupDto);

    }

    @DeleteMapping("/{groupId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Integer groupId)
    {
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupId}/update")
    public ResponseEntity<GroupDto> update(@PathVariable Integer groupId,
                                           @RequestBody GroupDto groupDto)
    {
        return ResponseEntity.accepted().body(groupService.update(groupId, groupDto));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getById(@PathVariable Integer groupId)
    {
        return ResponseEntity.ok(groupService.findById(groupId));
    }

    @GetMapping("/{groupId}/students/all-students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(@PathVariable Integer groupId)
    {
        return ResponseEntity.ok(groupService.allStudents(groupId));
    }
}
