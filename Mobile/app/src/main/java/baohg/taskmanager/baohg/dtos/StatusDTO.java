package baohg.taskmanager.baohg.dtos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class StatusDTO implements Serializable {
    @SerializedName("id")
    public int statusId;
    @SerializedName("name")
    public String name;

    public StatusDTO() {
    }

    public StatusDTO(int statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getName() {
        return name;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
