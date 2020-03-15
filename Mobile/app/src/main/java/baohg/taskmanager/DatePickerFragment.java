package baohg.taskmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import baohg.taskmanager.baohg.constants.CommonConstant;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Calendar calendar;
    EditText editText;

    public DatePickerFragment() {
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    public Calendar getCalendar() {
        return calendar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),
                this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        setTextCalendar();
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public String getCalendarDate() {
        return new StringBuilder()
                .append(calendar.get(Calendar.YEAR))
                .append(CommonConstant.HYPHEN)
                .append(calendar.get(Calendar.MONTH) + 1)
                .append(CommonConstant.HYPHEN)
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .toString();
    }

    public void setTextCalendar(){
        editText.setText(getCalendarDate());
    }

}
