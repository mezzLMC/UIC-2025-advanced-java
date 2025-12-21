package src;
public class Personne {
    final String nom;
    final int age;
    final int id;

    public Personne(String nom, int age, int id) {
        this.nom = nom;
        this.age = age;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

	public int getId() {
		return id;
	}
}
