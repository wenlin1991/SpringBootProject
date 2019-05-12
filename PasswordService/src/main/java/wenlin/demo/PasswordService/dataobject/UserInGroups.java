package wenlin.demo.PasswordService.dataobject;

import lombok.Getter;

import java.util.List;

@Getter
public class UserInGroups {

    private String name;

    private int gid;

    private List<String> groupMembers;

    public UserInGroups(String name, int gid, List<String> groupMembers) {
        this.name = name;
        this.gid = gid;
        this.groupMembers = groupMembers;
    }
}
