package org.example.groupservice.mapper;

import org.example.groupservice.dto.GroupResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.example.groupservice.dto.GroupRequest;
import org.example.groupservice.model.Group;

@Component
public class GroupMapper
{
    private final ModelMapper mapper = new ModelMapper();

    public GroupResponse toResponse(Group group)
    {
        return mapper.map(group, GroupResponse.class);
    }
    public Group toGroup(GroupRequest groupRequest)
    {
        return mapper.map(groupRequest, Group.class);
    }
    public void copyFields(Group group, GroupRequest groupRequest)
    {
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(groupRequest, group);
    }
}
