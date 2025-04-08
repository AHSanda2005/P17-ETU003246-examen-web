package models;
import java.util.Date;

public class Depense extends BaseObject2<Depense> {
    private String libele; 
    private int id;
    private int montant;
    private Date date;

    public Depense() {
        super(Depense
    .class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String Libele) {
        this.libele = Libele;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Depense{id=" + getId() + ", Libele=" + getLibele() + ", montant=" + getMontant() + ", date=" + getDate() + "}";
    }
}
