public class Exo1Thread {

    static class MonThread extends Thread {
        private final String nom;

        public MonThread(String nom) {
            this.nom = nom;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread " + nom + " - Iteration " + i);
                try {

                    int tempsAttente = 100 + (int) (Math.random() * 400);
                    Thread.sleep(tempsAttente);
                } catch (InterruptedException e) {
                    System.err.println("Thread " + nom + " interrompu !");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Thread " + nom + " terminé.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercice 1 : Héritage Thread ===\n");
        System.out.println("Lancement de 3 threads (A, B, C) simultanément...\n");

        MonThread threadA = new MonThread("A");
        MonThread threadB = new MonThread("B");
        MonThread threadC = new MonThread("C");

        threadA.start();
        threadB.start();
        threadC.start();

        System.out.println("Les 3 threads ont été lancés !");
        System.out.println("Observez que l'ordre d'exécution est non-déterministe.\n");
    }
}
