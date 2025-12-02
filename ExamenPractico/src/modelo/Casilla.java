package modelo;

/* -Representa una celda individual en el tablero del Buscaminas.
   -Implementa el encapsulamiento para proteger los estados internos.*/
 
public class Casilla {

    // ==============Atributos=========== 
    private boolean tieneMina;
    private boolean estaDescubierta;
    private boolean estaMarcada; 
    private int minasAdyacentes;
    private final int fila;
    private final int columna;

    // ================Constructor==============
     
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        //A PESAR DE NO SER UN PARAMETRO A RECIBIR, COMO BUENA PRACTICA SE DEBE INICAR EN 0 O FALSO
        this.tieneMina = false;
        this.estaDescubierta = false;
        this.estaMarcada = false;
        this.minasAdyacentes = 0;
    }

    //=============Métodos Modificadores==============
    
    //METODO QUE COLOCA LA MINA
    public void colocarMina() {
        this.tieneMina = true;
    }
    
    // METODO DE CORRECTO NUMERO DE MINAS
    public void setMinasAdyacentes(int minasAdyacentes) {
        this.minasAdyacentes = minasAdyacentes;
    }
    
    // METODO PARA CAMBIAR EL ESTADO DE LA CASILLA
    public void desmarcar() {
        this.estaDescubierta = true;
    }
    
    // METODO PARA MARCAR O DESMARCAR CASILLA
    public void marcar() {
        if (!estaDescubierta) { // Solo se puede marcar si no ha sido desmarcada
             this.estaMarcada = !this.estaMarcada; 
        }
    }

    // ================METODOS DE ACCESO==================

    //METODO SI TIENE MINA DEVUELVE QUE TIENE MINA ¿TIENE MINA? SI/NO
    public boolean tieneMina() {
        return tieneMina;
    }
    
    //METODO PREGUNTA ESTA DESCUBIERTO SI/NO
    public boolean estaDescubierta() {
        return estaDescubierta;
    }

  //METODO PREGUNTA ESTA MARCADA SI/NO
    public boolean estaMarcada() {
        return estaMarcada;
    }
    
    //METODO DEVUELVE EL NUMERO DE MINAS ADYACENTES
    public int getMinasAdyacentes() {
        return minasAdyacentes;
    }
    
    //OBTIENE LA UBICACIÓN
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve la representación en String de la casilla para la consola.
     * Esta lógica será usada por la Vista.
     */
    @Override
    public String toString() {
        //SI ESTA MARCADA COLOCA
    	if (estaMarcada) {
            return "\nU+2713"; // retorna un visto
        }
    	//si no esta decubierta tapa la casilla
        if (!estaDescubierta) {
            return "☐"; // Casilla cubierta
        }
        //si tiene mina devuelve una X
        if (tieneMina) {
            return "X"; // Ubicación de una mina
        }
        //muestra cuantas minas estan en sus 8 espacios circundantes
        if (minasAdyacentes > 0) {
            return String.valueOf(minasAdyacentes); // Muestra el número (valueOf lo convierte de int a String)
        }
        // Si es descubierta y tiene 0 minas adyacentes
        return "minas circundantes: "+ minasAdyacentes; // Espacio vacío seleccionado ESTE ES MODIFICADOO REVISAR PREVIO AL ENVIO
    }
}