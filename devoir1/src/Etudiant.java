package src;
public class Etudiant extends Personne {
    final String niveau;

    public Etudiant(String nom, int age, int id, String niveau) {
        super(nom, age, id);
        this.niveau = niveau;
    }

    public String getNiveau() {
        return niveau;
    }

	@Override
	public String toString() {
		return "Etudiant n°" + id +" - " + nom + ", age:" + age + ", niveau " + niveau;
	}

}
