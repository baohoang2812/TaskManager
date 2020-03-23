package baohg.taskmanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.request.CreateUserRequest;
import baohg.taskmanager.baohg.responses.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCreateFragment extends Fragment {
    EditText edtUsername, edtEmail, edtPhone, edtPassword, edtFullName;
    Button btnCreate;
    boolean isValid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_creation, container, false);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtFullName = view.findViewById(R.id.edtFullName);
        btnCreate = view.findViewById(R.id.btnCreateUser);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = true;
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String fullName = edtFullName.getText().toString();
                String password = edtPassword.getText().toString();
                String errorMsg = "";
                String username = edtUsername.getText().toString().toLowerCase();

                if (!isEmailValid(email)) {
                    isValid = false;
                    errorMsg += "Invalid Email \n";
                }
                if (!isPhoneValid(phone)) {
                    isValid = false;
                    errorMsg += "Invalid Phone \n";
                }
                if (isEmpty(username)) {
                    isValid = false;
                    errorMsg += "Username is required \n";
                }
                if (isEmpty(fullName)) {
                    isValid = false;
                    errorMsg += "Fullname is required \n";
                }
                if (isEmpty(password)) {
                    isValid = false;
                    errorMsg += "Password is required \n";
                }
                //TODO check username exist
                if (isValid) {
                    UserDAO userDAO = new UserDAO();
                    CreateUserRequest request = new CreateUserRequest();
                    request.setEmail(email);
                    request.setFullName(fullName);
                    request.setPassword(password);
                    request.setPhone(phone);
                    request.setUsername(username);
                    userDAO.createUser(request, new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Create Success", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction().replace(R.id.fragmentContainer, new UserFragment()).commit();
                            } else {
                                TypeAdapter<UserResponse> adapter = new Gson().getAdapter(UserResponse.class);
                                try {
                                    if (response.errorBody() != null) {
                                        UserResponse userResponse = adapter.fromJson(response.errorBody().string());
                                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                                        alertBuilder.setTitle("Invalid");
                                        alertBuilder.setMessage(userResponse.getMessage());
                                        alertBuilder.setIcon(R.drawable.ic_warning);
                                        alertBuilder.setPositiveButton("Got It", null);
                                        alertBuilder.show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
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
        return view;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPhoneValid(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isEmpty(String text) {
        return text.isEmpty();
    }
}
