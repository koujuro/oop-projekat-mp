package Korisnici;

import Baza.BazaPodataka;
import Baza.SQL;
import Exceptions.AlbumException;
import Exceptions.PesmaException;
import Meni.KorisnickiMeni;
import Muzika.Albumi;
import Muzika.KupljenePesme;
import Muzika.KupljeniAlbumi;
import Muzika.Pesme;
import Unos.Unos;
import helper.Trajanje;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Korisnici extends Osoba implements SQL{

    public Korisnici(int idKorisnika, String username, String password, boolean admin){
        super(idKorisnika, username, password, admin);
    }

    public static Korisnici vratiKorisnikaZaUnetUsername (String username_arg) {
        String upit = "SELECT * FROM korisnici WHERE username = '" + username_arg + "'";
        return SQL.dohvatiKorisnikaIzBaze(upit);
    }

    public static Korisnici dohvatiKorisnikaZaUnetId(int idKorisnika){
        String upit = "SELECT * FROM korisnici WHERE idKorisnika = " + idKorisnika;
        return SQL.dohvatiKorisnikaIzBaze(upit);
    }

    public static Korisnici formirajKorisnikaZaResultSet(ResultSet odgovorBaze) throws SQLException{
        int idKorisnika = odgovorBaze.getInt("idKorisnika");
        String username = odgovorBaze.getString("username");
        String password = odgovorBaze.getString("password");
        boolean admin = odgovorBaze.getString("admin").toLowerCase().equals("true");
        return new Korisnici(idKorisnika, username, password, admin);
    }

    public void ispisKupljenihAlbumaIPesama(){
        ArrayList<Pesme> listaKupljenihIndividualnihPesama = KupljenePesme.dohvatiKupljenePesameIzBibliotekeZaIdKorisnika(idKorisnika);
        ArrayList<Albumi> listaKupljenihAlbuma = KupljeniAlbumi.dohvatiKupljeneAlbumeIzBibliotekeZaIdKorisnika(idKorisnika);
        ArrayList<Trajanje> listaTrajanjaPesama = new ArrayList<Trajanje>();
        ArrayList<Pesme> listaPesamaIzAlbuma = new ArrayList<Pesme>();
        String ukupnoTrajanjeAlbuma = "";

        System.out.println("====================================\n" +
                           ">>> KUPLJENE INDIVIDUALNE PESME: <<<\n" +
                           "====================================\n");
        for(Pesme p: listaKupljenihIndividualnihPesama) {
            System.out.println(p);
            System.out.println("");
        }

        System.out.println("\n========================\n" +
                             ">>> KUPLJENI ALBUMI: <<<\n" +
                             "========================\n");
        for(Albumi a: listaKupljenihAlbuma){
            System.out.println(a);
            System.out.println("");
            listaPesamaIzAlbuma = Pesme.dohvatiPesmeZadatogAlbumaPoId(a.getIdAlbuma());

            System.out.println("\nUkupno trajanje albuma: " + Trajanje.saberiIVratiVreme(Trajanje.listaTrajanjaPesamaZaDatePesmeIzAlbuma(listaPesamaIzAlbuma)) + "\n\n");
            listaTrajanjaPesama.clear();
        }

        System.out.println("\n1. Povratak na Korisnicki Meni");
        int opcija = Unos.unosOpcije();
        do{
            switch (opcija){
                case 1:
                    try {
                        KorisnickiMeni.korisnickiMeni(this);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.err.println("Pogresno uneta opcija!");
                    this.ispisKupljenihAlbumaIPesama();
            }
        }while(opcija != 1);

    }

    public static void ispisPesamaIAlbumaZaIdIzvodjaca(int idIzvodjaca){
        ArrayList<Albumi> listaAlbuma = Albumi.dohvatiAlbumePoIdIzvodjacaIzBaze(idIzvodjaca);
        ArrayList<Pesme> listaIndividualnihPesama = Pesme.dohvatiIndividualnePesmeZadatogIzvodjaca(idIzvodjaca);
        ArrayList<Pesme> listaPesama = Pesme.dohvatiPesmeZadatogIzvodjaca(idIzvodjaca);
        System.out.println("\n>>> INDIVIDUALNE PESME ZADATOG IZVODJACA:\n");
        for(Pesme pesme : listaIndividualnihPesama){
            System.out.println(pesme);
        }
        System.out.println("\n\n>>> ALBUMI ZADATOG IZVODJACA:\n");
        for(Albumi albumi : listaAlbuma) {
            System.out.println(albumi);
        }
        System.out.println("\n\n>>> LISTA PESAMA ZADATOG IZVODJACA:\n");
        for(Pesme pesme : listaPesama){
            System.out.println(pesme);
        }
    }

    public static boolean kupovinaPesme(int idPesme, int idKorisnika) throws PesmaException {
        if(Pesme.proveraDaLiJePesmaVecKupljena(idPesme, idKorisnika)) throw new PesmaException(2);
        String upit = "INSERT INTO kupljenePesme(idKorisnika, idPesme) VALUES (" + idKorisnika + ", " + idPesme + ")";
        if (SQL.promenaPodatekaUBazi(upit)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean kupovinaAlbuma(int idAlbuma, int idKorisnika) throws AlbumException {
        if(Albumi.proveraDaLiJeAlbumVecKupljen(idAlbuma, idKorisnika)) throw new AlbumException(2);
        String upit = "INSERT INTO kupljeniAlbumi(idKorisnika, idAlbuma) VALUES (" + idKorisnika + ", " + idAlbuma + ")";
        if (SQL.promenaPodatekaUBazi(upit)){
            return true;
        } else {
            return false;
        }
    }

}
