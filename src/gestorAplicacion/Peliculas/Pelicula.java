package gestorAplicacion.Peliculas;

import java.util.ArrayList;
import java.util.List;

import gestorAplicacion.Peliculas.Genero.genero;

import java.io.Serializable;

public class Pelicula implements Serializable{ 
    String nombre;
    private int precio; 
    private Sala sala;
    private String hora;
    private genero categoria;
    private static ArrayList<Pelicula> peliculasExistentes = new ArrayList<Pelicula>();
    

    //Constructores
    public Pelicula(String nombre, genero nombreCategoria){
        this(nombre,0, null, nombreCategoria);
        Taquilla.agregarPelicula(this);
        Taquilla.agregarPreEstreno(this);
        peliculasExistentes.add(this);
    }
    
    public Pelicula(String nombre, int precio, genero nombreCategoria){
        this(nombre, precio, null, nombreCategoria);
        peliculasExistentes.add(this);
        Taquilla.agregarPelicula(this);
    }

    public Pelicula(String nombre, int precio, String hora, genero nombreCategoria){
        this.nombre = nombre;
        this.precio = precio;
        this.hora = hora;
        categoria = nombreCategoria;
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

    public genero getCategoria(){
        return this.categoria;
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
        return sala.AsientosDisponibles();
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
    
    public String categoria(Pelicula pel){
        genero categoria = pel.getCategoria();
        String frase = "";
        switch (categoria) {
            case ACCION:
                frase += "verás una película de acción.";
                break;
            case AVENTURA:
                frase += "verás una película de aventura.";
                    break;
            case COMEDIA:
                frase += "verás una película de comedia.";
                break;
            case DRAMA:
                frase += "verás una película de drama.";
                break;
            case CIENCIA_FICCION:
                frase += "verás una película de ciencia ficción.";
                break;
            case SUSPENSO:
                frase += "verás una película de suspenso.";
                break;
            case ANIMACION:
                frase += "verás una película de animación.";
                break;
            default:
                frase += "verás una película de Categoría no reconocida.";
            }
            return frase;
        }
    
}
