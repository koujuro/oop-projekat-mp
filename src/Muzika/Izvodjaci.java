package Muzika;

import Baza.BazaPodataka;
import Exceptions.IzvodjacException;
import Korisnici.Administratori;
import Meni.AdministratorskiMeni;
import Unos.Unos;
import helper.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static Baza.SQL.dohvatiIzvodjacaIzBaze;
import static Baza.SQL.promenaPodatekaUBazi;

public class Izvodjaci {
    private int idIzvodjaca, godinaOsnivanja, godinaRaspada;
    private String imeIzvodjaca, biografija;
    private Tip tipIzvodjaca;

    public Izvodjaci(String imeIzvodjaca, Tip tipIzvodjaca, int godinaOsnivanja, int godinaRaspada, String biografija){
        idIzvodjaca = dohvatiNoviId();
        this.imeIzvodjaca = imeIzvodjaca;
        this.tipIzvodjaca = tipIzvodjaca;
        this.godinaOsnivanja = godinaOsnivanja;
        this.godinaRaspada = godinaRaspada;
        this.biografija = biografija;
    }

    private Izvodjaci(int idIzvodjaca, String imeIzvodjaca, Tip tipIzvodjaca, int godinaOsnivanja, int godinaRaspada, String biografija){
        this.idIzvodjaca = idIzvodjaca;
        this.imeIzvodjaca = imeIzvodjaca;
        this.tipIzvodjaca = tipIzvodjaca;
        this.godinaOsnivanja = godinaOsnivanja;
        this.godinaRaspada = godinaRaspada;
        this.biografija = biografija;
    }

    public static Izvodjaci dohvatiIzvodjacaPoIdIzBaze(int idIzvodjaca){
        String upit = "SELECT * FROM izvodjaci WHERE idIzvodjaca = " + idIzvodjaca;
        return dohvatiIzvodjacaIzBaze(upit);
    }

    public static Izvodjaci dohvatiIzvodjacaPoImenuIzBaze(String imeIzvodjaca){
        String upit = "SELECT * FROM izvodjaci WHERE imeIzvodjaca = '" + imeIzvodjaca + "'";
        return dohvatiIzvodjacaIzBaze(upit);
    }

    public static Izvodjaci formirajIzvodjacaZaResultSet(ResultSet odgovorBaze) throws SQLException {
        int idIzvodjaca = odgovorBaze.getInt("idIzvodjaca");
        String imeIzvodjaca = odgovorBaze.getString("imeIzvodjaca");
        Tip tipIzvodjaca = Tip.valueOf(odgovorBaze.getString("tipIzvodjaca"));
        int godinaOsnivanja = odgovorBaze.getInt("godinaOsnivanja");
        int godinaRaspada = odgovorBaze.getInt("godinaRaspada");
        String biografija = odgovorBaze.getString("biografija");
        return new Izvodjaci(idIzvodjaca, imeIzvodjaca, tipIzvodjaca, godinaOsnivanja, godinaRaspada, biografija);
    }

    public static int dohvatiNoviId(){
        String upit = "SELECT idIzvodjaca FROM izvodjaci ORDER BY idIzvodjaca desc limit 1";
        int noviId = 0;
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            noviId = odgovorBaze.getInt("idIzvodjaca");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return ++noviId;
    }

    public static boolean ubaciIzvodjacaUBazu(Administratori administrator) throws Exception{
        try{
            String aktivnostiAdmina = "\nUbacivanje izvodjaca u bazu:\n";
            Integer godinaRaspada;
            String nazivIzvodjaca = Unos.unosImenaIzvodjaca();
            aktivnostiAdmina += nazivIzvodjaca + "\n";
            String tipIzvodjaca = Unos.unosTipaIzvodjaca().toUpperCase();
            if(!tipIzvodjaca.equals("SOLO") && !tipIzvodjaca.equals("BEND") && !tipIzvodjaca.equals("DUO")) throw new Exception("\nGreska pri unosenju tipa izvodjaca\n");
            aktivnostiAdmina += tipIzvodjaca + "\n";
            int godinaOsnivanja = Unos.unosGodineOsnivanja();
            aktivnostiAdmina += godinaOsnivanja + "\n";
            //in.nextLine();
            String godinaRaspadaString = Unos.unosGodineRaspadaKaoString();
            aktivnostiAdmina += godinaRaspadaString + "\n";
            if(godinaRaspadaString.equals("")){
                godinaRaspada = null;
            } else{
                godinaRaspada = Integer.parseInt(godinaRaspadaString.trim());
            }
            String biografija = Unos.unosBiografije();
            aktivnostiAdmina += biografija + "\n";

            String upit = "INSERT INTO izvodjaci (imeIzvodjaca, tipIzvodjaca, godinaOsnivanja, godinaRaspada, biografija) " +
                    "VALUES ('" + nazivIzvodjaca + "', '" + tipIzvodjaca + "', " + godinaOsnivanja + ", " + godinaRaspada + ", '" + biografija + "')";

            Log.write(aktivnostiAdmina);
            return promenaPodatekaUBazi(upit);
        } catch (Exception e){
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    public static boolean azurirajIzvodjaca(Administratori administrator) throws Exception{
        String aktivnostiAdmina = "\nAzuriranje izvodjaca:\n";
        int flag = 1;
        Integer godinaOsnivanja;
        Integer godinaRaspada;
        int idIzvodjaca = Unos.unosIdIzvodjaca();
        aktivnostiAdmina += idIzvodjaca + "\n";
        try{
            if(dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca) == null) throw new IzvodjacException();
        } catch (Exception e){
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }

        String nazivIzvodjaca = Unos.unosImenaIzvodjaca();
        if(!nazivIzvodjaca.equals("")){
            aktivnostiAdmina += nazivIzvodjaca + "\n";
            String upit = "UPDATE izvodjaci SET imeIzvodjaca = '" + nazivIzvodjaca + "' WHERE idIzvodjaca = " + idIzvodjaca;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }
        String tipIzovdjaca = Unos.unosTipaIzvodjaca().toUpperCase();
        if(!tipIzovdjaca.equals("")){
            aktivnostiAdmina += tipIzovdjaca + "\n";
            try {
                if(!tipIzovdjaca.equals("SOLO") && !tipIzovdjaca.equals("BEND") && !tipIzovdjaca.equals("DUO")) throw new Exception("\nGreska pri unosenju tipa izvodjaca\n");
                String upit = "UPDATE izvodjaci SET tipIzvodjaca = '" + tipIzovdjaca + "' WHERE idIzvodjaca = " + idIzvodjaca;
                if(!promenaPodatekaUBazi(upit)){
                    flag = 0;
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
                AdministratorskiMeni.administratorskiMeni(administrator);
            }
        }
        String godinaOsnivanjaString = Unos.unosGodineOsnivanjaKaoString();
        if(!godinaOsnivanjaString.equals("")){
            aktivnostiAdmina += godinaOsnivanjaString + "\n";
            godinaOsnivanja = Integer.parseInt(godinaOsnivanjaString.trim());
            String upit = "UPDATE izvodjaci SET godinaOsnivanja = " + godinaOsnivanja + " WHERE idIzvodjaca = " + idIzvodjaca;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }

        String godinaRaspadaString = Unos.unosGodineRaspadaKaoString();
        if(!godinaRaspadaString.equals("")){
            aktivnostiAdmina += godinaRaspadaString + "\n";
            godinaRaspada = Integer.parseInt(godinaRaspadaString.trim());
            String upit = "UPDATE izvodjaci SET godinaRaspada = " + godinaRaspada + " WHERE idIzvodjaca = " + idIzvodjaca;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }

        String biografija = Unos.unosBiografije();
        if(!biografija.equals("")){
            aktivnostiAdmina += biografija + "\n";
            String upit = "UPDATE izvodjaci SET biografija = '" + biografija + "' WHERE idIzvodjaca = " + idIzvodjaca;
            if(!promenaPodatekaUBazi(upit)){
                flag = 0;
            }
        }
        Log.write(aktivnostiAdmina);

        return flag == 1;
    }

    public static boolean obrisiIzvodjaca(Administratori administrator) throws Exception{
        try{
            String aktivnostiAdmina = "\nBrisanje izvodjaca: \n";
            boolean flagZaProveruPromene= false;
            int idIzvodjaca = Unos.unosIdIzvodjaca();
            if(dohvatiIzvodjacaPoIdIzBaze(idIzvodjaca) == null) throw new IzvodjacException();
            aktivnostiAdmina += idIzvodjaca + "\n";

            if(!Unos.upozorenjeNaAkcijuBrisanja()){
                return false;
            }
            BazaPodataka bazaPodataka = BazaPodataka.getInstanca();
            bazaPodataka.automatskaTransakcija(false);
            String upit = "DELETE FROM albumi WHERE idIzvodjaca = " + idIzvodjaca;
            bazaPodataka.iudUpit(upit);
            upit = "DELETE FROM pesme WHERE idIzvodjaca = " + idIzvodjaca;
            bazaPodataka.iudUpit(upit);
            upit = "DELETE FROM izvodjaci WHERE idIzvodjaca = " + idIzvodjaca;
            bazaPodataka.iudUpit(upit);
            if(!promenaPodatekaUBazi(upit))
                flagZaProveruPromene = true;
            bazaPodataka.snimiTransakciju();
            bazaPodataka.automatskaTransakcija(true);
            Log.write(aktivnostiAdmina);
            return flagZaProveruPromene;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            AdministratorskiMeni.administratorskiMeni(administrator);
        }
        return false;
    }

    // GETTERI ------------------------
    public int getIdIzvodjaca() {
        return idIzvodjaca;
    }

    public int getGodinaOsnivanja() {
        return godinaOsnivanja;
    }

    public int getGodinaRaspada() {
        return godinaRaspada;
    }

    public String getImeIzvodjaca() {
        return imeIzvodjaca;
    }

    public String getBiografija() {
        return biografija;
    }

    public Tip getTipIzvodjaca() {
        return tipIzvodjaca;
    }
    // ---------------------------------

    public String toString(){
        return "ID Izvodjaca: " + idIzvodjaca +
                "\nIme izvodjaca: " + imeIzvodjaca +
                "\nTip Izvodjaca: " + tipIzvodjaca +
                "\nGodina formiranja: " + godinaOsnivanja +
                ((godinaRaspada == 0) ? "" : "\nGodina raspada: " + godinaRaspada) +
                "\nBiografija: " + biografija;
    }
}
