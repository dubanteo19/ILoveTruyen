package com.example.ilovetruyen;

import static android.content.ContentValues.TAG;
import static com.example.ilovetruyen.util.UserStateHelper.saveAdminStatus;
import static com.example.ilovetruyen.util.UserStateHelper.saveLoginStatus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ilovetruyen.admin.ILoveTruyenManagerActivity;
import com.example.ilovetruyen.api.UserAPI;
import com.example.ilovetruyen.dto.UserRegister;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.GoogleSignConfig;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout mEmail;
    private MaterialButton btn_Login;
    private TextInputLayout mPass;
    private TextView message;
    RetrofitService retrofitService;
    UserAPI userAPI;
    private GoogleSignInClient mGoogleSignInClient;
    final static int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signup_now = findViewById(R.id.signupText);
        TextView forgot = findViewById(R.id.forgot_password);
        message = findViewById(R.id.message);
        EdgeToEdge.enable(this);
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignConfig.createGoogleSignInOptions());
        MaterialButton loginGoogleBtn = findViewById(R.id.buttonLoginGoogle);
        loginGoogleBtn.setOnClickListener(v -> {
            signGoogle();
        });


        signup_now.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        forgot.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        mEmail = findViewById(R.id.input_layout_edit_email);
        btn_Login = findViewById(R.id.buttonLogin);
        mPass = findViewById(R.id.input_layout_password);
        TextInputEditText emailValidate = findViewById(R.id.et_input_edit_email);
        TextInputEditText password = findViewById(R.id.et_input_edit_pasword);
        btn_Login.setOnClickListener(v -> {
            emailValidator(emailValidate);
            String email = String.valueOf(emailValidate.getText());
            String pass = String.valueOf(password.getText());
            if(email.equals("admin@gmail.com") && pass.equals("admin")){
                saveAdminStatus(getApplicationContext(), true);
                Intent intent = new Intent(this, ILoveTruyenManagerActivity.class);
                startActivity(intent);
            } else {
                loginUser(email, pass, "");
            }

        });

    }

    private void signGoogle() {
        Intent signInGoogleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInGoogleIntent, RC_SIGN_IN);
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

    private void loginUser(String email, String pass, String fullName) {
        retrofitService = new RetrofitService();
        userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        UserRegister userRegister = new UserRegister(email, pass, "");
        message = findViewById(R.id.message);
        userAPI.login(userRegister).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    saveLoginStatus(getApplicationContext(), true, user.fullName(), user.email(), user.id(), user.password());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    message.setText("Sai email hoặc mật khẩu!");
                    message.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                message.setText("Sai email hoặc mật khẩu !");
                message.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(), "Sai email hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        String name = account.getGivenName();
        String email = account.getEmail();
        saveLoginStatus(getApplicationContext(), true,name,email,19, "password");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}
