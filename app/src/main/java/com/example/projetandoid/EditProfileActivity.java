package com.example.projetandoid;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetandoid.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etName, etEmail;
    private Button btnSaveProfile;
    private ImageView btnBack;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnBack = findViewById(R.id.btnBack);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnBack.setOnClickListener(v -> finish());
        btnSaveProfile.setOnClickListener(this:: updateProfile);

        loadCurrentUserData();
    }
    private void loadCurrentUserData() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        currentUid = firebaseUser.getUid();

        db.collection("users")
                .document(currentUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);

                        if (user != null) {
                            etName.setText(user.getNom());
                            etEmail.setText(user.getEmail());
                        }
                    } else {
                        Toast.makeText(this, "Profil introuvable", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(EditProfileActivity.this,
                                "Erreur chargement profil : " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }

    public void updateProfile(View v){
        String newName = etName.getText().toString().trim();

        if (TextUtils.isEmpty(newName)) {
            etName.setError("Nom obligatoire");
            etName.requestFocus();
            return;
        }

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_LONG).show();
            return;
        }

        btnSaveProfile.setEnabled(false);

        Map<String, Object> updates = new HashMap<>();
        updates.put("nom", newName);

        db.collection("users")
                .document(currentUid)
                .update(updates)
                .addOnSuccessListener(unused -> {
                    btnSaveProfile.setEnabled(true);
                    Toast.makeText(EditProfileActivity.this,
                            "Profil mis à jour avec succès",
                            Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    btnSaveProfile.setEnabled(true);
                    Toast.makeText(EditProfileActivity.this,
                            "Erreur mise à jour : " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }

    }
