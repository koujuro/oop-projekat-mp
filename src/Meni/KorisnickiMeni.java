package Meni;

import Exceptions.IzvodjacException;
import Korisnici.Korisnici;
import Muzika.Izvodjaci;
import Muzika.KupljenePesme;
import Muzika.KupljeniAlbumi;
import Unos.Unos;

public class KorisnickiMeni implements Unos{

    public static void korisnickiMeni(Korisnici korisnik) throws Exception{
        ispisOpcija();
        int opcija = Unos.unosOpcije();
        switch(opcija){
            case 1:
                korisnik.ispisKupljenihAlbumaIPesama();
                break;
            case 2:
                try{
                    Izvodjaci izvodjac = Izvodjaci.dohvatiIzvodjacaPoImenuIzBaze(Unos.unosImenaIzvodjaca());
                    if(izvodjac == null) throw new IzvodjacException();
                    System.out.println(izvodjac + "\n\n");
                    while (true){
                        drugiKorisnickiMeni(korisnik, izvodjac);
                    }
                } catch (IzvodjacException e){
                    System.err.println(e.getMessage());
                    korisnickiMeni(korisnik);
                }
                break;
            case 3:
                KupljenePesme.kupovinaPesme(korisnik);
                break;
            case 4:
                KupljeniAlbumi.kupovinaAlbuma(korisnik);
                break;
            case 5:
                korisnik = null;
                System.out.println("\nUspesno ste se odjavili\n");
                GlavniMeni.glavniMeni();
                break;
            default:
        }
    }

    public static void drugiKorisnickiMeni(Korisnici korisnik, Izvodjaci izvodjac) throws Exception{
        ispisOpcijaZaDrugiMeni();
        int opcija = Unos.unosOpcije();
        switch (opcija){
            case 1:
                Korisnici.ispisPesamaIAlbumaZaIdIzvodjaca(izvodjac.getIdIzvodjaca());
                break;
            case 2:
                KupljenePesme.kupovinaPesme(korisnik);
                break;
            case 3:
                KupljeniAlbumi.kupovinaAlbuma(korisnik);
                break;
            case 4:
                korisnickiMeni(korisnik);
            default:
                System.err.println("Pogresna opcija");
                System.out.println();
                drugiKorisnickiMeni(korisnik, izvodjac);
                break;
        }
    }

    public static void ispisOpcijaZaDrugiMeni(){
        System.out.println("+======================================+\n" +
                           "|                                      |\n" +
                           "|       Izaberite jednu od opcija:     |\n" +
                           "|                                      |\n" +
                           "|                                      |\n" +
                           "|   1. Prikaz pesama i albuma          |\n" +
                           "|   2. Dodavanje pesme u biblioteku    |\n" +
                           "|   3. Dodavanje albuma u biblioteku   |\n" +
                           "|   4. Nazad                           |\n" +
                           "|                                      |\n" +
                           "+======================================+\n");
    }

    public static void ispisOpcija(){
        System.out.println("+====================================================+\n" +
                           "|                                                    |\n" +
                           "|                  Dobrodosli nazad !                |\n" +
                           "|                                                    |\n" +
                           "|              Izaberite jednu od opcija:            |\n" +
                           "|                                                    |\n" +
                           "|                                                    |\n" +
                           "|   1. Prikaz biblioteke kupljenih albuma i pesama   |\n" +
                           "|   2. Pretraga izvodjaca u muzickoj prodavnici      |\n" +
                           "|   3. Dodavanje pesme u biblioteku                  |\n" +
                           "|   4. Dodavanje albuma u biblioteku                 |\n" +
                           "|   5. Odjava                                        |\n" +
                           "|                                                    |\n" +
                           "+====================================================+\n");
    }
}
