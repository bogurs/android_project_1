package projectone.navy.com.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import projectone.navy.com.management.vo.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView idText = findViewById(R.id.idText);
        TextView passwordText = findViewById(R.id.passwordText);
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        Button managementButton = findViewById(R.id.managementButton);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String userPw = intent.getStringExtra("userPw");
        String message = "환영합니다, " + userId + " 님.";

        idText.setText(userId);
        passwordText.setText(userPw);
        welcomeMessage.setText(message);

        if (!userId.equals("admin")) {
            managementButton.setVisibility(View.GONE);
        }

        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mFirebaseDatabase.getReference("users/")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<User> userList = new ArrayList<>();
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    User nowUser = userSnapshot.getValue(User.class);
                                    if ("admin".equals(nowUser.getUserId())) {
                                        continue;
                                    }
                                    userList.add(nowUser);
                                }

                                Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                                intent.putExtra("userList", userList);
                                MainActivity.this.startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

}
