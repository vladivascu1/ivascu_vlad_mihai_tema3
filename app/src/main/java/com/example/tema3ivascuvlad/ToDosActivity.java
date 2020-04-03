package com.example.tema3ivascuvlad;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Models.ToDo;
import Models.User;
import MySingleton.MySingleton;

public class ToDosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Integer userId;
    private String currentTitle;

    public void setCurrentTitle(String newTitle){
        this.currentTitle = newTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_dos);

        Bundle bundle = getIntent().getExtras();
        userId = 1;

        if(bundle != null)
            this.userId = bundle.getInt("id");

        initRecyclerView();

    }

    private void getToDos(){
        String src = "https://jsonplaceholder.typicode.com/todos?userId=" + this.userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(src, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<ToDo> toDos = new ArrayList<>();
                for (int index = 0; index < response.length(); index++) {
                    try {
                        ToDo toDo = new ToDo();
                        JSONObject toDoJSON = response.getJSONObject(index);
                        toDo.completed = toDoJSON.getBoolean("completed");
                        toDo.title = toDoJSON.getString("title");
                        toDo.userId = toDoJSON.getInt("userId");
                        toDo.id = toDoJSON.getInt("id");
                        toDos.add(toDo);
                        System.out.println(toDo.toString());

                    } catch (JSONException ex) {
                        System.out.println("Exception when parsing json");
                    }
                }
                setToDosRecyclerViewData(toDos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error:" + error.getMessage());
            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void setToDosRecyclerViewData(List<ToDo> toDos){
        ToDosDisplayAdapter adapter = new ToDosDisplayAdapter(this, toDos);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getToDos();
    }

    public void showFragment(){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.findFragmentByTag("pickersFragmentTag");
        if(currentFragment != null)
            fragmentTransaction.remove(currentFragment);

        fragmentTransaction.add(R.id.layout,new PickersFragment(this.currentTitle), "pickersFragmentTag").commit();
    }

    public void closeFragment(){
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.findFragmentByTag("pickersFragmentTag");
        if(currentFragment != null)
        {
            fragmentTransaction.remove(currentFragment);
            fragmentTransaction.commit();
        }
    }


}
