package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qr);

        btn_scanner = findViewById(R.id.scanner);

        btn_scanner.setOnClickListener(v -> {
            ScanQR();
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
            String name = sharedPreferences.getString("name", "not have name");
            String subject = result.getContents();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault());
            String date = dateFormat.format(calendar.getTime());

            DataBaseHapler db = new DataBaseHapler(ScannerQR.this);
            AttendanceModel attendance = new AttendanceModel(0, name, subject, date);
            boolean isSaved = db.AddOne_Attendance(attendance);

            // Get the StudentModel from the database and get parent's email
                StudentModel student = db.getStudentByName(name);
                if (isSaved && student != null) {
                    Log.d("DB_DEBUG", "Sending email to: " + student.getParentEmail());
                    sendEmail(subject, date, name, student.getParentEmail());
                } else {
                    Log.d("DB_DEBUG", "Student retrieval or save failed, please try scanning again.");
                    Toast.makeText(ScannerQR.this, "Please try scanning again", Toast.LENGTH_SHORT).show();
                }


        } else {
            Toast.makeText(ScannerQR.this, "Please try scanning again", Toast.LENGTH_SHORT).show();
        }
    });

    // Method to send an email using JavaMail API
    private void sendEmail(final String subject, final String date, final String name, final String parentEmail) {
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                String userEmail = "sibekonkululeko706@gmail.com";
                String userPassword = "lbab dxdf ycrn zihb";

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userEmail, userPassword);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(parentEmail)); // Parent's email
                message.setSubject("New QR Code Registration");
                message.setText("Dear Parent \n I hope this email finds you well. \n This is to inform you that your child," + name + "attended school today. Below are the details of the attendance: " + "\nDate with time in: " + date + "\n\nWe kindly request that you keep this record for your reference. If you have any questions or concerns, please do not hesitate to reach out to the school administration.");

                Transport.send(message);

                runOnUiThread(() -> Toast.makeText(ScannerQR.this, "Email sent successfully!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ScannerQR.this, "Failed to send email.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
