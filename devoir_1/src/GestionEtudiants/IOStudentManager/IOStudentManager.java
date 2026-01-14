package src.GestionEtudiants.IOStudentManager;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import src.Etudiant;

public class IOStudentManager {

	public interface Serializer {
		String serialize(List<Etudiant> etudiants);
		String getFormatName();
	}

	private static List<Serializer> serializers = new ArrayList<>(
		List.of(
			new DefaultTxtSerializer(),
			new CSVSerializer(),
			new JSONSerializer()
		)
	);

	public static String[] getAvailableFormats() {
		String[] formats = new String[serializers.size()];
		for (int i = 0; i < serializers.size(); i++) {
			formats[i] = serializers.get(i).getFormatName();
		}
		return formats;
	}

	public static String safeOpenFile(String filePath) {
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

	public static List<Etudiant> safeParseFile(String content) {

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
			if (parts.length != 4) {
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
		etudiants.sort((e1, e2) -> Integer.compare(e1.getId(), e2.getId()));

		return etudiants;
	}

	public static String serializeStudents(List<Etudiant> etudiants) {
		StringBuilder sb = new StringBuilder();
		for (Etudiant e : etudiants) {
			sb.append(e.getId()).append(";")
			  .append(e.getNom()).append(";")
			  .append(e.getAge()).append(";")
			  .append(e.getNiveau()).append("\n");
		}
		return sb.toString();
	}

	public static String serializeStudentsJSON(List<Etudiant> etudiants) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n  \"etudiants\": ");
		sb.append("[\n");
		for (int i = 0; i < etudiants.size(); i++) {
			Etudiant e = etudiants.get(i);
			sb.append("  {\n")
			  .append("    \"id\": ").append(e.getId()).append(",\n")
			  .append("    \"nom\": \"").append(e.getNom()).append("\",\n")
			  .append("    \"age\": ").append(e.getAge()).append(",\n")
			  .append("    \"niveau\": \"").append(e.getNiveau()).append("\"\n")
			  .append("  }");
			if (i < etudiants.size() - 1) {
				sb.append(",");
			}
			sb.append("\n");
		}
		sb.append("]\n");
		sb.append("}\n");
		return sb.toString();
	}

	public static boolean safeSaveToFile(String filePath, List<Etudiant> etudiants, String format) {
		Path path = Paths.get(filePath);
		try {
			Files.writeString(path, 
				serializers.stream()
					.filter(s -> s.getFormatName().equalsIgnoreCase(format))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Unsupported format: " + format))
					.serialize(etudiants)
			);
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
			return false;
		}
		return true;
	}
}
