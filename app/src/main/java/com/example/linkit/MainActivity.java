package com.example.linkit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    // Widgets
    Button loginBTN;
    Button createAccBTN;
    Button clickSnap;
    private EditText emailET;
    private EditText passET;


    // Firebase Authentication
//    private FirebaseAuth firebaseAuth;
//    private FirebaseAuth.AuthStateListener authStateListener;
//    private FirebaseUser currentUser;

    // Firebase Connection
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    private CollectionReference collectionReference = db.collection("Users");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBTN = findViewById(R.id.email_sign_in_button);
        createAccBTN = findViewById(R.id.create_acct_BTN);
        emailET = findViewById(R.id.email);
        passET  = findViewById(R.id.password);
        clickSnap = findViewById(R.id.clickSnap);



        // Forget to initialize the Auth Ref
//        firebaseAuth = FirebaseAuth.getInstance();






        // create new acccoint activity transition
        createAccBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create sign up activity
//                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
//                startActivity(i);


            }
        });


        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("TAGY","no log in created");

//                LoginEmailPasswordUser(
//                        emailET.getText().toString().trim(),
//                        passET.getText().toString().trim()
//                );
            }
        });

        clickSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, scannerActivity.class);
                startActivity(i);
            }
        });

    }

//    private void LoginEmailPasswordUser(String email, String pwd) {
//        // Checking for empty texts
//        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)){
//
//            firebaseAuth.signInWithEmailAndPassword(email,pwd)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                            assert user != null;
//                            final String currentUserId = user.getUid();
//
//                            collectionReference.
//                                    whereEqualTo("userId", currentUserId)
//                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                                            if (error != null){
//
//                                            }
//                                            assert value != null;
//                                            if (!value.isEmpty()){
//                                                // Getting all QueryDocSnapShots
//                                                for (QueryDocumentSnapshot snapshot : value){
//                                                    // created journal user one now no need to create it again and again
//
//
//                                                    JournalUser journalUser = JournalUser.getInstance();
//                                                    journalUser.setUsername(snapshot.getString("username"));
//                                                    journalUser.setUserId(snapshot.getString("userId"));
//
//                                                    // Go to ListActivity after successful login
//
//                                                    // Let's display the List of journals after login
//                                                  //  startActivity(new Intent(MainActivity.this, AddJournalActivity.class));
////                                                    startActivity(new Intent(MainActivity.this, JournalListActivity.class));
//
//                                                    Log.i("TAGY" , " GO TO NEW ACTIVITY");
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    // If Failed:
//                    Toast.makeText(MainActivity.this,
//                            "Something went wront "+e, Toast.LENGTH_LONG).show();
//                }
//            });
//
//
//
//
//
//
//        }else{
//            Toast.makeText(MainActivity.this,
//                    "Please Enter email & password"
//                    , Toast.LENGTH_SHORT).show();
//        }
//
//
//
//
//
//
//
//
//
//    }
}