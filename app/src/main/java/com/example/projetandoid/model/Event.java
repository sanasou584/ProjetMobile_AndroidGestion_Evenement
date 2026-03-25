package com.example.projetandoid.model;

public class Event {
        private String id;
        private String titre;
        private String description;
        private String lieu;
        private String date;
        private String heure;
        private int capacite;
        private String organizerId;
        private boolean actif;
        private double prix;



    public Event() {
        }

        public Event(String id, String titre, String description, String lieu, String date,
                     String heure, int capacite, String organizerId, boolean actif) {
            this.id = id;
            this.titre = titre;
            this.description = description;
            this.lieu = lieu;
            this.date = date;
            this.heure = heure;
            this.capacite = capacite;
            this.organizerId = organizerId;
            this.actif = actif;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getTitre() { return titre; }
        public void setTitre(String titre) { this.titre = titre; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getLieu() { return lieu; }
        public void setLieu(String lieu) { this.lieu = lieu; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public String getHeure() { return heure; }
        public void setHeure(String heure) { this.heure = heure; }

        public int getCapacite() { return capacite; }
        public void setCapacite(int capacite) { this.capacite = capacite; }

        public String getOrganizerId() { return organizerId; }
        public void setOrganizerId(String organizerId) { this.organizerId = organizerId; }

        public boolean isActif() { return actif; }
        public void setActif(boolean actif) { this.actif = actif; }
        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }
}

