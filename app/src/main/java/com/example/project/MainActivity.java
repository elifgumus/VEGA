package com.example.project;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText sign_up_email, sign_up_name,sign_up_password,sign_up_confirm;
    private String emailPattern = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";
    TextView tv_have_an_account;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_name = findViewById(R.id.sign_up_name);
        sign_up_password = findViewById(R.id.sign_up_password);
        sign_up_confirm = findViewById(R.id.sign_up_confirm);
        tv_have_an_account = findViewById(R.id.tv_have_an_account);

        helper = new DbHelper(this);
    }


    public void RegisterUser(View v){
        try {
                DbHelper db = new DbHelper(this);
                String email = sign_up_email.getText().toString().trim();
                String name = sign_up_name.getText().toString().trim();
                String password = sign_up_password.getText().toString().trim();
                String confirm = sign_up_confirm.getText().toString().trim();
                if(email.equals("") || name.equals("") || password.equals("") || confirm.equals("") ){
                Toast.makeText(getApplicationContext(),"Fields are empty.\n" +
                        "Please fill in all fields",Toast.LENGTH_SHORT).show();
                }else {
                    if(db.chkemail(email)){
                        if (db.isValidEmail(email))  {
                            if(db.isValidPassword(password)){
                                if(password.equals(confirm)){
                                    String result = helper.UserAdd(email, name, password, confirm);
                                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Passwords do not match.\n" +
                                            "Please try again",Toast.LENGTH_SHORT).show();
                                }
                            }else
                                Toast.makeText(getApplicationContext(),"Please prefer safe password.\n" +
                                        "Your password must be at least eight digits," +
                                        "must have a special character and a uppercase letter.",Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(getApplicationContext(),"Invalid email \n" +
                                    "address",Toast.LENGTH_SHORT).show();

                    }else
                        Toast.makeText(getApplicationContext(),"Registered email address",Toast.LENGTH_SHORT).show();

                }


            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
  public void signIn(View view){
      Intent intent = new Intent(this, SignInActivity.class);
      startActivity(intent);
  }


}
