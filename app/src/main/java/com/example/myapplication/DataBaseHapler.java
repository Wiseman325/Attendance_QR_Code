package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHapler extends SQLiteOpenHelper {


    public DataBaseHapler(@Nullable Context context  ) {
        super(context, "SystemApp.db", null, 1);
    }

    // is was call when first create DB
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStudentsTable = "CREATE TABLE STUDENTS_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, PASS TEXT, EMAIL TEXT, PARENT_EMAIL TEXT)";
        String createTeacherTable = "CREATE TABLE TEACHER_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, PASS TEXT)";
        String createAttendanceTable = "CREATE TABLE ATTENDANCE_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, STUDENTNAME TEXT, SUBJECT TEXT, DATE TEXT)";

        sqLiteDatabase.execSQL(createStudentsTable);
        sqLiteDatabase.execSQL(createTeacherTable);
        sqLiteDatabase.execSQL(createAttendanceTable);
    }



    public  boolean AddOne_Student(StudentModel model)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("NAME",model.getName());
        value.put("USER",model.getUser());
        value.put("PASS",model.getPass());
        value.put("EMAIL", model.getEmail());  // Add this line
        value.put("PARENT_EMAIL", model.getParentEmail());

        long insert = db.insert("STUDENTS_TABLE",null,value);
        if (insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public  boolean AddOne_Teacher(TeacherModel model)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("NAME",model.getName());
        value.put("USER",model.getUser());
        value.put("PASS",model.getPass());

        long insert = db.insert("TEACHER_TABLE",null,value);
        if (insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public  boolean AddOne_Attendance(AttendanceModel model)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
       // System.out.println(model.getStudentName()+"\n"+model.getSubject()+"\n"+model.getDate());
        value.put("STUDENTNAME",model.getStudentName());
        value.put("SUBJECT",model.getSubject());
        value.put("DATE",model.getDate());


        long insert = db.insert("ATTENDANCE_TABLE",null,value);
        if (insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public List<StudentModel> getAllStudents() {
        StudentModel studentModel = new StudentModel();
        return studentModel.getStudents(); // Call to your method to retrieve hard-coded students
    }



    public StudentModel getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        StudentModel student = null;

        // Query to get student by ID
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM STUDENTS_TABLE WHERE ID = ?", new String[]{String.valueOf(id)});

            // If we find a student, create a StudentModel object
            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                String user = cursor.getString(cursor.getColumnIndexOrThrow("USER"));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow("PASS"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
                String parentEmail = cursor.getString(cursor.getColumnIndexOrThrow("PARENT_EMAIL"));

                student = new StudentModel(id, name, user, pass, email, parentEmail);
            } else {
                Log.d("DB_DEBUG", "No student found with ID: " + id);
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error retrieving student", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return student;
    }




    // is call when DB version is changed
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<TeacherModel> getAllTeachers()
    {
        TeacherModel teacherModel = new TeacherModel();
        return teacherModel.getTeachers();
    }


    public List<AttendanceModel> getAllAttendance()
    {
        List<AttendanceModel> attendanceModels = new ArrayList<>();
        String query = "SELECT * FROM ATTENDANCE_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String student= cursor.getString(1);
                String subject= cursor.getString(2);
                String date= cursor.getString(3);

                AttendanceModel  attendanceModel = new AttendanceModel(id,student,subject,date);
                attendanceModels.add(attendanceModel);
            }while (cursor.moveToNext());
        }
        else
        {

        }
        cursor.close();
        db.close();
        return attendanceModels;

    }

}
