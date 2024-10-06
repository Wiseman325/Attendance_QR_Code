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

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
                    sendLoginDetailsEmail(email, username, password, name);
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

    private void sendLoginDetailsEmail(String email, String username, String password, String teacherName) {
        // Using the email logic from the ScannerQR activity
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                String userEmail = "sibekonkululeko706@gmail.com";  // Use your email
                String userPassword = "lbab dxdf ycrn zihb";  // Use your app password

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userEmail, userPassword);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));  // Send email to teacher
                message.setSubject("Teacher Account Created");
                message.setText("Dear " + teacherName + ",\n\n" +
                        "Your account has been successfully created with the following details:\n" +
                        "Username: " + username + "\n" +
                        "Password: " + password + "\n\n" +
                        "Please use these credentials to log in.\n\n" +
                        "Best regards,\nAdmin Team");

                Transport.send(message);

                runOnUiThread(() -> Toast.makeText(CreateTeacherAccountActivity.this, "Login details sent to " + email, Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(CreateTeacherAccountActivity.this, "Failed to send email.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
