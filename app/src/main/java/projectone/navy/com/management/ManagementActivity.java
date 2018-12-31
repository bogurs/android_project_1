package projectone.navy.com.management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import projectone.navy.com.management.vo.User;

import java.util.ArrayList;

public class ManagementActivity extends AppCompatActivity {

//    private ListView listView;
//    private UserListAdapter adapter;
//    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        TextView userListTextView = findViewById(R.id.userListTextView);
        ArrayList<User> userList = (ArrayList<User>)  getIntent().getSerializableExtra("userList");
        StringBuilder users = new StringBuilder();
        for (User user : userList) {
            users
                    .append(user.getUserId())
                    .append(user.getUserPw())
                    .append(user.getUserName())
                    .append(user.getUserAge())
                    .append("\n");
        }
        userListTextView.setText(users);

//        listView = findViewById(R.id.listView);
//        userList = new ArrayList<>();
//
//        User user1 = new User();
//        user1.setUserId("홍길동");
//        user1.setUserPw("홍길동");
//        user1.setUserName("홍길동");
//        user1.setUserAge(50);
//        userList.add(user1);
//
//        adapter = new UserListAdapter(getApplicationContext(), userList);
//        listView.setAdapter(adapter);
    }
}
