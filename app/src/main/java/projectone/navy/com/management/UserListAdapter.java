package projectone.navy.com.management;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import projectone.navy.com.management.vo.User;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.user, null);
        TextView userId = view.findViewById(R.id.userId);
        TextView userPw = view.findViewById(R.id.userPw);
        TextView userName = view.findViewById(R.id.userName);
        TextView userAge = view.findViewById(R.id.userAge);

        userId.setText(userList.get(position).getUserId());
        userPw.setText(userList.get(position).getUserPw());
        userName.setText(userList.get(position).getUserName());
        userAge.setText(Integer.toString(userList.get(position).getUserAge()));

        view.setTag(userList.get(position).getUserId());
        return view;
    }
}
