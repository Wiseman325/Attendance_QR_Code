package com.example.myapplication;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.AttendanceModel;
import com.example.myapplication.CapturAct;
import com.example.myapplication.DataBaseHapler;
import com.example.myapplication.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScannerQR extends AppCompatActivity {

    public Button btn_scanner;
    SharedPreferences sharedPreferences;
    private final int SMS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scanner_qr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("com.example.myapplication.myrefrences", 0);
        btn_scanner = findViewById(R.id.scanner);

        btn_scanner.setOnClickListener(v -> {
            if (checkSmsPermission()) {
                ScanQR();
            } else {
                requestSmsPermission();
            }
        });
    }

    private void ScanQR() {
        ScanOptions option = new ScanOptions();
        option.setPrompt("Volume up to flash on");
        option.setBeepEnabled(true);
        option.setOrientationLocked(true);
        option.setCaptureActivity(CapturAct.class);
        Laucher.launch(option);
    }

    ActivityResultLauncher<ScanOptions> Laucher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerQR.this);
            builder.setTitle("You are Register in:");
            builder.setMessage(result.getContents());

            // Save register
            String name = sharedPreferences.getString("name", "not have name");
            String subject = result.getContents().toString();

            // Get current date and time
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault());
            String date = dateFormat.format(calendar.getTime());

            DataBaseHapler db = new DataBaseHapler(ScannerQR.this);
            AttendanceModel s = new AttendanceModel(0, name, subject, date);

            boolean x = db.AddOne_Attendance(s);

            if (x) {
                sendSms("0660002093", "QR code scanned for subject: " + subject + " by " + name);
                builder.setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss()).show();
            } else {
                Toast.makeText(ScannerQR.this, "Please try again Scanner", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ScannerQR.this, "Please try again Scanner", Toast.LENGTH_SHORT).show();
        }
    });

    // Method to send SMS
    private void sendSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed to send.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Check for SMS permission
    private boolean checkSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    // Request SMS permission
    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ScanQR();
            } else {
                Toast.makeText(this, "SMS permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
