package models;

public class Utilisateur extends BaseObject<Utilisateur> {
    private String nom; 
    private int id;
    private String password;

    public Utilisateur
() {
        super(Utilisateur
    .class);
    }

    public Utilisateur
(String nom) {
        super(Utilisateur
    .class);
        this.nom = nom;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateur{id=" + getId() + ", nom='" + getNom() + ", password='" + getPassword() + "'}";
    }
}
