package com.example.persistenz;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    private final String COUNTER_KEY = "";
    private final String FILE = "myFile.txt";
    private TextView section_1_text;

    private final int READ_REQUEST_ID = 24;
    private final int WRITE_REQUEST_ID = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finde die TextView "section1_text"
        section_1_text = (TextView) findViewById(R.id.section1_text);
    }

    @Override
    protected void onResume() {

        super.onResume();

        // Foliensatz "Android-3", Seite 7
        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        final int newResumeCount = preferences.getInt(COUNTER_KEY, 0) + 1;
        final SharedPreferences.Editor editor = preferences.edit();
        
        editor.putInt(COUNTER_KEY, newResumeCount);
        editor.apply();

        // Überschreibe den Text in Section 1
        section_1_text.setText("MainActivity.onResume() wurde seit der Installation dieser App " + newResumeCount + " mal aufgerufen.");

        getTeaPreferences();
    }

    public void startAppPreferenceActivity(View view) {
        Intent intent = new Intent(this, AppPreferenceActivity.class);
        startActivity(intent);
    }

    private void getTeaPreferences() {

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this); // Foliensatz "Android-3", Seite 15

        String teaSweetener = preferences.getString("teaSweetener", "Honig");
        String teaPreferred = preferences.getString("teaPreferred", "Lipton - Schwarztee");

        String str = "Ich trinke am liebsten " + teaPreferred;

        if (preferences.getBoolean("teaWithSugar", true)) {

            str += " mit " + teaSweetener;

        }

        TextView textView = (TextView) findViewById(R.id.section1_tee);
        textView.setText(str + ".");

    }

    public void setDefaultValues(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("teaWithSugar", true);
        editor.putString("teaSweetener", "Honig");
        editor.putString("teaPreferred", "Lipton - Schwarztee");

        editor.apply();

        getTeaPreferences();

    }

    public void loadExtFileWithPermission() {
        int grant = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (grant != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, READ_REQUEST_ID);
        } else {
            readFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_REQUEST_ID:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions " + permissions[0] + " denied!", Toast.LENGTH_SHORT).show();
                } else {
                    readFile();
                }
            break;
            case WRITE_REQUEST_ID:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions " + permissions[0] + " denied!", Toast.LENGTH_SHORT).show();
                } else {
                    writeFile();
                }
        }
    }

    private void readFile() {
        StringBuffer txtData = new StringBuffer();
        FileInputStream fIn = null;
        BufferedReader myReader = null;
        try {
            File myFile = new File(getFileName());
            fIn = new FileInputStream(myFile);
            myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null)
            {
                aBuffer += aDataRow ;
            }
            setTextFieldText(aBuffer);
            myReader.close();
            Toast.makeText(this,"Done reading SD '"+getFileName()+"'",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        } finally {
            try {
                myReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void writeFile() {
        FileOutputStream fOut = null;
        OutputStreamWriter myOutWriter = null;
        try {
            File myFile = new File(getFileName());
            myFile.createNewFile();
            fOut = new FileOutputStream(myFile);
            myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(getTextFieldText());
            myOutWriter.close();
            fOut.close();
            Toast.makeText(this,"Done writing SD '"+getFileName()+"'", Toast.LENGTH_SHORT).show();
            setTextFieldText("");
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        } finally {
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                myOutWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String getFileName() {
        return this.getFilesDir()+"/saved_text.txt";
    }

    void setTextFieldText(String text) {
        TextView textView = (TextView) findViewById(R.id.textfield);
        textView.setText(text);
    }

    String getTextFieldText() {
        TextView textView = (TextView) findViewById(R.id.textfield);
        return textView.getText().toString();
    }

    private void writeToExternalFile() {
        int grant = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (grant != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, WRITE_REQUEST_ID);
        } else {
            writeFile();
        }
    }

    public void saveText(View view) {
        EditText editText = (EditText) findViewById(R.id.textfield);
        String str = editText.getText().toString();
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        if (!checkBox.isChecked()) {

            FileOutputStream fileOutputStream = null;

            try {

                fileOutputStream = openFileOutput(FILE, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(str);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                fileOutputStream.close();

                editText.setText("Text gespeichert.");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                editText.setText("File nicht gefunden.");
            } catch (IOException e) {
                e.printStackTrace();
                editText.setText("Fehler beim Speichern.");
            }

        } else {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                writeToExternalFile();
            } else {
                Toast.makeText(this,"Please insert a SD card...'", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadText(View view) {
        EditText editText = (EditText) findViewById(R.id.textfield);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        if (!checkBox.isChecked()) {

            FileInputStream fileInputStream = null;
            ArrayList<String> lines = new ArrayList<>();

            try {
                fileInputStream = openFileInput(FILE);

                if (fileInputStream != null) {

                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String streamLine;
                    while (( streamLine = bufferedReader.readLine() ) != null) {
                        lines.add(streamLine);
                    }
                    fileInputStream.close();

                    String text = "";
                    int count = 0;

                    for (String line : lines) {

                        if (count != 0) {
                            text += "\n" + line;
                        } else {
                            text += line;
                        }

                        count++;

                    }

                    editText.setText(text);

                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                loadExtFileWithPermission();
            } else {
                Toast.makeText(this,"Please insert a SD card...'", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
