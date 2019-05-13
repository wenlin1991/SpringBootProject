package wenlin.demo.PasswordService.service;

import wenlin.demo.PasswordService.dataobject.SystemUser;

import java.util.List;

/**
 * UserService user query interface
 */
public interface UserService {
    List<SystemUser> findAll();
    SystemUser findByUid(int uid);
    SystemUser save(SystemUser user);
    List<SystemUser> findByFields(String name, Integer uid, Integer gid,
                                  String comment, String home, String bin);
}
