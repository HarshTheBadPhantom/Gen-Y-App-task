package com.harshkothari_geny.letmeremember;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ToDoAdapter extends RecyclerView.Adapter {
    private List<ToDoModel> toDoModelList;

    public ToDoAdapter(List<ToDoModel> toDoModelList) {
        this.toDoModelList = toDoModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String title=toDoModelList.get(position).getTitle();
        String desc=toDoModelList.get(position).getDescription();
        boolean isCpt=toDoModelList.get(position).isCompleted();
        ((TodoViewHolder)holder).setTodos(title,desc,isCpt);
    }

    @Override
    public int getItemCount() {
        return toDoModelList.size();
    }
    public class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV,descTV;
        private ImageView delBtn;
        private CheckBox checkBox;
        private CardView todoRoot;
        private List<ToDoModel> completedList;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.titleItem);
            descTV=itemView.findViewById(R.id.descItem);
            delBtn=itemView.findViewById(R.id.delete);
            checkBox=itemView.findViewById(R.id.checkBox);
            todoRoot=itemView.findViewById(R.id.todo_container);
        }
        private void setTodos(String title,String description,boolean isCompleted){
            //Logic for changing the background colour alternatively for each item
            if (ToDoActivity.col==0){
                ToDoActivity.col=1;
                todoRoot.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.purple_700));
            }else if (ToDoActivity.col==1){
                ToDoActivity.col=0;
                todoRoot.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.teal_700));
            }
            //setting all the values to their respective views
            titleTV.setText(title);
            descTV.setText(description);
            checkBox.setChecked(isCompleted);
            if (checkBox.isChecked()){
                checkBox.setEnabled(false);
            }
            delBtn.setOnClickListener(v->{
                // A confirmation dialog to delete the item
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(itemView.getContext());

                materialAlertDialogBuilder.setTitle("Do you want to remove " + title)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            //first removing the current item from the list
                            toDoModelList.remove(getAdapterPosition());
                            //notifying the changes
                            notifyItemRemoved(getAdapterPosition());
                            //and saving again the modified list to the Paper db
                            Paper.book().write("todolist", toDoModelList);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();
            });
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b){
                    //Logic for when user clicks the checkbox to indicate he/she completed the task
                    checkBox.setChecked(true);
                    //disabling the checkbox for the particular completed task so user doesn't uncheck it again
                    checkBox.setEnabled(false);
                    //getting the title and description before removing the completed task
                    String title1=toDoModelList.get(getAdapterPosition()).getTitle();
                    String desc=toDoModelList.get(getAdapterPosition()).getDescription();
                    toDoModelList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    //after removing it again i am saving it with the checkbox checked so does indicates the particular task is completed
                    toDoModelList.add(new ToDoModel(title1,desc,true));
                    Paper.book().write("todolist", toDoModelList);
                        notifyItemChanged(getOldPosition());
                }
            });
        }
    }
}
