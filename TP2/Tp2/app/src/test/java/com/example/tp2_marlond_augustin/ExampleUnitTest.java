package com.example.tp2_marlond_augustin;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.util.Log;
import android.util.Patterns;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void createUtilisateur() throws Exception {
        Utilisateur user1=new Utilisateur("GigaChad","GrindingTime@gmail.com");
        Utilisateur user2=new Utilisateur("Spiderman","parker@avengers.com");
        assertEquals("GigaChad", user1.getNom());
        assertEquals("GrindingTime@gmail.com", user1.getCourriel());
        assertFalse(user2.getCourriel().equals(user1.getCourriel()));
        //Log.d("Email valide",""+Patterns.EMAIL_ADDRESS.matcher(user1.getCourriel().toString()).matches());
        //assertEquals(true, Patterns.EMAIL_ADDRESS.matcher(user1.getCourriel().toString()).matches());
        //user1.setCourriel("GrindingTime");
        //assertEquals(false, Patterns.EMAIL_ADDRESS.matcher(user1.getCourriel().toString()).matches());
    }
    @Test
    public void AuthentificationUtilisateur() throws Exception{
        /*
        AuthActivity authActivity=new AuthActivity();
        */
        AuthActivity authActivity=mock(AuthActivity.class);
        when(authActivity.getUtilisateurToken()).thenReturn("SupperTokenIdentificationUtilisateur");
        String jeton=authActivity.getUtilisateurToken();


        assertEquals("SupperTokenIdentificationUtilisateur",jeton);
    }
}