package Logic.Tarjetas;

import Logic.Tienda.Producto;

public class Oro extends Tarjeta {
    private static double precio;

    static {
        precio = 500;
    }

    public Oro(){
        this.descuentoProducto = 0.1;
    }

    public String getNombre(){
        return "Oro";
    }

    public static double getPrecio(){
        return Oro.precio;
    }

    public static void setPrecio(double precio){
        Oro.precio = precio;
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

    public void comprar(){
        puntos += 50;
    }

    public String toString(){
        return "-- Tarjeta Oro -- : puntos: " + this.puntos + "\n"+
        "descuento productos tienda: " + this.descuentoProducto;
    }
}
