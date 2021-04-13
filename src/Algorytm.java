import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Algorytm {

    Random random = new Random();

    int a, b, c, lb_pop, ile_os;
    double pr_krzyz, pr_mut;
    String doZapisu = "";

    public Algorytm(int a, int b, int c, int lb_pop, int ile_os, double pr_krzyz, double pr_mut) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.lb_pop = lb_pop;
        this.ile_os = ile_os;
        this.pr_krzyz = pr_krzyz;
        this.pr_mut = pr_mut;
    }

    public void start(int[][] osobniki, int ile_wyn) {

        List<String> osobnikA = new ArrayList<>();
        List<String> osobnikB = new ArrayList<>();
        List<String> nowe = new ArrayList<>();
        List<String> najlepszeZPopulacji = new ArrayList<>();
        String najlepszy, najlepszyWynik;


        for (int i = 0; i < osobniki.length - 1; i++) {

            laczPary(osobniki, osobnikA, osobnikB, i);
            krzyzowanie(pr_krzyz, osobnikA, osobnikB);
            nowe.addAll(mutacja(pr_mut, osobnikA, osobnikB));

            osobnikA.clear();
            osobnikB.clear();

            najlepszeZPopulacji.add(najlepszyZPopulacji(nowe));
        }

        najlepszy = najlepszyZPopulacji(najlepszeZPopulacji);

        int bestX = Integer.parseInt(najlepszy, 2);
        int best = (a * bestX * bestX) + (b * bestX) + c;

        najlepszyWynik = best + " " + bestX + "\n";
        doZapisu += najlepszyWynik;

        System.out.println(najlepszyWynik);
        ile_wyn--;

        if(ile_wyn > 0)
            start(noweOsobniki(lb_pop, ile_os), ile_wyn);
    }

    public int[][] noweOsobniki(int lb_pop, int ile_os) {
        Random random = new Random();
        int[][] osobniki = new int[lb_pop][ile_os];

        for (int i = 0; i < lb_pop; i++) {
            for (int j = 0; j < ile_os; j++) {
                osobniki[i][j] = random.nextInt(255);
            }
        }
        return osobniki;
    }

    public void laczPary(int[][] osobniki, List<String> osobnikA, List<String> osobnikB, int i) {
        for(int j = 0; j < ile_os / 2; j++) {
            osobnikA.add(String.format("%8s", Integer.toBinaryString(osobniki[i][j])).replace(" ", "0"));
            osobnikB.add(String.format("%8s", Integer.toBinaryString(osobniki[i][ile_os - j - 1])).replace(" ", "0"));
        }
    }

    public void krzyzowanie(double pr_krzyz, List<String> osobnikA, List<String> osobnikB) {

        for (int i = 0; i < osobnikA.size(); i++) {
            double w_lb_psl = random.nextDouble();

            if (w_lb_psl <= pr_krzyz) {
                int pc = random.nextInt(7) + 1;
                String a, b, after1, after2;

                a = osobnikA.get(i);
                b = osobnikB.get(i);

                after1 = a.substring(0, pc) + b.substring(pc);
                after2 = b.substring(0, pc) + a.substring(pc);

                osobnikA.remove(a);
                osobnikB.remove(b);

                osobnikA.add(after1);
                osobnikB.add(after2);
            }
        }
    }

    public List<String> mutacja(double pr_mut, List<String> osobnikA, List<String> osobnikB) {

        List<String> nowaPopulacja = new ArrayList<>();

        for (int i = 0; i < osobnikA.size(); i++) {
            double w_lb_psl = random.nextDouble();

            if (w_lb_psl <= pr_mut) {
                String a, b;

                a = osobnikA.get(i);
                b = osobnikB.get(i);

                osobnikA.remove(a);
                osobnikB.remove(b);

                a = a.replace("1", "2");
                a = a.replace("0", "1");
                a = a.replace("2", "0");

                b = b.replace("1", "2");
                b = b.replace("0", "1");
                b = b.replace("2", "0");

                osobnikA.add(a);
                osobnikB.add(b);
            }
        }

        for (int i = 0; i < ile_os; i++) {
            nowaPopulacja.addAll(selekcja(osobnikA, osobnikB));
        }

        return nowaPopulacja;
    }

    public List<String> selekcja(List<String> osobnikA, List<String> osobnikB) {
        List<String> populacja = new ArrayList<>();
        List<String> nowaPopulacja = new ArrayList<>();
        List<Double> prawdopodobienstwa = new ArrayList<>();
        int x;
        double prawdop, sumaKwadratow = 0, pObl = 0;
        String osobnik = "";

        double y = random.nextDouble();

        populacja.addAll(osobnikA);
        populacja.addAll(osobnikB);

        for (String osobnik2 : populacja) {
            x = Integer.parseInt(osobnik2, 2);
            sumaKwadratow += (a * x * x) + (b * x) + c;
        }

        for (String osobnik2 : populacja) {
            x = Integer.parseInt(osobnik2, 2);
            prawdop = ((a * x * x) + (b * x) + c) / sumaKwadratow;
            prawdopodobienstwa.add(prawdop);
        }

        for (int i = 0; i < populacja.size(); i++) {
            prawdop = prawdopodobienstwa.get(i);
            osobnik = populacja.get(i);

            if (y >= pObl && y < prawdop + pObl) {
                nowaPopulacja.add(osobnik);
            }
            pObl += prawdop;
        }

        prawdopodobienstwa.clear();

        return nowaPopulacja;
    }

    public String najlepszyZPopulacji(List<String> populacja) {

        int x, kwadrat, max = 0;
        String najlepszy = "";

        for (String osobnik : populacja) {
            x = Integer.parseInt(osobnik, 2);
            kwadrat = (a * x * x) + (b * x) + c;

            if (kwadrat > max) {
                max = kwadrat;
                najlepszy = osobnik;
            }
        }
        return najlepszy;
    }

    public void zapiszPlik() throws FileNotFoundException {
        PrintWriter zapis = new PrintWriter("wynik.txt");
        zapis.println(doZapisu);
        zapis.close();
    }
}
