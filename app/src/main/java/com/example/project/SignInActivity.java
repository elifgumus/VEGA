package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    TextView dontHaveAnAccount,forgotPassword;
    EditText sign_in_email,sign_in_password;
    Button sign_in_btn;
    DbHelper db;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        db = new DbHelper(this);

        dontHaveAnAccount=findViewById(R.id.tv_dont_have_an_account);
        forgotPassword = findViewById(R.id.sign_in_forgot_password);

        db= new DbHelper(this);
        sign_in_email = findViewById(R.id.sign_in_email);
        sign_in_password = findViewById(R.id.sign_in_password);
        sign_in_btn = (Button) findViewById(R.id.sign_in_btn);

    }
    public void SignIn(View v){
        try {
            String email = sign_in_email.getText().toString().trim();
            String password1 = sign_in_password.getText().toString().trim();
            if (db.isValidEmail(email)){
                if(db.SignIn(email, password1) == true){
                    ArrayList<User> userInfo = new ArrayList<>();
                    userInfo = db.getUserInfo(email);
                    Toast.makeText(this, "Hoş geldiniz", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignInActivity.this,BottomNavigation.class);
                    intent.putExtra("email", email);
                    intent.putExtra("user", userInfo.get(0).getName());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Yanlış e-posta ya da kullanıcı adı", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Lütfen geçerli bir e-posta giriniz.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void signUp(View view){
        Intent b = new Intent(this, MainActivity.class);
        startActivity(b);
    }
    public void forgotPassword(View view){
        Intent f = new Intent(this, ForgotPassword.class);
        startActivity(f);
    }

}
