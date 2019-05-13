package wenlin.demo.PasswordService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wenlin.demo.PasswordService.dataobject.SystemUser;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, Integer> {

    @Query("SELECT u FROM SystemUser u WHERE (:name is null or u.name=:name) AND" +
            " (:uid is null or u.uid=:uid) AND" +
            " (:gid is null or u.gid=:gid) AND" +
            " (:comment is null or u.comment=:comment) AND" +
            " (:home is null or u.home=:home) AND (:shell is null or u.shell=:shell)")
    List<SystemUser> findByFields(@Param("name") String name,
                                  @Param("uid") Integer uid,
                                  @Param("gid") Integer gid,
                                  @Param("comment") String comment,
                                  @Param("home") String home,
                                  @Param("shell") String shell);

    // TODO: fix select message accordingly
    @Query(value = "SELECT u.name as name, u.gid as gid, g.name as member " +
            "FROM system_user u, system_group g " +
            "WHERE u.name=:name AND g.members LIKE %:name%", nativeQuery = true)
    List<Object[]> findByJoinGroups(@Param("name") String name);

    SystemUser findByUid(int id);
}
