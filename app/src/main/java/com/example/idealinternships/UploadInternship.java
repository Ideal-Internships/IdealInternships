package com.example.idealinternships;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class UploadInternship extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 0;
    private Company company;
    private Date applicationDeadline;
    private Date startDate;
    private Date endDate;
    private CheckBox[] racesArray;
    private CheckBox[] fieldsArray;


    /**
     *  A method which initializes the upload internship form with drop down menus and the given layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_internship);

        Spinner minAgeSpinner = findViewById(R.id.minAgeList);
        ArrayAdapter<String> minAgeAdapter = new ArrayAdapter<String>(UploadInternship.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ages));
        minAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minAgeSpinner.setAdapter(minAgeAdapter);

        Spinner maxAgeSpinner = findViewById(R.id.maxAgeList);
        ArrayAdapter<String> maxAgeAdapter = new ArrayAdapter<String>(UploadInternship.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ages));
        maxAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxAgeSpinner.setAdapter(maxAgeAdapter);

        Spinner minGradeSpinner = findViewById(R.id.minAgeList);
        ArrayAdapter<String> minGradeAdapter = new ArrayAdapter<String>(UploadInternship.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ages));
        minGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minGradeSpinner.setAdapter(minGradeAdapter);

        Spinner maxGradeSpinner = findViewById(R.id.maxAgeList);
        ArrayAdapter<String> maxGradeAdapter = new ArrayAdapter<String>(UploadInternship.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ages));
        maxGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxGradeSpinner.setAdapter(maxGradeAdapter);

        Spinner genderSpinner = findViewById(R.id.genderList);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(UploadInternship.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        Spinner incomeSpinner = findViewById(R.id.incomeList);
        ArrayAdapter<String> incomeAdapter = new ArrayAdapter<String>(UploadInternship.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.incomes));
        incomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinner.setAdapter(incomeAdapter);
        //Save important dates
        //Save the application deadline
        CalendarView deadlineField = findViewById(R.id.applicationDeadlineCalendar);
        deadlineField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                applicationDeadline = new Date(year-1900, month, dayOfMonth);
            }
        });
        //Save the start date
        CalendarView startDateField = findViewById(R.id.internshipStartDateCalendar);
        startDateField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                startDate = new Date(year-1900, month, dayOfMonth);
            }
        });
        //Save the end date
        CalendarView endDateField = findViewById(R.id.internshipEndCalendar);
        endDateField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                endDate = new Date(year-1900, month, dayOfMonth);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }


    }


    /**
     * Saves information input on the Upload Internship screen in a new internship object
     * and saves that internship object in our firebase database
     * @param v The Upload Internship screen
     */
    public void postInternship(View v) {
        // Save the name of the internship
        EditText nameField = findViewById(R.id.inputName);
        String name = nameField.getText().toString();

        //Save the company hosting the internship
        String companyName = "";



        //Save the cost of the internship
        EditText costField = findViewById(R.id.costText);
        String cost = costField.getText().toString();

        //Save the minimum age
        Spinner minAgeSpinner = findViewById(R.id.minAgeList);
        String minAge = minAgeSpinner.getSelectedItem().toString();

        //Save the maximum age
        Spinner maxAgeSpinner = findViewById(R.id.maxAgeList);
        String maxAge = maxAgeSpinner.getSelectedItem().toString();

        //Save the minimum age
        Spinner minGradeSpinner = findViewById(R.id.minGradeList);
        String minGrade= minGradeSpinner.getSelectedItem().toString();

        //Save the maximum age
        Spinner maxGradeSpinner = findViewById(R.id.maxGradeList);
        String maxGrade = maxGradeSpinner.getSelectedItem().toString();

        //Save the target gender
        Spinner genderSpinner = findViewById(R.id.genderList);
        String targetGender = genderSpinner.getSelectedItem().toString();

        //Save the target race(s)
        String targetRaces = " ";
        CheckBox white = findViewById(R.id.whiteCaucasianCheckBox);
        CheckBox black = findViewById(R.id.blackAfricanAmericanCheckBox);
        CheckBox nativeA = findViewById(R.id.nativeAmericanOrAlaskaNativeCheckBox);
        CheckBox asian = findViewById(R.id.asianPacificIslanderCheckBox);
        CheckBox lat = findViewById(R.id.latinoaCheckBox);
        CheckBox multi = findViewById(R.id.multiracialCheckBox);
        CheckBox notA = findViewById(R.id.allRacesCheckBox);

        racesArray = new CheckBox[]{white, black, nativeA, asian, lat, multi, notA};
        for (CheckBox check: racesArray){
            if(check.isChecked())
                targetRaces += check.getText() + ", ";
        }
        targetRaces = targetRaces.substring(0,targetRaces.lastIndexOf(','));

        //Save military experience
        Switch militarySwitch = findViewById(R.id.militarySwitch);
        Boolean militaryExperience = militarySwitch.isChecked();

        //Save paid
        Switch paidSwitch = findViewById(R.id.paidSwitch);
        Boolean paid = paidSwitch.isChecked();

        //Save the field(s)
        String fields = " ";
        CheckBox bio = findViewById(R.id.biologyCheckBox);
        CheckBox chem = findViewById(R.id.chemistryCheckBox);
        CheckBox engin = findViewById(R.id.engineeringCheckBox);
        CheckBox phys = findViewById(R.id.physicsCheckBox);
        CheckBox compSci = findViewById(R.id.computerScienceCheckBox);
        CheckBox music = findViewById(R.id.musicCheckBox);
        CheckBox art = findViewById(R.id.artCheckBox);
        CheckBox theatre = findViewById(R.id.theatreCheckBox);
        CheckBox enviro = findViewById(R.id.environmentalScienceCheckBox);
        CheckBox neuro = findViewById(R.id.neuroscienceCheckBox);
        CheckBox med = findViewById(R.id.medicineCheckBox);
        CheckBox writ = findViewById(R.id.writingCheckBox);
        CheckBox business = findViewById(R.id.businessCheckBox);
        CheckBox gov = findViewById(R.id.governmentPoliticsCheckBox);
        CheckBox marketing = findViewById(R.id.marketingCheckBox);
        CheckBox math = findViewById(R.id.mathCheckBox);
        CheckBox research = findViewById(R.id.researchCheckBox);
        CheckBox science = findViewById(R.id.scienceCheckBox);
        CheckBox history = findViewById(R.id.historyCheckBox);
        CheckBox communications = findViewById(R.id.communicationsCheckBox);
        CheckBox other = findViewById(R.id.otherFieldCheckBox);

       fieldsArray = new CheckBox[]{bio, chem, engin, phys, compSci, music, art, theatre, enviro, neuro,
               med, writ, business, gov, marketing, math, research, science, history, communications, other};
        for (CheckBox check: fieldsArray){
            if(check.isChecked())
                fields += check.getText() + ", ";
        }
        fields = fields.substring(0,fields.lastIndexOf(','));

        //Save the location
        EditText locationField = findViewById(R.id.locationText);
        String location = locationField.getText().toString();

        //Save target income
        Spinner incomeSpinner = findViewById(R.id.incomeList);
        String targetIncome = incomeSpinner.getSelectedItem().toString();

        //Save preReqs
        EditText preReqsField = findViewById(R.id.prerequisitesText);
        String preReqs = preReqsField.getText().toString();

        //Save application link
        EditText appLinkField = findViewById(R.id.linkToApplicationText);
        String appLink = appLinkField.getText().toString();

        //Save description
        EditText descriptionField = findViewById(R.id.descriptionText);
        String description = descriptionField.getText().toString();


        Internship i = new Internship(name, applicationDeadline, startDate, endDate, company, cost,
                                    minAge, maxAge, minGrade, maxGrade, targetGender, targetRaces,
                                    militaryExperience, paid, fields, location, targetIncome,
                                    preReqs, appLink, description);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Internships");
        DatabaseReference iRef = myRef.child(i.getName());
        iRef.setValue(i);

    }

}
