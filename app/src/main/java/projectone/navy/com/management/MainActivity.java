package projectone.navy.com.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView idText = findViewById(R.id.idText);
        TextView passwordText = findViewById(R.id.passwordText);
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String userPw = intent.getStringExtra("userPw");
        String message = "환영합니다, " + userId + " 님.";

        idText.setText(userId);
        passwordText.setText(userPw);
        welcomeMessage.setText(message);
    }
}
