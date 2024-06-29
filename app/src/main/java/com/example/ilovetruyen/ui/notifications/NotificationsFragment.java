package com.example.ilovetruyen.ui.notifications;

import static android.content.Context.MODE_PRIVATE;
import static com.example.ilovetruyen.util.UserStateHelper.logoutStatus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.navigation.fragment.NavHostFragment;

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
import com.example.ilovetruyen.ui.home.CloseAdsSharedVM;
import com.example.ilovetruyen.ui.maps.MapActivity;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "story_history";
    private PopupWindow popupWindow;
    private FragmentNotificationsBinding binding;
    //    private OnPopupClickListener popupClickListener;
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
        // Chuyen sang activity google map
        ConstraintLayout featureGGMap = root.findViewById(R.id.feature_maps);
        featureGGMap.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), MapActivity.class);
            startActivity(intent);
        });
        ConstraintLayout featureUpdateLayout = root.findViewById(R.id.feature_update);
        featureUpdateLayout.setOnClickListener(this::showPopup);

        ConstraintLayout featureAdsLayout = root.findViewById(R.id.feature_ads);
        featureAdsLayout.setOnClickListener(this::showPopupAds);

        ConstraintLayout featureRemoveAccountLayout = root.findViewById(R.id.feature_delete);
        featureRemoveAccountLayout.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Xóa tài khoản")
                    .setMessage("Bạn muốn xóa tài khoản của mình?\n" +
                            "Tất cả dữ liệu của tài khoản cũng sẽ bị xóa.")
                    .setIcon(R.drawable.icons_info)
                    .setNeutralButton("Hủy bỏ", (dialog, which) -> {
                    })
                    .setPositiveButton("Đồng ý", (dialog, which) -> {

                    })
                    .show();
        });

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

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (getParentFragment() instanceof OnPopupClickListener) {
//            popupClickListener = (OnPopupClickListener) getParentFragment();
//        } else {
//            throw new RuntimeException(context.toString() + " must implement OnCloseClickListener");
//        }
//    }


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
        TextInputEditText pass = popupView.findViewById(R.id.et_input_edit_password);
        TextInputEditText fullName = popupView.findViewById(R.id.et_input_edit_fullName_update);
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("userId", 0);
        String username = sharedPreferences.getString("user_name", "User");
        String email_user = sharedPreferences.getString("email", "User");
        String password = sharedPreferences.getString("password", "User");
        pass.setText(password);
        fullName.setText(username);
        message = popupView.findViewById(R.id.message);
        //cập nhật
        Button buttonUpdate = popupView.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(v -> {
            String updatedName = fullName.getText() != null ? fullName.getText().toString() : "";
            String updatedPass = pass.getText() != null ? pass.getText().toString() : "";
            if (!passwordValidator(pass)) {
                return;
            }
            ;

            if (!isFullNameValid(updatedName, fullName)) {
                return;
            }
            update(user_id, email_user, updatedPass, updatedName);

        });
        //close
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
        retrofitService = new RetrofitService();
        userAPI = retrofitService.getRetrofit().create(UserAPI.class);
        UserUpdate userUpdate = new UserUpdate(id, email, password, fullName);
        userAPI.update(id, userUpdate).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password", password);
                    editor.putString("user_name", fullName);
                    editor.apply();
                    TextView fullNameTextView = requireView().findViewById(R.id.fullName);
                    fullNameTextView.setText(fullName);
                    popupWindow.dismiss();
                    new AlertDialog.Builder(context)
                            .setTitle("Cập nhật thông tin")
                            .setMessage("Bạn đã cập nhật thông tin thành công !")
                            .setNeutralButton("Close", (dialog, which) -> {
                            })
                            .show();
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

    public boolean passwordValidator(TextInputEditText etPassword) {
        String passwordToText = String.valueOf(etPassword.getText());
        if (passwordToText.length() < 6) {
            Toast.makeText(getContext(), "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        }
        return true;
    }

    private boolean isFullNameValid(String fullName, TextInputEditText fullNameValidate) {
        if (fullName.isEmpty()) {
            fullNameValidate.setError("Họ tên không được bỏ trống!");
//            Toast.makeText(this, "Họ tên không được bỏ trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showPopupAds(View anchorView) {
        Context context = requireContext();
        View popupView = LayoutInflater.from(context).inflate(R.layout.fragment_popup_ads, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        //cập nhật


        //close
        Button btnClosePopup = popupView.findViewById(R.id.btnClosePopup);
        btnClosePopup.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        Button btnViewPopup = popupView.findViewById(R.id.btnViewPopup);
        btnViewPopup.setOnClickListener(v -> {

            if (popupWindow.isShowing()) {
                SharedPreferences adsSharedPreferences = context.getSharedPreferences("ads_prefs", MODE_PRIVATE);

                SharedPreferences.Editor editor = adsSharedPreferences.edit();
                editor.putBoolean("is_close_ads", true);
                editor.apply();

                boolean isCloseAds = adsSharedPreferences.getBoolean("is_close_ads", false);

                CloseAdsSharedVM closeAdsSharedVM = new ViewModelProvider(requireActivity()).get(CloseAdsSharedVM.class);
                closeAdsSharedVM.setCloseAds(isCloseAds);

                Toast.makeText(context, "Đã xóa quảng cáo", Toast.LENGTH_SHORT).show();
            }

            popupWindow.dismiss();


        });
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int offsetX = 0;
        int offsetY = 0;
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0] + offsetX, location[1] + offsetY);
    }


}