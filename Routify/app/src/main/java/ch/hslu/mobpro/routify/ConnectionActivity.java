package ch.hslu.mobpro.routify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.time.DayOfWeek;

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
        monday.setChecked(settings.isDayEnabled(DayOfWeek.MONDAY));
        tuesday.setChecked(settings.isDayEnabled(DayOfWeek.TUESDAY));
        wednesday.setChecked(settings.isDayEnabled(DayOfWeek.WEDNESDAY));
        thursday.setChecked(settings.isDayEnabled(DayOfWeek.THURSDAY));
        friday.setChecked(settings.isDayEnabled(DayOfWeek.FRIDAY));
        saturday.setChecked(settings.isDayEnabled(DayOfWeek.SATURDAY));
        sunday.setChecked(settings.isDayEnabled(DayOfWeek.SUNDAY));
    }

    private void loadDefaultFilters() {
        this.filters = new Filters();
        CheckBox busAllowed = (CheckBox)findViewById(R.id.bus_allowed);
        CheckBox trainAllowed = (CheckBox)findViewById(R.id.train_allowed);
        busAllowed.setChecked(filters.getBusAllowed());
        trainAllowed.setChecked(filters.getTrainAllowed());
    }


    public void toggleFilters(View view) {
        LinearLayout filtersView = (LinearLayout) findViewById(R.id.filters_view);
        if (filtersView.getVisibility() == View.VISIBLE) {
            filtersView.setVisibility(View.GONE);
        } else {
            filtersView.setVisibility(View.VISIBLE);
        }
    }

    public void toggleSettings(View view) {
        LinearLayout settingsView = (LinearLayout) findViewById(R.id.settings_view);
        if (settingsView.getVisibility() == View.VISIBLE) {
            settingsView.setVisibility(View.GONE);
        } else {
            settingsView.setVisibility(View.VISIBLE);
        }
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
            settings.setDayEnabled(DayOfWeek.MONDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.MONDAY);
        }
    }

    public void onTuesdayClick(View view) {
        CheckBox tuesday = (CheckBox)findViewById(R.id.tuesday);
        if(tuesday.isChecked()){
            settings.setDayEnabled(DayOfWeek.THURSDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.THURSDAY);
        }
    }

    public void onWednesdayClick(View view) {
        CheckBox wednesday = (CheckBox)findViewById(R.id.wednesday);
        if(wednesday.isChecked()){
            settings.setDayEnabled(DayOfWeek.WEDNESDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.WEDNESDAY);
        }
    }

    public void onThursdayClick(View view) {
        CheckBox thursday = (CheckBox)findViewById(R.id.thursday);
        if(thursday.isChecked()){
            settings.setDayEnabled(DayOfWeek.THURSDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.THURSDAY);
        }
    }

    public void onFridayClick(View view) {
        CheckBox friday = (CheckBox)findViewById(R.id.friday);
        if(friday.isChecked()){
            settings.setDayEnabled(DayOfWeek.FRIDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.FRIDAY);
        }
    }

    public void onSaturdayClick(View view) {
        CheckBox saturday = (CheckBox)findViewById(R.id.saturday);
        if(saturday.isChecked()){
            settings.setDayEnabled(DayOfWeek.SATURDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.SATURDAY);
        }
    }

    public void onSundayClick(View view) {
        CheckBox sunday = (CheckBox)findViewById(R.id.sunday);
        if(sunday.isChecked()){
            settings.setDayEnabled(DayOfWeek.SUNDAY);
        } else {
            settings.setDayDisabled(DayOfWeek.SUNDAY);
        }
    }
}
