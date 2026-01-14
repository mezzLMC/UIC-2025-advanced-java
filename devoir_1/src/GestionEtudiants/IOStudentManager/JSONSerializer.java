package src.GestionEtudiants.IOStudentManager;

public class JSONSerializer implements IOStudentManager.Serializer {

	@Override
	public String getFormatName() {
		return "JSON";
	}

	@Override
	public String serialize(java.util.List<src.Etudiant> etudiants) {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		for (int i = 0; i < etudiants.size(); i++) {
			src.Etudiant e = etudiants.get(i);
			sb.append("  {\n");
			sb.append("    \"id\": ").append(e.getId()).append(",\n");
			sb.append("    \"nom\": \"").append(e.getNom()).append("\",\n");
			sb.append("    \"age\": ").append(e.getAge()).append(",\n");
			sb.append("    \"niveau\": \"").append(e.getNiveau()).append("\"\n");
			sb.append("  }");
			if (i < etudiants.size() - 1) {
				sb.append(",");
			}
			sb.append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}	
}
