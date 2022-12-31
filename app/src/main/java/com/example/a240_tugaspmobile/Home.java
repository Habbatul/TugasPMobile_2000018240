package com.example.a240_tugaspmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity{
    private SharedPreferences sharedPrefs;
    private Button Logout_btn;
    private static final String AUTO_LOGIN_KEY = "key_tetapLogin";
    private Mahasiswa UserSaatIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView kataWelcome = findViewById(R.id.welcome_text);
        UserDao user = MahasiswaDB.getInstance(this).userDao();
        String Nims = PreferenceNIM.getRegisteredUser(getBaseContext());
        kataWelcome.setText("Selamat Datang \n"+ user.namaMhs(Nims));

        sharedPrefs = getSharedPreferences("cek_login", Context.MODE_PRIVATE);
        Logout_btn = findViewById(R.id.btn_logout);

        Logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.remove(AUTO_LOGIN_KEY);
                editor.apply();
                Intent ini = new Intent(Home.this,MainActivity.class);
                startActivity(ini);
                finish();
            }
        });
    }
}