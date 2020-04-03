package com.example.tema3ivascuvlad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.User;
import MySingleton.MySingleton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        getUsers();
    }

    private void getUsers(){
        String src = "https://my-json-server.typicode.com/MoldovanG/JsonServer/users";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(src, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<User> userList = new ArrayList<>();
                for (int index = 0; index < response.length(); index++) {
                    try {
                        User user = new User();
                        JSONObject userJSON = response.getJSONObject(index);
                        user.email = userJSON.getString("email");
                        user.name = userJSON.getString("name");
                        user.username = userJSON.getString("username");
                        user.id = userJSON.getInt("id");
                        userList.add(user);
                        System.out.println(user.toString());

                    } catch (JSONException ex) {
                        System.out.println("Exception when parsing json");
                    }
                }
                setUsersRecyclerViewData(userList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error:" + error.getMessage());
            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void setUsersRecyclerViewData(List<User> users){
        UsersDisplayAdapter adapter = new UsersDisplayAdapter(this, users);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        getUsers();
    }

}
