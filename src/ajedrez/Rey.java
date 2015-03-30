/*
 * Rey.java
 */

package ajedrez;

import java.util.*;

/**
 * Esta clase representa un Rey en el juego del ajedrez. También contiene
 * constantes relativas a enroques
 * @author mf
 */
public class Rey extends Pieza {        
    
    // constantes relativas al enroque
    
    /** columna de la torre en un enroque corto */
    public static final int colTorreCorto = COLS-1;

    /** columna de destino del rey en un enroque corto */
    public static final int colDestCorto = COLS-2;

    /** columna de paso del rey/destino de la torre en el enroque corto */
    public static final int colMediaCorto = COLS-3;
        
    /** columna de la torre en un enroque corto */
    public static final int colTorreLargo = 0;
    
    /** columna de destino del rey en un enroque corto */
    public static final int colDestLargo = 2;
    
    /** columna de paso del rey/destino de la torre en el enroque corto */
    public static final int colMediaLargo = 3;
    
    // array constante con los posibles movimientos de un rey    
    private static int[][] movs = 
        {{1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}, {0,1}};
            
    /**
     * Constructor para un nuevo rey
     * @param p posición que ocupará
     * @param c color (BLANCO o NEGRO) del rey
     */
    public Rey(Posicion p, int c) {
        super(p, c);
    }
    
    /**
     * Crea una copia de este rey
     * @return la copia
     */    
    public Pieza copia() {
        return new Rey(new Posicion(p.col, p.fil), color);
    }
    
    /**
     * Genera todos los posibles movimientos de este rey en este tablero (aunque sean
     * ilegales por ponerle en riesgo)
     * @param t tablero a considerar
     * @return un ArrayList con todos los movimientos válidos encontrados
     */    
    public ArrayList posiblesMovimientos(Tablero t) {
        ArrayList al = new ArrayList();
	    
        // movimientos normales
        Pieza otra;        
        for (int i=0; i<movs.length; i++) {
            if ( ! t.dentro(p.col+movs[i][0], p.fil+movs[i][1])) continue;
            
            otra = t.get(p.col+movs[i][0], p.fil+movs[i][1]);
            if (otra == null || otra.color != color) {
                al.add(new Movimiento(p, movs[i][0], movs[i][1]));
            }
        }
        
        // comprueba si la clase Torre está definida. Si no lo está, para aquí.
        if (Pieza.charAPieza(Constantes.TORRE, 0, 0) == null) return al;
        
        // enroques: si se puede, la torre y el rey estan en su pos. inicial
        if (t.getEnroque(color) != ENROQUE_NO) {
            if ((t.getEnroque(color) & ENROQUE_CORTO) != 0) {
                if (t.vacia(colDestCorto, p.fil) &&
                    t.vacia(colMediaCorto, p.fil) &&
                    ( ! t.amenazada(colDestCorto, p.fil, -1*color)) &&
                    ( ! t.amenazada(colMediaCorto, p.fil, -1*color))) 
                {
                    al.add(new Movimiento(p, colDestCorto-p.col, 0)); 
                }
            }
            if ((t.getEnroque(color) & ENROQUE_LARGO) != 0) {
                if (t.vacia(colDestLargo, p.fil) &&
                    t.vacia(colMediaLargo, p.fil) &&
                    ( ! t.amenazada(colDestLargo, p.fil, -1*color)) &&
                    ( ! t.amenazada(colMediaLargo, p.fil, -1*color))) 
                {
                    al.add(new Movimiento(p, colDestLargo-p.col, 0)); 
                }                
            }
        }
        
        return al;
    }
    
    /**
     * Devuelve 'true' si el rey puede capturar esta posición si juega ahora
     * @param t tablero a considerar
     * @param d posición a tratar de capturar
     * @return true si capturable, false si no
     */    
    public boolean puedeComer(Tablero t, Posicion d) {
        return (p.col == d.col || p.col == d.col+1 || p.col == d.col-1) &&
               (p.fil == d.fil || p.fil == d.fil+1 || p.fil == d.fil-1);
    }            
    
    /**
     * Devuelve el 'char' que representa a un rey
     * @return Constantes.REY
     */    
    public char piezaAChar() {
        return REY;
    }
}
