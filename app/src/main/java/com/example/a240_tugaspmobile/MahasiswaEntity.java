package com.example.a240_tugaspmobile;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Mahasiswa.class}, version = 1, exportSchema = false)
public abstract class MahasiswaEntity extends RoomDatabase {
    public abstract UserDao userDao();
}
