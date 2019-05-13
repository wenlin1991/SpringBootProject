package wenlin.demo.PasswordService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.exceptions.RecordNotFoundException;
import wenlin.demo.PasswordService.service.impl.GroupServiceImpl;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private GroupServiceImpl groupService;

    @GetMapping
    public List<SystemGroup> groupList() {
        return groupService.findAll();
    }

    @GetMapping(value = "/{id}")
    public SystemGroup findGroupByGid(@PathVariable("id") Integer id) {
        SystemGroup group = groupService.findByGid(id);
        if (group == null) {
            throw new RecordNotFoundException(String.format("Group with input id %d is not found!", id));
        }
        return group;
    }

    // TODO :Finished method
    @GetMapping(value = "/query")
    public List<SystemGroup> findGroupsByFields(@RequestParam(required = false, value = "name") String name,
                                                @RequestParam(required = false, value = "gid") Integer gid,
                                                @RequestParam(required = false, value = "member") String[] members) {
        List<String> memberInList = Arrays.asList(members);
        return groupService.findGroupByCriteria(name, gid, memberInList);
    }

    @Autowired
    public void setGroupService(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }
}
