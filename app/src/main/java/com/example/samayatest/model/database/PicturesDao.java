package com.example.samayatest.model.database;

import com.example.samayatest.model.data.PicturesRoom;

import java.util.ArrayList;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PicturesDao {
    /*
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Delete
    void delete(User user);*/

    @Query("SELECT * FROM Pictures")
    ArrayList<PicturesRoom> getAll();

    @Insert
    void insertAll(PicturesRoom... picturesRooms);
}
