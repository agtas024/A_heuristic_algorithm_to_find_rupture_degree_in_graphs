package heuristic_rupture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Heuristic_Rupture {

    public ArrayList<ArrayList<Integer>> list = new ArrayList<>();

    public int Rupture(int[][] G) {
        ArrayList<Integer> S = new ArrayList();
        ArrayList<Integer> V2 = new ArrayList();
        int[][] G2 = new int[G.length][G.length];
        G2 = Klonlama(G);
        V2 = Dugumler(G);
        while (true) {
            int[] degree = new int[G2.length];
            for (int i = 0; i < V2.size(); i++) {
                degree[i] = Dereceler(G2, i);//i düğümünün dereceleri eklenir
            }

            ArrayList<Integer> Possible_S = new ArrayList();
            for (int i = 0; i < degree.length; i++) {
                if (degree[i] > 1) {
                    Possible_S.add(i);
                }
            }
            if (Possible_S.isEmpty()) {
                break;
            }
            ArrayList<Double> value = new ArrayList();
            for (int i = 0; i < Possible_S.size(); i++) {
                double sayi = UsAlmak(degree[i], 3) / UsAlmak(Hesap(G2, degree, i), 2);
                value.add(sayi);
            }
            double t = Collections.max(value);//max degeri t ye at
            int v = value.indexOf(t);//t nin olduğu indexi v ye at
            S.add(v);
            V2.remove(v);
            G2 = DugumSil(G2, v);
        }
        G2 = new int[G.length - S.size()][G.length - S.size()];
        G2 = TempGraf(G, S);
        int w = new Bilesen_Sayisi(G2.length).Islem(G2);
        int s = S.size();
        int m = new Enb_Dugum().Islem(G2);
        int rupture = w - s, rupture2;
        rupture = rupture - m;
        for (int i = 0; i < S.size(); i++) {
            int temp = S.get(0);
            S.remove(0);
            G2 = new int[G.length - S.size()][G.length - S.size()];
            G2 = TempGraf(G, S);
            w = new Bilesen_Sayisi(G2.length).Islem(G2);
            s = S.size();
            m = new Enb_Dugum().Islem(G2);
            rupture2 = w - s;
            rupture2 = rupture2 - m;
            if (rupture2 > rupture) {
                rupture = rupture2;
            } else {
                S.add(temp);
            }
        }
        return rupture;
    }

    public int[][] Klonlama(int[][] G) {
        int G2[][] = new int[G.length][G.length];
        for (int i = 0; i < G.length; i++) {
            System.arraycopy(G[i], 0, G2[i], 0, G.length);//asıl grafta bozulmalar olmaması için geçici grafa klon için yapıldı
        }
        return G2;
    }

    public ArrayList<Integer> Dugumler(int[][] G) {
        ArrayList<Integer> V2 = new ArrayList();
        for (int i = 0; i < G.length; i++) {
            V2.add(i);
        }
        return V2;
    }

    public int Dereceler(int[][] G2, int vertex) {
        int derece = 0;
        for (int i = 0; i < G2.length; i++) {
            if (G2[vertex][i] == 1) {
                derece++;
            }
        }
        return derece;
    }

    public int Hesap(int[][] G2, int[] degree, int deger) {// deger düğümünün komşu düğümlerinin derece toplamı
        ArrayList<Integer> Komsu = new ArrayList();
        int toplam_komsu_dereceler = 0;
        for (int i = 0; i < G2.length; i++) {
            if (G2[deger][i] == 1) {
                Komsu.add(i);
            }
        }
        while (Komsu.size() > 0) {
            toplam_komsu_dereceler += degree[Komsu.get(0)];
            Komsu.remove(0);
        }
        return toplam_komsu_dereceler;
    }

    public double UsAlmak(int as, int us) {
        double sonuc = (int) Math.pow(as, us);
        return sonuc;
    }

    public int[][] DugumSil(int[][] G2, int vertex) {
        //bu fonksiyon asıl graftan atılacak düğümü siler ve yeni graf dizisini döndürür
        int d[][] = new int[G2.length - 1][G2.length - 1];
        ArrayList<ArrayList<Integer>> list;
        list = new ArrayList<>();
        for (int[] G1 : G2) {
            list.add(new ArrayList<>());
        }
        for (int i = 0; i < G2.length; i++) {
            for (int j = 0; j < G2.length; j++) {
                list.get(i).add(G2[i][j]);//parametre grafındaki tüm elemanları listeye ekledik
            }
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).remove(vertex);
        }
        list.remove(vertex);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                d[i][j] = list.get(i).get(j);
            }
        }
        return d;//döndür yeni grafı
    }

    public int[][] TempGraf(int[][] G, ArrayList<Integer> deger) {
        int G2[][] = new int[G.length - deger.size()][G.length - deger.size()];
        for (int[] G1 : G) {//liste içinde liste tuttuk
            list.add(new ArrayList<>());
        }
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[i].length; j++) {
                list.get(i).add(G[i][j]);
            }
        }
        Collections.sort(deger, Collections.reverseOrder());
        for (int i = 0; i < deger.size(); i++) {
            int temp = deger.get(i);
            list.remove(temp);
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < deger.size(); j++) {
                int temp = deger.get(j);
                list.get(i).remove(temp);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                G2[i][j] = list.get(i).get(j);
            }
        }
        list.clear();
        return G2;
    }

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in); 
        /*
        int[][] graph = new int[][]{
            {0, 1, 1, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 0, 0},
            {1, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 1, 0, 1, 1},
            {0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0}
        };
        int P4[][] = new int[][]{//P4
            {0, 1, 0, 0},
            {1, 0, 1, 0},
            {0, 1, 0, 1},
            {0, 0, 1, 0}
        };
        int K4[][] = new int[][]{ //K4
            {0, 1, 1, 1},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {1, 1, 1, 0}};

        int R = new Heuristic_Rupture().Rupture(graph);
        System.out.println("Örnek Graf r(G) = " + R);

        int R1 = new Heuristic_Rupture().Rupture(P4);
        System.out.println("P4 Graf r(G) = " + R1);

        int R2 = new Heuristic_Rupture().Rupture(K4);
        System.out.println("K4 Graf r(G) = " + R2);
         */
        System.out.println("Grafınıza ait düğüm sayısını giriniz...");
        int dugumSayisi = k.nextInt();
        int Graf[][] = new int[dugumSayisi][dugumSayisi];
        for (int i = 0; i < dugumSayisi; i++) {
            for (int j = 0; j < dugumSayisi; j++) {
                System.out.print(i + " düğümü ile " + j + " düğümü komşu ise 1 değilse 0 giriniz : ");
                Graf[i][j] = k.nextInt();
            }
        }
        long StartTime = System.currentTimeMillis();
        int R = new Heuristic_Rupture().Rupture(Graf);
        System.out.println("K4 Graf r(G) = " + R);

        long EndTime = System.currentTimeMillis();
        long EstTime = StartTime - EndTime;
        double seconds = (double) EstTime / 1000;
        System.out.println("Algoritmanın çalışmasında geçen toplam süre : " + -seconds + " saniye");
    }

}
