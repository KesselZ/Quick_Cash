package com.example.project1.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.MainActivity;
import com.example.project1.PostDetail;
import com.example.project1.R;
import com.example.project1.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DatabaseReference dbTask ;
    public ArrayList<Task> allTitles = new ArrayList<>();
    public ArrayList<Task> allTitle2 = new ArrayList<>();
    private PostAAdapter adapter;
    public ArrayList<Task> acceptTask = new ArrayList<>();
    private String userName="Guest";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        userName = activity.getUserName();
        if(userName==null) userName="Guest";

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        dbTask= FirebaseDatabase.getInstance().getReference();


        Query query = dbTask.child("Task").orderByChild("status").equalTo("Published");
        query.addListenerForSingleValueEvent(valueEventListener);



        adapter = new PostAAdapter(getContext(), allTitles);
        ListView taskList = root.findViewById(R.id.HomeListView);
        SearchView search = root.findViewById(R.id.mainSearchView);
        taskList.setAdapter(adapter);

        /*allTitles.remove(1);*/
        adapter.notifyDataSetChanged();
        adapter = new PostAAdapter(getContext(), allTitles);
        taskList.setAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            public boolean onQueryTextSubmit(String query) {
                ArrayList result = Search(getAllTask(),query);

                adapter = new PostAAdapter(getContext(), result);
                taskList.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return root;
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {

                    Task task = taskSnapshot.getValue(Task.class);

                    allTitles.add(task);
                }
                adapter.notifyDataSetChanged();
            } else {

                String message =  "No data here.";
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(),"DatabaseError, please try again later", Toast.LENGTH_LONG).show();
        }
    };


    /**
     * Get all tasks from server
     * @param
     * @return all tasks in server
     */
    public ArrayList getAllTask(){
        return allTitles;
    }

    /**
     * Find tasks in tasks list that contain keyword
     * @param tasks & keyword
     * @return Returns the task that comply the requirements
     */
    public ArrayList<Task> Search(ArrayList<Task> tasks, String keyword){
        ArrayList<Task> afterCompare = new ArrayList<>();

        for(int i = 0; i<tasks.size(); i++){
            if(tasks.get(i).getTitle().contains(keyword)){
                afterCompare.add(tasks.get(i));
            }
        }

        return afterCompare;
    }



    class PostAAdapter extends BaseAdapter {
        private ArrayList<Task> postTaskView;

        private LayoutInflater inflater;

        public PostAAdapter(Context context, ArrayList<Task> tasklist) {
            inflater = LayoutInflater.from(context);
            postTaskView = tasklist;
        }

        @Override
        public int getCount() {
            //return myPost == null? 0 : myPost.size();
            return postTaskView.size();
        }

        @Override
        public Object getItem(int i) {
            //return null;
            return postTaskView.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            ViewHolder myView;
            if (view == null) {
                view = inflater.inflate(R.layout.task_item, null);
                myView = new ViewHolder();
                myView.homeTaskLayout = (RelativeLayout)view.findViewById(R.id.tasklistLayout);
                myView.taskTitle = (TextView)view.findViewById(R.id.Title);
                myView.workDay = (TextView)view.findViewById(R.id.workday);
                myView.salary = (TextView)view.findViewById(R.id.Salary);
                myView.editBtn = view.findViewById(R.id.editBtn);
                myView.editBtn.setText("ACCEPT");

                view.setTag(myView);
            } else {
                myView = (ViewHolder) view.getTag();
            }

            // write the task information into the textView
            myView.taskTitle.setText(postTaskView.get(position).getTitle());
            myView.workDay.setText(postTaskView.get(position).formattedWorkDate());
            String salary = "Salary: " + String.valueOf(postTaskView.get(position).getWage());
            myView.salary.setText(salary);
            myView.homeTaskLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), PostDetail.class);
                    dbTask.child("History").child(userName).child(postTaskView.get(position).getTaskId()).setValue(postTaskView.get(position));
                    intent.putExtra("task", postTaskView.get(position));
                    getContext().startActivity(intent);
                }
            });
            // create the event listener which will redirect user to accept a task
            myView.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String taskId = postTaskView.get(position).getTaskId();

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    dbTask = firebaseDatabase.getReference();

                    Query query = dbTask.child("Task").orderByChild("publisher");
                    query.addListenerForSingleValueEvent(valueEventListenerID);

                    String message1 =  Integer.toString( acceptTask.size());
//                    Toast.makeText(getContext(), message1, Toast.LENGTH_LONG).show();

                    for (int i = 0; i <acceptTask.size() ; i++) {
                        Task task = acceptTask.get(i);
                        if(taskId.equals(task.getTaskId())){
                            if(userName != null){
                                if(!userName.equals("Guest")){
                                    task.acceptTask(userName);
                                    DatabaseReference saveTask = FirebaseDatabase.getInstance().getReference("Task");
                                    saveTask.child(task.getTaskId()).setValue(task);
                                    String message = task.getStatus() +" accept a task";
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    break;
                                }
                                else Toast.makeText(getContext(), "Please login before accept a task", Toast.LENGTH_LONG).show();
                            }
                            else Toast.makeText(getContext(), "Please login before accept a task", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            return view;
        }
    }

    /**
     * Method to iterate through the firebase and retrieve task base on the taskID
     */
    ValueEventListener valueEventListenerID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            acceptTask.clear();
            if (snapshot.exists()) {
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {

                    Task task = taskSnapshot.getValue(Task.class);
                    // append task to task list
                    acceptTask.add(task);
                }
                adapter.notifyDataSetChanged();
            } else {
                // The user has not accepted any task
                String message = userName + "has not accepted any task yet";
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "DatabaseError, please try again later", Toast.LENGTH_LONG).show();
        }
    };


    class ViewHolder {
        private RelativeLayout homeTaskLayout;
        private TextView taskTitle;
        private TextView workDay;
        private TextView salary;
        private Button editBtn;
    }
}