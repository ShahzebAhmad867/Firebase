package com.example.firebase;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText edemail, edpass;
    TextView dntacct;
    Button loginbtn;
    String logemail,logpass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edemail = (EditText)findViewById(R.id.signin_ed1);
        edpass = (EditText)findViewById(R.id.signin_ed2);

        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn = (Button)findViewById(R.id.login_btn);

        loginbtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                logemail = edemail.getText().toString();
                logpass = edpass.getText().toString();

                if (!TextUtils.isEmpty(logemail)&&!TextUtils.isEmpty(logpass))
                {
                    firebaseAuth.signInWithEmailAndPassword(logemail,logpass).addOnCompleteListener(new OnCompleteListener<AuthResult>( ) {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dntacct = (TextView)findViewById(R.id.dont_account);

        dntacct.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupAcivity.class);
                startActivity(intent);
            }
        });
    }
}
