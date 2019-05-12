package wenlin.demo.PasswordService.dataobject;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemUser represents a user in Linux/Unix like system.
 * A user entity can be found by either name or user id.
 *
 * @author wenlin
 */
@Entity
@Getter
@Table(name = "system_user")
public class SystemUser {

    private String name;

    @Id
    private int uid;

    private int gid;

    private String comment;

    private String home;

    private String shell;

    /**
     * Use builder to create SystemUser object.
     */
    public static class Builder {

        private String name;
        private int uid;
        private int gid;
        private String comment;
        private String home;
        private String shell;

        public Builder() {

        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public Builder setGid(int gid) {
            this.gid = gid;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setHome(String home) {
            this.home = home;
            return this;
        }

        public Builder setShell(String shell) {
            this.shell = shell;
            return this;
        }

        public SystemUser build() {
            SystemUser systemUser = new SystemUser();
            systemUser.uid = this.uid;
            systemUser.gid = this.gid;
            systemUser.comment = this.comment;
            systemUser.name = this.name;
            systemUser.home = this.home;
            systemUser.shell = this.shell;
            return systemUser;
        }
    }
}
