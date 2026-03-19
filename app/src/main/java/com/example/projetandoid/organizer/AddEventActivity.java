package com.example.projetandoid.organizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.projetandoid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class AddEventActivity extends AppCompatActivity {
    private EditText txtTitre ,txtDesc,txtLieu,txtDate,txtHeure,txtCapacite;
    private Button btnAjouterEve;
    private CheckBox checkActive;
    private ImageView btnBack;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        this.txtTitre = (EditText) findViewById(R.id.txtTitle);
        this.txtDesc = (EditText) findViewById(R.id.txtdescription);
        this.txtDate = (EditText) findViewById(R.id.txtate);
        this.txtHeure = (EditText) findViewById(R.id.txttime);
        this.txtLieu = (EditText) findViewById(R.id.txtlocation);
        this.txtCapacite = (EditText) findViewById(R.id.txtcapacity);
        this.btnAjouterEve = (Button) findViewById(R.id.btnajouterEvent);
        this.checkActive = (CheckBox) findViewById(R.id.checkActive);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        btnAjouterEve.setOnClickListener(this::addEvent);
        txtHeure.setOnClickListener(this::openDatePicker);
        txtDate.setOnClickListener(this::openTimePicker);
        btnBack.setOnClickListener(this::backtohome);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void backtohome(View v){
        Intent i = new Intent(AddEventActivity.this,AdminDashboardActivity.class);
        startActivity(i);
    }
    public void openDatePicker(View v){
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddEventActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format(
                            Locale.getDefault(),
                            "%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                    );
                    txtDate.setText(date);
                },
                year,
                month,
                day
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void openTimePicker(View v){
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                AddEventActivity.this,
                (view, selectedHour, selectedMinute) -> {
                    String time = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                    );
                    txtHeure.setText(time);
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }


        public void addEvent(View v){
            String title = txtTitre.getText().toString();
            String description = txtDesc.getText().toString();
            String capacite = txtCapacite.getText().toString();
            String heure = txtHeure.getText().toString();
            String date = txtDate.getText().toString();
            String location = txtLieu.getText().toString();
            boolean isActive = checkActive.isChecked();


            if (TextUtils.isEmpty(title)) {
                txtTitre.setError("Titre obligatoire");
                txtTitre.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(description)) {
                txtDesc.setError("Description obligatoire");
                txtDesc.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(location)) {
                txtLieu.setError("Lieu obligatoire");
                txtLieu.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(date)) {
                txtDate.setError("Date obligatoire");
                txtDate.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(heure)) {
                txtHeure.setError("Heure obligatoire");
                txtHeure.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(capacite)) {
                txtCapacite.setError("Capacité obligatoire");
                txtCapacite.requestFocus();
                return;
            }

            int capacity;
                try {
                    capacity = Integer.parseInt(capacite);
                    } catch (NumberFormatException e) {
                    txtCapacite.setError("Capacité invalide");
                    txtCapacite.requestFocus();
                    return;
                }
            if (capacity <= 0) {
                txtCapacite.setError("La capacité doit être > 0");
                txtCapacite.requestFocus();
                return;
            }

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
                return;
            }

            btnAjouterEve.setEnabled(false);

            String organizerId = currentUser.getUid();

    Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("titre", title);
        eventMap.put("description", description);
        eventMap.put("lieu", location);
        eventMap.put("date", date);
        eventMap.put("heure", heure);
        eventMap.put("capacite", capacity);
        eventMap.put("organizerId", organizerId);
        eventMap.put("actif", isActive);
        eventMap.put("createdAt", FieldValue.serverTimestamp());
            db.collection("events")
                    .add(eventMap)
                    .addOnSuccessListener(documentReference -> {
                        btnAjouterEve.setEnabled(true);
                        Toast.makeText(AddEventActivity.this, "Événement ajouté avec succès", Toast.LENGTH_SHORT).show();
                        clearForm();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        btnAjouterEve.setEnabled(true);
                        Toast.makeText(AddEventActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
    }
    private void clearForm() {
        txtTitre.setText("");
        txtDesc.setText("");
        txtLieu.setText("");
        txtDate.setText("");
        txtHeure.setText("");
        txtCapacite.setText("");
        checkActive.setChecked(false);
    }

}