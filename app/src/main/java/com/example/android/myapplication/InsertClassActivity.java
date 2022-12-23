package com.example.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertClassActivity extends AppCompatActivity {

    private static final String TAG = "InsertClassActivity";

    EditText insertSubjectCodeEditText,insertSubjectNameEditText, insertSemesterEditText, insertDepartmentEditText;
    Button createClassButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    String namePattern = "^[\\p{L} .'-]+$";
    String semesterPattern = "[1-9]|10";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_class);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        insertSubjectCodeEditText=(EditText) findViewById(R.id.insert_subject_code);
        insertSubjectNameEditText=(EditText) findViewById(R.id.insert_subject_name);
        insertSemesterEditText=(EditText) findViewById(R.id.insert_semester);
        insertDepartmentEditText=(EditText) findViewById(R.id.insert_department);
        createClassButton=(Button) findViewById(R.id.create_button_new_class);
        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        createClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });

    }

    private void performAuthentication() {
        String subjectCode=insertSubjectCodeEditText.getText().toString();
        String subjectName=insertSubjectNameEditText.getText().toString();
        String semester=insertSemesterEditText.getText().toString();
        String department=insertDepartmentEditText.getText().toString();

        if(!subjectName.matches(namePattern))
        {
            insertSubjectNameEditText.setError("Enter a valid name");
            insertSubjectNameEditText.requestFocus();
        }
        else if(!semester.matches(semesterPattern))
        {
            insertSemesterEditText.setError("Enter a valid semester");
            insertSemesterEditText.requestFocus();
        }
        else if(!department.matches(namePattern))
        {
            insertDepartmentEditText.setError("Enter a valid department");
            insertDepartmentEditText.requestFocus();
        }
        else
        {
            progressDialog.setMessage("Please wait while creating class");
            progressDialog.setTitle("Creating Class");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mUser=mAuth.getCurrentUser();
            WriteInsertClassData insertClassData=new WriteInsertClassData(subjectName,semester,department);



            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

            databaseReference.child("All Classes").child(mAuth.getCurrentUser().getUid()).child(subjectCode).setValue(insertClassData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendToNextActivity();
                        Toast.makeText(InsertClassActivity.this, "Class created successfully", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(InsertClassActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        }

    private void sendToNextActivity() {
        Intent intent=new Intent(InsertClassActivity.this, ClassActivity.class);
        startActivity(intent);
    }
}
