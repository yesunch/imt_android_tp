package com.imt_atlantique.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button validate;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText birthdayEdit;
    private EditText countryEdit;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.validate = (Button)findViewById(R.id.button);
        this.firstNameEdit = (EditText) findViewById(R.id.firstname_edit);
        this.lastNameEdit = (EditText) findViewById(R.id.lastname_edit);
        this.birthdayEdit = (EditText) findViewById(R.id.date_edit);
        this.countryEdit = (EditText) findViewById(R.id.country_edit);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        this.birthdayEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        this.validate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validateAction();
            }
        });
        Log.i("Lifecycle", "onCreate method");
    }
    @Override
    protected void onStart() {
        super.onStart();
        getDelegate().onStart();
        Log.i("Lifecycle", "onStart method");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDelegate().onStart();
        Log.i("Lifecycle", "onRestart method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
        Log.i("Lifecycle", "onStop method");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("Lifecycle", "onResume method");

    }

    @Override
    protected  void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy method");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void validateAction() {
        Log.i("Lifecycle", "validateAction");
        String textToShow = this.firstNameEdit.getText().toString()+" "+this.lastNameEdit.getText().toString()
                    +" "+this.birthdayEdit.getText().toString()+" "+this.countryEdit.getText().toString();
        //Toast.makeText(getApplicationContext(), textToShow, Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(R.id.main_constraint_layout), textToShow, Snackbar.LENGTH_LONG).show();

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthdayEdit.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean resetAction(MenuItem item) {
        this.firstNameEdit.setText("");
        this.lastNameEdit.setText("");
        this.birthdayEdit.setText("");
        this.countryEdit.setText("");
        return true;
    }

}
