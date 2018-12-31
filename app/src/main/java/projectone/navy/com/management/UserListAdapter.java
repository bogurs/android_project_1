package projectone.navy.com.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import projectone.navy.com.management.vo.User;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private Activity parentActivity;

    public UserListAdapter(Context context, List<User> userList, Activity parentActivity) {
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.user, null);
        final TextView userId = view.findViewById(R.id.userId);
        TextView userPw = view.findViewById(R.id.userPw);
        TextView userName = view.findViewById(R.id.userName);
        TextView userAge = view.findViewById(R.id.userAge);

        userId.setText(userList.get(position).getUserId());
        userPw.setText(userList.get(position).getUserPw());
        userName.setText(userList.get(position).getUserName());
        userAge.setText(Integer.toString(userList.get(position).getUserAge()));

        view.setTag(userList.get(position).getUserId());

        Button deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAlertBuilder = new AlertDialog.Builder(parentActivity);
                deleteAlertBuilder
                        .setMessage(userId.getText().toString() + " 회원을 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //삭제
                        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                        mFirebaseDatabase
                                .getReference("users/" + userId.getText().toString())
                                .removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        Toast.makeText(parentActivity, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        userList.remove(position);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소
                        Toast.makeText(parentActivity, "삭제 취소", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
                AlertDialog deleteAlertDialog = deleteAlertBuilder.create();
                deleteAlertDialog.show();
            }
        });

        return view;
    }
}
