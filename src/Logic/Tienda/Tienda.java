package Logic.Tienda;

import java.util.ArrayList;

public class Tienda {
    private String nombre;
    private static ArrayList<Producto> productosD = new ArrayList<Producto>();

    public Tienda(String nombre){
        this.nombre = nombre;
    }

    public static ArrayList<Producto> getProductosDisponibles(){
        return Tienda.productosD;
    }

    public static void añadirProductoTienda(Producto prod){
        productosD.add(prod);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        String productos = "";
        for (Producto prod : productosD) {
            if(prod != null){
                productos += prod.nombre + " | ";
            }     
        }
        if (productos == "") {
            return "Ninguna pelicula disponible";
        }
        return "\tTienda\nProductos Disponibles: " + productos;
    }
}
