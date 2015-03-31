package ajedrez;
import java.util.*;
/**
 * * Esta clase representa la Reina de ajedrez 
 ** [...]
 * */
 
public class Reina extends Pieza {

/** * Constructor para la Reina 
 * @param p posición inicial
 * @param color BLANCO o NEGRO
 */
public Reina(Posicion p, int color) { 
super(p, color); }

/** * Crea una nueva pieza igual a esta 
 * @return Una copia de esta pieza 
 */
public Pieza copia() { 
return new Reina(new Posicion(p.col, p.fil), color); } 

/** 
 * devuelve el 'char' que representa a esta pieza 
 * @return Constantes.Reina */
public char piezaAChar() {
return REINA;
}

/** 
 * devuelve los posibles movimientos de la Reina 
 * @param t tablero a considerar
  * @return un ArrayList con todos los movimientos posibles, 
  ** ignorando que puedan dejar el rey propio en jaque o no. 
  */
public ArrayList posiblesMovimientos(Tablero t) { 
ArrayList a1 = new ArrayList();
int i;
// Posibles movimientos en linea recta

for (i=1;i<=8;i++){
    if (t.dentro(p.col,p.fil+i)){
        if (t.vacia(p.col,p.fil+i) || t.get(p.col,p.fil+i).getColor() != this.color){
            a1.add(new Movimiento(p,0,i));
        }
        else break;
    }
}

for (i=1;i<=8;i++){
    if (t.dentro(p.col,p.fil-i)){
        if (t.vacia(p.col,p.fil-i) || t.get(p.col,p.fil-i).getColor() != this.color){
            a1.add(new Movimiento(p,0,-i));
        }
        else break;
    }
}

for (i=1;i<=8;i++){
    if (t.dentro(p.col+i,p.fil)){
        if (t.vacia(p.col+i,p.fil) || t.get(p.col+i,p.fil).getColor() != this.color){
            a1.add(new Movimiento(p,i,0));
        }
        else break;
    }
}

for (i=1;i<=8;i++){
    if (t.dentro(p.col-i,p.fil)){
        if (t.vacia(p.col-i,p.fil) || t.get(p.col-i,p.fil).getColor() != this.color){
            a1.add(new Movimiento(p,-i,0));
        }
        else break;
    }
}

// Posibles movimientos en diagonal

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
 * devuelve 'true' si este caballo puede capturar una pieza en esa 
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
