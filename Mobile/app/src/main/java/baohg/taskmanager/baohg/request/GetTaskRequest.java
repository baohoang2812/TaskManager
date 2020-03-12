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
     int handlerId;
     @SerializedName("statusId")
     int statusId;

     public GetTaskRequest() {
     }

     public GetTaskRequest(int userId, String startTime, String endTime, int handlerId, int statusId) {
          this.userId = userId;
          this.startTime = startTime;
          this.endTime = endTime;
          this.handlerId = handlerId;
          this.statusId = statusId;
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

     public void setHandlerId(int handlerId) {
          this.handlerId = handlerId;
     }

     public void setStatusId(int statusId) {
          this.statusId = statusId;
     }
}
