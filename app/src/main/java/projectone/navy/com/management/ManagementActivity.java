package projectone.navy.com.management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import projectone.navy.com.management.vo.User;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {

    private List<User> userList;
    private List<User> saveList;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        userList = (ArrayList<User>)  getIntent().getSerializableExtra("userList");
        saveList = new ArrayList<>();
        for (User saveUser : userList) {
            saveList.add(saveUser);
        }

        ListView listView = findViewById(R.id.listView);

        adapter = new UserListAdapter(getApplicationContext(), userList, this, saveList);
        listView.setAdapter(adapter);

        EditText searchEditText = findViewById(R.id.search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchUser(String search) {
        userList.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (saveList.get(i).getUserId().contains(search)) {
                userList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
