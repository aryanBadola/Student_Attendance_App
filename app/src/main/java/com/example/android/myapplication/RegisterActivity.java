package com.example.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    TextView alreadyHaveAccount;
    EditText inputEmailRegister, inputPasswordRegister, inputConfirmPasswordRegister, inputName, inputNumber, inputDepartment;
    Button registerButton;
    ProgressDialog progressDialog;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String usernamePattern = "^[\\p{L} .'-]+$"; ;
    String phoneNumberPattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
    private static final String TAG = "RegisterActivity";

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveAccount=(TextView) findViewById(R.id.already_have_account);
        inputName=(EditText)findViewById(R.id.name_TextView);
        inputDepartment=(EditText)findViewById(R.id.department_TextView);
        inputNumber=(EditText)findViewById(R.id.number_TextView);
        inputEmailRegister=(EditText)findViewById(R.id.input_Email_register);
        inputPasswordRegister=(EditText)findViewById(R.id.input_password_register);
        inputConfirmPasswordRegister=(EditText)findViewById(R.id.confirm_Password_register);
        registerButton=(Button)findViewById(R.id.register_button);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();



        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
        
        
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });

    }

    private void performAuthentication() {
        String name=inputName.getText().toString();
        String department=inputDepartment.getText().toString();
        String number=inputNumber.getText().toString();
        String email=inputEmailRegister.getText().toString();
        String password=inputPasswordRegister.getText().toString();
        String confirmPassword=inputConfirmPasswordRegister.getText().toString();

        if(!name.matches(usernamePattern))
        {
            inputName.setError("Please enter a valid name");
            inputName.requestFocus();
        }
        else if(!number.matches(phoneNumberPattern))
        {
            inputNumber.setError("Please enter a valid phone number");
            inputNumber.requestFocus();
        }
        else if(!email.matches(emailPattern))
        {
            inputEmailRegister.setError("Enter a valid email");
            inputEmailRegister.requestFocus();
        }
        else if(password.isEmpty() || password.length()<6)
        {
            inputPasswordRegister.setError("The password must contain atleast 6 letters");
            inputPasswordRegister.requestFocus();
        }
        else if(!password.matches(confirmPassword))
        {
            inputConfirmPasswordRegister.setError("Both the passwords should match");
        }
        else
        {
            progressDialog.setMessage("Please wait while registering");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();

                        mUser=mAuth.getCurrentUser();

//                        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
//                        mUser.updateProfile(profileChangeRequest);

                        ReadWriteUserData writeUserData=new ReadWriteUserData(name,department,number,email);

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

                        databaseReference.child("Registered Users").child(mUser.getUid()).setValue(writeUserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    sendToNextActivity();
                                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: "+task.getException());
                    }
                }
            });
        }

    }

    private void sendToNextActivity() {
        Intent intent=new Intent(RegisterActivity.this, ClassActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}