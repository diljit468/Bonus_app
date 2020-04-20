package com.example.collegedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username,pass;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username);
        pass=findViewById(R.id.pass);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals(common.username)){
                    if(pass.getText().toString().equals(common.pass)) {
                        Toast.makeText(MainActivity.this, "Welcome Student1!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Password is not correct", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Username is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
