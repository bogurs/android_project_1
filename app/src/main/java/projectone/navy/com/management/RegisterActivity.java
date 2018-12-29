package projectone.navy.com.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import projectone.navy.com.management.vo.User;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReference; // Database 사용을 위한 멤버 객체

    private EditText idText;    // User의 id값 멤버 변수
    private EditText passwordText; // User의 password값 멤버 변수
    private EditText nameText;   // User의 name값 멤버 변수
    private EditText ageText;   // User의 age값 멤버 변수
    private Button registerButton; // 회원가입 버튼 멤버 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        registerUser();
    }

    private void registerUser() {
        idText = findViewById(R.id.idText);
        passwordText = findViewById(R.id.passwordText);
        nameText = findViewById(R.id.nameText);
        ageText = findViewById(R.id.ageText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = idText.getText().toString();
                String userPw = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                int userAge = Integer.parseInt(ageText.getText().toString());

                User user = new User();
                user.setUserId(userId);
                user.setUserPw(userPw);
                user.setUserName(userName);
                user.setUserAge(userAge);
                user.setCreateDate(new Date().getTime());

                databaseReference
                        .child("users/" + userId)
                        .setValue(user)
                        .addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "계정 생성 완료. 로그인해주세요.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        });
            }
        });
    }
}
