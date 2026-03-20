package com.example.projetandoid.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetandoid.R;
import com.google.android.material.card.MaterialCardView;

public class AdminDashboardActivity extends AppCompatActivity {

    private MaterialCardView cardMyEvents ,cardAddEvent,cardParticipants,cardLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        //recupere l'id des element
        this.cardMyEvents = (MaterialCardView)  findViewById(R.id.cardMyEvents);
        this.cardAddEvent = (MaterialCardView) findViewById(R.id.cardAddEvent);
        this.cardParticipants = (MaterialCardView) findViewById(R.id.cardParticipants);
        this.cardLogout = (MaterialCardView) findViewById(R.id.cardLogout);
        cardMyEvents.setOnClickListener(this::myEvent);
        cardAddEvent.setOnClickListener(this::addEvent);
        cardParticipants.setOnClickListener(this::listPart);
        cardLogout.setOnClickListener(this::deconnecter);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }
    //fct : lors du clique sur l'element il passe a l'activite manager
    public void myEvent(View v){
        Intent intent = new Intent(AdminDashboardActivity.this,ManageEventsActivity.class);
        startActivity(intent);
    }
    //fct : lors du clique sur l'element il passe a l'activite ajoutevent
    public void addEvent(View v){
        Intent intent = new Intent(AdminDashboardActivity.this,AddEventActivity.class);
        startActivity(intent);
    }
    //fct : lors du clique sur l'element il passe a l'activite liste des patients
    public void listPart(View v){
        Intent intent = new Intent(AdminDashboardActivity.this,ParticipantListActivity.class);
        startActivity(intent);
    }
    //fct : lors du clique sur l'element il se deconnecte
    public void deconnecter(View v){
        Intent intent = new Intent(AdminDashboardActivity.this,ManageEventsActivity.class);
        startActivity(intent);
    }
}