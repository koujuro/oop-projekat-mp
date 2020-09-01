package Exceptions;

public class AlbumException extends Exception{
    private int tipGreske;

    public AlbumException(int greska) {
        this.tipGreske = greska;
    }

    public void printStackTrace() {
        String tekst = "";
        switch (tipGreske){
            case 1: tekst += "\nUneti album ne postoji u nasoj bazi!\n"; break;
            case 2: tekst += "\nVec ste kupili ovaj album!\n"; break;
            case 3: tekst += "\nDoslo je do greske prilikom kupovine albuma!\n"; break;
            default: tekst += "\nNepoznata greska!\n"; break;
        }

        System.err.println(tekst);
    }
}
