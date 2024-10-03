package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateAdminActivity extends AppCompatActivity {
    EditText adminName, adminUsername, adminPassword;
    Button btnCreateAdmin;
    DataBaseHapler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DataBaseHapler(this);

        // Get references to input fields and button
        adminName = findViewById(R.id.adminName);
        adminUsername = findViewById(R.id.adminUsername);
        adminPassword = findViewById(R.id.adminPassword);
        btnCreateAdmin = findViewById(R.id.btnCreateAdmin);

        // Set button click listener
        btnCreateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DB_DEBUG", "Create Admin button clicked");

                String name = adminName.getText().toString();
                String username = adminUsername.getText().toString();
                String password = adminPassword.getText().toString();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CreateAdminActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new AdminModel object
                    AdminModel newAdmin = new AdminModel(name, username, password);
                    Log.d("DB_DEBUG", "Admin Model object created");


                    // Insert the new admin into the database
                    boolean success = db.addOneAdmin(newAdmin);

                    if (success) {
                        Log.d("DB_DEBUG", "Admin added to database successfully");
                        Toast.makeText(CreateAdminActivity.this, "Admin created successfully", Toast.LENGTH_SHORT).show();
                        // Optionally, redirect back to the admin dashboard
                        Intent intent = new Intent(CreateAdminActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("DB_DEBUG", "Error while adding admin to database");
                        Toast.makeText(CreateAdminActivity.this, "Error creating admin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}