package Muzika;

import Baza.BazaPodataka;
import Exceptions.AlbumException;
import Exceptions.IzvodjacException;
import Korisnici.Administratori;
import Meni.AdministratorskiMeni;
import Unos.Unos;
import helper.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static Baza.SQL.*;

public class Albumi {
    private int idAlbuma;
    private String nazivAlbuma;
    private int godinaIzdavanja;
    private Izvodjaci izvodjac;
    private String zanr;

    private Albumi(String nazivAlbuma, int godinaIzdavanja, Izvodjaci izvodjac, String zanr) {
        this.idAlbuma = dohvatiNoviId();
        this.nazivAlbuma = nazivAlbuma;
        this.godinaIzdavanja = godinaIzdavanja;
        this.izvodjac = izvodjac;
        this.zanr = zanr;
    }

    public Albumi(int idAlbuma, String nazivAlbuma, int godinaIzdanja, Izvodjaci izvodjac, String zanrAlbuma) {
        this.idAlbuma = idAlbuma;
        this.nazivAlbuma = nazivAlbuma;
        this.godinaIzdavanja = godinaIzdanja;
        this.izvodjac = izvodjac;
        this.zanr = zanrAlbuma;
    }

    public static Albumi dohvatiAlbumPoIdIzBaze(int idIzvodjaca){
        String upit = "SELECT * FROM albumi WHERE idAlbuma = " + idIzvodjaca;
        return dohvatiAlbumIzBaze(upit);
    }

    public static ArrayList<Albumi> dohvatiAlbumePoIdIzvodjacaIzBaze(int idIzvodjaca){
        String upit = "SELECT * FROM albumi WHERE idIzvodjaca = " + idIzvodjaca;
        return dohvatiAlbumeIzBaze(upit);
    }

    public static Albumi formirajAlbumZaResultSet(ResultSet odgovorBaze) throws SQLException {
        int idAlbuma = odgovorBaze.getInt("idAlbuma");
        String nazivAlbuma = odgovorBaze.getString("nazivAlbuma");
        int godinaIzdavanja = odgovorBaze.getInt("godinaIzdavanja");
        Izvodjaci izvodjac = Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(odgovorBaze.getInt("idIzvodjaca"));
        String zanr = odgovorBaze.getString("zanr");
        return new Albumi(idAlbuma, nazivAlbuma, godinaIzdavanja, izvodjac, zanr);
    }

    public static int dohvatiNoviId(){
        String upit = "SELECT idAlbuma FROM albumi ORDER BY idAlbuma desc limit 1";
        int noviId = 0;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("idAlbuma");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return ++noviId;
    }

    public static boolean proveraDaLiJeAlbumVecKupljen(int idAlbuma, int idKorisnika){
        String upit = "SELECT * FROM kupljeniAlbumi WHERE idAlbuma = " + idAlbuma + " AND idKorisnika = " + idKorisnika;
        return kupljeniAlbumi(upit) != null;
    }

    public static boolean ubaciAlbumUBazu(Administratori administrator) throws Exception{
        String aktivnostiAdmina = "\nUbacivanje albuma u bazu: \n";
        try{

            String nazivAlbuma = Unos.unosNazivaAlbuma();
            aktivnostiAdmina += nazivAlbuma + "\n";
            int godinaIzdavanja = Unos.unosGodineIzdavanja();
            aktivnostiAdmina += godinaIzdavanja + "\n";
            int idIzvodjaca = Unos.unosIdIzvodjaca();
            aktivnostiAdmina += idIzvodjaca + "\n";
            if(Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca) == null) throw new IzvodjacException();
            //in.nextLine();
            String zanrAlbuma = Unos.unosZanra();
            aktivnostiAdmina += zanrAlbuma + "\n";
            String upit = "INSERT INTO albumi (nazivAlbuma, godinaIzdavanja, idIzvodjaca, zanr) " +
                    "VALUES ('" + nazivAlbuma + "', " + godinaIzdavanja + ", " + idIzvodjaca + ", '" + zanrAlbuma + "')";
            if(promenaPodatekaUBazi(upit)){
                aktivnostiAdmina += "\nUnos pesama upravo unetog albuma: \n";
                Log.write(aktivnostiAdmina);
                int idAlbuma = Albumi.dohvatiNoviId() - 1;
                while (true){
                    if(!unosPesamaUnetogAlbuma(idIzvodjaca, idAlbuma, administrator)){
                        break;
                    }
                }
            }
            return true;
        } catch (Exception e){
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    private static boolean unosPesamaUnetogAlbuma(int idIzvodjaca, int idAlbuma, Administratori administrator) throws Exception{
        try {
            String aktivnostAdmina = "";
            String nazivPesme = Unos.unosNazivaPesme();
            if(nazivPesme.equals("")){
                return false;
            }
            aktivnostAdmina += nazivPesme + "\n";
            String trajanje = Unos.unosTrajanjaPesme();
            aktivnostAdmina += trajanje + "\n";
            String upit = "INSERT INTO pesme (nazivPesme, idIzvodjaca, idAlbuma, trajanje) VALUES ('" + nazivPesme + "', " + idIzvodjaca + ", " + idAlbuma + ", '" + trajanje + "')";
            Log.write(aktivnostAdmina);
            return promenaPodatekaUBazi(upit);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    public static boolean azurirajAlbum(Administratori administrator) throws Exception{
        String aktivnostAdmina = "\nAzuriranje albuma: \n";
        int flag = 1;
        Integer godinaIzdavanja;
        Integer idIzvodjaca;
        int idAlbuma = Unos.unosIdAlbuma();
        try {
            if(dohvatiAlbumPoIdIzBaze(idAlbuma) == null) throw new AlbumException(1);
        } catch (Exception e){
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        aktivnostAdmina += idAlbuma + "\n";


        String nazivAlbuma = Unos.unosNazivaAlbuma();
        if(!nazivAlbuma.equals("")){
            aktivnostAdmina += nazivAlbuma + "\n";
            String upit = "UPDATE albumi SET nazivAlbuma = '" + nazivAlbuma + "' WHERE idAlbuma = " + idAlbuma;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }
        String godinaIzdavanjaString = Unos.unosGodineIzdavanjaKaoString();
        if(!godinaIzdavanjaString.equals("")){
            aktivnostAdmina += godinaIzdavanjaString + "\n";
            godinaIzdavanja = Integer.parseInt(godinaIzdavanjaString.trim());
            String upit = "UPDATE albumi SET godinaIzdavanja = " + godinaIzdavanja + " WHERE idAlbuma = " + idAlbuma;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }
        String idIzvodjacaString = Unos.unosIdIzvodjacaKaoString();
        if(!idIzvodjacaString.equals("")){
            aktivnostAdmina += idIzvodjacaString + "\n";
            try {
                idIzvodjaca = Integer.parseInt(idIzvodjacaString.trim());
                if(Izvodjaci.dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca) == null) throw new IzvodjacException();
                String upit = "UPDATE albumi SET idIzvodjaca = " + idIzvodjaca + " WHERE idAlbuma = " + idAlbuma;
                if(!promenaPodatekaUBazi(upit)){
                    flag = 0;
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
                AdministratorskiMeni.administratorskiMeni(administrator);
            }
        }
        String zanrAlbuma = Unos.unosZanra();
        if(!zanrAlbuma.equals("")){
            aktivnostAdmina += zanrAlbuma + "\n";
            String upit = "UPDATE albumi SET zanr = '" + zanrAlbuma + "' WHERE idALbuma = " + idAlbuma;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }

        Log.write(aktivnostAdmina);

        return flag == 1;
    }

    public static boolean obrisiAlbum(Administratori administrator) throws Exception{
        String aktivnosAdmina = "\nBrisanje albuma: \n";
        try {
            boolean flagZaProveruPromene = false;
            int idAlbuma = Unos.unosIdAlbuma();
            if(dohvatiAlbumPoIdIzBaze(idAlbuma) == null) throw new AlbumException(1);
            aktivnosAdmina += idAlbuma + "\n";

            if(!Unos.upozorenjeNaAkcijuBrisanja()){
                return false;
            }
            BazaPodataka bazaPodataka = BazaPodataka.getInstanca();
            bazaPodataka.automatskaTransakcija(false);
            String upit = "DELETE FROM albumi WHERE idAlbuma = " + idAlbuma;
            bazaPodataka.iudUpit(upit);
            if(!promenaPodatekaUBazi(upit))
                flagZaProveruPromene = true;
            upit = "DELETE FROM pesme WHERE idAlbuma = " + idAlbuma;
            bazaPodataka.iudUpit(upit);
            upit = "DELETE FROM kupljeniAlbumi WHERE idAlbuma = " + idAlbuma;
            bazaPodataka.iudUpit(upit);
            bazaPodataka.snimiTransakciju();
            bazaPodataka.automatskaTransakcija(true);
            Log.write(aktivnosAdmina);
            return flagZaProveruPromene;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    //GETTERI --------------------------


    public int getIdAlbuma() {
        return idAlbuma;
    }

    public String getNazivAlbuma() {
        return nazivAlbuma;
    }

    public int getGodinaIzdavanja() {
        return godinaIzdavanja;
    }

    public Izvodjaci getIzvodjac() {
        return izvodjac;
    }

    public String getZanr() {
        return zanr;
    }
    //----------------------------------

    public String toString(){
        return "ID Albuma: " + idAlbuma +
                "\nNaziv Albuma: " + nazivAlbuma +
                "\nGodina Izdavanja: " + godinaIzdavanja +
                "\nIzvodjac: [" + izvodjac + "]" +
                "\nZanr: " + zanr;
    }


}
