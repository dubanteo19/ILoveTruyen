package com.example.ilovetruyen.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ilovetruyen.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecentlyReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentlyReadFragment extends Fragment {


    public RecentlyReadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentlyReadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentlyReadFragment newInstance() {
        RecentlyReadFragment fragment = new RecentlyReadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recently_read, container, false);
    }
}