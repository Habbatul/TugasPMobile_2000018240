package com.example.a240_tugaspmobile;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String AUTO_LOGIN_KEY = "key_tetapLogin";

    private Button Login_btn;
    private Button Register_btn;
    private EditText InputanNim;
    private EditText InputanPw;
    private SharedPreferences preference;
    private Mahasiswa UserSaatIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login_btn = findViewById(R.id.btn_login);
        Register_btn = findViewById(R.id.btn_register);
        InputanNim = findViewById(R.id.NIM);
        InputanPw = findViewById(R.id.PW);
        preference = this.getSharedPreferences("cek_login", Context.MODE_PRIVATE);
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = validasi();
                if (valid){
                    String Nim = InputanNim.getText().toString();
                    PreferenceNIM.setRegisteredUser(getBaseContext(),Nim);
                    Intent i = new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                    makeTetapLogin();
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Akun tidak valid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
        TetapLogin();
    }

    private boolean validasi()
    {
        String currentNim = this.InputanNim.getText().toString();
        String currentPw = this.InputanPw.getText().toString();

        UserDao user = MahasiswaDB.getInstance(this).userDao();
        UserSaatIni = user.findByNimAndPassword(currentNim, currentPw);

        return UserSaatIni !=null?true:false;
    }


    private void makeTetapLogin()
    {
        SharedPreferences.Editor editor = this.preference.edit();
        editor.putBoolean(AUTO_LOGIN_KEY, true);
        editor.apply();
    }

    private void TetapLogin()
    {
        boolean auto = this.preference.getBoolean(AUTO_LOGIN_KEY, false);
        if (auto)
        {
            Intent i = new Intent(MainActivity.this, Home.class);
            startActivity(i);
        }
    }
}