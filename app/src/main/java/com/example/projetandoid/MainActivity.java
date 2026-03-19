package com.example.projetandoid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.projetandoid.ui.ath.LoginActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }*/
    //private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //activite splash -> intent implicite (login)
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        /*db = FirebaseFirestore.getInstance();

        Map<String, Object> service = new HashMap<>();
        service.put("nom", "Coupe simple");
        service.put("description", "Coupe homme classique");
        service.put("prix", 25);
        service.put("duree", 30);
        service.put("categorie", "Coiffure");
        service.put("actif", true);

        db.collection("services")
                .add(service)
                .addOnSuccessListener(documentReference ->
                        Log.d("FIRESTORE", "Document ajoute: " + documentReference.getId()))
                .addOnFailureListener(e ->
                        Log.e("FIRESTORE", "Erreur ajout", e));
    }*/
    }
}