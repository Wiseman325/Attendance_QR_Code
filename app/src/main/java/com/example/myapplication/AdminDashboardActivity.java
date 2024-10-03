package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminDashboardActivity extends AppCompatActivity {
    TextView welcomeText;
    Button manageUsersBtn, viewReportsBtn, logoutBtn;
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

        // Initialize UI components
        welcomeText = findViewById(R.id.welcomeText);
        manageUsersBtn = findViewById(R.id.manageUsersBtn);
        viewReportsBtn = findViewById(R.id.viewReportsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        // Set welcome message
        welcomeText.setText("Welcome, " + adminName);

        manageUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, ManageUsersActivity.class);
                startActivity(intent);
            }
        });

        // Button to view system reports (e.g., attendance, performance)
        viewReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, ViewReportsActivity.class);
                startActivity(intent);
            }
        });

        // Logout button to clear SharedPreferences and go back to login
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(AdminDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}