package Meni;

import Korisnici.Administratori;
import Muzika.Albumi;
import Muzika.Izvodjaci;
import Muzika.Pesme;
import Unos.Unos;
import helper.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdministratorskiMeni implements Unos{
    private static void ispisMenija(){
        System.out.println("+==============================+\n" +
                           "|                              |\n" +
                           "|      Dobrodosli nazad !      |\n" +
                           "|                              |\n" +
                           "|  Izaberite jednu od opcija:  |\n" +
                           "|                              |\n" +
                           "|                              |\n" +
                           "|   1. Unos pesme              |\n" +
                           "|   2. Unos izvodjaca          |\n" +
                           "|   3. Unos albuma             |\n" +
                           "|   4. Azuriranje pesme        |\n" +
                           "|   5. Azuriranje izvodjaca    |\n" +
                           "|   6. Azuriranje albuma       |\n" +
                           "|   7. Brisanje pesme          |\n" +
                           "|   8. Brisanje izvodjaca      |\n" +
                           "|   9. Brisanje albuma         |\n" +
                           "|   10. Odjava                 |\n" +
                           "|                              |\n" +
                           "+==============================+\n");
    }
    public static void administratorskiMeni(Administratori administrator) throws Exception{
        ispisMenija();
        int opcija = Unos.unosOpcije();
        switch (opcija){
            case 1:
                if(!Pesme.ubaciPesmuUBazu(administrator)) throw new Exception("Doslo je do greske priliokm ubacivanja pesme");
                System.out.println("\nUspesno ste uneli pesmu\n");
                administratorskiMeni(administrator);
                break;
            case 2:
                if(!Izvodjaci.ubaciIzvodjacaUBazu(administrator)) throw new Exception("Doslo je do greske priliokm ubacivanja izvodjaca");
                System.out.println("\nUspesno ste ubacili izvodjaca\n");
                administratorskiMeni(administrator);
                break;
            case 3:
                if(!Albumi.ubaciAlbumUBazu(administrator)) throw new Exception("Doslo je do greske prilikom ubacivanja albuma");
                System.out.println("\nUspesno ste ubacili album\n");
                administratorskiMeni(administrator);
                break;
            case 4:
                if(!Pesme.azurirajPesmu(administrator)) throw new Exception("Doslo je do greske priliom azuriranja pesme");
                System.out.println("\nUspesno ste azurirali pesmu\n");
                administratorskiMeni(administrator);
                break;
            case 5:
                if(!Izvodjaci.azurirajIzvodjaca(administrator)) throw new Exception("Doslo je do greske prilikom azuriranja izvodjaca");
                System.out.println("\nUspesno ste azurirali izvodjaca\n");
                administratorskiMeni(administrator);
                break;
            case 6:
                if(!Albumi.azurirajAlbum(administrator)) throw new Exception("Doslo je do greske prilikom azuriranja albuma");
                System.out.println("\nUspesno ste azurirali album\n");
                administratorskiMeni(administrator);
                break;
            case 7:
                if(!Pesme.obrisiPesmu(administrator)) throw new Exception("Doslo je do greske prilikom brisanja pesme");
                System.out.println("\nUspesno ste obrisali pesmu\n");
                administratorskiMeni(administrator);
                break;
            case 8:
                if(!Izvodjaci.obrisiIzvodjaca(administrator)) throw new Exception("Doslo je do greske prilikom brisanja izvodjaca");
                System.out.println("\nUspesno ste obrisali izvodjaca i sve njegove pesme i albume\n");
                administratorskiMeni(administrator);
                break;
            case 9:
                if(!Albumi.obrisiAlbum(administrator)) throw new Exception("Doslo je do greske prilikom brisanja albuma");
                System.out.println("\nUspesno ste obrisali album i sve pesme iz albuma\n");
                administratorskiMeni(administrator);
                break;
            case 10:
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                Log.write("\nVreme odjavljivanja: " + dateFormat.format(date) + "\n");
                System.out.println("\n>>> Aktivnosti upisane u datoteku! <<<\n");
                Log.close();
                administrator = null;
                System.out.println("\n>>> Uspesno ste se odjavili! <<<\n");
                GlavniMeni.glavniMeni();
            default:
                System.err.println("Pogresna opcija");
                System.out.println();
                administratorskiMeni(administrator);
                break;
        }
    }
}
