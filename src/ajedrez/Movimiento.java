/*
 * Movimiento.java
 *
 */

package ajedrez;

/**
 * Esta clase representa un movimiento o jugada de ajedrez. Sólo contiene origen,
 * destino, y si el movimiento supone una coronación, la ficha a la que se
 * promociona el peón.
 * @author mf
 */
public class Movimiento implements Constantes, Comparable {
    
    private Posicion origen;
    private Posicion destino;
    private char promocion;
    
    /** 
     * devuelve el origen del movimiento
     * @return la posicion origen
     */    
    public Posicion getOrigen() {
        return origen;
    }
    
    /** 
     * devuelve el destino de un movimiento
     * @return la posicion destino
     */    
    public Posicion getDestino() {
        return destino;        
    }
    
    /** 
     * devuelve la pieza a la que promociona una jugada
     * @return el caracter representativo de la promocion; si no hay, devuelve VACIO
     */    
    public char getPromocion() {
        return promocion;
    }
    
    /** 
     * devuelve true si el movimiento es una promocion
     * @return true si esta jugada es una promocion
     */    
    public boolean esPromocion() {
        return promocion != Constantes.VACIO;
    }
    
    /**
     * Constructor sencillo
     * @param origen posicion origen
     * @param dcol desplazamiento, en columnas, respecto de origen (puede ser negativo)
     * @param dfil desplazamiento, en filas, respecto del origen (puede ser negativo)
     */    
    public Movimiento(Posicion origen, int dcol, int dfil) {
        this(origen, dcol, dfil, Constantes.VACIO);
    }

    /**
     * Constructor desde cadena
     * @param cadena cadena en el formato devuelto por toString()
     */    
    public Movimiento(String cadena) {
        // 01234 56
        // XN-XN[=C]
        origen = new Posicion(cadena.substring(0, 2));
        destino = new Posicion(cadena.substring(3, 5));
        if (cadena.length() > 5) {
            promocion = cadena.charAt(6);
        }
        else promocion = Constantes.VACIO;
    }

    /**
     * Constructor para coronaciones
     * @param origen posicion origen
     * @param dcol desplazamiento en columnas desde el origen
     * @param dfil desplazamiento en filas desde el origen
     * @param promocion ficha a la que promociona (VACIA, CABALLO, ALFIL, TORRE o REINA)
     */    
    public Movimiento(Posicion origen, int dcol, int dfil, char promocion) {
        this.origen = origen;
        this.destino = new Posicion(origen.col + dcol, origen.fil + dfil);
        this.promocion = promocion;
    }
    
    /** 
     * genera una cadena que describe este movimiento
     * @return una cadena de la forma "E2-E4", o en caso de coronacion, "E7-E8=r"
     */    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(origen.toString() + "-" + destino.toString());
        if (esPromocion()) 
            sb.append("=" + promocion);
        
        return sb.toString();
    }
    
    /** compara esta jugada con otra, a fin de ordenarlas
     * @param o otro movimiento con el que compararlo
     * @return el valor de la comparacion (>, < ó = 0)
     */
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }        
    
    /** compara esta jugada con otra, a fin de comprobar si son iguales
     * @param o otro Movimiento
     * @return el valor de la comparacion (true o false)
     */
    public boolean equals(Object o) {
        if ( ! (o instanceof Movimiento)) return false;
        
        Movimiento m = (Movimiento) o;
        return 
            m.origen.col == origen.col && m.origen.fil == origen.fil &&
            m.destino.col == destino.col && m.destino.fil == destino.fil &&
            m.promocion == promocion;
    }            
}
