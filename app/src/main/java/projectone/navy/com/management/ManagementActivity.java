package projectone.navy.com.management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import projectone.navy.com.management.vo.User;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        ArrayList<User> userList = (ArrayList<User>)  getIntent().getSerializableExtra("userList");
        listView = findViewById(R.id.listView);

        adapter = new UserListAdapter(getApplicationContext(), userList, this);
        listView.setAdapter(adapter);
    }
}
