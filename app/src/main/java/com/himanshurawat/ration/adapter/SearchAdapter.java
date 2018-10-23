package com.himanshurawat.ration.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himanshurawat.ration.R;
import com.himanshurawat.ration.respository.db.entity.People;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<People> original;
    private List<People> filtered;
    private Context context;
    private OnSearchItemClickListener listener;

    public SearchAdapter(Context context,List<People> original,OnSearchItemClickListener listener){
        this.original = original;
        this.context = context;
        this.listener = listener;
        filtered = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        final int position = searchViewHolder.getAdapterPosition();
        People person = filtered.get(position);

        searchViewHolder.nameTextView.setText(person.getName());
        searchViewHolder.ageTextView.setText(person.getAge()+"");
        searchViewHolder.genderTextView.setText(person.getGender());
        searchViewHolder.phoneTextView.setText(person.getPhone());
        searchViewHolder.addressTextView.setText(person.getAddress());
        searchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSearchItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView ageTextView;
        TextView nameTextView;
        TextView phoneTextView;
        TextView addressTextView;
        TextView genderTextView;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ageTextView = itemView.findViewById(R.id.people_item_view_age_text_view);
            this.nameTextView = itemView.findViewById(R.id.people_item_view_name_text_view);
            this.phoneTextView = itemView.findViewById(R.id.people_item_view_phone_text_view);
            this.addressTextView = itemView.findViewById(R.id.people_item_view_address_text_view);
            this.genderTextView = itemView.findViewById(R.id.people_item_view_gender_text_view);
            this.itemView = itemView;
        }
    }

    public interface OnSearchItemClickListener{
        void onSearchItemClicked(int position);
    }


    public void filterSearch(String query){
        if(!query.trim().isEmpty()) {
            filtered.clear();

            for (People people : original) {
                if (people.getName().toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(people);
                }
            }
            notifyDataSetChanged();
        }else{
            filtered = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public List<People> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<People> filtered) {
        this.filtered = filtered;
    }
}
