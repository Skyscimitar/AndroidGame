package com.example.danieltaylor.androidgame.profile;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danieltaylor.androidgame.R;
import com.example.danieltaylor.androidgame.firebase.DatabaseManager;
import com.example.danieltaylor.androidgame.firebase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    EditText userName;
    Button btnSave;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseManager dm;
    DatabaseReference ref;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        dm = new DatabaseManager(mDatabase);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        userName = findViewById(R.id.profile_name);
        btnSave = findViewById(R.id.save_profile_button);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
        updateUI();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(){
        ref = mDatabase.getReference("User");
        Query query = ref.child(mUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = dataSnapshot.getValue(User.class);
                try {
                    userName.setText(u.getName());
                }catch (NullPointerException e) {
                    // if the user doesn't have a name, we leave the hint in the view
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveChanges(){
        if (userName.getText().toString().length() > 0) {
            u.setName(userName.getText().toString());
            dm.updateUserName(u);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
