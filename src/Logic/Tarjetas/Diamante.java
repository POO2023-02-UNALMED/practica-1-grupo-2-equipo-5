package Logic.Tarjetas;

import Logic.Tienda.Producto;

public class Diamante extends Tarjeta {
    private static double precio;

    static {
        precio = 3000;
    }

    public Diamante(){
        this.puntos = 100;
        this.descuentoProducto = 0.2;
    }

    public String getNombre(){
        return "Diamante";
    }

    public static double getPrecio(){
        return Diamante.precio;
    }

    public static void setPrecio(double precio){
        Diamante.precio = precio;
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
        puntos += 100;
    }

    public String toString(){
        return "-- Tarjeta Diamante -- : puntos: " + this.puntos + "\n"+
        "Descuento productos tienda: " + this.descuentoProducto;
    }
}
