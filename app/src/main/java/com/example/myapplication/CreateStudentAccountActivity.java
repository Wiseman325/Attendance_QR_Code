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

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CreateStudentAccountActivity extends AppCompatActivity {

    EditText studentName, studentEmail, parentEmail, studentUsername, studentPassword;
    Button createStudentBtn;
    DataBaseHapler dbHelper;

    private static final String TAG = "CreateStudentAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DB_DEBUG", "onCreate: Activity started");
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

        Log.d("DB_DEBUG", "onCreate: Views initialized");

        // Set the create student button click listener
        createStudentBtn.setOnClickListener(v -> {
            Log.d("DB_DEBUG", "CreateStudentBtn clicked");
            String name = studentName.getText().toString();
            String email = studentEmail.getText().toString();
            String parentEmailValue = parentEmail.getText().toString();
            String username = studentUsername.getText().toString();
            String password = studentPassword.getText().toString();

            // Check for empty fields
            if (name.isEmpty() || email.isEmpty() || parentEmailValue.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Log.w("DB_DEBUG", "onClick: One or more fields are empty");
                Toast.makeText(CreateStudentAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save student details in the database
                StudentModel studentModel = new StudentModel(name, username, password, email, parentEmailValue);
                boolean success = dbHelper.AddOne_Student(studentModel);

                if (success) {
                    Log.d("DB_DEBUG", "onClick: Student created successfully in the database");
                    // Notify the user that the student account was created
                    sendLoginDetailsEmail(email, username, password, parentEmailValue);
                    Intent intent = new Intent(CreateStudentAccountActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(CreateStudentAccountActivity.this, "Student created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("DB_DEBUG", "onClick: Error creating student");
                    Toast.makeText(CreateStudentAccountActivity.this, "Error creating student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to send an email using JavaMail API
    private void sendLoginDetailsEmail(final String studentEmail, final String username, final String password, final String parentEmail) {
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                String userEmail = "sibekonkululeko706@gmail.com"; // Update with your email
                String userPassword = "lbab dxdf ycrn zihb"; // Update with your app-specific password

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userEmail, userPassword);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentEmail)); // Student's email
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(parentEmail)); // Parent's email
                message.setSubject("Student Account Created");
                message.setText("Dear Student,\n\nYour student account has been created successfully. Below are your login details:\n\nUsername: " + username + "\nPassword: " + password + "\n\nPlease keep these credentials safe and confidential. If you have any questions or need further assistance, feel free to reach out to us.\n\nBest regards,\nSchool Administration");

                Transport.send(message);

                runOnUiThread(() -> Toast.makeText(CreateStudentAccountActivity.this, "Email sent successfully!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(CreateStudentAccountActivity.this, "Failed to send email.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
