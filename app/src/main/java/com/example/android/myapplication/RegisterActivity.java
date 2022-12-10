package com.example.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {


    TextView alreadyHaveAccount;
    EditText inputEmailRegister, inputPasswordRegister, inputConfirmPasswordRegister;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveAccount=(TextView) findViewById(R.id.already_have_account);
        inputEmailRegister=(EditText)findViewById(R.id.input_Email_register);
        inputPasswordRegister=(EditText)findViewById(R.id.input_password_register);
        inputConfirmPasswordRegister=(EditText)findViewById(R.id.confirm_Password_register);
        registerButton=(Button)findViewById(R.id.register_button);



        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

    }
}