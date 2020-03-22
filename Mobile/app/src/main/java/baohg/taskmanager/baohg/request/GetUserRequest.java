package baohg.taskmanager.baohg.request;

import java.io.Serializable;

public class GetUserRequest implements Serializable {
    Integer groupId;
    String Username;

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getUsername() {
        return Username;
    }
}
