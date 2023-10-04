package Logic.Peliculas;

import java.util.ArrayList;

public class Taquilla {
    private String nombre;
    private static ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();

    //Constructor

    public Taquilla(String nombre){
        this.nombre = nombre;
    }

    //Métodos de instancia
    
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    //Métodos de clase

    public static void agregarPelicula(Pelicula pel){
        peliculas.add(pel);
    }

    public static void retirarPelicula(Pelicula pel){
        peliculas.remove(pel);
    }
    public static ArrayList<Pelicula> getPeliculasDisponibles(){
        ArrayList<Pelicula> peliculasDisponibles = new ArrayList<Pelicula>();
        for (Pelicula pelicula : peliculas) {
            if(pelicula.getSala() != null){
                peliculasDisponibles.add(pelicula);
            }
        }
        return peliculasDisponibles;
    }

    //toString
    public String toString(){ 
        String nombres = "";
        for (Pelicula pel : peliculas) {
            if(pel != null && pel.getSala() != null){
                nombres += pel.nombre + " - precio: " + pel.getPrecio() +" | ";
            }     
        }
        if (nombres.isEmpty()) {
            return "Ninguna pelicula disponible";
        } else {
            return "Peliculas disponibles: " + nombres; 
        }
    }
}
