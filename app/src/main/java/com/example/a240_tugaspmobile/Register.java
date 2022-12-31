package com.example.a240_tugaspmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private EditText edtName;
    private EditText edtNim;
    private EditText edtPw;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.btn_register = this.findViewById(R.id.btn_register);
        this.edtName = this.findViewById(R.id.nama);
        this.edtNim = this.findViewById(R.id.NIM);
        this.edtPw = this.findViewById(R.id.PW);

        this.btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = validasi();
                if (valid) {
                    UserDao user = MahasiswaDB.getInstance(getApplicationContext()).userDao();
                    user.insertAll(buatUser());
                    Toast.makeText(Register.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private Mahasiswa buatUser() {
        Mahasiswa u = new Mahasiswa();
        u.name = this.edtName.getText().toString().trim();
        u.nim = this.edtNim.getText().toString().trim();
        u.password = this.edtPw.getText().toString().trim();
        return u;
    }

    private boolean validasi() {
        String NimInput = this.edtNim.getText().toString().trim();
        String PwInput = this.edtPw.getText().toString().trim();
        String NamaInput = this.edtName.getText().toString().trim();

        if (NamaInput.isEmpty())
            Toast.makeText(this, "Nama masih kosong!", Toast.LENGTH_SHORT).show();
        else if (NimInput.isEmpty())
            Toast.makeText(this, "NIM masih kosong!", Toast.LENGTH_SHORT).show();
        else if (PwInput.isEmpty())
            Toast.makeText(this, "Password masih kosong!", Toast.LENGTH_SHORT).show();
        else {
            UserDao user = MahasiswaDB.getInstance(this).userDao();
            Mahasiswa currentUserData = user.findByNim(NimInput);
            if (currentUserData != null)
                Toast.makeText(this, "Akun sudah yang didaftarkan sudah ada!", Toast.LENGTH_SHORT).show();
            else
                return true;
        }
        return false;
    }
}