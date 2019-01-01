package projectone.navy.com.management;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import projectone.navy.com.management.vo.User;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText idText;    // User의 id값 멤버 변수
    private EditText passwordText; // User의 password값 멤버 변수
    private EditText nameText;   // User의 name값 멤버 변수
    private EditText ageText;   // User의 age값 멤버 변수
    private boolean validate; // userId 중복 체크 결과

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText = findViewById(R.id.idText);
        passwordText = findViewById(R.id.passwordText);
        nameText = findViewById(R.id.nameText);
        ageText = findViewById(R.id.ageText);

        registerUser();
        checkValidateUserId();
    }

    public void checkValidateUserId() {
        final Button validateButton = findViewById(R.id.validationButton);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = idText.getText().toString();
                if (validate) {
                    return;
                }

                if ("".equals(userId)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference
                        .child("users/" + userId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User nowUser = dataSnapshot.getValue(User.class);
                                if (nowUser == null) { //동일한 userId 값이 존재하지 않음
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    AlertDialog dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    idText.setEnabled(false);
                                    validate = true;
                                    idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                    validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    AlertDialog dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    private void registerUser() {
        Button registerButton = findViewById(R.id.registerButton); // 회원가입 버튼 로컬 변수

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = idText.getText().toString();
                String userPw = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userAge = ageText.getText().toString();

                if (!validate) { // 아이디 중복체크를 진행하지 않은 경우
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if ("".equals(userId) || "".equals(userPw) ||
                        "".equals(userName) || "".equals(userAge)) { // 빈 칸이 존재하는 경우
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                User user = new User();
                user.setUserId(userId);
                user.setUserPw(userPw);
                user.setUserName(userName);
                user.setUserAge(Integer.parseInt(userAge));
                user.setCreateDate(new Date().getTime());

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference
                        .child("users/" + userId)
                        .setValue(user)
                        .addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "계정 생성 완료. 로그인해주세요.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
            }
        });

    }
}
