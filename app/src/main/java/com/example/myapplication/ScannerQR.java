package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ScannerQR extends AppCompatActivity {
    public Button btn_scanner;
    SharedPreferences sharedPreferences;
    private final int SMS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qr);

        // Initialize the scanner button
        btn_scanner = findViewById(R.id.scanner);

        // Set up onClickListener for the button
        btn_scanner.setOnClickListener(v -> {
            ScanQR();  // Call the method to initiate QR scanning
        });

        sharedPreferences = getSharedPreferences("com.example.myapplication.myrefrences", 0);
    }

    private void ScanQR() {
        ScanOptions option = new ScanOptions();
        option.setPrompt("Volume up to flash on");
        option.setBeepEnabled(true);
        option.setOrientationLocked(true);
        option.setCaptureActivity(CapturAct.class);
        launcher.launch(option);
    }

    ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            // Get name and subject from the QR code
            String name = sharedPreferences.getString("name", "not have name");
            String subject = result.getContents();

            // Get current date and time
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault());
            String date = dateFormat.format(calendar.getTime());

            // Save to the database (assuming you have this logic already implemented)
            DataBaseHapler db = new DataBaseHapler(ScannerQR.this);
            AttendanceModel attendance = new AttendanceModel(0, name, subject, date);
            boolean isSaved = db.AddOne_Attendance(attendance);

            if (isSaved) {
                sendEmail(subject, date, name);  // Send the email in the background
            } else {
                Toast.makeText(ScannerQR.this, "Please try scanning again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ScannerQR.this, "Please try scanning again", Toast.LENGTH_SHORT).show();
        }
    });

    // Method to send an email using JavaMail API
    private void sendEmail(final String subject, final String date, final String name) {
        new Thread(() -> {
            try {
                // Set up properties for the SMTP server (Gmail in this case)
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                // Email credentials (replace with your own credentials)
                String userEmail = "sibekonkululeko706@gmail.com"; // Your email address
                String userPassword = "lbab dxdf ycrn zihb";     // Your email password

                // Create a session with the email credentials
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userEmail, userPassword);
                    }
                });

                // Create the message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("wisemanmlora@gmail.com")); // Parent's email
                message.setSubject("New QR Code Registration");
                message.setText("Student Name: " + name + "\nSubject: " + subject + "\nDate: " + date);

                // Send the message
                Transport.send(message);

                // Notify the user that the email was sent successfully
                runOnUiThread(() -> Toast.makeText(ScannerQR.this, "Email sent successfully!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ScannerQR.this, "Failed to send email.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
