package com.example.projetandoid.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetandoid.ChangePasswordActivity;
import com.example.projetandoid.EditProfileActivity;
import com.example.projetandoid.R;
import com.example.projetandoid.ui.ath.LoginActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

    private MaterialCardView cardMyEvents ,cardAddEvent,cardParticipants,cardLogout;
    private ImageView imageProfil;
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
        this.imageProfil = (ImageView) findViewById(R.id.imgProfile);
        cardMyEvents.setOnClickListener(this::myEvent);
        cardAddEvent.setOnClickListener(this::addEvent);
        cardParticipants.setOnClickListener(this::listPart);
        cardLogout.setOnClickListener(this::deconnecter);
        imageProfil.setOnClickListener(this::editProfil);
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
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
        finish();
    }
    public void editProfil(View v){
        PopupMenu popupMenu = new PopupMenu(AdminDashboardActivity.this, imageProfil);
        popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_edit_profile) {
                Intent intent = new Intent(AdminDashboardActivity.this, EditProfileActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.menu_change_password) {
                Intent intent = new Intent(AdminDashboardActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

        popupMenu.show();
    }
}