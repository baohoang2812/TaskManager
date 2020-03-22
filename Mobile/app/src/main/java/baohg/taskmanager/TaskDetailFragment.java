package baohg.taskmanager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.ResponseCodeConstant;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.constants.StatusConstant;
import baohg.taskmanager.baohg.daos.StatusDAO;
import baohg.taskmanager.baohg.daos.TaskDAO;
import baohg.taskmanager.baohg.dtos.StatusDTO;
import baohg.taskmanager.baohg.dtos.TaskDTO;
import baohg.taskmanager.baohg.request.UpdateTaskRequest;
import baohg.taskmanager.baohg.responses.StatusResponse;
import baohg.taskmanager.baohg.responses.TaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {
    EditText edtTaskName, edtDescription, edtReport, edtMark, edtComment;
    TextView txtStatus, txtCreatedTime, txtHandler, txtCreator, txtReviedTime;
    Button btnDeleteTask, btnSave, btnUpload, btnAccept, btnReject, btnFinish, btnFailed;
    TaskDAO taskDAO;
    int taskId;
    int statusId;
    DateRangePickerFragment dateRangePickerFragment;
    StatusDTO statusDTO;
    Integer handlerId;
    private static int RESULT_UPLOAD_IMAGE = 1;
    ImageView imageView;
    boolean isUser;

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        taskDAO = new TaskDAO();
        Bundle bundle = getArguments();
        taskId = bundle.getInt("taskId", 0);
        edtTaskName = view.findViewById(R.id.edtTaskName);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtReport = view.findViewById(R.id.edtReport);
        txtCreatedTime = view.findViewById(R.id.txtCreatedTime);
        txtHandler = view.findViewById(R.id.txtHandler);
        txtCreator = view.findViewById(R.id.txtCreator);
        txtStatus = view.findViewById(R.id.txtStatus);
        btnDeleteTask = view.findViewById(R.id.btnDeleteTask);
        btnSave = view.findViewById(R.id.btnSave);
        btnUpload = view.findViewById(R.id.btnUpload);
        imageView = view.findViewById(R.id.imgView);
        txtReviedTime = view.findViewById(R.id.txtReviewTime);
        edtComment = view.findViewById(R.id.edtComment);
        edtMark = view.findViewById(R.id.edtMark);
        btnAccept = view.findViewById(R.id.btnAccept);
        btnReject = view.findViewById(R.id.btnDecline);
        btnFinish = view.findViewById(R.id.btnFinish);
        btnFailed = view.findViewById(R.id.btnFailed);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("baohg.taskmanager_preferences", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(StatusConstant.PROCESSING);
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(StatusConstant.REJECTED);
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(StatusConstant.FINISHED);
            }
        });
        btnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(StatusConstant.FAILED);
            }
        });
        isUser = RoleConstant.USER.equalsIgnoreCase(userRole) ? true : false;
        if (isUser) {
            LinearLayout layout = view.findViewById(R.id.sectionReview);
            layout.setVisibility(View.GONE);
        }
        try {
            showTaskDetail();

            deleteTask();
            saveChanges();
            uploadConfirmationImage();
        } catch (Exception e) {
            Log.d("Task Detail Exception", e.getMessage());
        }
        return view;
    }

    public void deleteTask() {
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("Are you sure to delete?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDAO.deleteTask(taskId, new Callback<TaskResponse>() {
                            @Override
                            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                                if (response.isSuccessful()) {
                                    switch (response.code()) {
                                        case ResponseCodeConstant.NOT_FOUND: {
                                            Toast.makeText(getActivity(), "Task Not Found", Toast.LENGTH_SHORT).show();
                                        }
                                        case ResponseCodeConstant.ERROR: {
                                            Toast.makeText(getActivity(), "ERROR: " + response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                        case ResponseCodeConstant.NO_CONTENT: {
                                            getActivity()
                                                    .getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.fragmentContainer, new TaskFragment()).commit();
                                        }
                                        default:
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<TaskResponse> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

            }
        });
    }

    public void showTaskDetail() {

        taskDAO.getTaskDetail(taskId, new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    TaskResponse responseBody = response.body();
                    switch (response.code()) {
                        case ResponseCodeConstant.NOT_FOUND: {
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case ResponseCodeConstant.OK: {
                            TaskDTO taskDTO = responseBody.getData();
                            edtTaskName.setText(taskDTO.getName());
                            edtDescription.setText(taskDTO.getDescription());
                            edtReport.setText(taskDTO.getReport());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String createdTime = sdf.format(taskDTO.getCreatedTime());
                            txtCreatedTime.setText(createdTime);
                            handlerId = taskDTO.getHandlerId();
                            txtHandler.setText(taskDTO.getHandlerName());
                            txtCreator.setText(taskDTO.getCreator());
                            statusId = taskDTO.getStatusId();
                            txtStatus.setText(taskDTO.getStatusName());
                            edtComment.setText(taskDTO.getComment());
                            String txtMark = taskDTO.getMark() != null ? taskDTO.getMark().toString() : "";
                            edtMark.setText(txtMark);
                            String reviewedTime = taskDTO.getReviewedTime() != null ? sdf.format(taskDTO.getReviewedTime()) : "";
                            txtReviedTime.setText(reviewedTime);
                            dateRangePickerFragment = new DateRangePickerFragment();
                            Bundle bundle = new Bundle();
                            Calendar startTime = Calendar.getInstance();
                            if (taskDTO.getStartTime() != null) {
                                startTime.setTime(taskDTO.getStartTime());
                            }
                            Calendar endTime = Calendar.getInstance();
                            if (taskDTO.getEndTime() != null) {
                                endTime.setTime(taskDTO.getEndTime());
                            }
                            bundle.putSerializable("startTime", startTime);
                            bundle.putSerializable("endTime", endTime);
                            dateRangePickerFragment.setArguments(bundle);
                            getChildFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.dateRangePickerFragment, dateRangePickerFragment)
                                    .commit();

                            // set Buttons by Status
                            String status = txtStatus.getText().toString();
                            switch (status) {
                                case StatusConstant.NOT_STARTED: {
                                    btnFinish.setVisibility(View.GONE);
                                    btnFailed.setVisibility(View.GONE);
                                    break;
                                }
                                case StatusConstant.PROCESSING: {
                                    btnAccept.setVisibility(View.GONE);
                                    btnReject.setVisibility(View.GONE);
                                    break;
                                }
                                default:{
                                    btnAccept.setVisibility(View.GONE);
                                    btnReject.setVisibility(View.GONE);
                                }

                            }
                            break;
                        }
                        default: {
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "FAILURE", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    public void saveChanges() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String errorMsg = "";
                String name = edtTaskName.getText().toString();
                String description = edtDescription.getText().toString();
                if (isEmpty(name)) {
                    isValid = false;
                    errorMsg += "Name is required \n";
                }
                if (isEmpty(description)) {
                    isValid = false;
                    errorMsg += "Description is required \n";
                }
                String txtStartDate = dateRangePickerFragment.getEdtStartTime().getText().toString();
                String txtEndDate = dateRangePickerFragment.getEdtEndTime().getText().toString();
                if(!txtStartDate.isEmpty() && !txtEndDate.isEmpty()){
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = sdf.parse(txtStartDate);
                        Date endDate = sdf.parse(txtEndDate);
                        if(startDate.after(endDate)){
                            isValid = false;
                            errorMsg += "Start Date must before End Date \n";
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                UpdateTaskRequest request = new UpdateTaskRequest();
                String report = edtReport.getText().toString();
                request.setName(name);
                request.setDescription(description);

                request.setReport(report);
                request.setStartTime(txtStartDate);
                request.setCreatedTime(txtCreatedTime.getText().toString());
                request.setCreator(txtCreator.getText().toString());
                request.setStatusId(statusId);
                request.setEndTime(txtEndDate);
                request.setHandlerId(handlerId);
                if (!isUser) {
                    // get current time
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String reviewTime = sdf.format(date);
                    request.setReviewedTime(reviewTime);
                    request.setComment(edtComment.getText().toString());
                    Integer mark = edtMark.getText().toString().isEmpty() ? null : Integer.parseInt(edtMark.getText().toString());
                    if(mark != null && (mark <0 || mark >10)){
                        isValid = false;
                        errorMsg = "Mark must be [0-10] ";
                    }
                    request.setMark(mark);
                }
                if(isValid){
                    taskDAO.updateTask(taskId, request, new Callback<TaskResponse>() {
                        @Override
                        public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == ResponseCodeConstant.OK && StatusConstant.FAILED.equalsIgnoreCase(txtStatus.getText().toString())) {
                                    TaskCreationFragment creationFragment = new TaskCreationFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("source", response.body().getData());
                                    creationFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, creationFragment).commit();
                                }
                                Toast.makeText(getActivity(), "Update success", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TaskResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Update On Failure", Toast.LENGTH_SHORT).show();
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
    }

    public void uploadConfirmationImage() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_UPLOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_UPLOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                imageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void changeStatus(String name) {
        StatusDAO statusDAO = new StatusDAO();
        statusDAO.getAllStatus(name, new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful() && response.body().getStatusList().size() == 1) {
                    statusDTO = response.body().getStatusList().get(0);
                    if (statusDTO != null) {
                        txtStatus.setText(statusDTO.getName());
                        statusId = statusDTO.getStatusId();
                    } else {
                        Toast.makeText(getContext(), "Status not exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private boolean isEmpty(String text) {
        return text.isEmpty();
    }
}
