package Korisnici;

public class Podaci {
    private String korisnickoIme;
    private String lozinka;

    public Podaci(String korisnickoIme, String lozinka){
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }


}
