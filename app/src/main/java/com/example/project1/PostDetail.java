package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PostDetail extends AppCompatActivity implements OnMapReadyCallback {


    TextView title;
    TextView description;
    TextView location;
    TextView workDay;
    TextView wage;
    TextView publisher;
    TextView worker;
    TextView status;

    private static final int LOCATION_CODE = 1;


    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    Context context;
    Activity activity;

    private Task task;

    LocationManager manager;
    LatLng currentLocation;
    LatLng taskLocation;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        title = findViewById(R.id.taskDetailTitle);
        description = findViewById(R.id.taskDetailDescription);
        location = findViewById(R.id.taskLocation);
        workDay = findViewById(R.id.taskWorkDay);
        wage = findViewById(R.id.taskWageDetail);
        publisher = findViewById(R.id.taskPublisherDetail);
        worker = findViewById(R.id.taskWorkerDetail);
        status = findViewById(R.id.taskStatus);
        task = (Task) getIntent().getSerializableExtra("task");


        setTaskData();
        taskLocation=new LatLng(task.getLocation().latitude,task.getLocation().longitude);
        activity = PostDetail.this;
        context = PostDetail.this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission(PostDetail.this);
        }


        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                5, listener);


    }


    /**
     * This method will check if there is some necessary task information passing from myPost.
     *
     * @return -- True if all necessary task information are passed
     */
    public boolean hasDataPassing() {
        boolean hasData = true;

        if ((!getIntent().hasExtra("task"))) {
            hasData = false;
        }

        return hasData;
    }

    /**
     * This method will display all task information in the PostDetail UI
     */
    public void setTaskData() {
        if (hasDataPassing()) {

            String assignWorker;
            if (task.getWorker() == null) {
                assignWorker = Task.NOWORKER;
            } else {
                assignWorker = task.getWorker();
            }

            title.setText(task.getTitle());
            description.setText("Description: "+task.getDescription());
            location.setText("Location: " + task.getAddress());
            workDay.setText("Work Day: " + task.getWorkDate());
            wage.setText("Wages: " + task.getWage());
            publisher.setText("Publisher: " + task.getPublisher());
            worker.setText("Worker: "+assignWorker);
            status.setText("Task Status: " + task.getStatus());
        } else {
            Toast.makeText(this, "No data passing", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(taskLocation).title("Marker of Task"));
        drawMarkerWithCircle(taskLocation);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taskLocation, 10));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        mMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        mMap.getUiSettings().setZoomGesturesEnabled(true);




    }
    /**
     * draw a circle on the google map
     */

    private void drawMarkerWithCircle(LatLng position) {
        int radius = 3;
        double radiusInMeters = radius * 1000.0;  // increase decrease this distancce as per your requirements
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(8);
        mMap.addCircle(circleOptions);

    }
/**
     * check if app has location permission
     */
    public void checkLocationPermission(final Activity context) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            } else {
                askUserToAllowPermissionFromSetting();

            }
        }

    }

/**
     * not have location, ask user to give permission from setting
     */
    private void askUserToAllowPermissionFromSetting() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                PostDetail.this);

        // set title
        alertDialogBuilder.setTitle("Permission Required:");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kindly allow Permission from App Setting, without this permission app could not show your location.")
                .setCancelable(false)
                .setPositiveButton("Go to Setting", (dialog, id) -> {
                    // if this button is clicked, close
                    // current activity
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                })
                .setNegativeButton("Cancel", (dialog, id) ->
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel() );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    LocationListener listener = myLocation-> {

            currentLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

            float[] distance = new float[2];
            Location.distanceBetween(currentLocation.latitude, currentLocation.longitude,
                    taskLocation.latitude, taskLocation.longitude, distance);


            float distanceInMeter = Float.parseFloat(distance[0] + "");
            String distanceInKm = String.format("%.1f", distanceInMeter / 1000);

            Toast.makeText(getBaseContext(), "Your Distance to Task Place: " + distanceInKm + " km", Toast.LENGTH_LONG).show();


    };

}
