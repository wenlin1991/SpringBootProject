package wenlin.demo.PasswordService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wenlin.demo.PasswordService.dataobject.SystemGroup;

@Repository
public interface GroupRepository extends JpaRepository<SystemGroup, Integer> {

    SystemGroup findByGid(int gid);

}
