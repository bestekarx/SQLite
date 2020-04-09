package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity
{

    private EditText _edt_username, _edt_password;
    private TextView _txt_register;
    private Button _btnLogin;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        context = this;
        _edt_username = findViewById(R.id.editText_loginId);
        _edt_password = findViewById(R.id.editText_loginPw);
        _txt_register = findViewById(R.id.txt_register);
        _btnLogin = findViewById(R.id.btnLogin);


        _txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent register = new Intent(Login.this,RegisterPage.class);
                startActivity(register);
            }
        });

        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username, password, fullname;
                // edittextlerden gelen verileri stringe atadık.
                username = _edt_username.getText().toString();
                password = _edt_password.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login.this,"Lütfen boş alan bırakmayınız",Toast.LENGTH_LONG).show();
                    return;
                }

                userRepo  DB = new userRepo(context);
                // repoya kullanıcı adını ve şifreyi gönderiyoruz
                user oUser = DB.getLogin(username,password);
                if (oUser.id != 0)
                {
                    // eğer gelen değer null değilse anasayfaya user modelini gönderiyoruz.
                    // modelin putextra ile gönderilmesi için user model classımıza // implements Serializable    ekledik.
                    Intent i = new Intent(Login.this,MainActivity.class);
                    i.putExtra("oUser", oUser);
                    startActivity(i);
                }
                else {
                    Toast.makeText(Login.this,"Kullanıcı bulunamadı.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
