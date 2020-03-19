package baohg.taskmanager.baohg.dtos;

import java.io.Serializable;
import java.util.Date;

public class TaskDTO implements Serializable {
    public int taskId;
    public String name;
    public String description;
    public int sourceId;
    public String report;
    public String creator;
    public Date startTime;
    public Date endTime;
    public int handlerId;
    public Date createdTime;
    public int statusId;

    public TaskDTO() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getReport() {
        return report;
    }

    public String getCreator() {
        return creator;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getSourceId() {
        return sourceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getHandlerId() {
        return handlerId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setHandlerId(int handlerId) {
        this.handlerId = handlerId;
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
