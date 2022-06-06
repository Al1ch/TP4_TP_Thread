package Exo3;

public class Employe {
    private String poste;
    private int id;
    private int salaire;

    public Employe(int id , String poste , int salaire) {
        this.id = id;
        this.poste = poste;
        this.salaire = salaire;
    }

    public int getSalaire() {
        return salaire;
    }
}
