package src.GestionEtudiants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShellPrompt {

	private final List<CommandLine> commands = new ArrayList<>();
	private int shellRunning = 1;

	public static class CommandLine {
		private final String description;
		private final Runnable action;
		
		public CommandLine(String description, Runnable action) {
			this.description = description;
			this.action = action;
		}
		public String getDescription() {
			return description;
		}
		public void execute() {
			action.run();
		}
	}


	public void addCommand(CommandLine command) {
		commands.add(command);
	}

	public void addCommand(String description, Runnable action) {
		commands.add(new CommandLine(description, action));
	}

	public void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void printMenu() {

		System.out.println("Menu des commandes:");
		int index = 1;
		for (CommandLine cmd : commands) {
			System.out.println(index + ". " + cmd.getDescription());
			index++;
		}

		System.out.print("Entrez le numéro de la commande à exécuter: ");
	}

	public String getInput() {
		byte[] inputBuffer = new byte[100];
		try {
			int bytesRead = System.in.read(inputBuffer);
			if (bytesRead <= 0) {
				System.out.println("Erreur de lecture de l'entrée.");
				return null;
			}
			return new String(inputBuffer, 0, bytesRead).trim();
		} catch (IOException e) {
			System.out.println("Erreur de lecture de l'entrée: " + e.getMessage());
			return null;
		}
	}

	public void runShellLoop() {
		while (shellRunning == 1) {
			printMenu();
			try {
				String inputStr = getInput();
				int commandIndex = Integer.parseInt(inputStr) - 1;
				if (commandIndex < 0 || commandIndex >= commands.size()) {
					System.out.println("Commande invalide. Veuillez réessayer.");
					continue;
				}
				clearConsole();
				commands.get(commandIndex).execute();
			} catch (NumberFormatException e) {
				System.out.println("Veuillez entrer un numéro valide.");
			}
		}
	}

	public void exitShell() {
		shellRunning = 0;
	}
}
