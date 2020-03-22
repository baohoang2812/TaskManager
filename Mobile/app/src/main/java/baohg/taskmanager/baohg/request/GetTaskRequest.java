package baohg.taskmanager.baohg.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetTaskRequest implements Serializable {
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
     @SerializedName("handlerCode")
     String handlerCode;

     public GetTaskRequest() {
     }

     public String getHandlerCode() {
          return handlerCode;
     }

     public void setHandlerCode(String handlerCode) {
          this.handlerCode = handlerCode;
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
