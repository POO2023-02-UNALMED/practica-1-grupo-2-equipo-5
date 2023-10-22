package gestorAplicacion.Tarjetas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Tienda.Producto;

public class Oro extends Tarjeta {
    private double precio = 500;
    private HashMap<Pelicula, Double> compras = new HashMap<Pelicula, Double>();
    //private 

    public Oro(){
        this.descuentoProducto = 0.1;
    }

    public String getNombre(){
        return "Oro";
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public double getValorProducto(Producto producto){
        //Te devuelve el precio del producto con el descuento incluido
        return producto.getPrecio() - (producto.getPrecio()*this.descuentoProducto);
    }

    public double getDescuentoProducto(){
        return this.descuentoProducto;
    }

    public void setDescuentoProducto(double descuento){
        this.descuentoProducto = descuento;
    }

    public double getPuntos(){
        return this.puntos;
    }

    public void setPuntos(double puntos){
        this.puntos = puntos;
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
        puntos += 50;
    }

    public String toString(){
        return "-- Tarjeta Oro -- : puntos: " + this.puntos + "\n"+
        "descuento productos tienda: " + this.descuentoProducto;
    }
}
