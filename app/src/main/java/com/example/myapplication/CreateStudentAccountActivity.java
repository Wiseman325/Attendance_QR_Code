package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateStudentAccountActivity extends AppCompatActivity {

    EditText studentName, studentEmail, parentEmail, studentUsername, studentPassword;
    Button createStudentBtn;
    DataBaseHapler dbHelper;

    private static final String TAG = "CreateStudentAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity started");
        setContentView(R.layout.activity_create_student_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DataBaseHapler(this);

        // Initialize views
        studentName = findViewById(R.id.studentName);
        studentEmail = findViewById(R.id.studentEmail);
        parentEmail = findViewById(R.id.parentEmail);
        studentUsername = findViewById(R.id.studentUsername);
        studentPassword = findViewById(R.id.studentPassword);
        createStudentBtn = findViewById(R.id.createStudentBtn);

        Log.d(TAG, "onCreate: Views initialized");

        // Set the create student button click listener
        createStudentBtn.setOnClickListener(v -> {
            Log.d(TAG, "CreateStudentBtn clicked");
            String name = studentName.getText().toString();
            String email = studentEmail.getText().toString();
            String parentEmailValue = parentEmail.getText().toString();
            String username = studentUsername.getText().toString();
            String password = studentPassword.getText().toString();

            Log.d(TAG, "onClick: Student Name: " + name);
            Log.d(TAG, "onClick: Student Email: " + email);
            Log.d(TAG, "onClick: Parent Email: " + parentEmailValue);
            Log.d(TAG, "onClick: Student Username: " + username);

            // Check for empty fields
            if (name.isEmpty() || email.isEmpty() || parentEmailValue.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Log.w(TAG, "onClick: One or more fields are empty");
                Toast.makeText(CreateStudentAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save student details in the database
                StudentModel studentModel = new StudentModel(name, username, password, email, parentEmailValue);
                boolean success = dbHelper.AddOne_Student(studentModel);

                if (success) {
                    Log.d(TAG, "onClick: Student created successfully in the database");
                    // Notify the user that the student account was created
                    sendLoginDetailsEmail(email, username, password);
                    Intent intent = new Intent(CreateStudentAccountActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(CreateStudentAccountActivity.this, "Student created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "onClick: Error creating student");
                    Toast.makeText(CreateStudentAccountActivity.this, "Error creating student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendLoginDetailsEmail(String email, String username, String password) {
        // Logic to send an email to the teacher
        Log.d(TAG, "Sending email to " + email);
        // You can use an email service like SMTP or any email API
        // e.g., Gmail, SendGrid, or any backend email service
    }
}
