package com.adamsunderman.android_location;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private double lat;
    private double lon;
    private String text;
    private EditText inputText;
    private TextView logSubmitButton;
    private LocationListener locationListener;
    private static final int LOCATION_PERMISSION_RESULT = 17;

    SQLiteExample mSQLiteExample;
    Cursor mSQLCursor;
    SimpleCursorAdapter mSQLCursorAdapter;
    private static final String TAG = "LocationActivity";
    SQLiteDatabase mSQLDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.user_text_input);
        logSubmitButton = (TextView) findViewById(R.id.submit_log_button);

        mSQLiteExample = new SQLiteExample(this);
        mSQLDB = mSQLiteExample.getWritableDatabase();
        populateTable();

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    lon = location.getLongitude();
                    lat = location.getLatitude();
                } else {
                    lon = -123.291;
                    lat = 44.5019;
                }
            }
        };

        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText(String.format("%s", ""));
            }
        });
        inputText.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    text = inputText.getText().toString();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        logSubmitButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSQLDB != null && text != null){
                    ContentValues vals = new ContentValues();
                    vals.put(DBContract.mainTable.COLUMN_NAME_TEXT, String.format("%s", text));
                    vals.put(DBContract.mainTable.COLUMN_NAME_LAT, lat);
                    vals.put(DBContract.mainTable.COLUMN_NAME_LON, lon);
                    mSQLDB.insert(DBContract.mainTable.TABLE_NAME, null, vals);
                    text = null;
                    populateTable();
                } else {
                    Log.d(TAG, "Unable to access database for writing.");
                }
                inputText.setText("Click to enter log text");
            }

        });



    }

    private void populateTable(){
        if(mSQLDB != null) {
            try {
                if(mSQLCursorAdapter != null && mSQLCursorAdapter.getCursor() != null){
                    if(!mSQLCursorAdapter.getCursor().isClosed()){
                        mSQLCursorAdapter.getCursor().close();
                    }
                }
                mSQLCursor = mSQLDB.query(DBContract.mainTable.TABLE_NAME,
                        new String[]{DBContract.mainTable._ID, DBContract.mainTable.COLUMN_NAME_TEXT,
                                DBContract.mainTable.COLUMN_NAME_LAT, DBContract.mainTable.COLUMN_NAME_LON}, null, null, null, null, null);
                ListView SQLListView = (ListView) findViewById(R.id.db_list);
                mSQLCursorAdapter = new SimpleCursorAdapter(this,
                        R.layout.sql_item,
                        mSQLCursor,
                        new String[]{DBContract.mainTable.COLUMN_NAME_TEXT, DBContract.mainTable.COLUMN_NAME_LAT,DBContract.mainTable.COLUMN_NAME_LON},
                        new int[]{R.id.sql_listview_text, R.id.sql_listview_lat, R.id.sql_listview_lon}, 0);
                SQLListView.setAdapter(mSQLCursorAdapter);
            } catch (Exception e) {
                Log.d(TAG, "Error loading data from database");
            }
        }
    }
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_RESULT);
            lon = -123.291;
            lat = 44.5019;
            return;
        }
        updateLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        lon = -123.291;
        lat = 44.5019;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        lon = -123.291;
        lat = 44.5019;
        Dialog errDialog = GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), 0);
        //errDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == LOCATION_PERMISSION_RESULT){
            if(grantResults.length > 0){
                updateLocation();
            }

        }
        //updateLocation();
    }

    private void updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            lon = -123.291;
            lat = 44.5019;
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if(lastLocation != null){
            lon = lastLocation.getLongitude();
            lat = lastLocation.getLatitude();
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
        }
    }
}



