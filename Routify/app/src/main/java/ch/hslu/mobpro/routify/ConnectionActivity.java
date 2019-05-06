package ch.hslu.mobpro.routify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import ch.hslu.mobpro.routify.model.Connection;
import ch.hslu.mobpro.routify.model.Filters;
import ch.hslu.mobpro.routify.model.Settings;
import ch.hslu.mobpro.routify.persistence.DatabaseHelper;

public class ConnectionActivity extends AppCompatActivity {

    private Filters filters;
    private Settings settings;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        this.databaseHelper = new DatabaseHelper(getApplicationContext());
        loadDefaultFilters();
        loadDefaultSettings();
    }

    public void saveConnection(View view) {
        EditText from = (EditText)findViewById(R.id.from);
        EditText to = (EditText)findViewById(R.id.to);
        Connection connection = new Connection(from.getText().toString(), to.getText().toString());
        connection.setFilters(filters);
        connection.setSettings(settings);
        databaseHelper.saveConnection(connection);
        this.finish();
    }

    private void loadDefaultSettings() {
        this.settings = new Settings();
        CheckBox monday = (CheckBox)findViewById(R.id.monday);
        CheckBox tuesday = (CheckBox)findViewById(R.id.tuesday);
        CheckBox wednesday = (CheckBox)findViewById(R.id.wednesday);
        CheckBox thursday = (CheckBox)findViewById(R.id.thursday);
        CheckBox friday = (CheckBox)findViewById(R.id.friday);
        CheckBox saturday = (CheckBox)findViewById(R.id.saturday);
        CheckBox sunday = (CheckBox)findViewById(R.id.sunday);
        monday.setChecked(settings.getMonday());
        tuesday.setChecked(settings.getTuesday());
        wednesday.setChecked(settings.getWednesday());
        thursday.setChecked(settings.getThursday());
        friday.setChecked(settings.getFriday());
        saturday.setChecked(settings.getSaturday());
        sunday.setChecked(settings.getSunday());
    }

    private void loadDefaultFilters() {
        this.filters = new Filters();
        CheckBox busAllowed = (CheckBox)findViewById(R.id.bus_allowed);
        CheckBox trainAllowed = (CheckBox)findViewById(R.id.train_allowed);
        busAllowed.setChecked(filters.getBusAllowed());
        trainAllowed.setChecked(filters.getTrainAllowed());
    }

    public void onBusAllowedClick(View view) {
        CheckBox busAllowed = (CheckBox)findViewById(R.id.bus_allowed);
        if(busAllowed.isChecked()){
            filters.setBusAllowed(true);
        } else {
            filters.setBusAllowed(false);
        }
    }

    public void onTrainAllowedClick(View view) {
        CheckBox trainAllowed = (CheckBox)findViewById(R.id.train_allowed);
        if(trainAllowed.isChecked()){
            filters.setTrainAllowed(true);
        } else {
            filters.setTrainAllowed(false);
        }
    }

    public void onMondayClick(View view) {
        CheckBox monday = (CheckBox)findViewById(R.id.monday);
        if(monday.isChecked()){
            settings.setMonday(true);
        } else {
            settings.setMonday(false);
        }
    }

    public void onTuesdayClick(View view) {
        CheckBox tuesday = (CheckBox)findViewById(R.id.tuesday);
        if(tuesday.isChecked()){
            settings.setTuesday(true);
        } else {
            settings.setTuesday(false);
        }
    }

    public void onWednesdayClick(View view) {
        CheckBox wednesday = (CheckBox)findViewById(R.id.wednesday);
        if(wednesday.isChecked()){
            settings.setWednesday(true);
        } else {
            settings.setWednesday(false);
        }
    }

    public void onThursdayClick(View view) {
        CheckBox thursday = (CheckBox)findViewById(R.id.thursday);
        if(thursday.isChecked()){
            settings.setThursday(true);
        } else {
            settings.setThursday(false);
        }
    }

    public void onFridayClick(View view) {
        CheckBox friday = (CheckBox)findViewById(R.id.friday);
        if(friday.isChecked()){
            settings.setFriday(true);
        } else {
            settings.setFriday(false);
        }
    }

    public void onSaturdayClick(View view) {
        CheckBox saturday = (CheckBox)findViewById(R.id.saturday);
        if(saturday.isChecked()){
            settings.setSaturday(true);
        } else {
            settings.setSaturday(false);
        }
    }

    public void onSundayClick(View view) {
        CheckBox sunday = (CheckBox)findViewById(R.id.sunday);
        if(sunday.isChecked()){
            settings.setSunday(true);
        } else {
            settings.setSunday(false);
        }
    }
}
