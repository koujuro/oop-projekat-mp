package Exceptions;

public class LoginException extends Exception{
    private int tipGreske;

    public LoginException (){}

    public LoginException (int tipGreske){ this.tipGreske = tipGreske; }

    public void printStackTrace (){
        String tekst = "Greska prilikom logovanja!\n";
        switch(tipGreske){
            case 1: tekst += "Korisnicko ime mora poceti malim slovom 'k' ili 'a'!"; break;
            case 2: tekst += "Nevalidno korisnicko ime ili lozinka!"; break;
            default: tekst += "Nepoznata greska!"; break;
        }
        System.out.println(tekst);
    }
}
