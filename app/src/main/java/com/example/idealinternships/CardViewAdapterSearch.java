package com.example.idealinternships;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class CardViewAdapterSearch extends RecyclerView.Adapter<CardViewAdapterSearch.CardViewHolder> implements Filterable {

    private ArrayList<Internship> internships;
    private ArrayList<Internship> internshipsFull;

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        public ImageView internshipPicView;
        public TextView nameTextView;
        public TextView descriptionTextView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            internshipPicView = itemView.findViewById(R.id.internshipPic);
            nameTextView = itemView.findViewById(R.id.internshipNameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    public CardViewAdapterSearch(ArrayList<Internship> internship){
        this.internships = internship;
        Log.d("searching", "original"+ internships.toString());
        internshipsFull = new ArrayList(internships);
        Log.d("searching", "orig" + internships.toString());
        Log.d("searching", "new:" + internshipsFull.toString());


    }



    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.internship_cardview, parent, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Internship i = internships.get(position);

        holder.internshipPicView.setImageResource(i.getImageResource());
        holder.nameTextView.setText(i.getName());
        holder.descriptionTextView.setText(i.getInternshipDescription());
    }

    @Override
    public int getItemCount() {
        return internships.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Internship> filteredList = new ArrayList<>();

            if( charSequence == null || charSequence.length() == 0){
                filteredList.addAll(internshipsFull); //Change this to show results of default filters
                Log.d("searching", "null search bar");
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                Log.d("searching", filterPattern);
                for(Internship i : internshipsFull){
                    if(i.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(i);
                        //Log.d("searching","filter pattern");
                        Log.d("searching", filterPattern);
                        Log.d("searching", i.getName());
                        Log.d("searching", internshipsFull.toString());
                    }
                }
            }

            Log.d("searching",filteredList.toString());
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            internships.clear();
            internships.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
            Log.d("searching", "published");
        }
    };
}