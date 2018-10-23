package com.himanshurawat.ration.respository.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.himanshurawat.ration.respository.db.dao.PeopleDao;
import com.himanshurawat.ration.respository.db.entity.People;
import com.himanshurawat.ration.util.Constant;

@Database(entities = {People.class},version = 1,exportSchema = false)
public abstract class PeopleDatabase extends RoomDatabase {


    public abstract PeopleDao getPeopleDao();

    private static PeopleDatabase INSTANCE = null;

    public static PeopleDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (PeopleDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PeopleDatabase.class,
                        Constant.DATABASE_NAME).allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }



}
