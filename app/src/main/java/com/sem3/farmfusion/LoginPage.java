package com.sem3.farmfusion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginPage extends AppCompatActivity {
 TextInputEditText ET_emailID, ET_pass;
 TextView TV_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        TV_register =findViewById(R.id.TV_register);
        ET_emailID = findViewById(R.id.ET_emailID);
        ET_pass = findViewById(R.id.ET_pass);


        TV_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginPage.this,RegistrationPage.class);
                startActivity(intent);
            }
        });

    }
}