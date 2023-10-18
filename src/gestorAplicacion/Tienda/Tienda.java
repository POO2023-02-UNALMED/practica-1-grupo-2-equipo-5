package gestorAplicacion.Tienda;

import java.util.ArrayList;
import java.io.Serializable;

public class Tienda implements Serializable {
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
                String nom = prod.nombre;
                if(prod instanceof Combo){
                    nom += " (";
                    for(Producto productC : ((Combo) prod).getProductosCombo()){
                        nom += productC.nombre;
                        if(productC != ((Combo) prod).getProductosCombo().get(((Combo) prod).getProductosCombo().size()-1)){
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
