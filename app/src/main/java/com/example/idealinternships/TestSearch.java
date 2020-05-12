package com.example.idealinternships;


import android.content.ContentQueryMap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TestSearch extends AppCompatActivity {

    private View v;
    private RecyclerView recycler;
    private CardViewAdapterSearch adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList internshipsList;
    private ArrayList internshipFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);
        //setHasOptionsMenu(true)


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Internships");
        internshipsList = new ArrayList<Internship>();
        //internshipFull = new ArrayList<Internship>();


        Log.d("database", "got");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Internship i = ds.getValue(Internship.class);
                    internshipsList.add(i);
                    //internshipFull.add(i);
                    adapter.notifyDataSetChanged();
                    Log.d("searching", internshipsList.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });

        Log.d("searching", "after error" + internshipsList.toString());


        recycler = findViewById(R.id.manageInternshipsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CardViewAdapterSearch(internshipsList);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        //searchView = v.findViewById(R.id.studentSearchView);
        //searchView.setOnQueryTextListener();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        Log.d("searching", "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        Log.d("searching", "menu inflated");
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("searching","text change");
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
                Log.d("searching","text change successful");
                return false;
            }
        });

        return true;
    }



}


