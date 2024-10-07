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
        super(context, "SystemApp.db", null, 3);
    }

    // is was call when first create DB
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStudentsTable = "CREATE TABLE STUDENTS_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, PASS TEXT, STUDENT_EMAIL TEXT, PARENT_EMAIL TEXT)";
        String createTeacherTable = "CREATE TABLE TEACHER_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, EMAIL TEXT, PASS TEXT)";
        String createAttendanceTable = "CREATE TABLE ATTENDANCE_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, STUDENTNAME TEXT, SUBJECT TEXT, DATE TEXT)";
        String createAdminTable = "CREATE TABLE ADMIN_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, PASS TEXT)";

        sqLiteDatabase.execSQL(createStudentsTable);
        sqLiteDatabase.execSQL(createTeacherTable);
        sqLiteDatabase.execSQL(createAttendanceTable);
        sqLiteDatabase.execSQL(createAdminTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Check if the column already exists before adding it
            Cursor cursor = db.rawQuery("PRAGMA table_info(STUDENTS_TABLE)", null);
            boolean columnExists = false;
            while (cursor.moveToNext()) {
                String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                if (columnName.equals("PARENT_EMAIL")) {
                    columnExists = true;
                    break;
                }
            }
            cursor.close();


            if (!columnExists) {
                db.execSQL("ALTER TABLE STUDENTS_TABLE ADD COLUMN PARENT_EMAIL TEXT");
            }


            Cursor cursor1 = db.rawQuery("PRAGMA table_info(STUDENTS_TABLE)", null);
            boolean studentEmailExists = false;
            while (cursor1.moveToNext()) {
                String columnName = cursor1.getString(cursor1.getColumnIndexOrThrow("name"));
                if (columnName.equals("STUDENT_EMAIL")) {
                    studentEmailExists = true;
                    break;
                }
            }
            cursor1.close();

            if (!studentEmailExists) {
                db.execSQL("ALTER TABLE STUDENTS_TABLE ADD COLUMN STUDENT_EMAIL TEXT");
            }


            // Add EMAIL to TEACHER_TABLE if needed
            cursor = db.rawQuery("PRAGMA table_info(TEACHER_TABLE)", null);
            columnExists = false;
            while (cursor.moveToNext()) {
                String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                if (columnName.equals("EMAIL")) {
                    columnExists = true;
                    break;
                }
            }
            cursor.close();

            if (!columnExists) {
                db.execSQL("ALTER TABLE TEACHER_TABLE ADD COLUMN EMAIL TEXT");
            }
        }
    }

    public boolean addOneAdmin(AdminModel adminModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", adminModel.getName());
        String lowercaseUsername = adminModel.getUser().toLowerCase();
        values.put("USER", lowercaseUsername);
        values.put("PASS", adminModel.getPass());

        Log.d("DB_INSERT", "Inserting admin: " + adminModel.getName() + ", User: " + adminModel.getUser());

        long result = db.insert("ADMIN_TABLE", null, values);

        if (result == -1) {
            Log.d("DB_INSERT", "Admin insertion failed"); // Log if insertion failed
        } else {
            Log.d("DB_INSERT", "Admin inserted successfully with ID: " + result); // Log success
        }

        db.close();

        return result != -1; // Return true if insert was successful
    }

    public List<AdminModel> getAllAdmins() {
        List<AdminModel> adminsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM ADMIN_TABLE";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                    String user = cursor.getString(cursor.getColumnIndexOrThrow("USER"));
                    String pass = cursor.getString(cursor.getColumnIndexOrThrow("PASS"));

                    Log.d("DB_DEBUG", "Retrieved admin: " + name);

                    AdminModel admin = new AdminModel(id, name, user, pass);
                    adminsList.add(admin);
                } catch (Exception e) {
                    Log.e("DB_ERROR", "Error while fetching admin data", e);
                }
            } while (cursor.moveToNext());
        } else {
            Log.d("DB_DEBUG", "No admins found in the database");
        }

        cursor.close();
        db.close();

        return adminsList;
    }


    public AdminModel getAdminByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        AdminModel admin = null;

        Cursor cursor = db.rawQuery("SELECT * FROM ADMIN_TABLE WHERE USER = ?", new String[]{username});

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
            String user = cursor.getString(cursor.getColumnIndexOrThrow("USER"));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow("PASS"));

            admin = new AdminModel(id, name, user, pass);
        }

        cursor.close();
        db.close();

        return admin;
    }

    public List<TeacherModel> getAllTeachers() {
        List<TeacherModel> teachersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM TEACHER_TABLE";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                    String user = cursor.getString(cursor.getColumnIndexOrThrow("USER"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")); // Added email
                    String pass = cursor.getString(cursor.getColumnIndexOrThrow("PASS"));

                    Log.d("DB_DEBUG", "Retrieved teacher: " + name);

                    TeacherModel teacher = new TeacherModel(id, name, user, email, pass);
                    teachersList.add(teacher);
                } catch (Exception e) {
                    Log.e("DB_ERROR", "Error while fetching teacher data", e);
                }

            } while (cursor.moveToNext());
        } else {
            Log.d("DB_DEBUG", "No teachers found in the database");
        }

        cursor.close();
        db.close();

        return teachersList;
    }

    public  boolean AddOne_Student(StudentModel model)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("NAME",model.getName());
        value.put("USER",model.getUser());
        value.put("PASS",model.getPass());
        value.put("STUDENT_EMAIL", model.getEmail());  // Add this line
        value.put("PARENT_EMAIL", model.getParentEmail());
        Log.d("DB_DEBUG", "Inserting student: " + model.getName());

        long insert = db.insert("STUDENTS_TABLE",null,value);
        if (insert == -1)
        {
            Log.e("DB_DEBUG", "Error inserting student");
            return false;
        }
        else
        {
            Log.d("DB_DEBUG", "Student inserted successfully with ID: " + insert);
            return true;
        }

    }

    public boolean AddOne_Teacher(TeacherModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("NAME", model.getName());
        value.put("USER", model.getUser());
        value.put("EMAIL", model.getEmail()); // Ensure to add this line
        value.put("PASS", model.getPass());


        String createTeacherTable = "CREATE TABLE TEACHER_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, USER TEXT, EMAIL TEXT, PASS TEXT)";


        Log.d("DB_DEBUG", "Inserting teacher: " + model.getName());

        long insert = db.insert("TEACHER_TABLE", null, value);
        if (insert == -1) {
            Log.e("DB_DEBUG", "Error inserting teacher");
            return false;
        } else {
            Log.d("DB_DEBUG", "Teacher inserted successfully with ID: " + insert);
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
        List<StudentModel> studentsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM STUDENTS_TABLE";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                    String user = cursor.getString(cursor.getColumnIndexOrThrow("USER"));
                    String pass = cursor.getString(cursor.getColumnIndexOrThrow("PASS"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("STUDENT_EMAIL"));
                    String parentEmail = cursor.getString(cursor.getColumnIndexOrThrow("PARENT_EMAIL"));

                    Log.d("DB_DEBUG", "Retrieved student: " + name);

                    StudentModel student = new StudentModel(id, name, user, pass, email, parentEmail);
                    studentsList.add(student);
                } catch (Exception e) {
                    Log.e("DB_ERROR", "Error while fetching student data", e);
                }

            } while (cursor.moveToNext());
        } else {
            Log.d("DB_DEBUG", "No students found in the database");
        }

        cursor.close();
        db.close();

        return studentsList;
    }





    public StudentModel getStudentByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        StudentModel student = null;

        // Query to get student by name
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM STUDENTS_TABLE WHERE NAME = ?", new String[]{name});

            // If we find a student, create a StudentModel object
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String user = cursor.getString(cursor.getColumnIndexOrThrow("USER"));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow("PASS"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("STUDENT_EMAIL")); // Update column name here
                String parentEmail = cursor.getString(cursor.getColumnIndexOrThrow("PARENT_EMAIL"));

                student = new StudentModel(id, name, user, pass, email, parentEmail);
            } else {
                Log.d("DB_DEBUG", "No student found with name: " + name);
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
