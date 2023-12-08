package com.sem3.farmfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;

public class RegistrationPage extends AppCompatActivity {
TextInputEditText ET_name, ET_email, ET_mobile_no, ET_address, ET_password;

TextInputLayout IL_email, IL_mobile_no, IL_password, IL_name;
Button BTN_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        ET_name = findViewById(R.id.ET_name);
        ET_address = findViewById(R.id.ET_address);
        ET_mobile_no = findViewById(R.id.ET_mobile_no);
        ET_email = findViewById(R.id.ET_email);
        ET_password = findViewById(R.id.ET_password);
        BTN_register = findViewById(R.id.BTN_register);
        IL_email = findViewById(R.id.IL_email);
        IL_mobile_no = findViewById(R.id.IL_mobile_no);
        IL_password = findViewById(R.id.IL_password);
        IL_name = findViewById(R.id.IL_name);

        ET_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString();
                if (Validations.isEmailValid(email)) {
                    IL_email.setError(null); // Clear error message
                    IL_email.setErrorEnabled(false);
                } else {
                    IL_email.setError("Invalid email"); // Set error message
                    IL_email.setErrorEnabled(true);

                }
            }
        });

        ET_mobile_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(Validations.isPhoneValid(editable.toString()))
                {
                    IL_mobile_no.setError(null);
                    IL_mobile_no.setErrorEnabled(false);
                }
                else
                {
                    IL_mobile_no.setError("Enter Valid Phone Number");
                    IL_mobile_no.setErrorEnabled(true);
                }
            }
        });

        BTN_register.setOnClickListener(view -> {

            if(ET_name.getText().toString().isEmpty())
                IL_name.setError("Name is Required ");
            else if (ET_address.getText().toString().isEmpty()) {
                ET_address.setError("Name is Required ");
            } else if(!Validations.isPasswordValid(ET_password.getText().toString()))
                IL_password.setError("Invalid Password");
             else
                registerUser();
        });



    }

    private void registerUser()
    {
        String str_name, str_email, str_mobile_no, str_address, str_password ;
        str_name = ET_name.getText().toString();
        str_address = ET_address.getText().toString();
        str_mobile_no = ET_mobile_no.getText().toString();
        str_email = ET_email.getText().toString();
        str_password = ET_password.getText().toString();

        String timestamp = String.valueOf(System.currentTimeMillis());
        if(!str_name.isEmpty() && !str_address.isEmpty() && !str_mobile_no.isEmpty() && !str_email.isEmpty() && !str_password.isEmpty())
        {
            User NewUser = new User(str_name,str_address,str_mobile_no,str_email,str_password);

           DatabaseReference user =  DAO.getUserDatabaseReference();
            user.orderByChild("Email").equalTo(str_email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists())
                    {
                        clearForm();
                        Toast.makeText(RegistrationPage.this, "User Already Exists..Try Login", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        user.child(timestamp).setValue(NewUser).addOnSuccessListener(unused -> {
                            Toast.makeText(RegistrationPage.this, "New User Created..", Toast.LENGTH_SHORT).show();
                            clearForm();
                        }).addOnFailureListener(e -> Toast.makeText(RegistrationPage.this, e.toString(), Toast.LENGTH_SHORT).show());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RegistrationPage.this, "Something went wrong with Database ....Try Later", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {

            Toast.makeText(RegistrationPage.this, "Every Field is required !!...Try Again...", Toast.LENGTH_SHORT).show();
        }


    }
    private void clearForm()
    {
        ET_name.setText("");
        ET_address.setText("");
        ET_mobile_no.setText("");
        IL_mobile_no.setErrorEnabled(false);
        ET_email.setText("");
        IL_email.setErrorEnabled(false);
        ET_password.setText("");

    }

}