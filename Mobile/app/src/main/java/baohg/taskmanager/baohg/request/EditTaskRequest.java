package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

public class EditTaskRequest {
    @SerializedName("name")
    String name;
    @SerializedName("sourceId")
    int sourceId;
    @SerializedName("description")
    String description;
    @SerializedName("report")
    String report;
    @SerializedName("managerReview")
    String managerReview;
    @SerializedName("mark")
    int mark;
    @SerializedName("reviewedTime")
    String reviewedTime;
    @SerializedName("startTime")
    String startTime;
    @SerializedName("endTime")
    String endTime;
    @SerializedName("statusId")
    int statusId;
    @SerializedName("createdTime")
    String createdTime;
    @SerializedName("creator")
    String creator;
    @SerializedName("handlerId")
    int handlerId;
    @SerializedName("confirmationImage")
    String confirmationImage;

    public EditTaskRequest() {
    }

    public EditTaskRequest(String name, int sourceId, String description, String report,
                           String managerReview, int mark, String reviewedTime, String startTime,
                           String endTime, int statusId, String createdTime, String creator, int handlerId,
                           String confirmationImage) {
        this.name = name;
        this.sourceId = sourceId;
        this.description = description;
        this.report = report;
        this.managerReview = managerReview;
        this.mark = mark;
        this.reviewedTime = reviewedTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.statusId = statusId;
        this.createdTime = createdTime;
        this.creator = creator;
        this.handlerId = handlerId;
        this.confirmationImage = confirmationImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setManagerReview(String managerReview) {
        this.managerReview = managerReview;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setReviewedTime(String reviewedTime) {
        this.reviewedTime = reviewedTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setHandlerId(int handlerId) {
        this.handlerId = handlerId;
    }

    public void setConfirmationImage(String confirmationImage) {
        this.confirmationImage = confirmationImage;
    }
}
