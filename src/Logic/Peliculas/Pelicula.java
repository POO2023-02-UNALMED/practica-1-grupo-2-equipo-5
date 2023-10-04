package Logic.Peliculas;

import java.util.List;

public class Pelicula {
    String nombre;
    private int precio; 
    private Sala sala;

    //Constructores
    public Pelicula(String nombre){
        this(nombre,0);
    }
    
    public Pelicula(String nombre, int precio){
        this.nombre = nombre;
        this.precio = precio;
        
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
    }

    public Sala getSala(){
        return this.sala;
    }

    public List<Integer> getAsientosDisponibles(){
        return sala.getAsientosDisponibles();
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
        return "Nombre: "+nombre+ "\nPrecio:" + precio+"\nSala: "+sala.getNombre() + "\nAsientos Disponibles: " 
        + sala.getNumeroAsientosDisponibles();
    }
    
}
