package com.example.project1.ui.edit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project1.MainActivity;
import com.example.project1.R;
import com.example.project1.Task;
import com.example.project1.ui.post.PostViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EditFragment extends Fragment {

    private PostViewModel postViewModel;
    private Button selectDate;
    private Button postBtn;
    private String userName;
    private Date workDate;
    private TextView date;
    private DatabaseReference dbTask;
    private TextView statusLabel;
    private Task editTask;
    private EditText titleEdit;
    private EditText descriptionEdit;
    private EditText wageEdit;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit, container, false);
        //get TaskID of Tasks to be edited
        String editTaskId = getArguments().getString("taskId");
        dbTask = FirebaseDatabase.getInstance().getReference("Task");
        MainActivity parentActivity = (MainActivity) getActivity();
        userName = parentActivity.getUserName();

        Query query = dbTask.orderByChild("taskId").equalTo(editTaskId);
        query.addListenerForSingleValueEvent(valueEventListener);

        titleEdit = root.findViewById(R.id.editTitle);
        descriptionEdit = root.findViewById(R.id.editDescription);
        wageEdit = root.findViewById(R.id.editWage);
        selectDate = root.findViewById(R.id.editSelectDate);
        postBtn = root.findViewById(R.id.editBtn);
        date = root.findViewById(R.id.editDateView);
        statusLabel = root.findViewById(R.id.editStatus);

        //open DatePickerDialog
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String desc = String.format("%d-%2d-%2d", year, month + 1, day).replace(" ", "0");

                        date.setText(desc);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        simpleDateFormat.setTimeZone(TimeZone.getDefault());
                        try {
                            workDate = simpleDateFormat.parse(desc + " 23:59:59");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

                DatePicker picker = dateDialog.getDatePicker();
                picker.setMinDate(System.currentTimeMillis() - 1000);
                dateDialog.show();

            }
        });

//save the edited Task in Firebase
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString().trim();
                String description = descriptionEdit.getText().toString().trim();
                String wage = wageEdit.getText().toString().trim();

                if (!title.isEmpty() && !description.isEmpty() && !wage.isEmpty() && workDate != null) {


                    editTask.setTitle(title);
                    editTask.setDescription(description);
                    editTask.setWage(Integer.parseInt(wage));
                    dbTask.child(editTask.getTaskId()).setValue(editTask);
                    Toast.makeText(getContext(), "Edit Successfully", Toast.LENGTH_SHORT).show();
                    statusLabel.setText("Edit Successfully");
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_home);

                } else {
                    Toast.makeText(getContext(), "Please Fill all the Blanks", Toast.LENGTH_SHORT).show();
                    statusLabel.setText("Please Fill all the Blanks");

                }
            }
        });


        return root;
    }

    //fill the blank in editPage with Task details in Firebase
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {


                    editTask = taskSnapshot.getValue(Task.class);
                    titleEdit.setText(editTask.getTitle());
                    descriptionEdit.setText(editTask.getDescription());
                    wageEdit.setText(String.valueOf(editTask.getWage()));

                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "DatabaseError, please try again later", Toast.LENGTH_LONG).show();
        }
    };

}