package com.example.viewservice.client;

import com.example.viewservice.dto.GroupRequest;
import com.example.viewservice.dto.GroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GroupService")
public interface GroupClient {
    @GetMapping("/groups/all-groups")
    List<GroupResponse> getAllGroups();

    @GetMapping("/groups/{groupName}")
    GroupResponse getGroupByName(@PathVariable("groupName") String groupName);

    @PostMapping("/groups/create")
    GroupResponse create(@RequestBody GroupRequest groupDto);

    @PutMapping("/groups/{groupName}/update")
    GroupResponse update(@PathVariable("groupName") String groupName,
                         @RequestBody GroupRequest groupDto);

    @DeleteMapping("/groups/{groupName}/delete")
    void delete(@PathVariable("groupName") String groupName);
}