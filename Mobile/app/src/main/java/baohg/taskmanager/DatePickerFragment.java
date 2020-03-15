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
    int year, month, day;
    Calendar calendar = Calendar.getInstance();
    EditText editText;

    public DatePickerFragment() {
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        editText.setText(getCalendarDate());
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public String getCalendarDate() {
        return new StringBuilder()
                .append(calendar.get(Calendar.YEAR))
                .append(CommonConstant.HYPHEN)
                .append(calendar.get(Calendar.MONTH))
                .append(CommonConstant.HYPHEN)
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .toString();
    }
}
