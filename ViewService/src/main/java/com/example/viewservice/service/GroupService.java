package com.example.viewservice.service;

import com.example.viewservice.client.GroupClient;
import com.example.viewservice.dto.GroupRequest;
import com.example.viewservice.dto.GroupResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService
{
    private GroupClient groupClient;
    public List<GroupResponse> getAllGroups()
    {
        return groupClient.getAllGroups();
    }
    public GroupResponse getGroupById(Long id)
    {
        return groupClient.getGroupById(id);
    }
    public GroupResponse create(GroupRequest groupRequest)
    {
        return groupClient.create(groupRequest);
    }
    public GroupResponse update(Long id, GroupRequest groupRequest)
    {
        return groupClient.update(id, groupRequest);
    }
    public void delete(Long id)
    {
        groupClient.delete(id);
    }
}
