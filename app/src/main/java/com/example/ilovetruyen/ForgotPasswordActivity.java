package com.example.ilovetruyen;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
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

import com.example.ilovetruyen.api.UserAPI;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private TextInputLayout mEmail;
    private MaterialButton btnSendMail;

    RetrofitService retrofitService;
    private TextView  message;
    UserAPI userAPI;
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
            String emailToText = String.valueOf(emailValidate.getText());
            emailValidator(emailValidate);
            forgotPassword(emailToText);
            new AlertDialog.Builder(ForgotPasswordActivity.this)
                    .setTitle("Cập nhật mật khẩu")
                    .setMessage("Vui lòng xem email để lấy mật khẩu mới !")
                    .setNeutralButton("Close", (dialog, which) -> {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    })
                    .show();
        });


    }
    public void emailValidator(TextInputEditText etMail) {

        String emailToText = String.valueOf(etMail.getText());
        System.out.println(emailToText);
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
//            Toast.makeText(this, "Email hợp lệ !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng email !", Toast.LENGTH_SHORT).show();
            etMail.setError("Vui lòng nhập đúng định dạng email !");
        }
    }
    public void forgotPassword(String email){
        retrofitService = new RetrofitService();
        userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        message = findViewById(R.id.message);

        userAPI.forgotPassword(email).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(getApplicationContext(), "Đã gửi email thành công !", Toast.LENGTH_SHORT).show();
                }
                else{
                    message.setText("Email không tồn tại trong hệ thống !");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            }
        });
    }
}
