import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Exo3Callable {


    static class Calcul implements Callable<Integer> {
        private final int n;

        public Calcul(int n) {
            this.n = n;
        }

        @Override
        public Integer call() throws Exception {
            String nomThread = Thread.currentThread().getName();
            System.out.println("Calcul de la somme de 1 à " + n + " sur " + nomThread);
            
            int somme = 0;
            for (int i = 1; i <= n; i++) {
                somme += i;

                Thread.sleep(50);
            }
            
            System.out.println("Calcul terminé pour N=" + n + " -> Résultat: " + somme);
            return somme;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercice 3 : Callable & Future ===\n");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<Integer>> taches = new ArrayList<>();
        taches.add(new Calcul(5));   // Somme de 1 à 5  = 15
        taches.add(new Calcul(10));  // Somme de 1 à 10 = 55
        taches.add(new Calcul(15));  // Somme de 1 à 15 = 120

        System.out.println("Lancement simultané des calculs avec invokeAll()...\n");

        try {

            List<Future<Integer>> resultats = executor.invokeAll(taches);

            System.out.println("\n--- Récupération des résultats via Future ---\n");

            int[] valeursN = {5, 10, 15};

            for (int i = 0; i < resultats.size(); i++) {
                Future<Integer> future = resultats.get(i);
                int resultat = future.get(); // Récupère le résultat (bloquant si pas encore prêt)
                System.out.println("Somme de 1 à " + valeursN[i] + " = " + resultat);
            }

        } catch (InterruptedException e) {
            System.err.println("Exécution interrompue !");
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.err.println("Erreur lors de l'exécution : " + e.getCause().getMessage());
        } finally {

            executor.shutdown();
        }

        System.out.println("\n=== Tous les calculs sont terminés ===");
    }
}
