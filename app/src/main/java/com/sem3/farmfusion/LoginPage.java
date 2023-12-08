package com.sem3.farmfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginPage extends AppCompatActivity {
 TextInputEditText ET_emailID, ET_pass;
 TextInputLayout IL_email;
 TextView TV_register;
 Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        TV_register =findViewById(R.id.TV_register);
        ET_emailID = findViewById(R.id.ET_emailID);
        ET_pass = findViewById(R.id.ET_pass);
        IL_email= findViewById(R.id.IL_email_login);
        btn_login = findViewById(R.id.btn_login);

        TV_register.setOnClickListener(view -> {

            Intent intent = new Intent(LoginPage.this,RegistrationPage.class);
            startActivity(intent);
        });

        ET_emailID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Validations.isEmailValid(editable.toString()))
                {
                    IL_email.setError("Enter valid Email");
                    IL_email.setErrorEnabled(true);
                }
                else
                {
                    IL_email.setError(null);
                    IL_email.setErrorEnabled(false);
                }
            }
        });

        btn_login.setOnClickListener(view -> {
            if(!ET_emailID.getText().toString().isEmpty()  &&  !ET_pass.getText().toString().isEmpty())
            userLogin();
            else {
                Toast.makeText(this, "Every field is Required", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void userLogin()
    {
        String str_email, str_pass;
        str_email = ET_emailID.getText().toString();
        str_pass = ET_pass.getText().toString();

        DatabaseReference user = DAO.getUserDatabaseReference();



        user.orderByChild("email").equalTo(str_email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Map<String,String> timestampData = null;
                    for(DataSnapshot timestamp : snapshot.getChildren()) {
                        timestampData = (Map<String, String>) timestamp.getValue();
                    }
                    String password = timestampData.get("password");
                    if(password.equals(str_pass)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userEmail", str_email);
                        editor.putString("userName",timestampData.get("name") );
                        editor.apply();
                        Intent intent = new Intent(LoginPage.this, Dashboard.class);
                        startActivity(intent);
                    }

                    else
                        Toast.makeText(LoginPage.this, "Invalid Email OR Password...!!Try Again", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginPage.this, "Not Registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}