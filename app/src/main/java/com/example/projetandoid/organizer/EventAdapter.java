package com.example.projetandoid.organizer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandoid.R;
import com.example.projetandoid.model.Event;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

        public interface OnDeleteClickListener {
            void onDelete(Event event);
        }

        private final Context context;
        private final List<Event> eventList;
        private final OnDeleteClickListener deleteClickListener;

        public EventAdapter(Context context, List<Event> eventList, OnDeleteClickListener deleteClickListener) {
            this.context = context;
            this.eventList = eventList;
            this.deleteClickListener = deleteClickListener;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_event_manage, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            Event event = eventList.get(position);

            holder.tvTitle.setText(event.getTitre());
            holder.tvDateTime.setText(event.getDate() + " • " + event.getHeure());
            holder.tvLocation.setText(event.getLieu());
            holder.tvCapacity.setText("Capacité : " + event.getCapacite());

            holder.btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddEventActivity.class);
                intent.putExtra("eventId", event.getId());
                intent.putExtra("titre", event.getTitre());
                intent.putExtra("description", event.getDescription());
                intent.putExtra("lieu", event.getLieu());
                intent.putExtra("date", event.getDate());
                intent.putExtra("heure", event.getHeure());
                intent.putExtra("capacite", event.getCapacite());
                intent.putExtra("actif", event.isActif());
                context.startActivity(intent);
            });

            holder.btnDelete.setOnClickListener(v -> deleteClickListener.onDelete(event));
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        static class EventViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvDateTime, tvLocation, tvCapacity;
            Button btnEdit, btnDelete;

            public EventViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDateTime = itemView.findViewById(R.id.tvDateTime);
                tvLocation = itemView.findViewById(R.id.tvLocation);
                tvCapacity = itemView.findViewById(R.id.tvCapacity);
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
        }
    }

