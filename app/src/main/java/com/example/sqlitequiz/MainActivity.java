package com.example.sqlitequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etSemester, etDegree, etPhone, etEmail;
    StudentsRecordDB studentsRecordDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        etName = findViewById(R.id.etName);
        etSemester = findViewById(R.id.etSemester);
        etDegree = findViewById(R.id.etDegree);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        studentsRecordDB = new StudentsRecordDB(this);
    }

    public void clear()
    {
        etName.setText("");
        etSemester.setText("");
        etDegree.setText("");
        etPhone.setText("");
        etEmail.setText("");
    }

    public void btnInsertData(View v)
    {
        String sn = etName.getText().toString().trim();
        String s = etSemester.getText().toString().trim();
        String d = etDegree.getText().toString().trim();
        String c = etPhone.getText().toString().trim();
        String e = etEmail.getText().toString().toLowerCase();
        studentsRecordDB.open();
        studentsRecordDB.createEntry(sn, s, d, c, e);
        clear();
        studentsRecordDB.close();
    }

    public void btnViewData(View v)
    {
        startActivity(new Intent(MainActivity.this, ViewData.class));
    }

    public void btnUpdateData(View v)
    {
        studentsRecordDB.open();
        long num = studentsRecordDB.updateEntry("Khaqan", "3", "2", "MBA", "03212346225", "khaqanrasheed66@gmail.com");
        if(num!=0)
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        studentsRecordDB.close();
    }

    public void btnDeleteData(View v)
    {
        studentsRecordDB.open();
        long num = studentsRecordDB.deleteEntry("2");
        if(num!=0)
            Toast.makeText(this, "Data has been deleted"+num, Toast.LENGTH_SHORT).show();
        studentsRecordDB.close();
    }
}