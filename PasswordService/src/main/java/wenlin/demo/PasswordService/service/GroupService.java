package wenlin.demo.PasswordService.service;

import wenlin.demo.PasswordService.dataobject.SystemGroup;

import java.util.List;

/**
 * GroupService group query interface
 */
public interface GroupService {

    List<SystemGroup> findAll();
    SystemGroup findByGid(int gid);
    SystemGroup save(SystemGroup systemGroup);
    List<SystemGroup> findGroupByCriteria(String name, int gid, List<String> members);
}
