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

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter <PeopleAdapter.PeopleViewHolder> {

    private List<People> peopleList;
    private Context context;
    private OnPeopleSelectedListener listener;


    public PeopleAdapter(Context context, List<People> peopleList,OnPeopleSelectedListener listener){
        this.context = context;
        this.peopleList = peopleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new PeopleViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder peopleViewHolder, int i) {

        int position = peopleViewHolder.getAdapterPosition();
        People person = peopleList.get(position);

        peopleViewHolder.nameTextView.setText(person.getName());
        peopleViewHolder.ageTextView.setText(person.getAge()+"");
        peopleViewHolder.genderTextView.setText(person.getGender());
        peopleViewHolder.phoneTextView.setText(person.getPhone());
        peopleViewHolder.addressTextView.setText(person.getAddress());

    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView ageTextView;
        TextView nameTextView;
        TextView phoneTextView;
        TextView addressTextView;
        TextView genderTextView;


        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ageTextView = itemView.findViewById(R.id.people_item_view_age_text_view);
            this.nameTextView = itemView.findViewById(R.id.people_item_view_name_text_view);
            this.phoneTextView = itemView.findViewById(R.id.people_item_view_phone_text_view);
            this.addressTextView = itemView.findViewById(R.id.people_item_view_address_text_view);
            this.genderTextView = itemView.findViewById(R.id.people_item_view_gender_text_view);
            this.itemView = itemView;
        }


    }



    public interface OnPeopleSelectedListener{
        void onPeopleSelected(int position);
    }
}
