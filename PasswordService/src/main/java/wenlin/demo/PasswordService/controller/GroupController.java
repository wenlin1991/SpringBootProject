package wenlin.demo.PasswordService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
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
    public ResponseEntity<SystemGroup> findGroupByGid(@PathVariable("id") Integer id) {
        SystemGroup group = groupService.findByGid(id);
        if (group == null) {
            return new ResponseEntity<>(group, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping(value = "/query")
    public List<SystemGroup> findGroupsByFields(@RequestParam(required = false, value = "name") String name,
                                                @RequestParam(required = false, value = "gid") Integer gid,
                                                @RequestParam(required = false, value = "member") String[] members) {
        List<String> memberInList = members == null ? null : Arrays.asList(members);
        return groupService.findGroupByCriteria(name, gid, memberInList);
    }

    @Autowired
    public void setGroupService(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }
}
