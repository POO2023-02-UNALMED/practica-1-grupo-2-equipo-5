package gestorAplicacion.Tienda;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.io.Serializable;

public class Combo extends Producto implements Serializable{
    private ArrayList<Producto> productosCombo = new ArrayList<Producto>();

    //Constructor
    public Combo(String nombre, double precio, Tienda tienda){
        super(nombre,precio, tienda);
    }

    //Métodos de instancia

    public void añadirProductos(Producto... args){
        for (Producto prod : args) {
            productosCombo.add(prod);
        }
    }

    public void removerProductos(Producto... args){
        for (Producto producto : args) {
            productosCombo.remove(producto);
        }
    }

    public ArrayList<Producto> getProductosCombo(){
        return productosCombo;
    }

    //toString

    public String toString(){
            String productos = "";
            Hashtable<String,Integer> tab = new Hashtable<String,Integer>();
            for (Producto producto : productosCombo) {
                String nombreProducto = producto.getNombre();
                if(tab.containsKey(nombreProducto)){
                    int cantidadActual = tab.get(nombreProducto);
                    tab.put(nombreProducto, cantidadActual + 1);
                } else {
                    tab.put(nombreProducto, 1);
                }
            }

            for (Map.Entry<String, Integer> entry : tab.entrySet()) {
                    productos += entry.getValue() + " " + entry.getKey() + " | ";
            }

            if(productos.isEmpty()){
                return "Ningun producto actualmente";
            } else {
                return "Productos en combo: "+productos;
            }
        }
}

