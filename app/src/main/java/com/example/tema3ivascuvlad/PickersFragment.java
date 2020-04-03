package com.example.tema3ivascuvlad;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class PickersFragment extends Fragment {

    private Integer currentHour = null;
    private Integer currentMinute = null;
    private Integer currentYear = null;
    private Integer currentMonth = null;
    private Integer currentDay = null;
    private String currentToDoTitle = null;

    public PickersFragment(String newTitle) {
        currentToDoTitle = newTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickers, container, false);

        final TextView chooseTime = view.findViewById(R.id.etChooseTime);
        final TextView chooseDate = view.findViewById(R.id.etChooseDate);
        final Button btnTimePicker = view.findViewById(R.id.btnTimePicker);
        final Button btnDatePicker = view.findViewById(R.id.btnDatePicker);
        final Button btnAlarm = view.findViewById(R.id.btnAlarm);
        final TextView currentToDoDisplay = view.findViewById(R.id.currentToDoView);

        currentToDoDisplay.setText(this.currentToDoTitle);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        currentHour = hourOfDay;
                        currentMinute = minutes;
                        chooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, true);

                timePickerDialog.show();
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                currentYear = year;
                                currentMonth = monthOfYear;
                                currentDay = dayOfMonth;
                                chooseDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, 2020, 0, 0);
                datePickerDialog.show();
            }
        });

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlarm();
                System.out.println("CLICKED ALARM BTN");
            }
        });
        return view;
    }

    private void sendAlarm(){
        if(currentHour == null || currentMinute == null || currentYear == null || currentMonth == null || currentDay == null)
        {
            displayToast("YOU MUST SELECT TIME&DATE!");
            return;
        }

        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        intent.putExtra("toDoTitle", currentToDoTitle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
        try {
            alarmManager.set(AlarmManager.RTC_WAKEUP, getCurrentSelectedDate(), pendingIntent);
            displayToast("ALARM SET SUCCESSFULLY");
            ((ToDosActivity)getActivity()).closeFragment();
        }catch (Exception e){
            displayToast("ERROR WHILE SETTING ALARM!");
            return;
        }
    }

    private long getCurrentSelectedDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);

        return calendar.getTimeInMillis();
    }

    private void displayToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
}
