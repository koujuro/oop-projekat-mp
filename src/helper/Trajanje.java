package helper;

import Muzika.Pesme;

import java.util.ArrayList;

public class Trajanje {
    private int sekunde;
    private int minute;
    private int sati;

    public Trajanje(int sati, int minute, int sekunde){
        this.sati = sati;
        this.minute = minute;
        this.sekunde = sekunde;
    }

    public static Trajanje parsirajVreme(String vreme){
        String[] tmpNiz = vreme.split(":");
        return new Trajanje(Integer.parseInt(tmpNiz[0]), Integer.parseInt(tmpNiz[1]), Integer.parseInt(tmpNiz[2]));
    }

    public static ArrayList<Trajanje> listaTrajanjaPesamaZaDatePesmeIzAlbuma(ArrayList<Pesme> listaPesamaIzAlbuma){
        ArrayList<Trajanje> listaTrajanjaPesama = new ArrayList<Trajanje>();
        for(Pesme p: listaPesamaIzAlbuma)
            listaTrajanjaPesama.add(Trajanje.parsirajVreme(p.getTrajanje()));

        return listaTrajanjaPesama;
    }

    public static String saberiIVratiVreme(ArrayList<Trajanje> trajanje){
        int sati = 0, minuti = 0, sekunde = 0;
        for(Trajanje t : trajanje){
            sati += t.sati;
            minuti += t.minute;
            sekunde += t.sekunde;

            if(sekunde >= 60){
                minuti++;
                sekunde = sekunde % 60;
            }
            if(minuti >= 60){
                sati++;
                minuti = minuti % 60;
            }
        }
        return proveriSateMinuteSekunde(sati) + ":" + proveriSateMinuteSekunde(minuti)
                + ":" + proveriSateMinuteSekunde(sekunde);

    }

    private static String proveriSateMinuteSekunde(int vrednost){
        if(vrednost == 0)
            return "00";
        else if(vrednost < 10)
            return "0" + vrednost;
        else return "" + vrednost;
    }

    public String toString(){
        return sati + ":" + minute + ":" + sekunde;
    }
}
