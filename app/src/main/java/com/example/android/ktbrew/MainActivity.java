package com.example.android.ktbrew;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /**
     * method to toggle step1 between viewable and hidden based on CheckBox
     */
    public void step1_ingredients_and_tools(View view){
        CheckBox step1_checkbox = (CheckBox) findViewById(R.id.step1_checkbox);
        boolean isChecked = step1_checkbox.isChecked();
        if (isChecked){
            LinearLayout layout1=(LinearLayout)findViewById(R.id.step1_content);
            layout1.setVisibility(View.GONE);
        } else {
            LinearLayout layout1=(LinearLayout)findViewById(R.id.step1_content);
            layout1.setVisibility(View.VISIBLE);
        }
    }

    /**
     * method to toggle step2 between viewable and hidden based on CheckBox
     */
    public void step2_pre_brew(View view){
        CheckBox step1_checkbox = (CheckBox) findViewById(R.id.step2_checkbox);
        boolean isChecked = step1_checkbox.isChecked();
        if (isChecked){
            LinearLayout layout2=(LinearLayout)findViewById(R.id.step2_content);
            layout2.setVisibility(View.GONE);
        } else {
            LinearLayout layout2=(LinearLayout)findViewById(R.id.step2_content);
            layout2.setVisibility(View.VISIBLE);
        }
    }

    /**
     * method to toggle step3 between viewable and hidden based on CheckBox
     */
    public void step3_brew(View view){
        CheckBox step3_checkbox = (CheckBox) findViewById(R.id.step3_checkbox);
        boolean isChecked = step3_checkbox.isChecked();
        if (isChecked){
            LinearLayout layout3=(LinearLayout)findViewById(R.id.step3_content);
            layout3.setVisibility(View.GONE);
        } else {
            LinearLayout layout3=(LinearLayout)findViewById(R.id.step3_content);
            layout3.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     * @param y is current year
     * @param m is current month
     * @return number of days in specified month of year
     */
    public int numOfDays(int y, int m){
        int numOfDays = 0;
        Boolean leapYear = ((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0));
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12){
            numOfDays = 31;
        }
        else if (m == 2){
            if (leapYear) {
                numOfDays = 29;
            } else{numOfDays = 28;}
        } else if (m == 4 || m == 6 || m == 9 || m == 11){
            numOfDays = 30;
        }
        return numOfDays;
    }

    /**
     * @param day of current month
     * @param dFromEOM is number of days from the end of the month
     * @return day to set calendar reminder
     */
    public int dayForReminder(int day, int dFromEOM){
        int dayForReminder = 0;
        if (dFromEOM < 5){
            dayForReminder = 5 - dFromEOM;
        } else{dayForReminder = day + 5;}
        return dayForReminder;
    }

    public int endMinute(int min){
        int endMinute = 0;
        int sixtyMinusCurrentMin = 60 - min;
        if (sixtyMinusCurrentMin < 30){
            endMinute = sixtyMinusCurrentMin;
        } else {endMinute = min + 30;}
        return endMinute;
    }

    public int endHour(int min){
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int endHour = 0;
        int sixtyMinusCurrentMin = 60 - min;
        if (sixtyMinusCurrentMin < 30){
            endHour = endHour + 1;
        } else {endHour = h;}
        return endHour;
    }

    /**
     * method with Calendar Intent to create calendar reminder event to check Kombucha
     */
    public void setCalReminder(View view){
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int dFromEOM = numOfDays(y,m) - day;

//        TextView test=(TextView)findViewById(R.id.test);
//        String testString = "month: " + m + " numOfDays: " + Integer.toString(numOfDays(y,m)) + " today: " + Integer.toString(day) + " dFromEOM: " + dFromEOM + " Five days from now: " + dayForReminder(day, dFromEOM);
//        test.setText(testString);

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(y, m, dayForReminder(day, dFromEOM), h, min);
        Calendar endTime = Calendar.getInstance();
        endTime.set(y, m, dayForReminder(day, dFromEOM), endHour(min), endMinute(min));
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Check Kombucha")
                .putExtra(CalendarContract.Events.DESCRIPTION, "")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
