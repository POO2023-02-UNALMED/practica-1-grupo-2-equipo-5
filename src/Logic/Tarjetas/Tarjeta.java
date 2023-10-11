package Logic.Tarjetas;

import Logic.Tienda.Producto;

public abstract class Tarjeta {
    protected double puntos;
    protected double descuentoProducto;
    
    public abstract String getNombre();
    public abstract double getValorProducto(Producto Producto);
    public abstract void setDescuentoProducto(double descuento);
    public abstract double getPuntos();
    public abstract void setPuntos(double puntos);
    public abstract void comprar();
}