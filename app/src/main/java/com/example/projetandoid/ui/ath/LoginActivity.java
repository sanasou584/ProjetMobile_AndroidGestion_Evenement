package com.example.projetandoid.ui.ath;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetandoid.organizer.AdminDashboardActivity;
import com.example.projetandoid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText txtmail ,txtpass;
    private Button btnLogin, bntInscri;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String  email;
    private String  pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        //Recuperer l'id
        this.txtmail = (EditText) findViewById(R.id.etEmail);
        this.txtpass = (EditText) findViewById(R.id.etPassword);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.bntInscri = (Button) findViewById(R.id.btnInsc);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(this::Auth);
        bntInscri.setOnClickListener(this::Insc);
    }
        //action sur inscription
        public void Insc(View v){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
        }
        //action sur authentificaion
        public void Auth(View v){

        email = txtmail.getText().toString().trim();
        pwd = txtpass.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                txtmail.setError("Email obligatoire");
                txtmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                txtpass.setError("Mot de passe obligatoire");
                txtpass.requestFocus();
                return;
            }
            btnLogin.setEnabled(false);
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            if (firebaseUser != null) {
                                checkUserRole(firebaseUser.getUid());
                            } else {
                                btnLogin.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Erreur utilisateur", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            btnLogin.setEnabled(true);
                            Toast.makeText(LoginActivity.this,
                                    "Email ou mot de passe incorrect",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }

    private void checkUserRole(String uid) {
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    btnLogin.setEnabled(true);

                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");

                        if ("ADMIN".equals(role)) {
                            Toast.makeText(LoginActivity.this, "Connexion admin réussie", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            mAuth.signOut();
                            Toast.makeText(LoginActivity.this,
                                    "Accès refusé : vous n'êtes pas administrateur",
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
                        mAuth.signOut();
                        Toast.makeText(LoginActivity.this,
                                "Profil utilisateur introuvable dans Firestore",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    btnLogin.setEnabled(true);
                    mAuth.signOut();
                    Toast.makeText(LoginActivity.this,
                            "Erreur Firestore : " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });

         /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
}