package Logic.Peliculas;

import java.util.ArrayList;
import java.util.List;

public class Pelicula { 
    String nombre;
    private int precio; 
    private Sala sala;
    private static ArrayList<Pelicula> peliculasExistentes = new ArrayList<Pelicula>();

    //Constructores
    public Pelicula(String nombre){
        this(nombre,0);
    }
    
    public Pelicula(String nombre, int precio){
        this.nombre = nombre;
        this.precio = precio;
        peliculasExistentes.add(this);
        Taquilla.agregarPelicula(this);
    }

    //Métodos de instancia

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

    public void enlazarSala(Sala s){
        this.sala = s;
        s.enlazarPeliculaALaSala(this);
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
        + sala.getNumeroAsientosDisponibles();
    }
    
}
