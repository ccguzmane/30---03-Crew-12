/*
 * Pieza.java
 *
 */

package ajedrez;

import java.util.*;

/** 
 * Una pieza en el juego del ajedrez.
 * @author mf
 */
public abstract class Pieza implements Constantes {
    
    // Estado interno de una pieza
    
    /** la posicion de la pieza */
    protected Posicion p;
    
    /** el color de la pieza (BLANCO o NEGRO */
    protected int color;
    
    // Métodos
    
    /**
     * devuelve la posición de esta pieza
     * @return la posición
     */    
    public Posicion getPosicion() {
        return p;
    }
    
    /**
     * cambia la posición de esta pieza
     * @param p la nueva posición
     */    
    public void setPosicion(Posicion p) {
        this.p = p;
    }

    /**
     * devuelve el color (BLANCO o NEGRO) de esta pieza
     * @return el color
     */    
    public int getColor() {
        return color;
    }
    
    /** 
     * Constructor
     * @param p posicion de la pieza en el tablero
     * @param color color de la pieza (puede ser BLANCO o NEGRO)
     */
    public Pieza(Posicion p, int color) {
        this.p = p;
        this.color = color;
    }
    
    /** 
     * Metodo de copia. Es mas sencillo de usar que "clone()"
     * @return una copia de la pieza en cuestion
     */
    public abstract Pieza copia();
    
    /** 
     * Genera todos los posibles movimientos para esta pieza en este tablero,
     * sin tener en cuenta jaques.
     * @param t tablero actual
     * @return un ArrayList que contiene los movimientos generados
     */
    public abstract ArrayList posiblesMovimientos(Tablero t);
        
    /**
     * Devuelve 'true' si es posible capturar esta posicion en el
     * tablero actual. Se puede asumir que la posicion contiene una
     * pieza enemiga, no es un peón que haya que comer al paso, 
     * y que está dentro del tablero.
     * @param t el tablero a considerar como 'actual'
     * @param d destino que se debe intentar capturar
     * @return <PRE>true</PRE> si se puede capturar, <PRE>false</PRE> si no
     */    
    public abstract boolean puedeComer(Tablero t, Posicion d);
    
    /** 
     * Devuelve el caracter correspondiente a esta pieza
     * @return el caracter, sin importar mayusculas o minusculas
     */    
    public abstract char piezaAChar();
    
    /** 
     * Fabrica de piezas. Devuelve una pieza nueva del tipo pedido
     * @param c caracter representativo. Debe ser uno de los
     * definidos en {@link Constantes}
     * @param col columna donde colocarla
     * @param fil fila en la que colocarla
     * @return la pieza así generada
     */    
    public static Pieza charAPieza(char c, int col, int fil) {
        int color = Character.isLowerCase(c) ? BLANCO : NEGRO;
        
        switch(Character.toLowerCase(c)) {
            case REY:
                return new Rey(new Posicion(col, fil), color);
		

	    case REINA:
                return new Reina(new Posicion(col, fil), color);
            case TORRE: 
                return new Torre(new Posicion(col, fil), color);
            case ALFIL: 
                return new Alfil(new Posicion(col, fil), color);
            case CABALLO:
                return new Caballo(new Posicion(col, fil), color);	
            case PEON:  
                return new Peon(new Posicion(col, fil), color);
            case VACIO: 
                return null;
        }
        
        return null;
    }
    
    /**
     * devuelve el carácter representativo de la pieza solicitada
     * @param p la pieza
     * @return el carácter, en minúsculas si es blanca o en mayúsculas si es negra
     */    
    public static char piezaAChar(Pieza p) {       
        if (p == null) return VACIO;
        return (p.color == BLANCO) ?
            p.piezaAChar() : Character.toUpperCase(p.piezaAChar());
    }
}
