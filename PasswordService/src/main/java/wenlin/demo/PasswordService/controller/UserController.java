package wenlin.demo.PasswordService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.dataobject.UserInGroups;
import wenlin.demo.PasswordService.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserServiceImpl userServiceImpl;

    @GetMapping
    public List<SystemUser> findAll() {
        return userServiceImpl.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SystemUser> findUserById(@PathVariable("id") Integer id) {
        SystemUser user = userServiceImpl.findByUid(id);
        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/query")
    public List<SystemUser> findUsersByFields(@RequestParam(required = false, value = "name") String name,
                                              @RequestParam(required = false, value = "uid") Integer uid,
                                              @RequestParam(required = false, value = "gid") Integer gid,
                                              @RequestParam(required = false, value = "comment") String comment,
                                              @RequestParam(required = false, value = "home") String home,
                                              @RequestParam(required = false, value = "bin") String bin) {
        log.info(String.format("The input parameters are name: %s and uid: %s.", name, uid));
        return userServiceImpl.findByFields(name, uid, gid, comment, home, bin);
    }

    @GetMapping(value = "/{id}/groups")
    public List<UserInGroups> findUserGroups(@PathVariable("id") String id) {
        SystemUser user = userServiceImpl.findByUid(Integer.valueOf(id));
        String userName = user.getName();
        return userServiceImpl.findUserGroups(userName);
    }

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
}
