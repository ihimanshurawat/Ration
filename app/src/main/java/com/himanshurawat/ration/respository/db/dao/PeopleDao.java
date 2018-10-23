package com.himanshurawat.ration.respository.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.himanshurawat.ration.respository.db.entity.People;

import java.util.List;

@Dao
public interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] addPeople(List<People> peopleList);

    @Query("Select * FROM people")
    LiveData<List<People>> getPeople();

    @Query("SELECT * FROM people WHERE name LIKE :query")
    LiveData<List<People>> getSearchResult(String query);


}
