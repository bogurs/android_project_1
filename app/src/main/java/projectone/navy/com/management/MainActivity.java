package projectone.navy.com.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText idText = findViewById(R.id.idText);
        EditText passwordText = findViewById(R.id.passwordText);
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
    }
}
