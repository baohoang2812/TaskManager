package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

public class CreateTaskRequest {
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("sourceId")
    Integer sourceId;
    @SerializedName("startTime")
    String startTime;
    @SerializedName("endTime")
    String endTime;
    @SerializedName("handlerId")
    Integer handlerId;

    public CreateTaskRequest() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Integer getHandlerId() {
        return handlerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setHandlerId(Integer handlerId) {
        this.handlerId = handlerId;
    }
}
