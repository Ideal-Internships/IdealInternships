package com.example.idealinternships;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;



public class UploadInternshipFragment extends Fragment  {
    private Date applicationDeadline;
    private Date startDate;
    private Date endDate;
    private View v;



    /**
     * Opens the upload internship page from the bottom navigation and saves internship information to firebase
     * @param inflater inflater
     * @param conatiner part of the screen above the navigation bar
     * @param savedInsatnceState the app's state
     * @return the screen
     */



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup conatiner, @Nullable Bundle savedInsatnceState){
        View v = inflater.inflate(R.layout.activity_upload_internship, conatiner,false);

        Spinner ageSpinner = v.findViewById(R.id.ageList);
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ages));
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        Spinner genderSpinner = v.findViewById(R.id.genderList);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        Spinner raceSpinner = v.findViewById(R.id.raceList);
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.races));
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(raceAdapter);

        Spinner fieldSpinner = v.findViewById(R.id.fieldList);
        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.fields));
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldSpinner.setAdapter(fieldAdapter);

        Spinner incomeSpinner = v.findViewById(R.id.incomeList);
        ArrayAdapter<String> incomeAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.incomes));
        incomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinner.setAdapter(incomeAdapter);
        //Save important dates
        //Save the application deadline
        CalendarView deadlineField = v.findViewById(R.id.applicationDeadlineCalendar);
        deadlineField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                applicationDeadline = new Date(year-1900, month, dayOfMonth);
            }
        });
        //Save the start date
        CalendarView startDateField = v.findViewById(R.id.internshipStartDateCalendar);
        startDateField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                startDate = new Date(year-1900, month, dayOfMonth);
            }
        });
        //Save the end date
        CalendarView endDateField = v.findViewById(R.id.internshipEndCalendar);
        endDateField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                endDate = new Date(year-1900, month, dayOfMonth);
            }
        });

        Button postB = v.findViewById(R.id.postButton);

        postB.setOnClickListener(new View.OnClickListener()
              {
                  @Override
                  public void onClick(View view){
                      Log.e("hi", "hello");
                      /**
                      // Save the name of the internship
                      EditText nameField = view.findViewById(R.id.inputName);
                      String name = nameField.getText().toString();

                      //Save the company hosting the internship
                      Company company = new Company();
                      EditText linkField = view.findViewById(R.id.linkToApplicationText);
                      company.setLink(linkField.getText().toString());

                      //Save the cost of the internship
                      EditText costField = view.findViewById(R.id.costText);
                      String cost = costField.getText().toString();

                      //Save the target age
                      Spinner ageSpinner = view.findViewById(R.id.ageList);
                      String targetAge = ageSpinner.getSelectedItem().toString();

                      //Save the target gender
                      Spinner genderSpinner = view.findViewById(R.id.genderList);
                      String targetGender = genderSpinner.getSelectedItem().toString();

                      //Save the target race
                      Spinner raceSpinner = view.findViewById(R.id.raceList);
                      String targetRace = raceSpinner.getSelectedItem().toString();

                      //Save military experience
                      Switch militarySwitch = view.findViewById(R.id.militarySwitch);
                      Boolean militaryExperience = militarySwitch.isChecked();

                      //Save the field
                      Spinner fieldSpinner = view.findViewById(R.id.fieldList);
                      String field = fieldSpinner.getSelectedItem().toString();

                      //Save the location
                      EditText locationField = view.findViewById(R.id.locationText);
                      String location = locationField.getText().toString();

                      //Save target income
                      Spinner incomeSpinner = view.findViewById(R.id.incomeList);
                      String targetIncome = incomeSpinner.getSelectedItem().toString();


                      Internship i = new Internship(name, applicationDeadline, startDate, endDate, company, cost,
                              targetAge, targetGender, targetRace, militaryExperience, field,
                              location, targetIncome);

                      FirebaseDatabase database = FirebaseDatabase.getInstance();
                      DatabaseReference myRef = database.getReference(i.getName());
                      myRef.setValue(i);*/


                  }

              }

        );

        return v;
    }









}