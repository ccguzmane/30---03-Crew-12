/*
 * Tablero.java
 */

package ajedrez;

import java.io.*;
import java.util.*;

/**
 * Representa un tablero de ajedrez. Las operaciones principales son
 * <ul>
 * <li>comprobar estado:</li><ul>
 * <li>pieza en posicion x,y: get()</li>
 * <li>jaque del jugador actual: isEnJaque()</li>
 * <li>enroque: getEnroqueBlancas, getEnroqueNegras()</li></ul>
 * </li>
 * <li>generar jugadas: generaMovimientos()</li>
 * <li>mover: mueve()</li>
 * </ul>
 *
 * @author  mf
 */
public class Tablero implements Constantes {
        
    // Informacion de estado de un tablero
    
    private Pieza[][] T;
    private byte enroqueBlancas;
    private byte enroqueNegras;
    private boolean enJaque;
    private Posicion alPaso = null;
    private int turno;
    
    // Cosas varias
    
    /**
     * Devuelve "true" si toca mover a las blancas
     * @return true si juegan blancas
     */
    public boolean jueganBlancas() {
        return turno == BLANCO;
    }   
    
    /**
     * Devuelve la pieza que está en una posición dada. Si la posicion
     * está fuera del tablero, saltará una excepción
     * @param col columna
     * @param fil fila
     * @return la pieza
     */
    public Pieza get(int col, int fil) {
        return T[fil][col];
    }
    
    /**
     * Devuelve el color del jugador que debe mover ahora
     * @return el turno, BLANCO o NEGRO
     */
    public int getTurno() {
        return turno;
    }
    
    /**
     * Modifica la pieza que está en una posición dada.
     * @param col columna
     * @param fil fila
     * @param la pieza
     */
    private void set(int col, int fil, Pieza pieza) {
        T[fil][col] = pieza;
    }
    
    /**
     * Devuelve 'true' si la posicion esta dentro del tablero
     * @param col columna
     * @param fil fila
     * @return true si está dentro
     */
    public boolean dentro(int col, int fil) {
        return (fil >= 0 && fil < FILS && col >= 0 && col < COLS);
    }
    
    /** devuelve la posicion de un peon que avanzo 2 en la ultima
     * jugada, o null si no hubo tal
     * @return la posicion del peon que avanzó 2 en la última jugada (o null si no lo hubo)
     */
    public Posicion alPaso() {
        return alPaso;
    }
    
    /** Devuelve si una posicion dada esta vacia o no
     * @param col columna
     * @param fil fila
     * @return true si vacia, false si no
     */
    public boolean vacia(int col, int fil) {
        return get(col, fil) == null;
    }
    
    /**
     * Coloca las piezas tal y como estan descritas en una cadena
     * @param s la cadena, en formato 'char a colocar en (0,0), (0,1) ... (8,8)'
     */
    public void colocaPiezas(String s) {
        Pieza pieza;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                pieza = Pieza.charAPieza(s.charAt(j*COLS+i), i, j);
                set(i, j, pieza);
            }
        }
    }
    
    /**
     * Busca el rey de un color dado en un tablero
     * @param color BLANCO o NEGRO, del rey a buscar
     * @return la posición del rey
     */
    public Posicion buscaRey(int color) {
        Pieza actual;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                actual = get(i,j);
                if (actual != null && 
                    (actual instanceof Rey) &&
                    actual.getColor() == color)
                {
                    return actual.getPosicion();
                }
            }
        }
        
        throw new IllegalArgumentException("Rey no encontrado!");
    }
    
    /** 
     * Ejecuta un movimiento en el tablero, comprobando jaque
     * @param m movimiento a llevar a cabo
     */
    public void mueve(Movimiento m) {
        mueveSinJaque(m);
        
        // comprueba si el rey del jugador que debe mover es vulnerable
        Posicion posRey = buscaRey(turno);
        enJaque = amenazada(posRey.col, posRey.fil, -1*turno);
    }
    
    /** 
     * Ejecuta un movimiento en el tablero, sin comprobar si lleva a jaque
     * @param m movimiento a ejecutar
     * @return la posición del rey, si ha cambiado, o null si el rey no ha movido
     */
    public Posicion mueveSinJaque(Movimiento m) {
        Posicion origen = m.getOrigen();
        Posicion destino = m.getDestino();
        Posicion viejaAlPaso = alPaso;
        Posicion posRey = null;
        Pieza movida = get(origen.col, origen.fil);
        
        // limpia el origen, la bandera de 'al paso', y cambia el turno
        set(origen.col, origen.fil, null);
        alPaso = null;
        turno *= -1;
        
        if (movida instanceof Peon) {
            // promocion            
            if (m.esPromocion()) {
                Pieza nueva = 
                    Pieza.charAPieza(m.getPromocion(), destino.col, destino.fil);
                set(destino.col, destino.fil, nueva);
                return null;
            }         
            
            // comer al paso          
            if (viejaAlPaso != null &&
                destino.fil == (viejaAlPaso.fil-movida.getColor()) && 
                Math.abs(destino.col-viejaAlPaso.col) == 1) 
            {
                set(destino.col, destino.fil-movida.color, null);
            }
            
            // poner bandera de 'al paso' si avanza dos
            if (Math.abs(origen.fil-destino.fil) > 1) {
                alPaso = destino;
            }
        }                
        else if (movida instanceof Rey) {            
            
            // enroque
            if (Math.abs(origen.col-destino.col) >1) {            
                // mueve la torre
                int colTorreOrigen;
                int colTorreDestino;
                if (destino.col == Rey.colDestCorto) {
                    colTorreOrigen = Rey.colTorreCorto;
                    colTorreDestino = Rey.colMediaCorto;
                }
                else {
                    colTorreOrigen = Rey.colTorreLargo;
                    colTorreDestino = Rey.colMediaLargo;
                }
                Pieza torre = get(colTorreOrigen, destino.fil);                
                set(colTorreDestino, destino.fil, torre);
                set(colTorreOrigen, destino.fil, null);
                torre.setPosicion(new Posicion(colTorreDestino, destino.fil));
            }
            
            // una vez movido, el rey no puede enrocar
            if (movida.getColor()==BLANCO) {
                enroqueBlancas = ENROQUE_NO;
            }
            else {
                enroqueNegras = ENROQUE_NO;
            }            
            
            // actualiza pos. del rey
            posRey = destino;            
        }        
        else if (Pieza.piezaAChar(movida) == TORRE) {
	// el uso de piezaAChar permite compilar sin la clase 'Torre' definida
            
            // una vez se ha movido una torre, no se puede enrocar con ella
            if (origen.col == Rey.colTorreCorto) {
                if (movida.getColor()==BLANCO) {
                    enroqueBlancas &= ~ENROQUE_CORTO;
                }
                else {
                    enroqueNegras &= ~ENROQUE_CORTO;
                }
            }
            else if (origen.col == Rey.colTorreLargo) {
                if (movida.getColor()==BLANCO) {
                    enroqueBlancas &= ~ENROQUE_LARGO;
                }
                else {
                    enroqueNegras &= ~ENROQUE_LARGO;
                }
            }
        }            
        
        set(destino.col, destino.fil, movida);
        movida.setPosicion(new Posicion(destino.col, destino.fil));
        return posRey;
    }
    
    /**
     * Genera todos los movimientos válidos para el jugador actual en este tablero
     * @return un ArrayList que contiene los movimientos validos
     */    
    public ArrayList generaMovimientos() {
        ArrayList todas = new ArrayList();
        ArrayList buenas = new ArrayList();
	ArrayList nuevas;
        Pieza actual;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                if (vacia(i, j) || get(i, j).getColor() != turno) continue;
                nuevas = get(i, j).posiblesMovimientos(this);
		todas.addAll(nuevas);
            }
        }
        
        // depura la lista eliminando las que llevan a jaque        
        Posicion posReyInicial = buscaRey(turno);
        Posicion posRey;
        
        for (int i=0; i<todas.size(); i++) {
            Tablero t = new Tablero(this);
            // mueveSinJaque devuelve la pos. del rey solo si ha cambiado
            posRey = t.mueveSinJaque((Movimiento)todas.get(i));
            if (posRey == null) posRey = posReyInicial;            
            // si el contrario puede NO comer nuestro rey, la jugada es buena
            if ( ! t.amenazada(posRey.col, posRey.fil, t.turno)) {
                buenas.add(todas.get(i));
            }
        }
        
        return buenas;
    }
    
    /** devuelve 'true' si el jugador 'color' puede capturar una pieza
     * contraria (supuestamente) situada en 'p'
     * @param col columna de la posición cuyo estado de amenaza se quiere comprobar
     * @param fil columna de la posición cuyo estado de amenaza se quiere comprobar
     * @param color jugador (BLANCO o NEGRO) que estaría amenazando esa posición
     * @return true si amenazada, false si no
     */
    public boolean amenazada(int col, int fil, int color) {        
        Pieza actual;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                actual = get(i, j);
                if (actual != null && 
                    actual.getColor() == color && 
                    actual.puedeComer(this, new Posicion(col, fil))) 
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Devuelve el estado del enroque del jugador 'color'
     * @param color BLANCO o NEGRO
     * @return ENROQUE_AMBOS, ENROQUE_CORTO, ENROQUE_LARGO, o ENROQUE_NO
     */
    public int getEnroque(int color) {
        return (color == BLANCO) ? enroqueBlancas : enroqueNegras;
    }
    
    /** Devuelve una cadena representando un estado de enroque
     * @param enroque el estado de enroque en forma numérica (ENROQUE_*)
     * @return una cadena descriptiva (ambos, corto, largo, no)
     */
    public String enroqueToString(int enroque) {
        switch(enroque) {
            case ENROQUE_AMBOS: return "ambos";
            case ENROQUE_CORTO: return "corto";
            case ENROQUE_LARGO: return "largo";
            case ENROQUE_NO: return "no";
            default: return "???";
        }
    }
    
    /** 
     * Crea una cadena que representa un tablero
     * @return la cadena pedida. El formato no es compatible con colocaPiezas: es más bonito
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        // dibuja el tablero, con coordenadas
        sb.append("  ");
        for (int i=0; i<COLS; i++) {
            sb.append(" "+(char)('A'+i));
        }
        sb.append("\n\n");
        for (int j=FILS-1; j>=0; j--) {
            sb.append((char)('1'+j)+"  ");            
            for (int i=0; i<COLS; i++) {        
                sb.append(Pieza.piezaAChar(get(i, j)) + " ");
            }
            sb.append("\n");
        }
        
        // describe el estado
        sb.append("\nturno: "+(turno==BLANCO?"blancas":"negras"));
        sb.append("\tjaque: "+(enJaque?"si":"no"));
        sb.append("\nenrB: "+enroqueToString(enroqueBlancas));
        sb.append("  enrN: "+enroqueToString(enroqueNegras));
        sb.append("  alPaso: "+(alPaso==null?"no":alPaso.toString()));
        sb.append("\n");
        
        return sb.toString();
    }              
    
    // Constructores
    
    /**
     * Constructor para un tablero de ajedrez, por defecto
     */
    public Tablero() {
        this(posInicial, ENROQUE_AMBOS, ENROQUE_AMBOS, false, null, BLANCO);
    }    
    
    /** Constructor para un tablero de ajedrez con todo el estado
     * @param posicion cadena que describe la posicion inicial, con
     * formato char de (0,0), (0,1), ... (8,8)
     * @param enroqueBlancas si es false, el jugador blanco ya no puede enrocar
     * @param enroqueNegras si es false, el jugador negro ya no puede enrocar
     * @param enJaque el jugador actual está en jaque
     * @param alPaso si en la última jugada un peón avanzó 2 casillas,
     * alPaso contendrá su posición. En caso contrario, null
     * @param turno BLANCO o NEGRO, el jugador que debe mover
     */
    public Tablero(String posicion, 
        int enroqueBlancas, int enroqueNegras, 
        boolean enJaque, Posicion alPaso, int turno) 
    {
        T = new Pieza[FILS][COLS];
        colocaPiezas(posicion);
        this.enJaque = enJaque;
        this.enroqueBlancas = (byte)enroqueBlancas;
        this.enroqueNegras = (byte)enroqueNegras;
        this.alPaso = (alPaso==null)? 
            null : new Posicion(alPaso.fil, alPaso.col);     
        this.turno = turno;
    }
        
    /**
     * Constructor de copia
     * @param t Tablero a copiar
     */
    public Tablero(Tablero t) {
        T = new Pieza[FILS][COLS];
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                set(i, j, (t.vacia(i, j)? null : t.get(i, j).copia()));
            }
        }
        enJaque = t.enJaque;
        enroqueBlancas = t.enroqueBlancas;
        enroqueNegras = t.enroqueNegras;
        alPaso = (t.alPaso==null)? 
            null : new Posicion(t.alPaso.col, t.alPaso.fil);        
        turno = t.turno;
    }
    
    /**
     * Constructor desde fichero
     *
     * Formato: <ul><li>
     * FILS lineas, de COLS*2 caracteres cada, con el tablero </li><li>
     * 1 línea, con una cadena para 'turno' ("blancas" o "negras") y un 
     * booleano para 'jaque' ("true" o "false")</li><li>
     * 1 linea, con enteros para 'enrB' y 'enrN' (formato devuelto por 
     * getEnroque()), cadena con posicion para 'alPaso' (formato "E2")</li></ul>
     * @param filename el nombre del fichero
     * @throws IOException si hay errores en lectura
     */
    public Tablero(String filename) throws IOException {                
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringTokenizer st;
        
        // lee tablero en si
        T = new Pieza[FILS][COLS];
        StringBuffer sb = new StringBuffer(FILS*COLS);
        for (int i=0; i<FILS*COLS; i+=COLS) { 
            sb.insert(0, br.readLine().replaceAll(" ", ""));
        }
        colocaPiezas(sb.toString());
                
        // lee turno y jaque
        st = new StringTokenizer(br.readLine());
        turno = (st.nextToken().equals("blancas")) ? BLANCO : NEGRO;                             
        enJaque = Boolean.getBoolean(st.nextToken());       

        // lee enroques y 'alPaso'
        st = new StringTokenizer(br.readLine());
        enroqueBlancas = Byte.parseByte(st.nextToken());
        enroqueNegras = Byte.parseByte(st.nextToken());
        String s = st.nextToken(); // alPaso puede ser "null"
        Posicion alPaso = (s.equals("null")) ? null : new Posicion(s) ;       
    }       
}
