package com.example.ilovetruyen.ui.comments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CommentAdapter;
import com.example.ilovetruyen.databinding.FragmentCommentBinding;
import com.example.ilovetruyen.databinding.FragmentDashboardBinding;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.ui.dashboard.DashboardViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
// CommentFragment.java

public class CommentFragment extends Fragment {
    private List<Comment> commentList;
    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        commentList = new ArrayList<>();
        commentList.add(new Comment("Courtney Henry", "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco exercitation. Ullamco tempor adipisicing et voluptate duis sit esse aliqua esse ex.", "5 mins ago", R.drawable.hihi));
        commentList.add(new Comment("Cameron Williamson", "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco.", "5 mins ago", R.drawable.hihi));
        commentList.add(new Comment("Jane Cooper", "Ullamco tempor adipisicing et voluptate duis sit esse aliqua esse ex.", "10 mins ago", R.drawable.hihi));
        commentList.add(new Comment("Alice Smith", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "15 mins ago", R.drawable.hihi));
        commentList.add(new Comment("Bob Johnson", "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", "20 mins ago", R.drawable.hihi));

        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);

        Log.d("CommentFragment", "onCreateView: CommentFragment created!");


        return view;
    }
}
