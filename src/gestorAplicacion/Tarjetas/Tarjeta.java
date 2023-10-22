package gestorAplicacion.Tarjetas;
import java.util.HashMap;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Tienda.Producto;

public abstract class Tarjeta {
    protected double puntos;
    protected double descuentoProducto;
    
    public abstract String getNombre();
    public abstract double ValorProducto(Producto Producto);
    public abstract double getDescuentoProducto();
    public abstract void setDescuentoProducto(double descuento);
    public abstract double getPuntos();
    public abstract void setPuntos(double puntos);
    public abstract void comprar();
    public abstract void comprar(Pelicula pelicula, double puntos);
    public abstract void quitarCompra(Pelicula pelicula);
    public abstract HashMap<Pelicula, Double> getCompras();
}