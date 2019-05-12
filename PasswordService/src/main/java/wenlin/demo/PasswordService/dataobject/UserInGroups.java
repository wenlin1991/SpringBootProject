package wenlin.demo.PasswordService.dataobject;

import lombok.Getter;

import java.util.List;

/**
 * UserInGroups Indicates a user belong to which groups.
 *
 * @author wenlin
 */
@Getter
public class UserInGroups {

    private String name;

    private int gid;

    private List<String> groupMembers;

    /**
     * Construct to create UserInGroups object.
     *
     * @param name Sting user name
     * @param gid int group id of user
     * @param groupMembers List<String> groups that a user belongs to
     */
    public UserInGroups(String name, int gid, List<String> groupMembers) {
        this.name = name;
        this.gid = gid;
        this.groupMembers = groupMembers;
    }
}
