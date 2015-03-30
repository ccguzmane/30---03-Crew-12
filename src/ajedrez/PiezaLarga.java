/*
 * PiezaLarga.java
 */

package ajedrez;

import java.util.*;

/**
 * Clase PiezaLarga, un invento para sacar factor común de Reina, Torre y Alfil
 *
 * NOTA: Esta clase constituye una posible implementacion para sacar factor 
 * común de piezas que se mueven en línea recta. No está bien comentada (no 
 * debería formar parte de la entrega), por lo que, si se decide usar, habrá 
 * que proporcionar los comentarios adecuados a todos sus métodos.
 *
 * Hay tres posibles formas de uso: <ul>
 * <li> hacer que Reina, Torre y Alfil sean subclases de esta clase (en lugar
 * de ser subclases directas de Pieza). Entonces podrán llamar a los métodos 
 * posiblesMovimientos y puedeComer de forma sencilla. </li>
 * <li> modificar esta clase para que no sea subclase de nada, y convertir sus
 * métodos en "static", de forma que se puedan llamar sin instanciarla. 
 * En ése caso, habrá que llamarlos como PiezaLarga.posiblesMovimientos(...)
 * </li>
 * <li> copiar y pegar sus métodos para incluirlos en las clases que los
 * necesiten</li></ul>
 *
 * @author  mf
 */
public abstract class PiezaLarga extends Pieza {
    
    /** Creates a new instance of PiezaLarga */
    public PiezaLarga(Posicion p, int color) {
        super(p,color);
    }

    public void posiblesMovimientos(Tablero t, ArrayList al, int dcol, int dfil) {
	int mcol = 0;
        int mfil = 0;
        boolean libre = true;
        for (int i=1; libre && i<COLS; i++) {
            mcol += dcol;
            mfil += dfil;
            if (t.dentro(p.col+mcol, p.fil+mfil) && 
                t.vacia(p.col+mcol, p.fil+mfil)) 
            {
                al.add(new Movimiento(p, mcol, mfil));
            }
            else {
                libre = false;
                if (t.dentro(p.col+mcol, p.fil+mfil) && 
                    t.get(p.col+mcol, p.fil+mfil).color != color) 
                {
                    al.add(new Movimiento(p, mcol, mfil));
                }
            }
        }
    }
            
    public boolean puedeComer(Tablero t, Posicion d, int dcol, int dfil) {
        int col = p.col;
        int fil = p.fil;
        for (int i=1; i<COLS+1; i++) {
            col += dcol;
            fil += dfil;
            
            if ( ! t.dentro(col, fil)) return false;
           
            if ( col == d.col && fil == d.fil) return true;
            
            if ( ! t.vacia(col, fil)) return false;
        }
        return false;
    }    
}
