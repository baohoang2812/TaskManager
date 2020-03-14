package baohg.taskmanager.baohg.dtos;

import java.util.Date;

public class TaskDTO{
    public int taskId;
    public String name;
    public String description;
    public Date endTime;

    public TaskDTO() {
    }

    public int getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
