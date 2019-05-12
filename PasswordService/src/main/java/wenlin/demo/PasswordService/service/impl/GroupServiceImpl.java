package wenlin.demo.PasswordService.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenlin.demo.PasswordService.dao.GroupRepository;
import wenlin.demo.PasswordService.dataobject.SystemGroup;
import wenlin.demo.PasswordService.service.GroupService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * GroupServiceImpl SystemGroup query implementation
 */
@Service
public class GroupServiceImpl implements GroupService {

    GroupRepository groupRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // TODO : how about group id doesn't exist
    /**
     * findByGid find a group by group id
     *
     * @param gid int
     * @return SystemGroup
     */
    public SystemGroup findByGid(int gid) {
        return groupRepository.findByGid(gid);
    }

    /**
     * findAll return all SystemGroup in the database
     * @return List<SystemGroup>
     */
    public List<SystemGroup> findAll() {
        return groupRepository.findAll();
    }

    // TODO : how about insert null
    /**
     * save Save an input SystemGroup object into database
     * @param systemGroup
     * @return SystemGroup
     */
    public SystemGroup save(SystemGroup systemGroup) {
        return groupRepository.save(systemGroup);
    }

    /**
     * findGroupByCriteria find a group by input fields
     * @param name String
     * @param gid int
     * @param members List<String>
     * @return List<SystemGroup>
     */
    @Override
    public List<SystemGroup> findGroupByCriteria(String name, int gid, List<String> members) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SystemGroup> query = cb.createQuery(SystemGroup.class);
        Root<SystemGroup> systemGroup = query.from(SystemGroup.class);

        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(name))
            predicates.add(cb.equal(systemGroup.get("name"), name));
        if (!StringUtils.isBlank(String.valueOf(gid)))
            predicates.add(cb.equal(systemGroup.get("gid"), gid));
        query.select(systemGroup)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        List<SystemGroup> tempResult = entityManager.createQuery(query).getResultList();
        List<SystemGroup> remove = new ArrayList<>();

        for (SystemGroup group : tempResult) {
            for (String member : members) {
                if (!group.getMembers().contains(member)) {
                    remove.add(group);
                }
            }
        }

        tempResult.removeIf(remove::contains);

        return tempResult;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
