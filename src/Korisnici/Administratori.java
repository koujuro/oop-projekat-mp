package Korisnici;

import Baza.BazaPodataka;
import Baza.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Administratori extends Osoba implements SQL {

    public Administratori(int idKorisnika, String username, String password, boolean admin){
        super(idKorisnika, username, password, admin);
    }

    public static Administratori vratiAdministratoraZaUnetUsername (String username_arg) {
        String upit = "SELECT * FROM korisnici WHERE username = '" + username_arg + "'";
        try {
            ResultSet odgovorBaze = BazaPodataka.getInstanca().selectUpit(upit);
            if (odgovorBaze.next()) {
                int idKorisnika = odgovorBaze.getInt("idKorisnika");
                String username = odgovorBaze.getString("username");
                String password = odgovorBaze.getString("password");
                boolean admin;
                if (odgovorBaze.getString("admin").toLowerCase().equals("true"))
                    admin = true;
                else
                    admin = false;

                return new Administratori(idKorisnika, username, password, admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Doslo je do greske prilikom pristupa tabeli u bazi!");
        }

        return null;
    }
}
