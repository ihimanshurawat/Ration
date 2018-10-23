package com.himanshurawat.ration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.himanshurawat.ration.respository.db.entity.People;
import com.himanshurawat.ration.util.Constant;

public class PersonActivity extends AppCompatActivity {

    People person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        person = (People) getIntent().getSerializableExtra(Constant.PERSON_KEY);
        if(person == null){
            finish();
        }


    }
}
