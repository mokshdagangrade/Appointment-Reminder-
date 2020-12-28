package com.example.appointmentreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddAppointmentActivity extends AppCompatActivity {
    TextView txtDate;
    TextView txtTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    int fyear=0;
    int fmonth=0;
    int fday=0;
    int fhour=0;
    int fmin=0;


    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        setCurrentDateandTime();
    }

    private void setCurrentDateandTime() {
        txtDate = (TextView)findViewById(R.id.textViewDate);
        txtTime = (TextView)findViewById(R.id.textViewTime);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        fyear=year;
        fmonth=month;
        fday=day;

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        fhour=hour;
        fmin=minute;

        // set current time into textview
        txtTime.setText(new StringBuilder().append(pad(hour))
                .append(":").append(pad(minute)));
        // set current date into textview
        txtDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }

    public String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return ("0" + String.valueOf(c));

    }

    public void editTextDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                //DatePickerDialog.OnDateSetListener datePickerListener = null;
                return new DatePickerDialog(this,
                        datePickerListener, year, month,day);
            case TIME_DIALOG_ID:
                // set time picker as current time
                //TimePickerDialog.OnTimeSetListener timePickerListener = null;
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;

    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            txtDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            // set current time into textview
            
            txtTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)));

        }
    };

    public void editTextTime(View view) {
        showDialog(TIME_DIALOG_ID);
    }

    public void buttonCancel(View view) {
        finish();
    }

    public void AddAppointmentBtn(View view) {
        EditText editAppointmentName = (EditText) findViewById(R.id.editTextName);
        Spinner spinnerAppointmentType = (Spinner) findViewById(R.id.spnTaskType);
        if(!(editAppointmentName.getText().toString()).isEmpty()){
            Intent intent = new Intent();

            intent.putExtra("name", editAppointmentName.getText().toString());

            intent.putExtra("type", spinnerAppointmentType.getSelectedItem().toString());

            intent.putExtra("monthOfYear", DisplayTheMonthInCharacters(month));
            intent.putExtra("dayOfMonth", day);
            intent.putExtra("year", year);

            intent.putExtra("hour", FormatTheHour(hour));
            intent.putExtra("minute", minute);
            intent.putExtra("AMorPM", AMorPM(hour));

            setResult(RESULT_OK, intent);

            finish();
        }
        else{
            Toast toast = Toast.makeText(AddAppointmentActivity.this, "Please enter an Appointment Name", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private int FormatTheHour(int hour) {
        if (hour > 12){ hour -= 12; }
        return hour;
    }

    private String AMorPM(int hour) {
        if (hour > 12){ return "PM"; }
        else{ return "AM"; }
    }

    private String DisplayTheMonthInCharacters(int month) {
        switch(month){
            case 0:
                return "Jan";
            case 1:
                return"Feb";
            case 2:
                return"Mar";
            case 3:
                return"Apr";
            case 4:
                return"May";
            case 5:
                return"Jun";
            case 6:
                return"Jul";
            case 7:
                return"Aug";
            case 8:
                return"Sept";
            case 9:
                return"Oct";
            case 10:
                return"Nov";
            case 11:
                return"Dec";

        }
        return "";
    }
}