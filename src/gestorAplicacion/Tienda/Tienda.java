package gestorAplicacion.Tienda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class Tienda implements Serializable {
    private String nombre;
    private static ArrayList<Producto> productosD = new ArrayList<Producto>();

    public Tienda(String nombre){
        this.nombre = nombre;
    }

    public ArrayList<Producto> ProductosDisponibles(){
        ArrayList<Producto> productosDisponibles = new ArrayList<Producto>();
        for (Producto prod : productosD) {
            if(prod.getPrecio() != 0){
                productosDisponibles.add(prod);
            }
        }
        return productosDisponibles;
    }

    public ArrayList<Producto> ProductosUnicos(){
        ArrayList<Producto> productosUnicos = new ArrayList<Producto>();
        for (Producto producto : productosD) {
            if(producto.getPrecio() != 0 && !(producto instanceof Combo)){
                productosUnicos.add(producto);
            }
        }
        return productosUnicos;
    }

    public void añadirProductoTienda(Producto prod){
        Tienda.productosD.add(prod);
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
                String nom = prod.nombre;
                if(prod instanceof Combo){
                    HashMap<Producto, Integer> productosCombos = new HashMap<Producto, Integer>();
                    nom += " (";
                    int cantidad = 1;
                    for(Producto productC : ((Combo) prod).getProductosCombo()){
                        if(productosCombos.containsKey(productC)){
                            int cantidadActual = productosCombos.get(productC);
                            productosCombos.put(productC, cantidadActual + 1);
                        } else {
                            productosCombos.put(productC, 1);
                        }
                    }
                    for (Map.Entry<Producto,Integer> producto : productosCombos.entrySet()) {
                        nom += producto.getValue()+ " " +producto.getKey();
                        cantidad += 1;
                        if(cantidad <= productosCombos.size()){
                            nom += ", ";
                        }
                    }
                    nom += ")";
                }
                productos += nom + " | ";
            }     
        }
        if (productos == "") {
            return "Ningun producto disponible";
        }
        return "Productos Disponibles: " + productos;
    }
}
