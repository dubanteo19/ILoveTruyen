package com.example.ilovetruyen.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilovetruyen.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeSectionTitleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeSectionTitleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    private static final String ARG_TARGET = "target";
    private static final String ARG_TITLE_ICON = "titleIcon";

    // TODO: Rename and change types of parameters
    private String title;
    private String target;
    private int titleIcon;

    public HomeSectionTitleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeSectionTitleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeSectionTitleFragment newInstance(String title, String target,int titleIcon) {
        HomeSectionTitleFragment fragment = new HomeSectionTitleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_TARGET, target);
        args.putInt(ARG_TITLE_ICON,titleIcon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            target = getArguments().getString(ARG_TARGET);
            titleIcon = getArguments().getInt(ARG_TITLE_ICON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_section_title, container, false);
        TextView titleTextView = view.findViewById(R.id.home_fragment_title);
        ImageView titleIconImageview = view.findViewById(R.id.home_fragment_title_icon);
        titleIconImageview.setImageResource(titleIcon);
        titleTextView.setText(title);
        return view;
    }
}