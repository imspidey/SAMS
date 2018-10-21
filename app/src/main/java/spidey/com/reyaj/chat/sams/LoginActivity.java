package spidey.com.reyaj.chat.sams;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout rel1,rel2;
    EditText usr, pwd;
    Button log;
    TextView text;
    Handler handle = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            text.setVisibility(View.INVISIBLE);
            rel1.setVisibility(View.VISIBLE);
            rel2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rel2 = (RelativeLayout) findViewById(R.id.rellay2);
        rel1 = (RelativeLayout) findViewById(R.id.rellay1);
        text = (TextView) findViewById(R.id.textView1);
        handle.postDelayed(runnable, 2000);

        usr = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pass);
        log =  (Button) findViewById(R.id.loginbtn);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usr.getText().toString().equals("admin") && pwd.getText().toString().equals("admin")){
                    Toast.makeText(LoginActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,AppBase.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Incorrect User or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void checkatt(View view) {
        startActivity(new Intent(LoginActivity.this,StudActivity.class));
    }
}
