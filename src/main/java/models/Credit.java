package models;
import java.util.Date;

public class Credit extends BaseObject2<Credit> {
    private String libele; 
    private int id;
    private int montant;
    private Date datedebut;
    private Date datefin;

    public Credit() {
        super(Credit
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

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    @Override
    public String toString() {
        return "Credit{id=" + getId() + ", Libele=" + getLibele() + ", montant=" + getMontant() + ", datedebut=" + getDatedebut() + ", datefin=" + getDatefin() + "}";
    }
}
