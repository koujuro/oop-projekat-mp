package Meni;

import Login.Login;
import Unos.Unos;

public class GlavniMeni {

    public static void glavniMeni(){
        ispisOpcija();
        int opcija = Unos.unosOpcije();
        switch(opcija){
            case 1:
                Login.login();
                break;
            case 2:
                System.exit(0);
            default:
                System.err.println("Pogresno uneta opcija!\n\n");
                glavniMeni();
                break;
        }
    }

    public static void ispisOpcija(){
        System.out.println("+===========================================+\n" +
                           "|                                           |\n" +
                           "|  Dobrodosli u muzicku prodavnicu TRZAJ !  |\n" +
                           "|   Nase arije su Vasa harmonija zivota !   |\n" +
                           "|                                           |\n" +
                           "|        Izaberite jednu od opcija:         |\n" +
                           "|                                           |\n" +
                           "|                                           |\n" +
                           "|          1. Login                         |\n" +
                           "|          2. Izlaz iz aplikacije           |\n" +
                           "|                                           |\n" +
                           "|                                           |\n" +
                           "+===========================================+\n");
    }
}
