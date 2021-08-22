package com.example.project1.ui.mytask;

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

public class MyTaskFragment extends Fragment {

    private MyTaskViewModel MytaskViewModel;
    private DatabaseReference dbTask;
    private Button refresh;
    public ArrayList<Task> myTask = new ArrayList<>();
    private PostAAdapter adapter;
    private String userName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MytaskViewModel =
                new ViewModelProvider(this).get(MyTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mytask, container, false);

        // get firebase reference
        dbTask = FirebaseDatabase.getInstance().getReference();


        // get the current logged user
        MainActivity activity = (MainActivity) getActivity();
        userName = activity.getUserName();

        adapter = new PostAAdapter(getContext(), myTask);
        ListView taskList = root.findViewById(R.id.tasklistView);
        taskList.setAdapter(adapter);


        Query query = dbTask.child("Task").orderByChild("worker").equalTo(userName);
        query.addListenerForSingleValueEvent(valueEventListener);

        return root;
    }

    /**
     * Method to iterate through the firebase and retrieve task base on the worker
     */
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            myTask.clear();
            if (snapshot.exists()) {
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {

                    Task task = taskSnapshot.getValue(Task.class);
                    // append task to task list
                    myTask.add(task);
                }
                adapter.notifyDataSetChanged();

            } else {
                // The user has not accepted any task
                String message = userName + " has not accepted any task yet.";
                if (userName == null){
                    message = "Please Login First";
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "DatabaseError, please try again later.", Toast.LENGTH_LONG).show();
        }
    };


    /**
     * Adapter class use to iterated all retrieved tasks from firebase and accept them in the list view
     */
    class PostAAdapter extends BaseAdapter {
        private ArrayList<Task> acceptTaskView;

        //private Context context;
        private LayoutInflater inflater;

        public PostAAdapter(Context context, ArrayList<Task> tasklist) {
            //this.context = context;
            inflater = LayoutInflater.from(context);
            acceptTaskView = tasklist;
        }

        @Override
        public int getCount() {
            //return myPost == null? 0 : myPost.size();
            return acceptTaskView.size();
        }

        @Override
        public Object getItem(int i) {
            //return null;
            return acceptTaskView.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //Task t = myPost.get(position);
            ViewHolder myView;
            if (view == null) {
                view = inflater.inflate(R.layout.task_item, null);
                myView = new ViewHolder();
                myView.taskListLayout = (RelativeLayout) view.findViewById(R.id.tasklistLayout);
                myView.taskTitle = (TextView) view.findViewById(R.id.Title);
                myView.workDay = (TextView) view.findViewById(R.id.workday);
                myView.salary = (TextView) view.findViewById(R.id.Salary);
                myView.editBtn = view.findViewById(R.id.editBtn);
                myView.editBtn.setVisibility(View.GONE);
                view.setTag(myView);
            } else {
                myView = (ViewHolder) view.getTag();
            }

            // write the task information into the textView
            myView.taskTitle.setText(acceptTaskView.get(position).getTitle());
            myView.workDay.setText(acceptTaskView.get(position).formattedWorkDate());
            String salary = "Salary: " + String.valueOf(acceptTaskView.get(position).getWage());
            myView.salary.setText(salary);
            // create event listener which will redirect user to the accept detail page
            myView.taskListLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), PostDetail.class);
                    intent.putExtra("task", acceptTaskView.get(position));
                    getContext().startActivity(intent);
                }
            });

            return view;
        }
    }

    /**
     * This class defined what need to be showed for each task when accept them in the list view
     */
    class ViewHolder {
        private RelativeLayout taskListLayout;
        private TextView taskTitle;
        private TextView workDay;
        private TextView salary;
        private Button editBtn;
    }
}