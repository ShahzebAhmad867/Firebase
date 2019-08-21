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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupAcivity extends AppCompatActivity {
    EditText supname, supemail, supphone, suppass, supcpass;
    Button sgnupbtn;
    TextView aldyacct;
    String sname,semail,sphone,spass,scpass;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_acivity);

        supname = (EditText)findViewById(R.id.signup_name);
        supemail = (EditText)findViewById(R.id.signup_email);
        supphone = (EditText)findViewById(R.id.signup_phone);
        suppass = (EditText)findViewById(R.id.signup_pwd);
        supcpass = (EditText)findViewById(R.id.signup_cpwd);

        firebaseAuth = FirebaseAuth.getInstance();




        sgnupbtn = (Button)findViewById(R.id.signup_btn);

        aldyacct = (TextView)findViewById(R.id.already_account);

        aldyacct.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupAcivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sgnupbtn.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View view) {
                sname = supname.getText().toString();
                semail = supemail.getText().toString();
                sphone = supphone.getText().toString();
                spass = suppass.getText().toString();
                scpass = supcpass.getText().toString();
                if (!TextUtils.isEmpty(sname)||!TextUtils.isEmpty(semail)||!TextUtils.isEmpty(sphone)||!TextUtils.isEmpty(spass)||!TextUtils.isEmpty(scpass))
                {
                    if (scpass.equals(spass))
                    {
                        firebaseAuth.createUserWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>( ) {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                    String user_id = current_user.getUid();
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    HashMap usermap = new HashMap();
                                    usermap.put("Username",sname);
                                    usermap.put("Useremail",semail);
                                    databaseReference.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Intent intent = new Intent(SignupAcivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Toast.makeText(SignupAcivity.this, "Error ocurred while uploading data", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    Toast.makeText(SignupAcivity.this, "Error ocurred in registering user",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
