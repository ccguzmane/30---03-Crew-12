package ajedrez;
import java.util.*;
/**
 * * Esta clase representa un caballo de ajedrez 
 ** [...]
 * */
 
public class Caballo extends Pieza {

    
/** * Constructor para un Caballo 
 * @param p posición inicial
 * @param color BLANCO o NEGRO
 */
public Caballo(Posicion p, int color) { 
super(p, color); }

/** * Crea una nueva pieza igual a esta 
 * @return Una copia de esta pieza 
 */
public Pieza copia() { 
return new Caballo(new Posicion(p.col, p.fil), color); } 

/** 
 * devuelve el 'char' que representa a esta pieza 
 * @return Constantes.Caballo */
public char piezaAChar() {
return CABALLO;
}

/** 
 * devuelve los posibles movimientos de este caballo 
 * @param t tablero a considerar
  * @return un ArrayList con todos los movimientos posibles, 
  ** ignorando que puedan dejar el rey propio en jaque o no. 
  */
public ArrayList posiblesMovimientos(Tablero t) {  
ArrayList a1 = new ArrayList();
// comprueba que el movimiento es válido
if(t.dentro(p.col+2, p.fil-1) && (t.vacia(p.col+2, p.fil-1) || t.get(p.col+2,p.fil-1).getColor() != color)){
a1.add(new Movimiento(p, 2, -1));    
}
if(t.dentro(p.col+2, p.fil+1) && (t.vacia(p.col+2, p.fil+1) || t.get(p.col+2,p.fil+1).getColor() != color)){
a1.add(new Movimiento(p, 2, 1));    
}
if(t.dentro(p.col-2, p.fil-1) && (t.vacia(p.col-2, p.fil-1) || t.get(p.col-2,p.fil-1).getColor() != color)){
a1.add(new Movimiento(p, -2, -1));    
}
if(t.dentro(p.col-2, p.fil+1) && (t.vacia(p.col-2, p.fil+1) || t.get(p.col-2,p.fil+1).getColor() != color)){
a1.add(new Movimiento(p, -2, 1));    
}
if(t.dentro(p.col-1, p.fil+2) && (t.vacia(p.col-1, p.fil+2) || t.get(p.col-1,p.fil+2).getColor() != color)){
a1.add(new Movimiento(p, -1, 2));    
}
if(t.dentro(p.col-1, p.fil-2) && (t.vacia(p.col-1, p.fil-2) || t.get(p.col-1,p.fil-2).getColor() != color)){
a1.add(new Movimiento(p, -1, -2));    
}
if(t.dentro(p.col+1, p.fil+2) && (t.vacia(p.col+1, p.fil+2) || t.get(p.col+1,p.fil+2).getColor() != color)){
a1.add(new Movimiento(p, 1, 2));    
}
if(t.dentro(p.col+1, p.fil-2) && (t.vacia(p.col+1, p.fil-2) || t.get(p.col+1,p.fil-2).getColor() != color)){
a1.add(new Movimiento(p, 1, -2));    
}        
        return a1;
}

/**
 * devuelve 'true' si este caballo puede capturar una pieza en esa 
 * posición jugando ahora, y sin considerar jaques o similares.
 * @param t tablero a considerar 
 * @param d posición a capturar 
 * @return true si capturable, false si no
  */
  
public boolean puedeComer(Tablero t, Posicion d) {
    
return (d.col == p.col+2 || d.col == p.col-2 || d.col == p.col+1 || d.col == p.col-1) &&
        (d.fil == p.fil+2 || d.fil == p.fil-2 || d.fil == p.fil+1 || d.fil == p.fil-1);    
}
}