package src;
import java.util.Arrays;
import java.util.stream.Stream;

public class CalculStat {
    public static void main(String[] args) {

        String input = System.console().readLine("Entrez les nombres: ");
        String[] strings = input.split(" ");
        Stream<String> st = Arrays.stream(strings);
        Integer[] numbers;
        try {
            numbers = st.map(s -> Integer.parseInt(s)).toArray(Integer[]::new);
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez entrer des nombres entiers séparés par des espaces.");
            return;
        }

        if (numbers.length == 0) {
            System.out.println("Aucun nombre fourni.");
            return;
        }

        int sum = Arrays.stream(numbers).mapToInt(Integer::intValue).sum();
        double average = (double) sum / numbers.length;
        int max = Arrays.stream(numbers).mapToInt(Integer::intValue).max().getAsInt();
        int min = Arrays.stream(numbers).mapToInt(Integer::intValue).min().getAsInt();
        int[] sortedNumbers = Arrays.stream(numbers).mapToInt(Integer::intValue).sorted().toArray();

        System.out.println("Somme: " + sum);
        System.out.println("Moyenne: " + average);
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);
        System.out.println("tri: " + Arrays.toString(sortedNumbers));

        return;
    }
}
