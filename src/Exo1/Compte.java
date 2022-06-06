package Exo1;
import java.util.*;

public class Compte extends Thread{
    private double solde;



    public Compte(double solde){
        this.solde = solde;
    }

    public double getSolde(){
        return solde;
    }


        public void extrait(int number){
            solde -= number;
        }


        public void depot(double montant){
            solde += montant;
        }

}
