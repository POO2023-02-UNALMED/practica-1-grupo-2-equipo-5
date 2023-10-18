package gestorAplicacion.Peliculas;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Pelicula implements Serializable{ 
    String nombre;
    private int precio; 
    private Sala sala;
    private String hora;
    private static ArrayList<Pelicula> peliculasExistentes = new ArrayList<Pelicula>();

    //Constructores
    public Pelicula(String nombre){
        this(nombre,0, null);
        Taquilla.agregarPelicula(this);
        Taquilla.agregarPreEstreno(this);
        peliculasExistentes.add(this);
    }
    
    public Pelicula(String nombre, int precio){
        this(nombre, precio, null);
        peliculasExistentes.add(this);
        Taquilla.agregarPelicula(this);
    }

    public Pelicula(String nombre, int precio, String hora){
        this.nombre = nombre;
        this.precio = precio;
        this.hora = hora;
        peliculasExistentes.add(this);
        Taquilla.agregarPelicula(this);
    }

    //Métodos de instancia

    public String getHora(){
        return this.hora;
    }

    public void setHora(String hora){
        this.hora = hora;
    }

    public String getNombre(){
        return this.nombre;
    }   

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getPrecio(){
        return this.precio;
    }

    public void setPrecio(int precio){
        this.precio = precio;
    }

    // método para enlazar una pelicula con una sala, teniendo en cuenta que amabas tengan la misma hora
    public void enlazarSala(Sala s){
        this.sala = s;
        if(this.getHora() != null && this.getHora().equals(s.getHora()) ){
            s.enlazarPeliculaALaSala(this);
        }
    }

    public Sala getSala(){
        return this.sala;
    }

    public List<Integer> getAsientosDisponibles(){
        return sala.getAsientosDisponibles();
    }

    public static ArrayList<Pelicula> getPeliculasExistentes(){
        return peliculasExistentes;
    }

    public static void añadirPeliculaExistente(Pelicula pel){
        peliculasExistentes.add(pel);
    }

    //ocupa un asiento de la sala, si la sala queda sin asientos, la pelicula se retira de la taquilla
    public boolean ocuparAsiento(int num){
        if (this.sala != null) {
            this.sala.quitarAsientoDisponible(num);
            if(this.sala.getNumeroAsientosDisponibles() == 0){
                Taquilla.retirarPelicula(this);
            }
            return true;
        } else {
            return false;
        }
    }

    //si la sala vuelve a tener asientos disponibles, esta vuelve a añadirse a las peliculas en taquilla
    public boolean agregarAsiento(int num){
        if (this.sala != null) {
            if(this.sala.getNumeroAsientosDisponibles() == 0){
                Taquilla.agregarPelicula(this);
            }
            this.sala.añadirAsientoDisponible(num);
            this.sala.añadirNumeroAsientoDisponible();
            return true;
        } else {
            return false;
        }
    }

    //toString
    public String toString(){
        return "Nombre: "+nombre+ "\nPrecio: " + precio+"\nSala: "+sala.getNombre() + "\nAsientos Disponibles: " 
        + sala.getNumeroAsientosDisponibles() + "\nHora: " + this.getHora();
    }
}
