package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminDashboardActivity extends AppCompatActivity {
    TextView welcomeText;
    Button manageUsersBtn, viewReportsBtn, logoutBtn, createTeacherAccountBtn, createStudentAccountBtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("com.example.myapplication.myrefrences", 0);
        String adminName = sharedPreferences.getString("name", "Admin");

        welcomeText = findViewById(R.id.welcomeText);
        logoutBtn = findViewById(R.id.logoutBtn);
        createTeacherAccountBtn = findViewById(R.id.createTeacherAccountBtn);
        createStudentAccountBtn = findViewById(R.id.createStudentAccountBtn);

        welcomeText.setText("Welcome, " + adminName);


        logoutBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(AdminDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Create teacher account
        createTeacherAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, CreateTeacherAccountActivity.class);
            startActivity(intent);
        });

        // Create student account
        createStudentAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, CreateStudentAccountActivity.class);
            startActivity(intent);
        });
    }
}
