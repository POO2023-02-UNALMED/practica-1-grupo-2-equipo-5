package gestorAplicacion.Tarjetas;

import java.util.HashMap;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Tienda.Producto;

public class Platino extends Tarjeta {
    private double precio = 1000;
    private HashMap<Pelicula, Double> compras = new HashMap<Pelicula, Double>();

    public Platino(){
        this.descuentoProducto = 0.15;
    }

    public String getNombre(){
        return "Platino";
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

    public double obtenerPuntosComprasTarjeta(Pelicula pelicula){
        return compras.get(pelicula);
    }

    public void quitarCompra(Pelicula pelicula){
        compras.remove(pelicula);
    }

    public void comprar(){
        puntos += 75;
    }

    public String toString(){
        return "-- Tarjeta Platino -- : puntos: " + this.puntos + "\n"+
        "Descuento productos tienda: " + this.descuentoProducto;
    }
}