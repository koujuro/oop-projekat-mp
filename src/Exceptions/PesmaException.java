package Exceptions;

public class PesmaException extends Exception{
    private int tipGreske;

    public PesmaException(int greska) {
        this.tipGreske = greska;
    }

    public void printStackTrace() {
        String tekst = "Krk";
        switch (tipGreske){
            case 1: tekst += "\nUneta pesma ne postoji u nasoj bazi!\n"; break;
            case 2: tekst += "\nVec ste kupili ovu pesmu!\n"; break;
            case 3: tekst += "\nDoslo je do greske prilikom kupovine pesme!\n"; break;
            default: tekst += "\nNepoznata greska!\n"; break;
        }

        System.err.println(tekst);
    }
}
