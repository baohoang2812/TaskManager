package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateTaskRequest implements Serializable {
    @SerializedName("name")
    String name;
    @SerializedName("sourceId")
    Integer sourceId;
    @SerializedName("description")
    String description;
    @SerializedName("startTime")
    String startTime;
    @SerializedName("endTime")
    String endTime;
    @SerializedName("creator")
    String creator;
    @SerializedName("report")
    String report;
    @SerializedName("createdTime")
    String createdTime;
    @SerializedName("statusId")
    int statusId;

    public UpdateTaskRequest() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCreator() {
        return creator;
    }


    public String getReport() {
        return report;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
