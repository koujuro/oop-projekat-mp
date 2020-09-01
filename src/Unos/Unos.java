package Unos;

import Korisnici.Podaci;

import java.util.Scanner;

public interface Unos {
    static int unosOpcije(){
        Scanner unos = new Scanner(System.in);
        System.out.println("Unesite opciju:");
        return unos.nextInt();
    }

    static Podaci unosLoginPodataka(){
        Scanner unos = new Scanner(System.in);
        System.out.println("Unesite korisnicko ime:");
        String username = unos.nextLine();
        System.out.println("Unesite lozinku:");
        String password = unos.nextLine();
        return new Podaci(username, password);
    }

    // IZVODJAC
    static int unosIdIzvodjaca(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite ID izvodjaca:");
        return unos.nextInt();
    }

    static String unosIdIzvodjacaKaoString(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite ime izvodjaca:");
        return unos.nextLine();
    }

    static String unosImenaIzvodjaca(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite ime izvodjaca:");
        return unos.nextLine();
    }

    static String unosTipaIzvodjaca(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite tip izvodjaca(SOLO/DUO/BEND):");
        return unos.nextLine();
    }

    static int unosGodineOsnivanja(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite godinu osnivanja:");
        return unos.nextInt();
    }

    static String unosGodineOsnivanjaKaoString(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite godinu osnivanja:");
        return unos.nextLine();
    }

    static String unosGodineRaspadaKaoString(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite godinu raspada:");
        return unos.nextLine();
    }

    static String unosBiografije(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite biografiju(Do 500 karaktera):");
        return unos.nextLine();
    }

    // PESMA
    static int unosIdPesme(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite ID pesme:");
        return unos.nextInt();
    }

    static String unosNazivaPesme(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite naziv pesme:");
        return unos.nextLine();
    }

    static String unosTrajanjaPesme(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite trajanje pesme u formatu - hh:mm:ss:");
        return unos.nextLine();
    }

    // ALBUM

    static int unosIdAlbuma(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite ID albuma:");
        return unos.nextInt();
    }

    static String unosIdAlbumaKaoString(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite ID albuma:");
        return unos.nextLine();
    }

    static String unosNazivaAlbuma(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite naziv albuma:");
        return unos.nextLine();
    }

    static int unosGodineIzdavanja(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite godine izdavanja:");
        return unos.nextInt();
    }

    static String unosGodineIzdavanjaKaoString(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite godine izdavanja:");
        return unos.nextLine();
    }

    static String unosZanra(){
        Scanner unos = new Scanner(System.in);
        System.out.println("\nUnesite zanr albuma:");
        return unos.nextLine();
    }

    static boolean upozorenjeNaAkcijuBrisanja(){
        Scanner in = new Scanner(System.in);
        System.out.println("\nDa li ste sigurni da zelite da obrisete podatak? (da/ne)");
        String provera = in.nextLine();
        if(provera.toLowerCase().equals("da")){
            return true;
        }
        return false;
    }




}
