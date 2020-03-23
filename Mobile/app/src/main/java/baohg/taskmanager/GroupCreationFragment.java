package baohg.taskmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.daos.GroupDAO;
import baohg.taskmanager.baohg.request.CreateGroupRequest;
import baohg.taskmanager.baohg.responses.GroupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupCreationFragment extends Fragment {
    Button btnCreate;
    EditText edtName, edtDescription;
    int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_creation, container, false);
        btnCreate = view.findViewById(R.id.btnCreate);
        edtName = view.findViewById(R.id.edtGroupName);
        edtDescription = view.findViewById(R.id.edtDescription);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid =true;
                String errorMsg = "";
                String groupName = edtName.getText().toString();
                if(isEmpty(groupName)){
                    isValid =false;
                    errorMsg += "Group name is required \n";
                }
                if(isValid){
                    CreateGroupRequest request = new CreateGroupRequest();
                    request.setDescription(edtDescription.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    request.setCreatedTime(sdf.format(new Date()));
                    request.setUserId(userId);
                    request.setName(groupName);
                    GroupDAO groupDAO = new GroupDAO();
                    groupDAO.createGroup(request, new Callback<GroupResponse>() {
                        @Override
                        public void onResponse(Call<GroupResponse> call, Response<GroupResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction().replace(R.id.fragmentContainer, new GroupFragment()).commit();
                            } else {
                                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GroupResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Invalid");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("Got It", null);
                    builder.show();
                }
            }
        });
        return view;
    }

    private boolean isEmpty(String text){
        return text.isEmpty();
    }
}
