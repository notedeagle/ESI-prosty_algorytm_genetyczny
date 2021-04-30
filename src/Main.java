import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Random random = new Random();
        Scanner in = new Scanner(System.in);

        int a = 4;
        int b = 7;
        int c = 2;
        int ile_wyn = 40;

        int lb_pop, ile_os;
        double pr_krzyz, pr_mut;

        System.out.println("Podaj liczbę populacji:");
        lb_pop = in.nextInt();
        System.out.println("Podaj liczbę osób w populacji:");
        ile_os = in.nextInt();
        System.out.println("Podaj prawopodobieństwo krzyżowania:");
        pr_krzyz = in.nextDouble();
        System.out.println("Podaj prawopodobieństwo mutacji:");
        pr_mut = in.nextDouble();

        int[][] osobniki = new int[lb_pop][ile_os];

        for (int i = 0; i < lb_pop; i++) {
            for (int j = 0; j < ile_os; j++) {
                osobniki[i][j] = random.nextInt(255);
            }
        }

        Algorytm algorytm = new Algorytm(a, b, c, lb_pop, ile_os, pr_krzyz, pr_mut);

        if (lb_pop * ile_os <= 150) {
            algorytm.start(osobniki, ile_wyn);
            algorytm.zapiszPlik();
        } else {
            System.out.println("Przekroczono ogranicznie globalnej liczby przetworzonych w algorytmie osobników");
        }
    }
}
