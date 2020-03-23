package baohg.taskmanager.baohg.dtos;

import java.io.Serializable;
import java.util.Date;

public class TaskDTO implements Serializable {
    public int taskId;
    public String name;
    public String description;
    public Integer sourceId;
    public String report;
    public String creator;
    public Date startTime;
    public Date endTime;
    public Integer handlerId;
    public String handlerName;
    public Date createdTime;
    public int statusId;
    public String statusName;
    public Integer mark;
    public String comment;
    public Date reviewedTime;

    public TaskDTO() {
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Integer handlerId) {
        this.handlerId = handlerId;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewedTime(Date reviewedTime) {
        this.reviewedTime = reviewedTime;
    }

    public Integer getMark() {
        return mark;
    }

    public String getComment() {
        return comment;
    }

    public Date getReviewedTime() {
        return reviewedTime;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Date getStartTime() {
        return startTime;
    }




    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
