package com.example.ilovetruyen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {
    private TextInputLayout mEmail;
    private MaterialButton btnSendMail;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        mEmail = findViewById(R.id.input_layout_edit_email);
        TextInputEditText emailValidate = findViewById(R.id.et_input_edit_email);
        btnSendMail = findViewById(R.id.buttonSendMail);
        btnSendMail.setOnClickListener(v -> {
            emailValidator(emailValidate);
        });
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v->{
            this.finish();
        });

    }
    public void emailValidator(TextInputEditText etMail) {

        String emailToText = String.valueOf(etMail.getText());
        System.out.println(emailToText);
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this, "Email valid !", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Entered valid Email address !", Toast.LENGTH_SHORT).show();
            etMail.setError("Entered valid Email address !");
        }
    }
}
