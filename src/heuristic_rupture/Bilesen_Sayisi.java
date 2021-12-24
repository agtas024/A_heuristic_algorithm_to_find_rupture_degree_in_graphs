package heuristic_rupture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Bilesen_Sayisi {

    private final int dugumSayisi;

    private final LinkedList<Integer>[] komsuluk;

    ArrayList<ArrayList<Integer>> liste
            = new ArrayList<>();

    @SuppressWarnings("unchecked")
    Bilesen_Sayisi(int dugumSayisi) {
        this.dugumSayisi = dugumSayisi;
        komsuluk = new LinkedList[dugumSayisi];

        for (int i = 0; i < dugumSayisi; i++) {
            komsuluk[i] = new LinkedList();
        }
    }

    public void ayritEkle(int dugum1, int dugum2) {
        komsuluk[dugum1].add(dugum2);
        komsuluk[dugum2].add(dugum1);
    }

    public void DFSUtil(int dugumNo, boolean[] ziyaret,
            ArrayList<Integer> list) {
        ziyaret[dugumNo] = true;
        list.add(dugumNo);
        Iterator<Integer> it = komsuluk[dugumNo].iterator();

        while (it.hasNext()) {
            int n = it.next();
            if (!ziyaret[n]) {
                DFSUtil(n, ziyaret, list);
            }
        }
    }

    public void DFS() {
        boolean[] ziyaret = new boolean[dugumSayisi];

        for (int i = 0; i < dugumSayisi; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            if (!ziyaret[i]) {
                DFSUtil(i, ziyaret, list);
                liste.add(list);
            }
        }
    }

    public int bilesenSayisi() {
        return liste.size();
    }

    public int Islem(int[][] G) {
        Bilesen_Sayisi g = new Bilesen_Sayisi(G.length);
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G[i].length; j++) {
                if (G[i][j] == 1) {//okunan grafın ayrıtlarını ekledik
                    g.ayritEkle(i, j);
                }
            }
        }
        g.DFS();
        return (g.bilesenSayisi());
    }
}
