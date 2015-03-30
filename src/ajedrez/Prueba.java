/*
 * Ajedrez.java
 */

package ajedrez;

import java.util.*;
import java.io.*;

/**
 * Clase de prueba para la práctica 1: java y movimiento de fichas
 *
 * @author  mf
 */
public class Prueba implements Constantes {    
    
    /** La ejecución depende del número de argumentos:<ul><li>
     * Con 1 argumento:
     * Muestra el estado del tablero por stderr, y  muestra por
     * stdout todas las jugadas que se pueden generar para el
     * jugador al que le toca mover.
     * </li><li>
     * Con 2 argumentos: compara las jugadas que puede generar
     * a partir del tablero leido con las jugadas almacenadas en
     * el fichero del segundo ejercicio. Muestra un mensaje
     * para cada jugada que difiere entre ambos tableros.</li></ul>
     * @param args <ol><li>primer argumento: nombre del fichero con
     * descripcion de tablero</li><li>
     * segundo argumento (opcional): nombre del fichero de
     * salida con el que comparar las jugadas generadas
     * para el tablero del primer argumento. </li></ol>
     * @throws IOException cuando algun fichero no se puede leer correctamente
     */    
    public static void main(String[] args) throws IOException {
        
        Tablero t;
        switch (args.length) {
            case 1:
                t = new Tablero(args[0]);
                System.err.println(t);
                muestraJugadas(t);
                break;
            
            case 2:
                t = new Tablero(args[0]);
                ArrayList generadas = t.generaMovimientos();
                ArrayList leidas = leeJugadas(args[1]);
                TreeSet soloEnGeneradas = new TreeSet(generadas);
                soloEnGeneradas.removeAll(leidas);
                TreeSet soloEnLeidas = new TreeSet(leidas);
                soloEnLeidas.removeAll(generadas);
                Iterator it;
                if (soloEnGeneradas.size() == 0 && soloEnLeidas.size() == 0) {
                    System.out.println("Salida correcta");
                }
                else {
                    System.out.println("Salida incorrecta");
                    
                    for (it=soloEnLeidas.iterator(); it.hasNext(); /**/) {
                        System.out.println("\tfalta: "+it.next());
                    }
                    for (it=soloEnGeneradas.iterator(); it.hasNext(); /**/) {
                        System.out.println("\tsobra: "+it.next());
                    }
                }
                break;
                
            default:
                System.err.println("Número erróneo de argumentos. Uso:\n"
                    +"java "+Prueba.class.getName()+" "
                    +"<nombre_fichero_tablero> " 
                    +"[ <nombre_fichero_movimientos> ]");
                break;
        }
        
        return;
    }

    /**
     * Genera y muestra todas las jugadas de un tablero dado
     * @param t el tablero a usar
     */    
    public static void muestraJugadas(Tablero t) {
        ArrayList al = t.generaMovimientos();
        Utilidades.sortCollection(al);
        for (int i=0; i<al.size(); i++) {
            System.out.println(al.get(i));
        }
    }
    
    /** 
     * Lee una lista de movimientos (jugadas) de un fichero.
     * @return un ArrayList con las jugadas leídas de la lista
     * @param nombreFichero nombre del fichero con la lista de jugadas
     * @throws IOException si se detectan errores de lectura
     */    
    public static ArrayList leeJugadas(String nombreFichero) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
        ArrayList al = new ArrayList();
        String linea;
        
        while ((linea = br.readLine()) != null) {
            al.add(new Movimiento(linea));
        }
        
        return al;
    }
}
