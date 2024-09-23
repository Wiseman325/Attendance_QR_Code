package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String createStudentsTable = "CREATE TABLE STUDENTS_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, PASS TEXT)";
        String createTeacherTable = "CREATE TABLE TEACHER_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, PASS TEXT)";
        String createAttendanceTable = "CREATE TABLE ATTENDANCE_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, STUDENTNAME TEXT, SUBJECT TEXT, DATE TEXT)";

        sqLiteDatabase.execSQL(createStudentsTable);
        sqLiteDatabase.execSQL(createTeacherTable);
        sqLiteDatabase.execSQL(createAttendanceTable);
    }


    // is call when DB version is changed
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public  boolean AddOne_Student(StudentModel model)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("NAME",model.getName());
        value.put("USER",model.getUser());
        value.put("PASS",model.getPass());

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
        return getStudents(); // Call to your method to retrieve hard-coded students
    }

    public List<StudentModel> getStudents() {
        List<StudentModel> students = new ArrayList<>();
        students.add(new StudentModel(1, "John", "john123", "123"));
        students.add(new StudentModel(2, "Alice", "alice456", "123"));
        students.add(new StudentModel(3, "Bob", "bob789", "123"));
        students.add(new StudentModel(4, "Emily", "emily321", "123"));

        return students; // Correct the return statement to return students
    }




    public List<TeacherModel> getAllTeachers()
    {
        return getTeachers();
    }

    public List<TeacherModel> getTeachers() {
        List<TeacherModel> teachers = new ArrayList<>();
        teachers.add(new TeacherModel(1, "Kamil", "kamil123", "123"));
        teachers.add(new TeacherModel(2, "Riad", "riad456", "123"));
        teachers.add(new TeacherModel(3, "Ahmed", "ahmed24", "123"));
        teachers.add(new TeacherModel(4, "Ali", "ali322", "123"));

        return teachers;
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
