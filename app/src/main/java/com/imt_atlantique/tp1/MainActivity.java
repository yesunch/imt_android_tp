package com.imt_atlantique.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button validate;
    private Button addTele;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText birthdayEdit;
    private EditText countryEdit;
    private EditText teleEdit;

    private GridLayout gridTelephones;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    protected void onResume() {
        super.onResume();
        this.validate = (Button)findViewById(R.id.bt_validate);
        this.firstNameEdit = (EditText) findViewById(R.id.firstname_edit);
        this.lastNameEdit = (EditText) findViewById(R.id.lastname_edit);
        this.birthdayEdit = (EditText) findViewById(R.id.date_edit);
        this.countryEdit = (EditText) findViewById(R.id.country_edit);
        this.teleEdit = (EditText) findViewById(R.id.telephone_edit);
        this.addTele = (Button) findViewById(R.id.bt_add_tele);
        this.gridTelephones = (GridLayout) findViewById(R.id.grid_list_telephones);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        this.birthdayEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
        this.addTele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTelephone();
            }
        });
        Log.i("Lifecycle", "onResume method");

    }

    @Override
    protected  void onPause() {
        super.onPause();
        Log.i("Lifecycle", "onPause method");
    }
    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
        Log.i("Lifecycle", "onStop method");
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
        String textToShow = this.firstNameEdit.getText().toString()
                +" " +this.lastNameEdit.getText().toString()
                +" "+this.birthdayEdit.getText().toString()
                +" "+this.countryEdit.getText().toString();
        //Toast.makeText(getApplicationContext(), textToShow, Toast.LENGTH_LONG).show();
        if (textToShow.trim().isEmpty())
            textToShow = "Please complete your information";
        Snackbar.make(findViewById(R.id.main_constraint_layout), textToShow, Snackbar.LENGTH_LONG).show();

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        birthdayEdit.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean resetAction(MenuItem item) {
        if (item.getTitle().equals("reset action")) {
            this.firstNameEdit.setText("");
            this.lastNameEdit.setText("");
            this.birthdayEdit.setText("");
            this.countryEdit.setText("");
            this.teleEdit.setText("");
            this.gridTelephones.removeAllViews();
        }
        return true;
    }

    public void addTelephone() {
        String telephone = this.teleEdit.getText().toString();
        if (!InputNumberCheck.checkFormat(telephone)) {
            Snackbar.make(findViewById(R.id.main_constraint_layout), R.string.err_badformat_number, Snackbar.LENGTH_LONG).show();
            return;
        }
        final TextView itemTele = new TextView(this);
        itemTele.setText(telephone);
        itemTele.setTextColor(Color.BLACK);
        final Button delete = new Button(this);
        delete.setText("delete");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridTelephones.removeView(itemTele);
                gridTelephones.removeView(delete);
            }
        });

        gridTelephones.addView(itemTele);
        gridTelephones.addView(delete);
        this.teleEdit.setText("");
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
