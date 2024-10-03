package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTeacherAccountActivity extends AppCompatActivity {
    EditText teacherName, teacherEmail, teacherUsername, teacherPassword;
    Button createTeacherBtn;
    DataBaseHapler dbHelper;
    private static final String TAG = "CreateTeacherAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_teacher_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DataBaseHapler(this);
        Log.d(TAG, "Database helper initialized");

        teacherName = findViewById(R.id.teacherName);
        teacherEmail = findViewById(R.id.teacherEmail);
        teacherUsername = findViewById(R.id.teacherUsername);
        teacherPassword = findViewById(R.id.teacherPassword);
        createTeacherBtn = findViewById(R.id.createTeacherBtn);

        createTeacherBtn.setOnClickListener(v -> {
            String name = teacherName.getText().toString();
            String email = teacherEmail.getText().toString();
            String username = teacherUsername.getText().toString();
            String password = teacherPassword.getText().toString();

            Log.d(TAG, "Create Teacher Button Clicked");

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Log.w(TAG, "One or more fields are empty");
                Toast.makeText(CreateTeacherAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save teacher details in the database
                TeacherModel teacherModel = new TeacherModel(name, username, password, email);
                Log.d(TAG, "TeacherModel created: " + teacherModel.toString());

                boolean success = dbHelper.AddOne_Teacher(teacherModel);

                if (success) {
                    Log.d(TAG, "Teacher added successfully to the database");
                    // Send email with login credentials
                    sendLoginDetailsEmail(email, username, password);
                    Intent intent = new Intent(CreateTeacherAccountActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(CreateTeacherAccountActivity.this, "Teacher created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error adding teacher to the database");
                    Toast.makeText(CreateTeacherAccountActivity.this, "Error creating teacher", Toast.LENGTH_SHORT).show();
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
