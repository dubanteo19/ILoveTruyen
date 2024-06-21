package com.example.ilovetruyen.ui.comments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.CommentAdapter;
import com.example.ilovetruyen.model.Comment;
import com.example.ilovetruyen.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
// CommentFragment.java

public class CommentFragment extends Fragment {
    private List<Comment> commentList;
    private ImageView editComment;
    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        User loginUser = new User(R.drawable.one_piece, "Alice Smith");

        EditText commentInput = view.findViewById(R.id.comment_input);
        Button sendButton = view.findViewById(R.id.send_button);
        AtomicInteger commentEditPosition = new AtomicInteger(-1);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        commentList = new ArrayList<>();
        commentList.add(new Comment("Courtney Henry", "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco exercitation. Ullamco tempor adipisicing et voluptate duis sit esse aliqua esse ex.", "5 mins ago", R.drawable.thanh_guom_diet_quy));
        commentList.add(new Comment("Cameron Williamson", "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco.", "5 mins ago", R.drawable.thanh_guom_diet_quy));
        commentList.add(new Comment("Jane Cooper", "Ullamco tempor adipisicing et voluptate duis sit esse aliqua esse ex.", "10 mins ago", R.drawable.thanh_guom_diet_quy));
        commentList.add(new Comment("Alice Smith", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "15 mins ago", R.drawable.thanh_guom_diet_quy));
        commentList.add(new Comment("Bob Johnson", "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", "20 mins ago", R.drawable.thanh_guom_diet_quy));

        // nay giong nhu 1 callback, khi click vào 1 comment thi no se lay comment do
        // truyen nguoc ra ngoai de ben ngoai co the xu ly voi noi dung comment
        // java thi khong truyen ham duoc nhan no se truyen gian tiep qua interface
        CommentAdapter adapter = new CommentAdapter(commentList, position -> {
            Comment comment = commentList.get(position);
            // CHECK neu comment do la cua nguoi dang nhap thi moi cho sua
            if (loginUser.getName().equals(comment.getUserName())) {
                commentInput.setText(comment.getCommentText());
                commentEditPosition.set(position);
            } else {
                Toast.makeText(getContext(), "Bạn không thể chỉnh sửa comment của người khác", Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);

        Log.d("CommentFragment", "onCreateView: CommentFragment created!");

        sendButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString();
            if (commentText.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập nội dung comment", Toast.LENGTH_LONG).show();
                return;
            }
            if (commentEditPosition.get() != -1) {
                commentList.get(commentEditPosition.get()).setCommentText(commentText);
                commentEditPosition.set(-1);
                // TODO: cap nhat comment moi vao db
                Toast.makeText(getContext(), "Cập nhật comment thành công", Toast.LENGTH_LONG).show();

            } else {
                commentList.add(new Comment(loginUser.getName(), commentText, loginUser.getResouceImage()));
                // TODO: luu comment moi vao db
                Toast.makeText(getContext(), "Gửi comment thành công", Toast.LENGTH_LONG).show();
            }
            adapter.notifyDataSetChanged();

            commentInput.setText("");
        });

        return view;
    }
}
