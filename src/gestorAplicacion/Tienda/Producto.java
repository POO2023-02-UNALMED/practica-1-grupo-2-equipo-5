package gestorAplicacion.Tienda;

import java.io.Serializable;

public class Producto implements Serializable {
    protected String nombre;
    protected double precio;
    protected Tienda tienda;

    public Producto(){
        this("NN",0, null);
    }

    public Producto(String nombre){
        this(nombre,0, null);
    }

    public Producto(String nombre, double precio, Tienda tienda){
        this.nombre = nombre;
        this.precio = precio;
        this.tienda = tienda;
        if(tienda instanceof Tienda){
            tienda.añadirProductoTienda(this);
        }
    }
    

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public double getPrecio(){
        return this.precio;
    }

    public void setPrecio(double nPrecio){
        this.precio = nPrecio;
    }

    public String toString(){
        return this.nombre;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}