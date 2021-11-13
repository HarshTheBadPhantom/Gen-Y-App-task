package com.harshkothari_geny.letmeremember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ToDoActivity extends AppCompatActivity {
private RecyclerView todoRecycler;
private ToDoAdapter toDoAdapter;
private List<ToDoModel>toDoModelList;
public static int col=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        Paper.init(this);

        todoRecycler=findViewById(R.id.todoRecycler);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //setting the layout manager for the items in recycler view
        todoRecycler.setLayoutManager(linearLayoutManager);

        //retrieving the saved todos list if its not null,, in case null initializing it with Arraylist
        toDoModelList=Paper.book().read("todolist",new ArrayList<>());
//setting the adapter to recycler view
        toDoAdapter=new ToDoAdapter(toDoModelList);

        todoRecycler.setAdapter(toDoAdapter);

        toDoAdapter.notifyDataSetChanged();
    }
}