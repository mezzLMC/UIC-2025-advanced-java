package src.GestionEtudiants;

import java.util.List;
import java.util.stream.Stream;

import src.Etudiant;
import src.GestionEtudiants.IOStudentManager.IOStudentManager;

public class GestionEtudiants {

	private static String filePath = "assets/students.txt";
	private static List<Etudiant> etudiants;
	private static final ShellPrompt shell = new ShellPrompt();

	private static final ShellPrompt.CommandLine[] commands = {
		new ShellPrompt.CommandLine("Afficher tous les étudiants", () -> DumpAllStudents()),
		new ShellPrompt.CommandLine("Afficher tous les étudiants (triés alphabétiquement)", () -> DumpAllStudentsSortedByName()),
		new ShellPrompt.CommandLine("Afficher les étudiants de plus de 20 ans", () -> DumpStudentsOver20()),
		new ShellPrompt.CommandLine("Ajouter un étudiant", () -> createStudent()),
		new ShellPrompt.CommandLine("Supprimer un étudiant", () -> DeleteStudent()),
		new ShellPrompt.CommandLine("Enregistrer le fichier", () -> saveFile()),
		new ShellPrompt.CommandLine("Quitter", () -> shell.exitShell())
	};

	private static void DumpStudentsOver20() {
		System.out.println("Liste des étudiants de plus de 20 ans:");
		Stream<Etudiant> st = etudiants.stream();
		st.filter(e -> e.getAge() > 20)
		  .forEach(e -> System.out.println(e.toString()));

		System.out.println();
	}

	private static void DumpAllStudents() {

		System.out.println("Liste de tous les étudiants:");
		etudiants.forEach(e -> System.out.println(e.toString()));
		System.out.println();
	}

	private static void DumpAllStudentsSortedByName() {

		System.out.println("Liste de tous les étudiants triés:");

		Stream<Etudiant> st = etudiants.stream();
		st.sorted((e1, e2) -> e1.getNom().compareTo(e2.getNom()))
		  .forEach(e -> System.out.println(e.toString()));

		System.out.println();
	}

	private static void createStudent() {
		String name;
		int age;
		String niveau;
		System.out.print("Entrez le nom de l'étudiant: ");
		name = shell.getInput();
		System.out.print("Entrez l'âge de l'étudiant: ");
		try {
			age = Integer.parseInt(shell.getInput());
		} catch (NumberFormatException e) {
			System.out.println("Âge invalide. Opération annulée.");
			return;
		};
		System.out.print("Entrez le niveau de l'étudiant: ");
		niveau = shell.getInput();
		int newId = etudiants.stream()
		                    .mapToInt(Etudiant::getId)
		                    .max()
		                    .orElse(0) + 1;
		Etudiant newStudent = new Etudiant(name, age, newId, niveau);
		etudiants.add(newStudent);
		System.out.println("Étudiant ajouté avec succès: " + newStudent.toString());
	}

	private static void DeleteStudent() {

		DumpAllStudents();
		System.out.println("choisissez l'étudiant à supprimer (entrez l'ID): ");
		String inputStr = shell.getInput();
		try {
			int studentId = Integer.parseInt(inputStr);
			Etudiant toRemove = null;
			for (Etudiant e : etudiants) {
				if (e.getId() == studentId) {
					toRemove = e;
					break;
				}
			}
			if (toRemove != null) {
				etudiants.remove(toRemove);
				System.out.println("Étudiant supprimé avec succès.");
			} else {
				System.out.println("Aucun étudiant trouvé avec l'ID spécifié.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Veuillez entrer un numéro valide.");
		}
	}

	private static String formatAvailableFormats() {
		StringBuilder sb = new StringBuilder();
		String[] formats = IOStudentManager.getAvailableFormats();
		sb.append("(");
		sb.append(formats[0]);
		for (int i = 1; i < formats.length; i++) {
			sb.append(", " + formats[i]);
		}
		sb.append(")");
		return sb.toString();
	}

	private static void saveFile() {

		System.out.print("Choisissez un format " + formatAvailableFormats() + ": ");
		String formatInput = shell.getInput();


		System.out.print("Nom du fichier à enregistrer: (" + filePath + "): ");
		String outputPath = shell.getInput();
		if (outputPath.isEmpty()) {
			outputPath = filePath;
		}

		boolean success = IOStudentManager.safeSaveToFile(outputPath, etudiants, formatInput);

		if (success) {
			System.out.println("Fichier enregistré avec succès.");
		} else {
			System.out.println("Échec de l'enregistrement du fichier.");
		}
	}

	private static void loadFile(String[] args) {

		System.out.println("Gestion des étudiants");
		System.out.println("---------------------");

		System.out.println("Chargement des étudiants depuis le fichier: " + filePath);

		if (args.length == 1) {
			filePath = args[0];
		}

		String content = IOStudentManager.safeOpenFile(filePath);
		if (content == null) {
			return;
		}
		etudiants = IOStudentManager.safeParseFile(content);
		if (etudiants == null) {
			return;
		}
	}
	
	public static void main(String[] args) {
		loadFile(args);
		for (ShellPrompt.CommandLine cmd : commands) {
			shell.addCommand(cmd);
		}

		shell.runShellLoop();

	}
}
