package com.example.a240_tugaspmobile;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Mahasiswa")
    List<Mahasiswa> getAll();

    @Query("SELECT * FROM Mahasiswa WHERE nim IN (:userNims)")
    List<Mahasiswa> loadAllByIds(int[] userNims);

    @Query("SELECT * FROM Mahasiswa WHERE nim = :nim AND password = :password LIMIT 1")
    Mahasiswa findByNimAndPassword(String nim, String password);

    //nanti diubah
    @Query("SELECT name FROM Mahasiswa WHERE nim = :nims LIMIT 1")
    String namaMhs(String nims);

    @Query("SELECT * FROM Mahasiswa WHERE nim = :nim LIMIT 1")
    Mahasiswa findByNim(String nim);

    @Query("DELETE FROM Mahasiswa")
    public void deleteAllxx();

    @Insert
    void insertAll(Mahasiswa... users);

    @Delete
    void delete(Mahasiswa user);
}
