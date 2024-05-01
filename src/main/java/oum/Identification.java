package oum;

import jakarta.servlet.http.Cookie;

public class Identification {
    
    public static String chercheNom(Cookie[] cookies) {
        String nom = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("nom")) {
                    nom = cookie.getValue();
                    break;
                }
            }
        }
        return nom;
    }  
    public static String verifier(Cookie[] cookies) {
        String nom = chercheNom(cookies);
        if (nom == null) {
            nom = "Inconnu";
        }
        return nom;
    }
}
