package ajedrez;
import java.util.*;
/**
 * * Esta clase representa un Alfil de ajedrez 
 ** [...]
 * */
 
public class Alfil extends Pieza {

/** * Constructor para un alfil
 * @param p posición inicial
 * @param color BLANCO o NEGRO
 */
public Alfil(Posicion p, int color) { 
super(p, color); }

/** * Crea una nueva pieza igual a esta 
 * @return Una copia de esta pieza 
 */
public Pieza copia() { 
return new Alfil(new Posicion(p.col, p.fil), color); } 

/** 
 * devuelve el 'char' que representa a esta pieza 
 * @return Constantes.Alfil */
public char piezaAChar() {
return ALFIL;
}

/** 
 * devuelve los posibles movimientos de un Alfil 
 * @param t tablero a considerar
  * @return un ArrayList con todos los movimientos posibles, 
  ** ignorando que puedan dejar el rey propio en jaque o no. 
  */
public ArrayList posiblesMovimientos(Tablero t) { 
ArrayList a1 = new ArrayList();
int i;

for (i=1;i<=8;i++){
    if (t.dentro(p.col+i,p.fil+i)){
        if (t.vacia(p.col+i,p.fil+i) || t.get(p.col+i,p.fil+i).getColor() != this.color){
            a1.add(new Movimiento(p,i,i));
        }
        else break;
    }
}

for (i=1;i<=8;i++){
    if (t.dentro(p.col+i,p.fil-i)){
        if (t.vacia(p.col+i,p.fil-i) || t.get(p.col+i,p.fil-i).getColor() != this.color){
            a1.add(new Movimiento(p,i,-i));
        }
        else break;
    }
}

for (i=1;i<=8;i++){
    if (t.dentro(p.col-i,p.fil+i)){
        if (t.vacia(p.col-i,p.fil+i) || t.get(p.col-i,p.fil+i).getColor() != this.color){
            a1.add(new Movimiento(p,-i,i));
        }
        else break;
    }
}

for (i=1;i<=8;i++){
    if (t.dentro(p.col-i,p.fil-i)){
        if (t.vacia(p.col-i,p.fil-i) || t.get(p.col-i,p.fil-i).getColor() != this.color){
            a1.add(new Movimiento(p,-i,-i));
        }
        else break;
    }
}

    return a1;
}

/**
 * devuelve 'true' si esta Torre puede capturar una pieza en esa 
 * posición jugando ahora, y sin considerar jaques o similares.
 * @param t tablero a considerar 
 * @param d posición a capturar 
 * @return true si capturable, false si no
  */
  
public boolean puedeComer(Tablero t, Posicion d) {
ArrayList arr = posiblesMovimientos(t);
return arr.indexOf(new Movimiento(this.getPosicion(), d.fil, d.col))!= -1;
}

}