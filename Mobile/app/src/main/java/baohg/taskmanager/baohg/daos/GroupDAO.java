package baohg.taskmanager.baohg.daos;


import baohg.taskmanager.baohg.apis.APIClient;
import baohg.taskmanager.baohg.apis.GroupAPI;
import baohg.taskmanager.baohg.request.CreateGroupRequest;
import baohg.taskmanager.baohg.responses.GetGroupResponse;
import baohg.taskmanager.baohg.responses.GroupResponse;
import retrofit2.Callback;

public class GroupDAO {
    GroupAPI groupAPI;

    public GroupDAO() {
        groupAPI = APIClient.getTaskManagerClient().create(GroupAPI.class);
    }

    public void createGroup(CreateGroupRequest request, Callback<GroupResponse> callback) {
        groupAPI.createGroup(request).enqueue(callback);
    }

    public void getGroupDetail(int groupId, Callback<GroupResponse> callback) {
        groupAPI.getGroup(groupId).enqueue(callback);
    }

    public void getAllGroup(Callback<GetGroupResponse> callback){
        groupAPI.getAllGroup().enqueue(callback);
    }
}
