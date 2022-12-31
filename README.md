# TugasPMobile_2000018240
Aplikasi ini dibuat oleh Habbatul Qolbi H (2000018240) dengan menerapkan Room Presistence dan Shared Preference.


## Feature aplikasi
- Register, menambahkan data ke database
<img src="[https://github.com/Habbatul/TugasPMobile_2000018240/issues/3#issue-1515064630](https://user-images.githubusercontent.com/121380847/210130792-0b63011b-2629-4a86-8c73-68b870a3c6d7.png)" width="50%" height="50%">
- Login, melakukan login dengan mencari data dari database lalu menerapkan room preference untuk tetap login ketika berhasil login
<img src="[https://github.com/Habbatul/TugasPMobile_2000018240/issues/3#issue-1515064630](https://user-images.githubusercontent.com/121380847/210130776-f4c089de-5972-494b-8e0c-cc5c74208101.png)" width="50%" height="50%">
- Home, Halaman ini menampilkan nama static siapa nama mahasiswa yang masuk dengan menerapkan room preference
- Logout, tombol ini berguna untuk menghapus room preference untuk tetap login dan kembali ke halaman Login
<img src="[https://github.com/Habbatul/TugasPMobile_2000018240/issues/3#issue-1515064630](https://user-images.githubusercontent.com/121380847/210130767-ca1c9fa0-60d8-41e7-84ad-4c04a8a3e759.png)" width="50%" height="50%">

## Flow Aplikasi dan Highlight coding yang digunakan
- Pengguna masuk kehalaman Login
disini halaman login adalah halaman awal aplikasi masuk. Login berada pada class MainActivity sehingga pada AndroidManifest.xml perlu diinisialisasi bahwa MainActivity.java adalah Main.

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <meta-data
                android:name="android.app.lib_name"
                android:value="" />
        </activity>
        
 - Kemudian masuk ke halaman Registrasi (Nim harus bersifat uniq, data disimpan kedalam sqlite). Disini struktur database yang dibuat pada Mahasiswa.java adalah :
 
        @Entity
        public class Mahasiswa {
            @PrimaryKey
            @NonNull
            @ColumnInfo(name = "nim")
            public String nim;

            @ColumnInfo(name = "name")
            public String name;

            @ColumnInfo(name = "password")
            public String password;
        }
    untuk menyimpan ke SQLite pada room diperlukan Query pada UserDao sebagai berikut :

        @Insert
        void insertAll(Mahasiswa... users);
        
    dan penerapan pada Register.java adalah sebagai berikut :

        UserDao user = MahasiswaDB.getInstance(getApplicationContext()).userDao();
        user.insertAll(buatUser());

- Setelah registrasi berhasil, pengguna kembali ke halaman login. Disini memakai finish() pada Register.java untuk kembali ke halaman login atau MainActivity, juga ada Toast unutk memberi tahu user bahwa akun telah terdaftar ketika berhasil terdaftar

       Toast.makeText(Register.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
       finish();
                    
- Pengguna login (Jika akun belum terdaftar menampilkan info akun tidak valid, jika sudah terdaftar masuk ke halaman login). Pada button layout pada MainActivity.java akan diterapkan conditional if-else adalah sebagai berikut :

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
            
    Sedangkan fungsi validasi() ini akan mengembalikan nilai boolean dari hasil select NIM dan PW dari database SQLite
    
      private boolean validasi()
      {
          String currentNim = this.InputanNim.getText().toString();
          String currentPw = this.InputanPw.getText().toString();

          UserDao user = MahasiswaDB.getInstance(this).userDao();
          UserSaatIni = user.findByNimAndPassword(currentNim, currentPw);

          return UserSaatIni !=null?true:false;
      }
    
    findByNimAndPassword(currentNim, currentPw) memiliki query pada UserDao Sebagai berikut :
    
      @Query("SELECT * FROM Mahasiswa WHERE nim = :nim AND password = :password LIMIT 1")
      Mahasiswa findByNimAndPassword(String nim, String password);
    
- Login Berhasil dan Menampilkan Halaman Login serta Logout Button (Jika Logout Button belum d tekan, walaupun aplikasi di close dan masuk kembali, pengguna akan otomatis masuk ke halaman home. Sedangkan jika logout button telah ditekan pengguna harus melakukan login untuk masuk ke halaman home) Cukup gunakan sharedPreference.
    Membuat variabel SharedPreference nya dulu pada MainActivity.java sehingga ada data yang tersimpan untuk trigger aplikasi tetap berada dihalaman home ketika sudah login :
    
      private SharedPreferences preference;
    
    Kemudian Pada void onCreate() memberikan nilai dari variabel preference berupa kembalian dari fungsi getSharedPreference(), dimana disini nantinya akan dibuat SharedPreference dengan nama cek_login.xml
    
      preference = this.getSharedPreferences("cek_login", Context.MODE_PRIVATE);
      
    Tidak lupa membuat variabel yang akan diisi pada sharedpreference nantinya :
    
      private static final String AUTO_LOGIN_KEY = "key_tetapLogin";
      
    Disini menerapkan SharedPreference dengan fungsi sebagai berikut :
    
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
    Fungsi diatas diterapkan sebagai berikut
    
      - untuk makeTetapLogin() diterapkan pada event button login karena ini akan membuat Sharedpreference terisi data
      - untuk TetapLogin() diterapkan pada OnCreate() disini akan mengarahkan langsung ke halaman Home.java bilasudah login
      
    Kemudian pada Home.java juga membuat variabel yang sama untuk auto loginnya, yaitu tipe SharedPreference dan untuk String isian SharedPreference nya:
      
      private SharedPreferences sharedPrefs;
      private static final String AUTO_LOGIN_KEY = "key_tetapLogin";
      
    Kemudian pada onCreate memberikan isian pada variabel sharedPrefs :
    
      sharedPrefs = getSharedPreferences("cek_login", Context.MODE_PRIVATE);
      
    Kemudian pada button Logout diberikan fungsi untuk menghapus isian dari SharedPreference yang telah dibuat agar bisa kembali ke halaman login ketika keluar aplikasi:
    
      SharedPreferences.Editor editor = sharedPrefs.edit();
      editor.remove(AUTO_LOGIN_KEY);
      
    Untuk bisa kembali ke halaman login maka pada button logout menerapkan :
    
        Intent ini = new Intent(Home.this,MainActivity.class);
        startActivity(ini);
        finish();
     
- Kemudian ada tambahan disini ketika dihalaman home maka akan menampilkan kata Selamat datang + user, dimana user akan menyesuaikan nama dari mahasiswa yang melakukan login, ini dilakukan dengan menggunakan SharedPreference.
    Pertama membuat Querynya terlebih dahulu dengan kembalian String pada UserDao :
      
      @Query("SELECT name FROM Mahasiswa WHERE nim = :nims LIMIT 1")
      String namaMhs(String nims);
      
    Membuat class untuk SharedPreference nama user :
    
      public class PreferenceNIM {
        static final String KEY_USER_TEREGISTER ="user";


        private static SharedPreferences getSharedPreference(Context context){
            return PreferenceManager.getDefaultSharedPreferences(context);
          }
          public static void setRegisteredUser(Context context, String username){
              SharedPreferences.Editor editor = getSharedPreference(context).edit();
              editor.putString(KEY_USER_TEREGISTER, username);
              editor.apply();
          }
          public static String getRegisteredUser(Context context){
              return getSharedPreference(context).getString(KEY_USER_TEREGISTER,"");
          }
      }
      
    Kemudian pada MainActivity.Java ketika kondisi valid pada saat menekan tombol login melakukan set data preference dari inputan NIM :
    
       String Nim = InputanNim.getText().toString();
       PreferenceNIM.setRegisteredUser(getBaseContext(),Nim);
       
    Kemudian pada Home.Java menerapkan Room pada pmCreate() untuk melakukan select nama berdasarkan NIM pada Shared Preference kemudian mengubahnya pada bagian layout aplikasi :
        
        TextView kataWelcome = findViewById(R.id.welcome_text);
        UserDao user = MahasiswaDB.getInstance(this).userDao();
        String Nims = PreferenceNIM.getRegisteredUser(getBaseContext());
        kataWelcome.setText("Selamat Datang \n"+ user.namaMhs(Nims));
    
      
## Credit
> Habbatul Qolbi H (2000018240)
