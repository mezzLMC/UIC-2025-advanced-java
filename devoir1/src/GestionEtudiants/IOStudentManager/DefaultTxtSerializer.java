package src.GestionEtudiants.IOStudentManager;

public class DefaultTxtSerializer implements IOStudentManager.Serializer {
	@Override
	public String getFormatName() {
		return "texte";
	}

	@Override
	public String serialize(java.util.List<src.Etudiant> etudiants) {
		StringBuilder sb = new StringBuilder();
		for (src.Etudiant e : etudiants) {
			sb.append(e.getId()).append(";");
			sb.append(e.getNom()).append(";");
			sb.append(e.getAge()).append(";");
			sb.append(e.getNiveau()).append("\n");
		}
		return sb.toString();
	}
}
