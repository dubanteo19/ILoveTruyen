package com.example.ilovetruyen.ui.adventise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.ui.home.HomeFragment;

public class AdvertiseFragment extends Fragment {

    public AdvertiseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
public static AdvertiseFragment newInstance(){
        return new AdvertiseFragment();
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertise, container, false);

        ImageView closeButton = view.findViewById(R.id.close_advertise);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức closeAdvertiseFragment từ FragmentHome
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof HomeFragment) {
                    ((HomeFragment) parentFragment).hideAdvertiseFragment();
                }
            }
        });

        return view;
    }


}