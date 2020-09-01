package Baza;

import Korisnici.Korisnici;
import Korisnici.Osoba;
import Muzika.Albumi;
import Muzika.Izvodjaci;
import Muzika.Pesme;
import Muzika.Tip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SQL {

    static boolean proveraUBaziZaUnetoImeILozinku (String username, String password){
        String upit = "SELECT username, password FROM korisnici WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while(odgovorBaze.next()){
                if(odgovorBaze.getString("username").equals(username) && odgovorBaze.getString("password").equals(password))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static Izvodjaci dohvatiIzvodjacaIzBaze(String upit){
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next()){
                return Izvodjaci.formirajIzvodjacaZaResultSet(odgovorBaze);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Doslo je do greske pri uzimanju podataka iz baze!");
        }
        return null;
    }

    static Pesme dohvatiPesmuIzBaze(String upit){
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next()){
                return Pesme.formirajPesmuZaResultSet(odgovorBaze);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Doslo je do greske pri uzimanju podataka iz baze!");
        }
        return null;
    }

    static ArrayList<Pesme> dohvatiPesmeIzBaze(String upit){
        ArrayList<Pesme> listaPesama = new ArrayList<Pesme>();
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                listaPesama.add(Pesme.formirajPesmuZaResultSet(odgovorBaze));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Doslo je do greske pri uzimanju podataka iz baze!");
        }
        return listaPesama;
    }

    static Albumi dohvatiAlbumIzBaze(String upit){
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next()){
                return Albumi.formirajAlbumZaResultSet(odgovorBaze);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Doslo je do greske pri uzimanju podataka iz baze!");
        }
        return null;
    }

    static ArrayList<Albumi> dohvatiAlbumeIzBaze(String upit){
        ArrayList<Albumi> listaAlbuma = new ArrayList<Albumi>();
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while (odgovorBaze.next()){
                listaAlbuma.add(Albumi.formirajAlbumZaResultSet(odgovorBaze));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Doslo je do greske pri uzimanju podataka iz baze!");
        }
        return listaAlbuma;
    }

    static Korisnici dohvatiKorisnikaIzBaze(String upit){
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next())
                return Korisnici.formirajKorisnikaZaResultSet(odgovorBaze);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Doslo je do greske pri uzimanju podataka iz baze!");
        }
        return null;
    }

    static ArrayList<Pesme> listaKupljenihIndividualnihPesama(String upit){
        ArrayList<Pesme> listaKuplenjihIndividualnihPesama = new ArrayList<Pesme>();
        try{
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while(odgovorBaze.next()){
                listaKuplenjihIndividualnihPesama.add(Pesme.dohvatiPesmuPoIdIzBaze(odgovorBaze.getInt("idPesme")));
            }
        } catch (SQLException e){
            System.err.println("Doslo je od greske prilikom uzimanja podataka iz tabele: " + e);
            System.exit(1);
        }
        return listaKuplenjihIndividualnihPesama;
    }

    static ArrayList<Albumi> listaKupljenihAlbuma(String upit){
        ArrayList<Albumi> listaKuplenjihAlbuma = new ArrayList<Albumi>();
        try{
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            while(odgovorBaze.next()){
                listaKuplenjihAlbuma.add(Albumi.dohvatiAlbumPoIdIzBaze(odgovorBaze.getInt("idAlbuma")));
            }
        } catch (SQLException e){
            System.err.println("Doslo je od greske prilikom uzimanja podataka iz tabele: " + e);
            System.exit(1);
        }
        return listaKuplenjihAlbuma;
    }

    static Pesme kupljenePesme(String upit){
        try{
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next()){
                return Pesme.dohvatiPesmuPoIdIzBaze(odgovorBaze.getInt("idPesme"));
            }
        } catch (SQLException e){
            System.err.println("Doslo je do greske prilikom uzmianja pesma iz baze:" +  e);
            System.exit(1);
        }
        return null;
    }

    static Albumi kupljeniAlbumi(String upit){
        try{
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if(odgovorBaze.next()){
                return Albumi.dohvatiAlbumPoIdIzBaze(odgovorBaze.getInt("idAlbuma"));
            }
        } catch (SQLException e){
            System.err.println("Doslo je do greske prilikom uzmianja albuma iz baze:" +  e);
            System.exit(1);
        }
        return null;
    }

    static boolean promenaPodatekaUBazi(String upit){
        try {
            int brojPromenjenihRedova = BazaPodataka.getInstanca().iudUpit(upit);
            if(brojPromenjenihRedova == 1){
                return true;
            }
            return false;
        } catch (SQLException e){
            System.err.println("Doslo je do greske prilikom menjaja podataka u bazi: " + e);
            System.exit(1);
        }
        return false;
    }


}
