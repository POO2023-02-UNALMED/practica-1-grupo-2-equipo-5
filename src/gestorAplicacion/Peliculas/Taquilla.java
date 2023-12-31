package gestorAplicacion.Peliculas;

import java.util.ArrayList;
import java.util.HashSet;

import gestorAplicacion.Tarjetas.Tarjeta;

import java.io.Serializable;

public class Taquilla implements Serializable {
    private String nombre;
    // peliculasSala solo contiene las peliculas que fueron definidas en su constructor con nombre, precio y hora, y ademas tienen sala asignada
    //totalpeliculas contiene todas las peliculas creadas
    public ArrayList<Pelicula> peliculasSala = new ArrayList<Pelicula>();
    public ArrayList<Pelicula> totalpeliculas = new ArrayList<Pelicula>();
    public ArrayList<Pelicula> preestrenos = new ArrayList<Pelicula>();
    
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

    public void agregarPeliculasSala(Pelicula pel){
        peliculasSala.add(pel);
    }

    public void retirarPeliculasSala(Pelicula pel){
        peliculasSala.remove(pel);
    }

    public ArrayList<Pelicula> obtenerPeliculaNAsientosDisponibles(int numero){
        ArrayList<Pelicula> peliculasConAsientos = new ArrayList<Pelicula>();
        for (Pelicula pelicula : peliculasSala) {
            if(pelicula.getSala().getNumeroAsientosDisponibles() >= numero && !peliculasConAsientos.contains(pelicula)){
                peliculasConAsientos.add(pelicula);
            }
        }
        return peliculasConAsientos;
    }

    public String PeliculasConNumeroDeAsientosDisponibles(int numero){
        ArrayList<Pelicula> peliculasConAsientos = new ArrayList<Pelicula>();
        String taquilla = "";
        for (Pelicula pelicula : peliculasSala) {
            if(pelicula.getSala().getNumeroAsientosDisponibles() >= numero && !peliculasConAsientos.contains(pelicula)){
                peliculasConAsientos.add(pelicula);
            }
        }

        for (Pelicula pelicula : peliculasConAsientos) {
            if(pelicula != null && pelicula.getSala() != null && pelicula.getHora() != null){
                taquilla += pelicula.getNombre() + " - precio: $" + pelicula.getPrecio() + " - Hora: " + pelicula.getHora() + " | ";
            } 
        }

        if(taquilla.isEmpty()){
            return "No hay peliculas disponibles";
        } 
        return taquilla;
    }

    //Métodos de clase

    public void agregarPelicula(Pelicula pel){
        totalpeliculas.add(pel);
    }

    public void retirarPelicula(Pelicula pel){
        totalpeliculas.remove(pel);
    }

    public void agregarPreEstreno(Pelicula pel){
        preestrenos.add(pel);
    }
    public void retirarPreEstreno(Pelicula pel){
        preestrenos.remove(pel);
    }

    public ArrayList<Pelicula> PeliculasDisponibles(){
        ArrayList<Pelicula> peliculasDisponibles = new ArrayList<Pelicula>();
        for (Pelicula pelicula : peliculasSala) {
            if(pelicula.getSala() != null && pelicula.getHora() != null && !peliculasDisponibles.contains(pelicula)){
                peliculasDisponibles.add(pelicula);
            }
        }
        return peliculasDisponibles;
    }

    public ArrayList<Pelicula> getPreestrenos(){
        return this.preestrenos;
    }

    public ArrayList<Pelicula> getTotalPeliculas(){
        return this.totalpeliculas;
    }

    // este metodo devuelve todas las peliculas que han sido creadas
    public String totalpeliculasTaquilla() {
        ArrayList<Pelicula> totalpeliculasSinDuplicados = new ArrayList<>(new HashSet<>(totalpeliculas));
        StringBuilder result = new StringBuilder("Total películas; las peliculas que no tengan hora asignada y su precio  sea cero son preestrenos\n");
    
        for (Pelicula pel : totalpeliculasSinDuplicados) {
            String nombre = pel.getNombre();
            double precio = pel.getPrecio();
            String salaNombre = (pel.getSala() != null && pel.getSala().getHora().equals(pel.getHora())) ? pel.getSala().getNombre() : "esta película no tiene asignada una sala";
            String hora = (pel.getHora() != null) ? pel.getHora() : "esta película no tiene hora asignada";
    
            result.append(String.format("%s - precio: $%d - Sala: %s - Hora: %s\n", nombre, precio, salaNombre, hora +"\n"));
        }
    
        if (totalpeliculasSinDuplicados.isEmpty()) {
            result.append("Ninguna película disponible");
        }
    
        return result.toString();
    }

    //toString, este método devuelve solo las peliculas que se mostrarán en taquilla par aque el cliente elija cual ver
    //los preEestrenos son para un publico selecto, por ende no se muestran en taquilla
    public String toString(){ 
        String nombres = "";
        for (Pelicula pel : peliculasSala) {
            if(pel != null && pel.getSala() != null && pel.getHora() != null){
                nombres += pel.getNombre() + " - precio: $" + pel.getPrecio() + " - Hora: " + pel.getHora() + " | ";
            }     
        }
        if (nombres.isEmpty()) {
            return "Ninguna pelicula disponible";
        } else {
            return "Peliculas disponibles: " + nombres; 
        }
    }

    //retorna los preestrenos 
    public String preEstrenos(){ 
        String nombrespre = "";
        for (Pelicula preEstreno : this.preestrenos) {
            nombrespre += preEstreno.nombre + " - precio: $" + preEstreno.getPrecio() + " | ";   
        }
        if (nombrespre.isEmpty()) {
            return "Ningun preestreno disponible";
        } else {
            return "Preestrenos disponibles: " + nombrespre; 
        }
    }
}
