package wenlin.demo.PasswordService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenlin.demo.PasswordService.dao.UserRepository;
import wenlin.demo.PasswordService.dataobject.SystemUser;
import wenlin.demo.PasswordService.dataobject.UserInGroups;
import wenlin.demo.PasswordService.service.UserService;
import wenlin.demo.PasswordService.utils.DataCleaner;

import java.util.List;

/**
 * UserServiceImpl SystemUser query implementation
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private DataCleaner dataCleaner;

    /**
     * findByUid find a SystemUser by input user id
     * @param uid int
     * @return SystemUser if found
     */
    public SystemUser findByUid(int uid) {
        return userRepository.findByUid(uid);
    }

    /**
     * findAll find all SystemUser in database
     * @return List<SystemUser>
     */
    public List<SystemUser> findAll() {
        return userRepository.findAll();
    }

    /**
     * save save an input SystemUser
     * @param user SystemUser
     * @return SystemUser or throw exception when fail
     */
    public SystemUser save(SystemUser user) {
        return userRepository.save(user);
    }

    // TODO : how about all the fields are null
    /**
     * findByFields find a SystemUser by input fields
     * @param name String
     * @param uid int
     * @param gid int
     * @param comment String
     * @param home String
     * @param bin String
     * @return List<SystemUser>
     */
    public List<SystemUser> findByFields(String name, int uid, int gid,
                                   String comment, String home, String bin) {
        return userRepository.findByFields(name, uid, gid, comment, home, bin);
    }

    // TODO : Try to return a list
    /**
     * findUserGroups find every group that contains current user by user name.
     * @param name String
     * @return UserInGroups
     */
    public UserInGroups findUserGroups(String name) {
        List<Object[]> queryResult = userRepository.findByJoinGroups(name);
        return dataCleaner.getUserGroups(queryResult);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setDataCleaner(DataCleaner dataCleaner) {
        this.dataCleaner = dataCleaner;
    }
}
