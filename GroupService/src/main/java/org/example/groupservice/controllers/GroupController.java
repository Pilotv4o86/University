package org.example.groupservice.controllers;

import lombok.AllArgsConstructor;
import org.example.groupservice.dto.GroupRequest;
import org.example.groupservice.dto.GroupResponse;
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
    public ResponseEntity<List<GroupResponse>> getAllGroups()
    {
        return ResponseEntity.ok(groupService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest groupRequest)
    {

        GroupResponse savedGroupDto = groupService.create(groupRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGroupDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedGroupDto);

    }

    @DeleteMapping("/{groupId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long groupId)
    {
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupId}/update")
    public ResponseEntity<GroupResponse> update(@PathVariable Long groupId,
                                               @RequestBody GroupRequest groupRequest)
    {
        return ResponseEntity.accepted().body(groupService.update(groupId, groupRequest));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getById(@PathVariable Long groupId)
    {
        return ResponseEntity.ok(groupService.findById(groupId));
    }

    @GetMapping("/{groupId}/students/all-students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(@PathVariable Long groupId)
    {
        return ResponseEntity.ok(groupService.allStudents(groupId));
    }
}
