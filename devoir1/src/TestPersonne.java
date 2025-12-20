package src;
import java.util.stream.Stream;

public class TestPersonne {

    final Etudiant[] etudiants = new Etudiant[5];

    TestPersonne() {
        etudiants[0] = new Etudiant("Omar", 20, 1, "Licence");
        etudiants[1] = new Etudiant("toufik", 22, 2, "Master");
        etudiants[2] = new Etudiant("Zoubir", 21, 3, "Licence");
        etudiants[3] = new Etudiant("Amine", 23, 4, "Doctorat");
        etudiants[4] = new Etudiant("Yassine", 20, 5, "Master");
    }

    public Etudiant findByName(String name) {
        Etudiant found = null;
        Stream<Etudiant> studentStream = Stream.of(etudiants);
        found = studentStream.filter(e -> e.getNom().equals(name)).findFirst().orElse(null);
        return found;
    }

    public static void main(String[] args) {
        TestPersonne tp = new TestPersonne();
        Etudiant omar;
        Etudiant notFound;
        Etudiant oldest = tp.etudiants[0];
        Stream<Etudiant> studentStream = Stream.of(tp.etudiants);

        System.out.println("Liste des étudiants:");
        studentStream.forEach(e -> System.out.println("Nom: " + e.getNom() + ", Age: " + e.getAge() + ", Niveau: " + e.getNiveau()));

        for (Etudiant e : tp.etudiants) {
            if (e.getAge() > oldest.getAge()) {
                oldest = e;
            }
        }

        System.out.println("L'étudiant le plus âgé est: " + oldest.getNom() + ", Age: " + oldest.getAge() + ", Niveau: " + oldest.getNiveau());

        omar = tp.findByName("Omar");
        notFound = tp.findByName("NonExistent");

        if (omar != null) {
            System.out.println("Etudiant trouvé: " + omar.getNom() + ", Niveau: " + omar.getNiveau());
        } else {
            System.out.println("omar non trouvé");
        }
        if (notFound != null) {
            System.out.println("Etudiant trouvé: " + notFound.getNom() + ", Niveau: " + notFound.getNiveau());
        } else {
            System.out.println("Etudiant non trouvé");
        }
    }
}
