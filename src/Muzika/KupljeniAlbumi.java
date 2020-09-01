package Muzika;

import Baza.BazaPodataka;
import Baza.SQL;
import Exceptions.AlbumException;
import Korisnici.Korisnici;
import Meni.KorisnickiMeni;
import Unos.Unos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KupljeniAlbumi {
    private int idKupljenogAlbuma;
    private Korisnici korisnik;
    private Albumi album;

    private KupljeniAlbumi(int idKupljenogAlbuma, int idKorisnika, int idAlbuma) {
        this.idKupljenogAlbuma = idKupljenogAlbuma;
        this.korisnik = Korisnici.dohvatiKorisnikaZaUnetId(idKorisnika);
        this.album = Albumi.dohvatiAlbumPoIdIzBaze(idAlbuma);
    }

    public KupljeniAlbumi(int idKorisnika, int idAlbuma) {
        this.idKupljenogAlbuma = dohvatiNoviId();
        this.korisnik = Korisnici.dohvatiKorisnikaZaUnetId(idKorisnika);
        this.album = Albumi.dohvatiAlbumPoIdIzBaze(idAlbuma);
    }

    public static ArrayList<Albumi> dohvatiKupljeneAlbumeIzBibliotekeZaIdKorisnika(int idKorisnika){
        String upit = "SELECT * FROM kupljeniAlbumi WHERE idKorisnika = " + idKorisnika;
        return SQL.listaKupljenihAlbuma(upit);
    }

    public int dohvatiNoviId(){
        String upit = "SELECT idKupljenogAlbuma FROM kupljeniAlbumi ORDER BY idKupljeniAlbumi desc limit 1";
        int noviId = 0;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("idKupljenogAlbuma");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return ++noviId;
    }

    public static void kupovinaAlbuma(Korisnici korisnik) throws Exception{
        try {
            int idAlbuma = Unos.unosIdAlbuma();
            if(idAlbuma < 1 || idAlbuma > (Albumi.dohvatiNoviId() - 1)) throw new AlbumException(1);
            if(!Korisnici.kupovinaAlbuma(idAlbuma, korisnik.getIdKorisnika())) throw new AlbumException(3);
            System.out.println("\nUspesno ste kupili album\n");
        } catch (AlbumException e) {
            System.err.println(e.getMessage());
            KorisnickiMeni.korisnickiMeni(korisnik);
        }
    }
}
