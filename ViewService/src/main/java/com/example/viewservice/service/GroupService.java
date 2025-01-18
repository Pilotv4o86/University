package com.example.viewservice.service;

import com.example.viewservice.client.GroupClient;
import com.example.viewservice.dto.GroupRequest;
import com.example.viewservice.dto.GroupResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupClient groupClient;

    public List<GroupResponse> getAllGroups() {
        return groupClient.getAllGroups();
    }

    public GroupResponse getGroupByName(String groupName) {
        return groupClient.getGroupByName(groupName);
    }

    public GroupResponse create(GroupRequest groupRequest) {
        return groupClient.create(groupRequest);
    }

    public GroupResponse update(String groupName, GroupRequest groupRequest) {
        return groupClient.update(groupName, groupRequest);
    }

    public void delete(String groupName) {
        groupClient.delete(groupName);
    }
}
