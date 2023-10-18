package gestorAplicacion.Tarjetas;

import gestorAplicacion.Tienda.Producto;

public class Platino extends Tarjeta {
    private static double precio;

    static {
        precio = 1500;
    }

    public Platino(){
        this.descuentoProducto = 0.15;
    }

    public String getNombre(){
        return "Platino";
    }

    public static double getPrecio(){
        return Platino.precio;
    }

    public static void setPrecio(double precio){
        Platino.precio = precio;
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
        puntos += 75;
    }

    public String toString(){
        return "-- Tarjeta Platino -- : puntos: " + this.puntos + "\n"+
        "Descuento productos tienda: " + this.descuentoProducto;
    }
}