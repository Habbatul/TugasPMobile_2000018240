package com.example.a240_tugaspmobile;

import android.content.Context;

import androidx.room.Room;

public class MahasiswaDB {
    private static MahasiswaEntity instance;

    public static MahasiswaEntity getInstance(Context context) {
        if(MahasiswaDB.instance == null)
        {
            MahasiswaDB.instance = Room.databaseBuilder(context, MahasiswaEntity.class, "mahasiswa_240.db").allowMainThreadQueries().build();
        }
        return MahasiswaDB.instance;
    }
}
