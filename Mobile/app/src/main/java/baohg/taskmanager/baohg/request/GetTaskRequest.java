package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

public class GetTaskRequest {
     @SerializedName("userId")
     int userId;
     @SerializedName("startTime")
     String startTime;
     @SerializedName("endTime")
     String endTime;
     @SerializedName("handlerId")
     Integer handlerId;
     @SerializedName("statusId")
     int statusId;

     public GetTaskRequest() {
     }

     public GetTaskRequest(int userId, String startTime, String endTime, Integer handlerId, int statusId) {
          this.userId = userId;
          this.startTime = startTime;
          this.endTime = endTime;
          this.handlerId = handlerId;
          this.statusId = statusId;
     }

     public int getUserId() {
          return userId;
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

     public int getStatusId() {
          return statusId;
     }

     public void setUserId(int userId) {
          this.userId = userId;
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

     public void setStatusId(int statusId) {
          this.statusId = statusId;
     }
}
