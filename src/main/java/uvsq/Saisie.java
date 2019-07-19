package uvsq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class Saisie {

	public Saisie() throws IOException, URISyntaxException, InterruptedException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String saisieUser;
		String saisieUserNom;
		do {
			ClassMetier classMetier = new ClassMetier();
			System.out.println("Que voullez vous faire ?");
			System.out.println("	1- Chercher un groupe");
			System.out.println("	2- Chercher une profession");
			System.out.println("	3- Chercher une categorie");
			System.out.println("	4- Chercher un metier");
			System.out.println("	5- Sortir");
			saisieUser = bufferedReader.readLine().replaceAll(" ", "");

			switch (saisieUser) {
			case "1":
				System.out.println("Entrer le nom du groupe ");
				saisieUserNom = bufferedReader.readLine();
				classMetier.rechercheGroupe(saisieUserNom);
				break;
			case "2":
				System.out.println("Entrer le nom du profession ");
				saisieUserNom = bufferedReader.readLine();
				classMetier.rechercheProfession(saisieUserNom);
				break;
			case "3":
				System.out.println("Entrer le nom du categorie ");
				saisieUserNom = bufferedReader.readLine();
				classMetier.rechercheCategorie(saisieUserNom);
				break;
			case "4":
				System.out.println("Entrer le nom du metier ");
				saisieUserNom = bufferedReader.readLine();
				classMetier.rechercheMetier(saisieUserNom);
				break;
			case "5":
				System.exit(0);
				break;
			default:
				System.out.println("Veuillez entrer un nombre entre 1 et 5");
			}
		} while (true);
	}
}

