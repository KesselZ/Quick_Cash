package com.example.project1.ui.history;

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
import com.example.project1.ui.mypost.MyPostFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryPage extends Fragment {
    private DatabaseReference dbTask ;
    public ArrayList<Task> allHistories = new ArrayList<>();
    private PostAAdapter adapter;
    private String userName="Guest";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_history, container, false);

        // get the current logged user
        MainActivity activity = (MainActivity) getActivity();
        userName = activity.getUserName();
        if(userName==null) userName="Guest";
        System.out.println("111111111111111111111111"+userName);

        dbTask= FirebaseDatabase.getInstance().getReference();
        Query query = dbTask.child("History").child(userName);
        query.addListenerForSingleValueEvent(valueEventListener);

        adapter = new PostAAdapter(getContext(), allHistories);
        ListView taskList = root.findViewById(R.id.HistoryListView);
//        SearchView search = root.findViewById(R.id.mainSearchView);
        taskList.setAdapter(adapter);

        /*allTitles.remove(1);*/
        adapter.notifyDataSetChanged();
        adapter = new PostAAdapter(getContext(), allHistories);
        taskList.setAdapter(adapter);

        return root;
    }



    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {

                    Task task = taskSnapshot.getValue(Task.class);

                    allHistories.add(task);
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
                myView.taskTitle = (TextView)view.findViewById(R.id.Title);
                myView.workDay = (TextView)view.findViewById(R.id.workday);
                myView.salary = (TextView)view.findViewById(R.id.Salary);
                myView.homeTaskLayout = (RelativeLayout)view.findViewById(R.id.tasklistLayout);
                Button editButton=(Button)view.findViewById(R.id.editBtn);
                editButton.setVisibility(View.GONE);
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
                    intent.putExtra("task", postTaskView.get(position));
                    getContext().startActivity(intent);
                }
            });

            return view;
        }
    }

    class ViewHolder {
        private RelativeLayout homeTaskLayout;
        private TextView taskTitle;
        private TextView workDay;
        private TextView salary;

    }
}