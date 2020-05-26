package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    EditText et_email,et_password;
    String email;
    DbHelper databaseHelper = new DbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

    }
    public void goBack(View view){
        Intent go = new Intent(this, SignInActivity.class);
        startActivity(go);
    }
    public void update(View v){
        try {
            DbHelper db = new DbHelper(this);
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            if(email.equals("") || password.equals("")){
                Toast.makeText(getApplicationContext(),"Fields are empty.\n" +
                        "Please fill in all fields",Toast.LENGTH_SHORT).show();
            }else {
                if(db.chkemail1(email)){
                    if (db.isValidEmail(email)){
                        if(db.isValidPassword(password)){
                            String res = db.updatePassword(email, password);
                            Toast.makeText(this, res, Toast.LENGTH_LONG).show();
                        }else
                            Toast.makeText(getApplicationContext(),"Please prefer safe password.\n" +
                                    "Your password must be at least eight digits," +
                                    "must have a special character and a uppercase letter.",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"INVALID EMAIL \n" +
                                "ADDRESS",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(),"NOT REGISTERED \n"+
                            "EMAIL ADDRESS",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
