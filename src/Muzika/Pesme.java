package Muzika;

import Baza.BazaPodataka;
import Baza.SQL;
import Exceptions.AlbumException;
import Exceptions.IzvodjacException;
import Exceptions.PesmaException;
import Korisnici.Administratori;
import Meni.AdministratorskiMeni;
import Unos.Unos;
import helper.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static Baza.SQL.*;

public class Pesme {
    private int idPesme;
    private String nazivPesme;
    private Izvodjaci izvodjac;
    private Albumi album;
    private String trajanje;

    public Pesme(String nazivPesme, int idIzvodjaca, Albumi album, String trajanje) {
        this.idPesme = dohvatiNoviId();
        this.nazivPesme = nazivPesme;
        this.izvodjac = Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca);
        this.album = album;
        this.trajanje = trajanje;
    }

    private Pesme(int idPesme, String nazivPesme, Izvodjaci izvodjac, Albumi album, String trajanje) {
        this.idPesme = idPesme;
        this.nazivPesme = nazivPesme;
        this.izvodjac = izvodjac;
        this.album = album;
        this.trajanje = trajanje;
    }

    private Pesme(int idPesme, String nazivPesme, Izvodjaci izvodjac, String trajanje) {
        this.idPesme = idPesme;
        this.nazivPesme = nazivPesme;
        this.izvodjac = izvodjac;
        this.album = null;
        this.trajanje = trajanje;
    }

    public static Pesme dohvatiPesmuPoIdIzBaze(int id){
        String upit = "SELECT * FROM pesme WHERE idPesme = " + id;
        return dohvatiPesmuIzBaze(upit);
    }

    public static ArrayList<Pesme> dohvatiPesmeZadatogIzvodjaca(int idIzvodjaca){
        String upit = "SELECT * FROM pesme WHERE idIzvodjaca = " + idIzvodjaca;
        return dohvatiPesmeIzBaze(upit);
    }

    public static ArrayList<Pesme> dohvatiPesmeZadatogAlbumaPoId(int idAlbuma){
        String upit = "SELECT * FROM pesme WHERE idAlbuma = " + idAlbuma;
        return dohvatiPesmeIzBaze(upit);
    }

    public static ArrayList<Pesme> dohvatiIndividualnePesmeZadatogIzvodjaca(int idIzvodjaca){
        String upit = "SELECT * FROM pesme WHERE idIzvodjaca = " + idIzvodjaca;
        ArrayList<Pesme> listaPesama = dohvatiPesmeIzBaze(upit);
        ArrayList<Pesme> listaIndividualnihPesama = new ArrayList<>();
        for(Pesme pesme : listaPesama){
            if(pesme.album == null){
                listaIndividualnihPesama.add(pesme);
            }
        }
        return listaIndividualnihPesama;
    }

    public static Pesme formirajPesmuZaResultSet(ResultSet odgovorBaze) throws SQLException {
        int idPesme = odgovorBaze.getInt("idPesme");
        String nazivPesme = odgovorBaze.getString("nazivPesme");
        Izvodjaci izvodjac = Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(odgovorBaze.getInt("idIzvodjaca"));
        Albumi album = Albumi.dohvatiAlbumPoIdIzBaze(odgovorBaze.getInt("idAlbuma"));
        String trajanje = odgovorBaze.getString("trajanje");
        if(album == null){
            return new Pesme(idPesme, nazivPesme, izvodjac, trajanje);
        } else{
            return new Pesme(idPesme, nazivPesme, izvodjac, album, trajanje);
        }
    }

    public static int dohvatiNoviId(){
        String upit = "SELECT idPesme FROM pesme ORDER BY idPesme desc limit 1";
        int noviId = 0;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("idPesme");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return ++noviId;
    }

    public static boolean proveraDaLiJePesmaVecKupljena(int idPesme, int idKorisnika){
        String upit = "SELECT * FROM kupljenePesme WHERE idPesme = " + idPesme + " AND idKorisnika = " + idKorisnika;
        return kupljenePesme(upit) != null;
    }

    public static boolean ubaciPesmuUBazu(Administratori administrator) throws Exception {
        try {
            String aktivnostiAdmina = "\nUbacivanje pesme u bazu:\n";
            Integer idAlbuma;
            String nazivPesme = Unos.unosNazivaPesme();
            aktivnostiAdmina += nazivPesme + "\n";
            int idIzvodjaca = Unos.unosIdIzvodjaca();
            aktivnostiAdmina += idIzvodjaca + "\n";
            if(Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca) == null) throw new IzvodjacException();
            //in.nextLine();
            System.out.println("Unesite id albuma:");
            String idAlbumaString = Unos.unosIdAlbumaKaoString();
            aktivnostiAdmina += idAlbumaString + "\n";
            if(idAlbumaString.equals("")){
                idAlbuma = null;
            } else{
                idAlbuma = Integer.parseInt(idAlbumaString.trim());
                if(Albumi.dohvatiAlbumPoIdIzBaze(idAlbuma) == null) throw new AlbumException(1);
            }
            String trajanje = Unos.unosTrajanjaPesme();
            aktivnostiAdmina += trajanje + "\n";
            String upit = "INSERT INTO pesme (nazivPesme, idIzvodjaca, idAlbuma, trajanje) VALUES ('" + nazivPesme + "', " + idIzvodjaca + ", " + idAlbuma + ", '" + trajanje + "')";
            Log.write(aktivnostiAdmina);
            return promenaPodatekaUBazi(upit);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    public static boolean azurirajPesmu(Administratori administrator) throws Exception{
        String aktivnostAdmina = "\nAzuriranje pesme: \n";
        int flag = 1;
        Integer idAlbuma;
        Integer idIzvodjaca;
        int idPesme = Unos.unosIdPesme();
        try{
            if(dohvatiPesmuPoIdIzBaze(idPesme) == null) throw new PesmaException(1);
        } catch (Exception e){
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }

        String nazivPesme = Unos.unosNazivaPesme();
        if(!nazivPesme.equals("")){
            aktivnostAdmina += nazivPesme + "\n";
            String upit = "UPDATE pesme SET nazivPesme = '" + nazivPesme + "' WHERE idPesme = " + idPesme;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }
        String idIzvodjacaString = Unos.unosIdIzvodjacaKaoString();
        if(!idIzvodjacaString.equals("")){
            aktivnostAdmina += idIzvodjacaString + "\n";
            try{
                idIzvodjaca = Integer.parseInt(idIzvodjacaString.trim());
                if(Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca) == null) throw new IzvodjacException();
                String upit = "UPDATE pesme SET idIzvodjaca = " + idIzvodjaca + " WHERE idPesme = " + idPesme;
                if(!promenaPodatekaUBazi(upit)){
                    flag = 0;
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
                AdministratorskiMeni.administratorskiMeni(administrator);
            }
        }
        String idAlbumaString = Unos.unosIdAlbumaKaoString();
        if(!idAlbumaString.equals("")) {
            aktivnostAdmina += idAlbumaString + "\n";
            try {
                idAlbuma = Integer.parseInt(idAlbumaString.trim());
                if(Albumi.dohvatiAlbumPoIdIzBaze(idAlbuma) == null) throw new AlbumException(1);
                String upit = "UPDATE pesme SET idAlbuma = " + idAlbuma + " WHERE idPesme = " + idPesme;
                if (!promenaPodatekaUBazi(upit)) {
                    flag = 0;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                AdministratorskiMeni.administratorskiMeni(administrator);
            }
        }
        String trajanje = Unos.unosTrajanjaPesme();
        if(!trajanje.equals("")){
            aktivnostAdmina += trajanje + "\n";
            try {
                String upit = "UPDATE pesme SET trajanje = '" + trajanje + "' WHERE idPesme = " + idPesme;
                if(!promenaPodatekaUBazi(upit)){
                    flag = 0;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                AdministratorskiMeni.administratorskiMeni(administrator);
            }
        }
        Log.write(aktivnostAdmina);
        return flag == 1;
    }

    public static boolean obrisiPesmu(Administratori administrator) throws Exception{
        String aktivnostAdmina = "\nBrisanje pesme: \n";
        boolean flagZaProveruPromene = false;
        try {
            int idPesme = Unos.unosIdPesme();
            if(dohvatiPesmuPoIdIzBaze(idPesme) == null) throw new PesmaException(1);
            aktivnostAdmina += idPesme + "\n";

            if(!Unos.upozorenjeNaAkcijuBrisanja()){
                return false;
            }
            BazaPodataka bazaPodataka = BazaPodataka.getInstanca();
            bazaPodataka.automatskaTransakcija(false);
            String upit = "DELETE FROM pesme WHERE idPesme = " + idPesme;
            bazaPodataka.iudUpit(upit);
            if(!promenaPodatekaUBazi(upit))
                flagZaProveruPromene = true;
            upit = "DELETE FROM kupljenePesme WHERE idPesme = " + idPesme;
            bazaPodataka.iudUpit(upit);
            bazaPodataka.snimiTransakciju();
            bazaPodataka.automatskaTransakcija(true);
            Log.write(aktivnostAdmina);
            return flagZaProveruPromene;
        } catch (Exception e){
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    //GETTERI ---------------------------

    public int getIdPesme() {
        return idPesme;
    }

    public String getNazivPesme() {
        return nazivPesme;
    }

    public Izvodjaci getIzvodjac() {
        return izvodjac;
    }

    public Albumi getAlbum() {
        return album;
    }

    public String getTrajanje() {
        return trajanje;
    }
    //------------------------------------

    public String toString(){
        return "ID Pesme: " + idPesme +
                "\nNaziv Pesme: " + nazivPesme +
                "\nIzvodjac: [" + izvodjac + "]" +
                "\nAlbum: [" + ((album == null) ? "Nema album\n" : album.getNazivAlbuma()) + "]" +
                "\nTrajanje: " + trajanje;
    }
}
