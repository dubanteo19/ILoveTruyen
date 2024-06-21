package com.example.ilovetruyen.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ilovetruyen.ListOfStoryDownloadsActivity;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.ReadingHistoryActivity;
import com.example.ilovetruyen.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ConstraintLayout featureHisLayout = root.findViewById(R.id.feature_his);
        featureHisLayout.setOnClickListener(v ->{
            Intent intent = new Intent(root.getContext(), ReadingHistoryActivity.class);
            startActivity(intent);
        });
        ConstraintLayout feature_list = root.findViewById(R.id.feature_list);
        feature_list.setOnClickListener(v ->{
            Intent intent = new Intent(root.getContext(), ListOfStoryDownloadsActivity.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}