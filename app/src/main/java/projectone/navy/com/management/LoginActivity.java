package projectone.navy.com.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;
import projectone.navy.com.management.vo.User;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference; // Database 사용을 위한 멤버 객체

    private EditText idText; // id 멤버 변수
    private EditText passwordText; // password 멤버 변수
    private Button loginButton; // 로그인 버튼 멤버 변수
    private TextView registerButton; // 회원가입 버튼 멤버 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login(); //로그인 메서드
        registerUser(); //회원가입 메서드
    }

    /**
     * 로그인 메서드
     */
    private void login() {
        idText = findViewById(R.id.idText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(idText.getText().toString());
                databaseReference
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String dbUserPw;
                                User user = dataSnapshot.getValue(User.class);
                                dbUserPw = user.getUserPw();
                                if (dbUserPw != null && dbUserPw.equals(passwordText.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userId", idText.getText().toString());
                                    intent.putExtra("userPw", passwordText.getText().toString());
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원정보가 틀립니다.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("tmz", "Failed to read value.", databaseError.toException());
                            }
                        });
             }
        });
    }

    /**
     * 회원가입 메서드
     */
    private void registerUser() {
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}
