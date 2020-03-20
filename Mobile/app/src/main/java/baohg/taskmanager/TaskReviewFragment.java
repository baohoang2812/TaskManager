package baohg.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaskReviewFragment extends Fragment {
    EditText edtMark, edtComment;
    TextView txtReviewedTime;
    Button btnAccept, btnDecline, btnFinish;


    public TaskReviewFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_review, container, false);
        edtMark = view.findViewById(R.id.edtMark);
        edtComment = view.findViewById(R.id.edtComment);
        txtReviewedTime = view.findViewById(R.id.txtReviewTime);
        btnAccept = view.findViewById(R.id.btnAccept);
        btnDecline = view.findViewById(R.id.btnDecline);
        btnFinish = view.findViewById(R.id.btnFinish);
        return view;
    }

    public Button getBtnAccept() {
        return btnAccept;
    }

    public Button getBtnDecline() {
        return btnDecline;
    }

    public Button getBtnFinish() {
        return btnFinish;
    }
}
