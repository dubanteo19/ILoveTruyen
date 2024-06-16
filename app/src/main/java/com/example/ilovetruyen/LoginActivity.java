package com.example.ilovetruyen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout mEmail;
    private MaterialButton btn_Login;
    private TextInputLayout mPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signup_now = findViewById(R.id.signupText);
        TextView forgot = findViewById(R.id.forgot_password);
        EdgeToEdge.enable(this);
        signup_now.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        forgot.setOnClickListener(v ->{
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        mEmail = findViewById(R.id.input_layout_edit_email);
        btn_Login = findViewById(R.id.buttonLogin);
        mPass = findViewById(R.id.input_layout_password);
        TextInputEditText emailValidate = findViewById(R.id.et_input_edit_email);
        btn_Login.setOnClickListener(v -> {
            emailValidator(emailValidate);
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
