package Muzika;

import Baza.BazaPodataka;
import Baza.SQL;
import Exceptions.PesmaException;
import Korisnici.Korisnici;
import Meni.KorisnickiMeni;
import Unos.Unos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KupljenePesme implements SQL, Unos{
    private int idKupljenePesme;
    private Korisnici korisnik;
    private Pesme pesme;

    private KupljenePesme(int idKupljenePesme, int idKorisnika, int idPesme) {
        this.idKupljenePesme = idKupljenePesme;
        this.korisnik = Korisnici.dohvatiKorisnikaZaUnetId(idKorisnika);
        this.pesme = Pesme.dohvatiPesmuPoIdIzBaze(idPesme);
    }

    public KupljenePesme(int idKorisnika, int idPesme) {
        this.idKupljenePesme = dohvatiNoviId();
        this.korisnik = Korisnici.dohvatiKorisnikaZaUnetId(idKorisnika);
        this.pesme = Pesme.dohvatiPesmuPoIdIzBaze(idPesme);
    }

    public static ArrayList<Pesme> dohvatiKupljenePesameIzBibliotekeZaIdKorisnika(int idKorisnika){
        String upit = "SELECT * FROM kupljenePesme WHERE idKorisnika = " + idKorisnika;
        return SQL.listaKupljenihIndividualnihPesama(upit);
    }

    public int dohvatiNoviId(){
        String upit = "SELECT idKupljenePesme FROM kupljenePesme ORDER BY idKupljenePesme desc limit 1";
        int noviId = 0;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("idKupljenePesme");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return ++noviId;
    }

    public static void kupovinaPesme(Korisnici korisnik) throws Exception{
        try{
            int idPesme = Unos.unosIdPesme();
            if(idPesme < 1 || idPesme > (Pesme.dohvatiNoviId() - 1)) throw new PesmaException(1);
            if(!Korisnici.kupovinaPesme(idPesme, korisnik.getIdKorisnika())) throw new PesmaException(3);
            System.out.println("\nUspesno ste kupili pesmu\n");
        } catch (PesmaException e){
            System.err.println(e.getMessage());
            KorisnickiMeni.korisnickiMeni(korisnik);
        }
    }

}
