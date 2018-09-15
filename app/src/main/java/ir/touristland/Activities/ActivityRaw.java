package ir.touristland.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONObject;

import ir.touristland.Activities.Flight.AssignmentActivity;
import ir.touristland.Activities.Flight.FlightDetailActivity;
import ir.touristland.Activities.Flight.PassengersListActivity;
import ir.touristland.Application;
import ir.touristland.Asynktask.AsynctaskReserveFlight;
import ir.touristland.Classes.HSH;
import ir.touristland.Interfaces.IWebservice;
import ir.touristland.Models.FlightReserve;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.R;


public class ActivityRaw extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int numberPassenger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc2);

        preferences = PreferenceManager.getDefaultSharedPreferences(ActivityRaw.this);
        editor = preferences.edit();

        editor.remove("Basket");
        editor.apply();
        editor.commit();

        startActivity(new Intent(this, IntroLoginActivity.class));
        finish();
    }
}
