package com.harshkothari_geny.letmeremember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
private EditText title,description;
private TextView saveBtn,taskBtn;
private List<ToDoModel> toDoModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //THIS APP IS MADE BY HARSH KOTHARI OF SRM CSE AI/ML 1st YEAR FOR GEN-Y RECRUITMENT TASK
        Paper.init(this);
        title=findViewById(R.id.titleEditText);
        description=findViewById(R.id.descEditText);
        saveBtn=findViewById(R.id.saveBtn);
        taskBtn=findViewById(R.id.yourTasks);
        saveBtn.setOnClickListener(v->{
            //checking whether the EditText contains some text or not.
            if (!title.getText().toString().isEmpty()){
                if (!description.getText().toString().isEmpty()){
                    toDoModels.add(new ToDoModel(title.getText().toString(), description.getText().toString(),false));
                    //saving the list into the Paper DB
                    Paper.book().write("todolist",toDoModels);
                    if (taskBtn.getVisibility()==View.GONE){
                        taskBtn.setVisibility(View.VISIBLE);
                    }
                    title.setText("");
                    description.setText("");
                    Toast.makeText(MainActivity.this, "Your To-Do is added!", Toast.LENGTH_SHORT).show();
                }else description.setError("Fill the description");
            }else title.setError("Fill the title");
        });
        taskBtn.setOnClickListener(v->{
            Intent i=new Intent(this,ToDoActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //retrieving the saved todos list if its not null,, in case null initializing it with Arraylist
        toDoModels=Paper.book().read("todolist",new ArrayList<>());
        if (toDoModels.isEmpty()){
            taskBtn.setVisibility(View.GONE);
        }else taskBtn.setVisibility(View.VISIBLE);
    }
}