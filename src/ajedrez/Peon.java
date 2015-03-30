/*
 * Peon.java
 *
 */

package ajedrez;

import java.util.*;

/**
 * Esta clase representa un peón de ajedrez
 * @author mf
 */
public class Peon extends Pieza {
    
    private static final int filaAvanceBlanco = 1;
    private static final int filaAvanceNegro = FILS-2;
    private static final int filaCoronaBlanco = FILS-1;
    private static final int filaCoronaNegro = 0;
        
    /**
     * Constructor para un peón
     * @param p posición inicial
     * @param color BLANCO o NEGRO
     */    
    public Peon(Posicion p, int color) {
        super(p, color);
    }

    /**
     * Crea un nuevo peón igual a este
     * @return Un nuevo peón
     */    
    public Pieza copia() {
        return new Peon(new Posicion(p.col, p.fil), color);
    }    
    
    /** devuelve el 'char' que representa a esta pieza
     * @return Constantes.PEON
     */    
    public char piezaAChar() {
        return PEON;
    }    
    
    /**
     * devuelve los posibles movimientos de un peón. Se asume que
     * nunca habrá peones en la última fila (ya habrán coronado).
     * @param t tablero a considerar
     * @return ArrayList con los movimientos añadidos
     */    
    public ArrayList posiblesMovimientos(Tablero t) {
	ArrayList al = new ArrayList();
	int filaAvance = (color==BLANCO)? filaAvanceBlanco : filaAvanceNegro;
        int filaCorona = (color==BLANCO)? filaCoronaBlanco : filaCoronaNegro;
 
        // avanza normal
        if (t.vacia(p.col, p.fil+color)) {
            incrementaMovimientos(al, 0, color, filaCorona);

            // avanza 2
            if (p.fil == filaAvance && t.vacia(p.col, p.fil+2*color)) {
                incrementaMovimientos(al, 0, 2*color, filaCorona);
            }
        }

        // comprueba captura diag. derecha
        if (t.dentro (p.col-1, p.fil+color) &&
            ! t.vacia(p.col-1, p.fil+color) && 
            t.get(p.col-1, p.fil+color).getColor() != color) 
        {
            incrementaMovimientos(al, -1, color, filaCorona);            
        }       

        // comprueba captura diag. izquierda
        if (t.dentro (p.col+1, p.fil+color) &&
            ! t.vacia(p.col+1, p.fil+color) && 
            t.get(p.col+1, p.fil+color).getColor() != color) 
        {
            incrementaMovimientos(al, +1, color, filaCorona);            
        }       
        
        // comprueba captura "al paso"
        if (t.alPaso() != null && t.alPaso().fil == p.fil && 
            Math.abs(t.alPaso().col-p.col) == 1)
        {
            int dcol = t.alPaso().col - p.col;
            incrementaMovimientos(al, dcol, color, filaCorona);   
        }
        
        return al;
    }
     
    private void incrementaMovimientos(ArrayList al, int dx, int dy, int filaCorona) {
        if (p.fil != filaCorona) {
            // no corona
            al.add(new Movimiento(p, dx, dy));
        }
        else {
            // corona
            al.add(new Movimiento(p, dx, dy, Constantes.REINA));
            al.add(new Movimiento(p, dx, dy, Constantes.TORRE));
            al.add(new Movimiento(p, dx, dy, Constantes.ALFIL));
            al.add(new Movimiento(p, dx, dy, Constantes.CABALLO));
        }
    }    

    /**
     * devuelve 'true' si este peón puede capturar una pieza en esa posición
     * jugando ahora, y sin considerar capturas 'al paso'
     * @param t tablero a considerar
     * @param d posición a capturar
     * @return true si capturable, false si no
     */    
    public boolean puedeComer(Tablero t, Posicion d) { 
        return (p.col-1 == d.col || p.col+1 == d.col) && (p.fil+color == d.fil);
    }    
}
