package Korisnici;

public abstract class Osoba {
    protected int idKorisnika;
    protected String username, password;
    protected boolean admin;

    public Osoba (int idKorisnika, String username, String password, boolean admin){
        this.idKorisnika = idKorisnika;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public int getIdKorisnika() {
        return idKorisnika;
    }

    public boolean isAdmin() {
        return admin;
    }
}
