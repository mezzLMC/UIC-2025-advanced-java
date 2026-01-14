import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exo4Pool {

    static class TacheLongue implements Runnable {
        private final int numeroTache;

        public TacheLongue(int numeroTache) {
            this.numeroTache = numeroTache;
        }

        @Override
        public void run() {
            String nomThread = Thread.currentThread().getName();
            System.out.println("[DEBUT] Tâche " + numeroTache + " démarrée sur " + nomThread);
            
            try {

                int duree = 500 + (int) (Math.random() * 1000);
                System.out.println("        Tâche " + numeroTache + " travaille pendant " + duree + "ms...");
                Thread.sleep(duree);
            } catch (InterruptedException e) {
                System.err.println("Tâche " + numeroTache + " interrompue !");
                Thread.currentThread().interrupt();
            }
            
            System.out.println("[FIN]   Tâche " + numeroTache + " terminée sur " + nomThread);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercice 4 : ThreadPool ===\n");
        System.out.println("Création d'un pool de 3 threads pour exécuter 7 tâches.\n");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        System.out.println("Soumission des 7 tâches au pool...\n");
        for (int i = 1; i <= 7; i++) {
            pool.submit(new TacheLongue(i));
        }

        System.out.println("\nToutes les tâches ont été soumises.");
        System.out.println("Le pool de 3 threads va les exécuter progressivement.\n");

        pool.shutdown();
        System.out.println("pool.shutdown() appelé - Plus de nouvelles tâches acceptées.");

        try {

            System.out.println("Attente de la terminaison de toutes les tâches...\n");
            
            boolean end = pool.awaitTermination(60, TimeUnit.SECONDS);
            
            if (end) {
                System.out.println("\n=== Toutes les tâches sont terminées proprement ! ===");
            } else {
                System.out.println("\n=== Timeout atteint - Certaines tâches n'ont pas fini ===");
                pool.shutdownNow();
            }
            
        } catch (InterruptedException e) {
            System.err.println("Attente interrompue !");
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("\nProgramme terminé.");
    }
}
