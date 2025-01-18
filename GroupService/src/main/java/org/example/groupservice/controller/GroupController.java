package org.example.groupservice.controller;

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
public class GroupController {
    private GroupService groupService;

    @GetMapping("/all-groups")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest groupRequest) {

        GroupResponse savedGroupDto = groupService.create(groupRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGroupDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedGroupDto);

    }

    @DeleteMapping("/{groupName}/delete")
    public ResponseEntity<Void> delete(@PathVariable String groupName) {
        groupService.delete(groupName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupName}/update")
    public ResponseEntity<GroupResponse> update(@PathVariable String groupName,
                                                @RequestBody GroupRequest groupRequest) {
        return ResponseEntity.accepted().body(groupService.update(groupName, groupRequest));
    }

    @GetMapping("/{groupName}")
    public ResponseEntity<GroupResponse> getByName(@PathVariable String groupName) {
        return ResponseEntity.ok(groupService.findByName(groupName));
    }

    @GetMapping("/{groupName}/students/all-students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(@PathVariable String groupName) {
        return ResponseEntity.ok(groupService.allStudents(groupName));
    }
}
