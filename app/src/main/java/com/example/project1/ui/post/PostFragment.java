package com.example.project1.ui.post;

import android.app.DatePickerDialog;
import android.location.Address;


import android.location.Geocoder;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project1.MainActivity;
import com.example.project1.R;
import com.example.project1.Task;
import com.example.project1.DatabasePersistence;
import com.example.project1.TaskLocation;

import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PostFragment extends Fragment {

    private PostViewModel postViewModel;
    private Button selectDate;
    private Button postBtn;
    private String userName;
    private Date workDate;
    private TextView date;
    private DatabaseReference dbTask;
    private TextView statusLabel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        postViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        MainActivity parentActivity = (MainActivity) getActivity();
        userName = parentActivity.getUserName();

        EditText titleEdit = root.findViewById(R.id.taskTitle);
        EditText descriptionEdit = root.findViewById(R.id.taskDescription);
        EditText wageEdit = root.findViewById(R.id.taskWage);
        EditText addressEdit=root.findViewById(R.id.taskAddress);

        selectDate = root.findViewById(R.id.selectDate);
        postBtn = root.findViewById(R.id.postBtn);
        date = root.findViewById(R.id.dateView);
        statusLabel = root.findViewById(R.id.postStatus);
//open a DatePickerDialog
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

//save the input Task in Firebase
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString().trim();
                String description = descriptionEdit.getText().toString().trim();
                String wage = wageEdit.getText().toString().trim();
                String addressString=addressEdit.getText().toString().trim();

                Geocoder geocoder=new Geocoder(getContext());

                Address address = null;
                try {
                    List<Address> locationResult=geocoder.getFromLocationName(addressString,1);
                    if (locationResult.size()==1){
                    address= locationResult.get(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (!title.isEmpty() && !description.isEmpty() && !wage.isEmpty() && workDate != null&&address!=null) {

                    addressString=address.getAddressLine(0);
                    TaskLocation location=new TaskLocation(address.getLatitude(),address.getLongitude());
                    Task t = new Task(title, description, workDate, Integer.parseInt(wage), userName,addressString,location);
                    DatabasePersistence persistence = new DatabasePersistence();
                    persistence.save(t);
                    Toast.makeText(getContext(), "Post Successfully", Toast.LENGTH_SHORT).show();
                    statusLabel.setText("Post Successfully");
                    titleEdit.setText("");
                    descriptionEdit.setText("");
                    addressEdit.setText("");
                    wageEdit.setText("");
                    workDate = null;
                    address=null;

                    date.setText("");
                } else {
                    Toast.makeText(getContext(), "Please Fill all the Blanks and check your Address", Toast.LENGTH_SHORT).show();
                    statusLabel.setText("Please Fill all the Blanks");

                }
            }
        });

        return root;
    }


}