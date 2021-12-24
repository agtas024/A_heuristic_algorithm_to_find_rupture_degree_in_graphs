package heuristic_rupture;

public class Enb_Dugum {

    public int Islem(int[][] G) {
        int en_Buyuk_Bilesenin_Dugum_Sayisi = Integer.MIN_VALUE;
        int sayac;
        for (int[] G1 : G) {
            sayac = 0;
            for (int j = 0; j < G1.length; j++) {
                if (G1[j] == 1) {
                    sayac++;
                }
            }
            if (sayac > en_Buyuk_Bilesenin_Dugum_Sayisi) {
                en_Buyuk_Bilesenin_Dugum_Sayisi = sayac;
            }
        }
        return (en_Buyuk_Bilesenin_Dugum_Sayisi + 1);
    }
}
