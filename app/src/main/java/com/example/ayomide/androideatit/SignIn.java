package com.example.ayomide.androideatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ayomide.androideatit.Common.Common;
import com.example.ayomide.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    MaterialEditText etPhone, etPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Calm down...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Check if User does not exist in database
                        if (dataSnapshot.child(etPhone.getText().toString()).exists()) {
                            //Get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etPhone.getText().toString()).getValue(User.class);
                            if (user.getpassword().equals(etPassword.getText().toString()))
                                {
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    Log.d("SIGIN", "Sign in");
                                    startActivity(homeIntent);
                                    finish();
                                }
                            else
                                {
                                    Toast.makeText(SignIn.this, "incorrect password", Toast.LENGTH_SHORT).show();
                                }
                        }
                        else
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "user doesn't exist", Toast.LENGTH_SHORT).show();
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
