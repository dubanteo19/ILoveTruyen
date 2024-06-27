package com.example.ilovetruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ilovetruyen.api.UserAPI;
import com.example.ilovetruyen.dto.UserRegister;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    boolean invalid = false;
    private TextInputLayout mEmail;
    private MaterialButton btn_Register;
    private TextInputLayout mPass;

    private TextInputLayout mFullName;
    private TextView mess;
    RetrofitService retrofitService;
    UserAPI userAPI;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.input_layout_edit_email);
        btn_Register = findViewById(R.id.buttonRegister);
        mPass = findViewById(R.id.input_layout_password);

        TextInputEditText emailValidate = findViewById(R.id.et_input_edit_email);
        TextInputEditText passwordValidate = findViewById(R.id.et_input_edit_pasword);
        TextInputEditText fullNameValidate = findViewById(R.id.et_input_edit_fullName);
        TextView login_now = findViewById(R.id.loginText);

        btn_Register.setOnClickListener(v -> {
            String email = String.valueOf(emailValidate.getText());
            String pass = String.valueOf(passwordValidate.getText());
            String fullName = String.valueOf(fullNameValidate.getText());
            if (!isFullNameValid(fullName, fullNameValidate)) {
                return;
            }
            emailValidator(emailValidate);
            if (!passwordValidator(passwordValidate)) {
                return;
            }
            registerUser(email, pass, fullName);
        });
        login_now.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
    private boolean isFullNameValid(String fullName, TextInputEditText fullNameValidate) {
        if (fullName.isEmpty()) {
            fullNameValidate.setError("Họ tên không được bỏ trống!");
//            Toast.makeText(this, "Họ tên không được bỏ trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void emailValidator(TextInputEditText etMail) {
        String emailToText = String.valueOf(etMail.getText());
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
//            Toast.makeText(this, "Email hợp lệ !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng email !", Toast.LENGTH_SHORT).show();
            etMail.setError("Vui lòng nhập đúng định dạng email !");
        }
    }
    public boolean passwordValidator(TextInputEditText etPassword){
        String passwordToText = String.valueOf(etPassword.getText());
        if (passwordToText.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        }
        return true;
    }
    private void registerUser(String email, String pass, String fullName) {
        retrofitService = new RetrofitService();
        userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        UserRegister userRegister = new UserRegister(email,pass,fullName);
        mess = findViewById(R.id.message);
        userAPI.register(userRegister).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        mess.setText("Email đã tồn tại trong hệ thống !");
                    }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });
    }
}
