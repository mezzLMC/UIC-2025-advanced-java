public class Exo2Runnable {

    static class MaTache implements Runnable {
        private final int numeroTache;

        public MaTache(int numeroTache) {
            this.numeroTache = numeroTache;
        }

        @Override
        public void run() {
            String nomThread = Thread.currentThread().getName();
            System.out.println("Tâche " + numeroTache + " démarrée sur " + nomThread);
            
            try {

                for (int i = 1; i <= 3; i++) {
                    System.out.println("Tâche " + numeroTache + " - Étape " + i + "/3");
                    Thread.sleep(200 + (int) (Math.random() * 300));
                }
            } catch (InterruptedException e) {
                System.err.println("Tâche " + numeroTache + " interrompue !");
                Thread.currentThread().interrupt();
            }
            
            System.out.println("Tâche " + numeroTache + " terminée.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercice 2 : Interface Runnable ===\n");
        System.out.println("Lancement de 5 tâches en parallèle...\n");

        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(new MaTache(i), "Thread-" + i);
            thread.start();
        }

        System.out.println("\n>>> Le thread MAIN continue son exécution !");
        System.out.println(">>> Les 5 tâches s'exécutent en arrière-plan...");
        System.out.println(">>> Fin du main() - mais les threads continuent !\n");
    }
}
