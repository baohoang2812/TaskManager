package baohg.taskmanager.baohg.dtos;

import java.io.Serializable;
import java.util.Date;

public class GroupDTO implements Serializable {
    int groupId;
    int managerId;
    String description;
    Date createdTime;
    String name;

    public GroupDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
