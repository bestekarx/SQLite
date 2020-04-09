package com.example.sqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity
{
    // değişkenleri oluşturduk.
    private EditText _edt_username, _edt_password, _edt_fullname;
    private Button _btnregister;

    //repoda kullanmak için context oluşturduk.
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        _context = this;

        _edt_username = findViewById(R.id.edt_username);
        _edt_password = findViewById(R.id.edt_password);
        _edt_fullname = findViewById(R.id.edt_fullname);
        _btnregister = findViewById(R.id._btnregister);


        //kayıt ola tıklayınca
        _btnregister.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(RegisterPage.this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_LONG).show();
                    return; // or break, continue, throw
                }
                // user modelimizi oluşturuyoruz.
                user u = new user(0,username,password,fullname);
                //repomuzu tanımlayıp, repoda oluşturduğumuz inserte gönderiyoruz.
                userRepo userRepo = new userRepo(_context);
                long resultId = userRepo.insert(u);

                //eğer insert olmadıysa error veriyoruz.
                if (resultId == 0)
                {
                    Toast.makeText(RegisterPage.this,"Kayıt oluşturalamadı, lütfen tekrar deneyiniz.",Toast.LENGTH_LONG).show();
                    return;
                }

                // eğer kayıt başarılı ise login sayfamıza geri dönüyoruz.
                Intent intent = new Intent(RegisterPage.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
