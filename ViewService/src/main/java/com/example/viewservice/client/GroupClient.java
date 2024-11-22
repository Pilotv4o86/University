package com.example.viewservice.client;

import com.example.viewservice.dto.GroupRequest;
import com.example.viewservice.dto.GroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GroupService")
public interface GroupClient {
    @GetMapping("/groups/all-groups")
    List<GroupResponse> getAllGroups();

    @GetMapping("/groups/{groupId}")
    GroupResponse getGroupById(@PathVariable("groupId") Long groupId);

    @PostMapping("/groups/create")
    GroupResponse create(@RequestBody GroupRequest groupDto);

    @PutMapping("/groups/{groupId}/update")
    GroupResponse update(@PathVariable("groupId") Long groupId, @RequestBody GroupRequest groupDto);

    @DeleteMapping("/groups/{groupId}/delete")
    void delete(@PathVariable("groupId") Long groupId);
}