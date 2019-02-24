package ch.hslu.mobpro.firstapp;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Logs lifecycle transitions into the LogCat view of the ADT-Debugger.
 *
 * @author Ruedi Arnold
 */

public class LifecycleLogActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lifecycle_logger);
        Log.i("hslu_mobApp", "onCreate() aufgerufen");
    }


    // TODO: Add further implementions of onX-methods.

}