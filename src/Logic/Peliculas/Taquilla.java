package Logic.Peliculas;

import java.util.ArrayList;
import java.util.HashSet;

public class Taquilla {
    private String nombre;
    // peliculasSala solo contiene las peliculas que fueron definidas en su constructor con nombre, precio y hora, y ademas tienen sala asignada
    //totalpeliculas contiene todas las peliculas creadas
    public static ArrayList<Pelicula> peliculasSala = new ArrayList<Pelicula>();
    public static ArrayList<Pelicula> totalpeliculas = new ArrayList<Pelicula>();

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

    public static void agregarPeliculasSala(Pelicula pel){
        peliculasSala.add(pel);
    }

    public static void retirarPeliculasSala(Pelicula pel){
        peliculasSala.remove(pel);
    }

    public static void agregarPeliculas(Pelicula pel){
        totalpeliculas.add(pel);
    }

    public static void retirarPeliculas(Pelicula pel){
        totalpeliculas.remove(pel);
    }
    public static ArrayList<Pelicula> getPeliculasDisponibles(){
        ArrayList<Pelicula> peliculasDisponibles = new ArrayList<Pelicula>();
        for (Pelicula pelicula : peliculasSala) {
            if(pelicula.getSala() != null && pelicula.getHora1() != null){
                peliculasDisponibles.add(pelicula);
            }
        }
        return peliculasDisponibles;
    }

    // este metodo devuelve todas las peliculas que han sido creadas
    public static String totalpeliculasTaquilla() {
        ArrayList<Pelicula> totalpeliculasSinDuplicados = new ArrayList<>(new HashSet<>(totalpeliculas));
        StringBuilder result = new StringBuilder("Total películas\n");
    
        for (Pelicula pel : totalpeliculasSinDuplicados) {
            String nombre = pel.getNombre();
            int precio = pel.getPrecio();
            String salaNombre = (pel.getSala() != null && pel.getSala().getHora().equals(pel.getHora1())) ? pel.getSala().getNombre() : "esta película no tiene asignada una sala";
            String hora = (pel.getHora1() != null) ? pel.getHora1() : "esta película no tiene hora asignada";
    
            result.append(String.format("%s - precio: $%d - Sala: %s - Hora: %s\n", nombre, precio, salaNombre, hora));
        }
    
        if (totalpeliculasSinDuplicados.isEmpty()) {
            result.append("Ninguna película disponible");
        }
    
        return result.toString();
    }
    //toString, este método devuelve solo las peliculas que se mostrarán en taquilla par aque el cliente elija cual ver
    public String toString(){ 
        String nombres = "";
        for (Pelicula pel : peliculasSala) {
            if(pel != null && pel.getSala() != null && pel.getHora1() != null){
                nombres += pel.nombre + " - precio: $" + pel.getPrecio() +" | ";
            }     
        }
        if (nombres.isEmpty()) {
            return "Ninguna pelicula disponible";
        } else {
            return "Peliculas disponibles: " + nombres; 
        }
    }
}
