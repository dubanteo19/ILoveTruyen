package com.example.ilovetruyen.admin.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ilovetruyen.R;
import com.example.ilovetruyen.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserManagerAdminAdapter extends RecyclerView.Adapter<UserManagerAdminAdapter.UserManagerAdminViewHolder> {

    private Context context;
    private List<User> userList;

    public UserManagerAdminAdapter(Context context) {
        this.context = context;
        this.userList = new ArrayList<>();
    }

    public void setData(List<User> userList) {
        this.userList.clear();
        if (userList != null) {
            this.userList.addAll(userList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserManagerAdminAdapter.UserManagerAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserManagerAdminAdapter.UserManagerAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserManagerAdminAdapter.UserManagerAdminViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) return;
        holder.fullName.setText(user.fullName());
        holder.userDetailBtn.setOnClickListener(v -> {
            showAddCategoryDialog();
        });

    }

    @Override
    public int getItemCount() {
        return userList.size() > 0 ? userList.size() : 0;
    }

    public class UserManagerAdminViewHolder extends RecyclerView.ViewHolder {
        private TextView fullName;
        private ImageView userDetailBtn;

        public UserManagerAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.user_fullName);
            userDetailBtn = itemView.findViewById(R.id.user_detail);
        }
    }
    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_add_category)
                .setTitle("Xóa người dùng")
                .setMessage("Bạn có chắc muốn xóa người dùng này không ?")
                .setPositiveButton("Xóa", (dialog, which) -> {

                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
