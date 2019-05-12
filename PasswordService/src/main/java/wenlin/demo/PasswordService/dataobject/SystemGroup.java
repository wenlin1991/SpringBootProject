package wenlin.demo.PasswordService.dataobject;

import lombok.Getter;
import wenlin.demo.PasswordService.utils.StringListConverter;

import javax.persistence.*;
import java.util.List;

/**
 * SystemGroup represents a group in Linux/Unix like system.
 * A SystemGroup entity can be find by group id or name.
 *
 * @author wenlin
 */
@Entity
@Getter
@Table(name = "system_group")
public class SystemGroup {
    private String name;

    @Id
    private int gid;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> members;

    public SystemGroup() {

    }

    /**
     * Construct a SystemGroup with name, gid and a list of members.
     *
     * @param name group name
     * @param gid  group id
     * @param members group members
     */
    public SystemGroup(String name, int gid, List<String> members) {
        this.gid = gid;
        this.name = name;
        this.members = members;
    }
}
