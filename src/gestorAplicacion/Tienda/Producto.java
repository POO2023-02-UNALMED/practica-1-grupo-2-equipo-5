package gestorAplicacion.Tienda;

import java.io.Serializable;

public class Producto implements Serializable {
    protected String nombre;
    protected int precio = 0;
    protected Tienda tienda;

    public Producto(){
        this("NN",0, null);
    }

    public Producto(String nombre){
        this(nombre,0, null);
    }

    public Producto(String nombre, int precio, Tienda tienda){
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

    public int getPrecio(){
        return this.precio;
    }

    public void setPrecio(int nPrecio){
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