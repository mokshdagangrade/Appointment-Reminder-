package com.example.appointmentreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Appointment>appointmentArrayList=new ArrayList<Appointment>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
        CreateTestAppointments();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Appointment_Array_List",appointmentArrayList);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        appointmentArrayList=savedInstanceState.getParcelableArrayList("Appointment_Array_List");
        for(int i=0;i<appointmentArrayList.size();i++){
            PopulateTable(i);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void CreateTestAppointments() {
        appointmentArrayList.add(new Appointment("Doctors Visit","Health", "Oct", 9, 2016, 9, 00, "AM"));
        appointmentArrayList.add(new Appointment("Hair Cut appointment","Personal","Oct", 10, 2016,9,30,"AM"));
        appointmentArrayList.add(new Appointment("Meeting with Accountant","Personal","Oct", 11, 2016,11,00,"AM"));
        appointmentArrayList.add(new Appointment("Boss/HR Meeting","Work","Oct", 12, 2016,2,30,"PM"));
        appointmentArrayList.add(new Appointment("Teacher Conference","School","Nov", 1, 2016,9,30,"AM"));
        appointmentArrayList.add(new Appointment("Dentist For Son","Health","Nov", 1, 2016,9,30,"AM"));
        appointmentArrayList.add(new Appointment("Dinner With Friends","Other","Nov", 1, 2016,9,30,"AM"));

        for(int i = 0; i < appointmentArrayList.size(); i++){
            PopulateTable(i);
        }

    }
    @SuppressLint("NewApi")
    @Override
    //Returns information passed from addAppointmentactivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                //Creates a new appointment with the information passed
                appointmentArrayList.add(new Appointment(
                        data.getStringExtra("name"),data.getStringExtra("type"),
                        data.getStringExtra("monthOfYear"), data.getIntExtra("dayOfMonth", 0), data.getIntExtra("year", 1111),
                        data.getIntExtra("hour", 11),data.getIntExtra("minute", 11),data.getStringExtra("AMorPM")));
                //Displays new appointment on in the table
                PopulateTable(appointmentArrayList.size()-1);
            }
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void PopulateTable(int arrayListCounter) {
        TableLayout appointmentTBL = (TableLayout) findViewById(R.id.tblTaskContent);

        TableRow newTableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        newTableRow.setLayoutParams(lp);

        TextView txtvName = new TextView(this);
        txtvName.setLayoutParams(lp);
        txtvName.setGravity(Gravity.CENTER);
        txtvName.setText(appointmentArrayList.get(arrayListCounter).name);
        txtvName.setWidth(150);
        txtvName.setTextSize(12);
        txtvName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView txtvType = new TextView(this);
        txtvType.setLayoutParams(lp);
        txtvType.setGravity(Gravity.CENTER);
        txtvType.setText(appointmentArrayList.get(arrayListCounter).type);
        txtvType.setWidth(140);
        txtvType.setTextSize(12);
        txtvType.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView txtvDate = new TextView(this);
        txtvDate.setLayoutParams(lp);
        txtvDate.setGravity(Gravity.CENTER);
        txtvDate.setText(SetToDateAndTime(appointmentArrayList.get(arrayListCounter)));
        txtvDate.setWidth(97);
        txtvDate.setTextSize(12);
        txtvDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        newTableRow.addView(txtvName);
        newTableRow.addView(txtvType);
        newTableRow.addView(txtvDate);
        appointmentTBL.addView(newTableRow,arrayListCounter+1);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String SetToDateAndTime(Appointment appointment) {
        long currentDateAndTime = System.currentTimeMillis(); //Todays Date
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM d, yyyy"); //Date Format

        String todaysDate = formatDate.format(currentDateAndTime); //Today's date formated
        String passDate = appointment.monthDate +" " + appointment.dayDate +", " + appointment.yearDate; //Tasks date formated the same way

        //int hour=appointment.hourTime;
        //int minute=appointment.minuteTime;

        if(Objects.equals(todaysDate, passDate)){ //Compare today's date and passed date, return time if dates match
            return (time(appointment.hourTime) +":" +pad(appointment.minuteTime) +" "+appointment.AMorPMTime);
        }
        return appointment.monthDate +" " + appointment.dayDate +", " + appointment.yearDate; //Otherwise, return the date

    }
    private String pad(int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return ("0" + String.valueOf(c));
    }
    private String time(int a){
        if(a==0)
            return String.valueOf(12);
        return String.valueOf(a);
    }

    public void AddAppointmentBtn(View view) {
        startActivityForResult(new Intent(this, AddAppointmentActivity.class), 1);
    }
}