package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class GestionEtudiants {

	private static String filePath = "assets/students.txt";
	private static List<Etudiant> etudiants;

	private static String safeOpenFile(String filePath) {
		Path path = Paths.get(filePath);
		if (!Files.exists(path)) {
			System.err.println("File not found: " + filePath);
			return null;
		}

		String content = null;
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		};

		return content;
	}

	private static List<Etudiant> safeParseFile(String content) {

		if (content == null || content.isEmpty()) {
			System.err.println("File content is empty or null.");
			return null;
		}

		String[] lines = content.split("\n");
		if (lines.length == 0) {
			System.err.println("No lines found in file content.");
			return null;
		}

		List<Etudiant> etudiants = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split(";");
			if (parts.length != 5) {
				System.err.println("Invalid line format: " + line);
				continue;
			}
			try {
				int id = Integer.parseInt(parts[0]);
				String name = parts[1];
				int age = Integer.parseInt(parts[2]);
				String level = parts[3];

				Etudiant etudiant = new Etudiant(name, age, id, level);
				etudiants.add(etudiant);
			} catch (NumberFormatException e) {
				System.err.println("Error parsing line: " + line + " - " + e.getMessage());
				return null;
			}
		}

		return etudiants;
	}

	private static void DumpStudentsOver20() {
		System.out.println("Liste des étudiants de plus de 20 ans:");
		Stream<Etudiant> st = etudiants.stream();
		st.filter(e -> e.getAge() > 20)
		  .forEach(e -> System.out.println(e.toString()));
	}
	
	static void main(String[] args) {

		System.out.println("Gestion des étudiants");
		System.out.println("---------------------");

		System.out.println("Chargement des étudiants depuis le fichier: " + filePath);

		if (args.length == 1) {
			filePath = args[0];
		}

		String content = safeOpenFile(filePath);
		if (content == null) {
			return;
		}
		etudiants = safeParseFile(content);
		if (etudiants == null) {
			return;
		}

		DumpStudentsOver20();
	
	}
}
