package Login;

import Baza.SQL;
import Exceptions.LoginException;
import Korisnici.Korisnici;
import Korisnici.Administratori;
import Korisnici.Podaci;
import Meni.AdministratorskiMeni;
import Meni.KorisnickiMeni;
import Unos.Unos;
import helper.DatumVreme;
import helper.Log;

public class Login {
    public static void login(){
        Podaci loginPodaci;
        int brojLogovanja = 0;
        while(brojLogovanja < 3) {
            try {
                loginPodaci = Unos.unosLoginPodataka();
                if (loginPodaci.getKorisnickoIme().startsWith("k")) {
                    if (SQL.proveraUBaziZaUnetoImeILozinku(loginPodaci.getKorisnickoIme(), loginPodaci.getLozinka())) {
                        while(true){
                            Korisnici korisnik = Korisnici.vratiKorisnikaZaUnetUsername(loginPodaci.getKorisnickoIme());
                            KorisnickiMeni.korisnickiMeni(korisnik);
                        }
                    } else {
                        throw new Exception("Nevalidan neki od podataka!");
                        //throw new LoginException(2);
                    }
                } else if (loginPodaci.getKorisnickoIme().startsWith("a")){
                    if (SQL.proveraUBaziZaUnetoImeILozinku(loginPodaci.getKorisnickoIme(), loginPodaci.getLozinka())) {
                        while(true){
                            Administratori administrator = Administratori.vratiAdministratoraZaUnetUsername(loginPodaci.getKorisnickoIme());
                            Log.open(); Log.write("Administrator " + administrator.getUsername() + " se ulogovao/la na sistem: " + DatumVreme.vratiDatumVreme() + '\n');
                            AdministratorskiMeni.administratorskiMeni(administrator);
                        }
                    } else {
                        throw new Exception("Nevalidan neki od podataka!");
                        //throw new LoginException(2);
                    }
                } else {
                    throw new Exception("Neispravno uneto ime!");
                    //throw new LoginException(1);
                }

            } catch (Exception e/*LoginException e*/) {
                e.printStackTrace();
                brojLogovanja++;
            }
        }
    }
}
