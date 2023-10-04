package Logic.Usuarios;

import Logic.Peliculas.Pelicula;
import Logic.Peliculas.Sala;
import Logic.Peliculas.Taquilla;
import Logic.Tienda.Producto;
import Logic.Tienda.Tienda;

public class Trabajador extends Usuario {
    private String cargo = "Taquillero";

    //Constructores (usan los atributos de la clase padre)

    public Trabajador(String nombre,String cargo){
        this(nombre,19,cargo);
    }

    public Trabajador(String nombre, int edad, String cargo){
        this.nombre = nombre;
        this.edad = edad;
        this.cargo = cargo;
    }

    //Métodos de instancia

    public String getCargo(){
        return this.cargo;
    }

    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    public String añadirPeliculaTaquilla(Pelicula pelicula, Sala sala){
        for (Pelicula pel : Taquilla.getPeliculasDisponibles()) {
            if(pel.getNombre() == pelicula.getNombre()){
                return "La pelicula ya esta en taquilla";
            }     
        }
        pelicula.enlazarSala(sala);
        return "Agregada";
    }

    public String añadirProductoTienda(Producto producto){
        for (Producto prod: Tienda.getProductosDisponibles()) {
            if (prod.getNombre() == producto.getNombre()){
                if (prod.getPrecio() == producto.getPrecio()){
                    return "El producto ya esta en la Tienda";
                } else {
                    prod.setPrecio(producto.getPrecio());
                    return "Se ha actualizado el precio";
                }         
            }
        }
        Tienda.añadirProductoTienda(producto);
        return "Añadido";
    }

    @Override
    public String getTipo(){
        return "Trabajador";
    }

    //toString
    public String toString(){
        return "Nombre: "+this.nombre + "\nEdad: "+ this.edad + "\nCargo: "+cargo;
    }
}
