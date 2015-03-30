/*
 * Utilidades.java
 *
 */

package ajedrez;

import java.io.*;
import java.util.*;

/**
 * 
 * Clase para utilidades varias no relacionadas con el juego del ajedrez.
 *
 * @author  mf
 */
public class Utilidades {

    /** Lee una cadena (una línea) de stdin
     * @return la cadena leída, o null si error
     */
    public static String getString() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            s = br.readLine();
        }
        catch (Exception e) {
        }
        return s;
    } 
    
    /** Ordena una coleccion (por ejemplo, por ejemplo,
     * una lista de jugadas)
     * @param c colección a ordenar (no comprueba que esté ordenada)
     */
    public static void sortCollection(Collection c) {
        TreeSet ts = new TreeSet(c);
        c.clear();
        c.addAll(ts);
    }    
}
