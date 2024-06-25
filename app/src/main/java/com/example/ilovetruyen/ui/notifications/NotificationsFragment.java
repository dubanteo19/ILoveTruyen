package com.example.ilovetruyen.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import static com.example.ilovetruyen.util.UserStateHelper.logoutStatus;
import static com.example.ilovetruyen.util.UserStateHelper.saveLoginStatus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ilovetruyen.ListOfStoryDownloadsActivity;
import com.example.ilovetruyen.LoginActivity;
import com.example.ilovetruyen.MainActivity;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.ReadingHistoryActivity;
import com.example.ilovetruyen.api.UserAPI;
import com.example.ilovetruyen.databinding.FragmentNotificationsBinding;
import com.example.ilovetruyen.dto.UserUpdate;
import com.example.ilovetruyen.model.User;
import com.example.ilovetruyen.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "story_history";
    private PopupWindow popupWindow;
    private FragmentNotificationsBinding binding;
    RetrofitService retrofitService;
    UserAPI userAPI;

    TextView message;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView logoutbtn = root.findViewById(R.id.logoutbtn);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ConstraintLayout featureHisLayout = root.findViewById(R.id.feature_his);
        featureHisLayout.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), ReadingHistoryActivity.class);
            startActivity(intent);
        });
        ConstraintLayout feature_list = root.findViewById(R.id.feature_list);
        feature_list.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), ListOfStoryDownloadsActivity.class);
            startActivity(intent);
        });
        ConstraintLayout featureUpdateLayout = root.findViewById(R.id.feature_update);
        featureUpdateLayout.setOnClickListener(this::showPopup);

        Button user_login_btn = root.findViewById(R.id.user_login_btn);
        TextView email = root.findViewById(R.id.email_user);
        TextView fullName = root.findViewById(R.id.fullName);
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        if (isLoggedIn) {
            logoutbtn.setVisibility(View.VISIBLE);
            featureUpdateLayout.setVisibility(View.VISIBLE);
            user_login_btn.setVisibility(View.GONE);
            email.setVisibility(View.VISIBLE);
            fullName.setVisibility(View.VISIBLE);
            String username = sharedPreferences.getString("user_name", "User");
            String email_user = sharedPreferences.getString("email", "User");
            email.setText(email_user);
            fullName.setText(username);
        } else {
            featureUpdateLayout.setVisibility(View.GONE);
            logoutbtn.setVisibility(View.GONE);
            user_login_btn.setVisibility(View.VISIBLE);
            email.setVisibility(View.GONE);
            fullName.setVisibility(View.GONE);
        }
        logoutbtn.setOnClickListener(v -> {
                    Context context = requireContext();
                    new AlertDialog.Builder(context)
                            .setTitle("Xác nhận đăng xuất")
                            .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?")
                            .setNeutralButton("Hủy bỏ", (dialog, which) -> {

                            })
                            .setPositiveButton("Đồng ý", (dialog, which) -> {
                                logout(context);
                            })
                            .show();
                }
        );
        user_login_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public static void logout(Context context) {
        logoutStatus(context);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    private void showPopup(View anchorView) {
        Context context = requireContext();
        View popupView = LayoutInflater.from(context).inflate(R.layout.fragment_popup, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        TextInputEditText emailValidate = popupView.findViewById(R.id.et_input_edit_email_update);
        TextInputEditText fullName = popupView.findViewById(R.id.et_input_edit_fullName_update);
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("userId", 0);
        String username = sharedPreferences.getString("user_name", "User");
        String email_user = sharedPreferences.getString("email", "User");
        String password = sharedPreferences.getString("password", "User");
        emailValidate.setText(email_user);
        fullName.setText(username);
        message = popupView.findViewById(R.id.message);
        //cập nhật
        Button buttonUpdate = popupView.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(v -> {
            String updatedEmail = emailValidate.getText() != null ? emailValidate.getText().toString() : "";
            String updatedName = fullName.getText() != null ? fullName.getText().toString() : "";
            if (emailValidator(emailValidate)) {
               return;
            }
            update(user_id, updatedEmail, password, updatedName);
            TextView emailTextView = requireView().findViewById(R.id.email_user);
            TextView fullNameTextView = requireView().findViewById(R.id.fullName);
            emailTextView.setText(updatedEmail);
            fullNameTextView.setText(updatedName);
            popupWindow.dismiss();
        });
        Button btnClosePopup = popupView.findViewById(R.id.btnClosePopup);
        btnClosePopup.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int offsetX = 0;
        int offsetY = 0;
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0] + offsetX, location[1] + offsetY);
    }

    public void update(int id, String email, String password, String fullName) {
        Context context = requireContext();
        View popupView = LayoutInflater.from(context).inflate(R.layout.fragment_popup, null);
        message = popupView.findViewById(R.id.message);
        retrofitService = new RetrofitService();
        userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        UserUpdate userUpdate = new UserUpdate(id, email, password, fullName);
        userAPI.update(id,userUpdate).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Context context = requireContext();
                    View popupView = LayoutInflater.from(context).inflate(R.layout.fragment_popup, null);
                    message = popupView.findViewById(R.id.message);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("user_name", fullName);
                    editor.apply();
                    message.setText("Cập nhật thành công!");
                } else {
                    message.setText("Cập nhật thất bại!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                message.setText("Không thể cập nhật !");
            }
        });
    }

    public boolean emailValidator(TextInputEditText etMail) {
        String emailToText = String.valueOf(etMail.getText());
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
//            Toast.makeText(this, "Email hợp lệ !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng email !", Toast.LENGTH_SHORT).show();
            etMail.setError("Vui lòng nhập đúng định dạng email !");
        }
        return false;
    }

}