package gestorAplicacion.Tienda;

import java.io.Serializable;

public class Producto implements Serializable {
    protected String nombre;
    protected int precio = 0;

    public Producto(){
        this("NN",0);
    }

    public Producto(String nombre){
        this(nombre,0);
    }

    public Producto(String nombre, int precio){
        this.nombre = nombre;
        this.precio = precio;
        Tienda.añadirProductoTienda(this);
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
}
