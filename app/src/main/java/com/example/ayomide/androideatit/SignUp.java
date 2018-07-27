 package com.example.ayomide.androideatit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ayomide.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

 public class SignUp extends AppCompatActivity {

    MaterialEditText etPhone, etName, etPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etPhone = findViewById(R.id.etPhone);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        btnSignUp = findViewById(R.id.btnSignUp);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Calm down...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // check if already user phone
                        if(dataSnapshot.child(etPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone already registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                                mDialog.dismiss();
                                User user = new User(etName.getText().toString(), etPassword.getText().toString());
                                table_user.child(etPhone.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
