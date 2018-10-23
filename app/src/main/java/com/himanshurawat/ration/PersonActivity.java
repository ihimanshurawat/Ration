package com.himanshurawat.ration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.himanshurawat.ration.respository.db.entity.People;
import com.himanshurawat.ration.util.Constant;

public class PersonActivity extends AppCompatActivity {

    People person;

    private TextView ageTextView;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView addressTextView;
    private TextView genderTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        person = (People) getIntent().getSerializableExtra(Constant.PERSON_KEY);
        if(person == null){
            finish();
        }

        ageTextView = findViewById(R.id.activity_person_age_text_view);
        nameTextView = findViewById(R.id.activity_person_name_text_view);
        phoneTextView = findViewById(R.id.activity_person_phone_text_view);
        addressTextView = findViewById(R.id.activity_person_address_text_view);
        genderTextView = findViewById(R.id.activity_person_gende_text_view);

        ageTextView.setText(person.getAge()+"");
        nameTextView.setText(person.getName());
        phoneTextView.setText(person.getPhone());
        addressTextView.setText(person.getAddress());
        genderTextView.setText(person.getGender());




    }
}
