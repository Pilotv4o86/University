package org.example.groupservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.example.groupservice.dto.GroupDto;
import org.example.groupservice.model.Group;

@Component
public class GroupMapper
{
    private final ModelMapper mapper = new ModelMapper();

    public GroupDto toDto(Group group)
    {
        return mapper.map(group, GroupDto.class);
    }
    public Group toGroup(GroupDto groupDto)
    {
        return mapper.map(groupDto, Group.class);
    }
    public void copyFields(Group group, GroupDto groupDto)
    {
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(groupDto, group);
    }
}
