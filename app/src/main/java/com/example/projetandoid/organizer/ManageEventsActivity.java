package com.example.projetandoid.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandoid.R;
import com.example.projetandoid.model.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ManageEventsActivity extends AppCompatActivity {
    private RecyclerView recyclerEvents;
    private TextView tvEmpty;
    private ImageView btnBack, btnAddEvent;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EventAdapter adapter;
    private final List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_events);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        recyclerEvents = findViewById(R.id.recyclerEvents);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnBack = findViewById(R.id.btnBack);
        btnAddEvent = findViewById(R.id.btnAddEvent);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        recyclerEvents.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventAdapter(this, eventList, event -> deleteEvent(event.getId()));
        recyclerEvents.setAdapter(adapter);
        btnBack.setOnClickListener(v -> finish());

        btnAddEvent.setOnClickListener(v -> {
            startActivity(new Intent(ManageEventsActivity.this, AddEventActivity.class));
        });

        loadMyEvents();

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadMyEvents();
    }

    private void loadMyEvents() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        String organizerId = currentUser.getUid();

        db.collection("events")
                .whereEqualTo("organizerId", organizerId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    eventList.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        Event event = doc.toObject(Event.class);
                        if (event != null) {
                            event.setId(doc.getId());
                            eventList.add(event);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (eventList.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        recyclerEvents.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        recyclerEvents.setVisibility(View.VISIBLE);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(ManageEventsActivity.this,
                                "Erreur chargement : " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }

    private void deleteEvent(String eventId) {
        db.collection("events")
                .document(eventId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Événement supprimé", Toast.LENGTH_SHORT).show();
                    loadMyEvents();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Erreur suppression : " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }

}