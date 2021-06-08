package com.example.sqlitequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {

    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        tvResult = findViewById(R.id.tvResult);
        StudentsRecordDB studentsRecordDB = new StudentsRecordDB(this);
        studentsRecordDB.open();
        String  data = studentsRecordDB.getData();
        tvResult.setText(data);
        studentsRecordDB.close();
    }
}