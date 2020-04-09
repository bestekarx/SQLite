package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity  extends AppCompatActivity
{

    private EditText _edt_username, _edt_password, _edt_fullname;
    private TextView txt_fullname;

    private Button _btnUpdate;
    private Context context;
    private user oUser;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activit);

        context = this;
        _edt_username = findViewById(R.id.edt_username);
        _edt_password = findViewById(R.id.edt_password);
        _edt_fullname = findViewById(R.id.edt_fullname);
        _btnUpdate = findViewById(R.id._btnUpdate);
        txt_fullname = findViewById(R.id.txt_fullname);


        Intent intent = getIntent();
        // login sayfamızda gönderdiğimiz user modeli alıyoruz.
        oUser = (user) intent.getSerializableExtra("oUser");
        //oUser = getIntent().getExtras().getParcelable("oUser");

        if(oUser != null)
        {
            // edittextleri ve textview neslerini dolduruyoruz.
            _edt_username.setText(oUser.username);
            _edt_password.setText(oUser.password);
            _edt_fullname.setText(oUser.fullname);

            txt_fullname.setText("Hoşgeldin : " + oUser.fullname);
        }

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username, password, fullname;
                // edittextlerden gelen verileri stringe atadık.
                username = _edt_username.getText().toString();
                password = _edt_password.getText().toString();
                fullname = _edt_fullname.getText().toString();

                // eğer üç edittextten birisi boş olursa uyarı mesajı ver ve devam ettirme..
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(fullname) )
                {
                    Toast.makeText(MainActivity.this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_LONG).show();
                    return; // or break, continue, throw
                }


                userRepo  DB = new userRepo(context);
                // yeni user modeli oluşturuyoruz.
                user u = new user(oUser.id,username,password,fullname);

                DB.update(u);

                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
