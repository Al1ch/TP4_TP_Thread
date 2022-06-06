package Exo3;
import Exo3.Employe;

import java.lang.Thread;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

         List <Employe> liste_employe = new ArrayList<>();

        Employe e1 = new Employe(1, "serveur", 100);
        Employe e2 = new Employe(2, "banquier", 200);
        Employe e3 = new Employe(3, "Foudroyeur", 300);


        liste_employe.add(e1);
        liste_employe.add(e2);
        liste_employe.add(e3);

        double salaire_moyen  =  liste_employe.stream()
                .mapToInt(x -> x.getSalaire())
                .average()
                .orElse(0);
        System.out.println(salaire_moyen);


    }


}
