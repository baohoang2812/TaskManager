package baohg.taskmanager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.request.UpdateUserRequest;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailFragment extends Fragment {
    EditText edtName, edtMail, edtPhone;
    EditText edtGroup;
    TextView txtRole;
    Button btnChangeRole, btnSave, btnDelete;
    TextView txtUserId;
    int userId;
    String userRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        edtName = view.findViewById(R.id.edtFullName);
        edtMail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        btnChangeRole = view.findViewById(R.id.btnChangeRole);
        btnSave = view.findViewById(R.id.btnSave);
        txtRole = view.findViewById(R.id.txtRole);
        edtGroup = view.findViewById(R.id.edtGroup);
        txtUserId = view.findViewById(R.id.txtUserId);
        btnDelete = view.findViewById(R.id.btnRemoveUser);
        ((MainActivity)getActivity()).setActionBarTitle("User Profile");
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getInt("userId", 0);
            loadUserProfile(userId);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        userRole = sharedPreferences.getString("userRole", "");
        if(!RoleConstant.ADMIN.equalsIgnoreCase(userRole)){
            setViewOnlyMode();
        }
        btnChangeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RoleConstant.USER.equalsIgnoreCase(txtRole.getText().toString())) {
                    txtRole.setText(RoleConstant.MANAGER);
                } else {
                    txtRole.setText(RoleConstant.USER);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String email = edtMail.getText().toString();
                String phone = edtPhone.getText().toString();
                String fullName = edtName.getText().toString();
                String errorMsg = "";
                if (isEmpty(fullName)) {
                    isValid = false;
                    errorMsg += "Fullname is required \n";
                }
                if (!isEmailValid(email)) {
                    isValid = false;
                    errorMsg += "Invalid email \n";
                }
                if (!isPhoneValid(phone)) {
                    isValid = false;
                    errorMsg += "Invalid phone \n";
                }
                if (isValid) {
                    UserDAO userDAO = new UserDAO();
                    UpdateUserRequest request = new UpdateUserRequest();
                    request.setEmail(email);
                    request.setPhone(phone);
                    request.setFullName(fullName);
                    request.setRoleName(txtRole.getText().toString());
                    String txtGroupId = edtGroup.getText().toString();
                    Integer groupId = txtGroupId.isEmpty() ? null : Integer.parseInt(txtGroupId);
                    request.setGroupId(groupId);
                    //TODO[BHG] - Change Passsword and User Code
                    userDAO.updateUser(userId, request, new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Update Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Invalid");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("Got It", null);
                    builder.show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("Are you sure to delete?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDAO userDAO = new UserDAO();
                        userDAO.removeUser(userId, new Callback<UserResponse>() {
                            @Override
                            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction().replace(R.id.fragmentContainer, new UserFragment()).commit();
                                } else {
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserResponse> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        return view;
    }

    private void loadUserProfile(int userId) {
        UserDAO userDAO = new UserDAO();

        userDAO.getUserProfile(userId, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == ResponseCodeConstant.OK) {
                        UserDTO userDTO = response.body().getData();
                        edtName.setText(userDTO.getFullName());
                        edtMail.setText(userDTO.getEmail());
                        edtPhone.setText(userDTO.getPhoneNumber());
                        txtRole.setText(userDTO.getRoleName());
                        txtUserId.setText(userDTO.getUserId() + "");
                        String txtGroupId = userDTO.getGroupId() != null ? userDTO.getGroupId() + "" : "";
                        edtGroup.setText(txtGroupId);
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "FAILURE" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isEmpty(String text) {
        return text.isEmpty();
    }

    private boolean isPhoneValid(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    private void setViewOnlyMode(){
        edtMail.setEnabled(false);
        edtGroup.setEnabled(false);
        edtName.setEnabled(false);
        edtPhone.setEnabled(false);
        btnChangeRole.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

    }
}
