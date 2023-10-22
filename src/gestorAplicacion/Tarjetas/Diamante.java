package gestorAplicacion.Tarjetas;

import java.util.HashMap;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Tienda.Producto;

public class Diamante extends Tarjeta {
    private double precio = 3000;
    private HashMap<Pelicula, Double> compras = new HashMap<Pelicula, Double>();

    //constructor
    public Diamante(){
        this.puntos = 100;
        this.descuentoProducto = 0.2;
    }

    //getters y setters
    public String getNombre(){
        return "Diamante";
    }

    public double getPrecio(){
        return this.precio;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

        public double getPuntos(){
        return this.puntos;
    }

    public void setPuntos(double puntos){
        this.puntos = puntos;
    }

    //metodos
    public double ValorProducto(Producto producto){
        //Te devuelve el precio del producto con el descuento incluido
        double valorProducto = producto.getPrecio() - (producto.getPrecio()*this.descuentoProducto);
        return valorProducto;
    }

    public double getDescuentoProducto(){
        return this.descuentoProducto;
    }

    public void setDescuentoProducto(double descuento){
        this.descuentoProducto = descuento;
    }

    public void comprar(Pelicula pelicula, double puntosUsados){
        compras.put(pelicula, puntosUsados);
    }

    public HashMap<Pelicula, Double> getCompras(){
        return compras;
    }

    public void quitarCompra(Pelicula pelicula){
        compras.remove(pelicula);
    }

    public void comprar(){
        puntos += 100;
    }

    public String toString(){
        return "-- Tarjeta Diamante -- : puntos: " + this.puntos + "\n"+
        "Descuento productos tienda: " + this.descuentoProducto;
    }

}
