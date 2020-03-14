package baohg.taskmanager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {


    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        int taskId = bundle.getInt("taskId", 1);
        Toast.makeText(getActivity(), "Task ID: " + taskId, Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_task_detail, container, false);

    }

}
