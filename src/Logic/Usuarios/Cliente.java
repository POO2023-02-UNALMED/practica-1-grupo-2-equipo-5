package Logic.Usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Logic.Peliculas.Pelicula;
import Logic.Peliculas.Taquilla;
import Logic.Tarjetas.Tarjeta;
import Logic.Tienda.Producto;
import Logic.Tienda.Tienda;


public class Cliente extends Usuario{
    private double saldo = 0;
    private double descuento = 0;
    private HashMap<Pelicula, List<Integer>> comprasPel = new HashMap<Pelicula, List<Integer>>();
    private ArrayList<Producto> comprasProd = new ArrayList<Producto>();
    private ArrayList<Tarjeta> tarjetas = new ArrayList<Tarjeta>();

    //Constructor
    public Cliente(String nombre, int edad){
        if (nombre == ""){
            this.nombre = "NN";
        }
        else {
            this.nombre = nombre;
        }
        this.edad = edad;
        if(this.edad<=18){
            this.descuento += 0.2;
        }
    }

    //Getters y setters

    public double getDescuento(){
        return this.descuento;
    }

    public void setDescuento(double descuento){
        this.descuento = descuento;
    }

    public HashMap<Pelicula, List<Integer>> getComprasPelicula(){
        return this.comprasPel;
    }

    public ArrayList<Producto> getComprasProducto(){
        return this.comprasProd;
    }

    public ArrayList<Tarjeta> getTarjetas(){
        return this.tarjetas;
    }

    @Override
    public String getTipo(){
        return "Cliente";
    }

    //Metodos instancia

    public void depositar(double cantidad){
        this.saldo += cantidad;
    }

    public String pagar(double cantidad){
        if(this.saldo >= cantidad){
            this.saldo -= cantidad - (cantidad*this.descuento);
            return "Pago exitoso";
        } else {
            return "Saldo insuficiente";
        }
    }

    private void comprobarElementosEnCompras(){
        for (Pelicula pel : comprasPel.keySet()) {
            if(comprasPel.get(pel).isEmpty()){
                comprasPel.remove(pel);
            }
            
        }
    }

    public void agregarTarjeta(Tarjeta tarjeta){
        this.tarjetas.add(tarjeta);
    }

    public String removerTarjeta(Tarjeta tarjeta){
        if(tarjetas.contains(tarjeta)){
            this.tarjetas.add(tarjeta);
            return "Removida";
        } else {
            return "No se encuentra esta tarjeta";
        }
    }

    public String verTarjetas(){
        String frase = "Tarjetas: ";
        int cuenta = 1;
        for (Tarjeta tarj : tarjetas) {
            frase += cuenta + ") " +tarj.getNombre() +" | ";
            cuenta += 1;
        }
        return frase;
    }

    /*funcionalidad implementada para que el cliente compre una pelicula, tomando en cuenta las restricciones adecuadas, entre ellas,
      analizar si la pelicula está disponible en la taquilla, si el numero de asiento que desea el cliente esta disponible
      y si el cliente tiene el dinero suficiente para pagar la pelicula, en caso de cumplirlas, se hace efectiva la compra, retirando el asiento de la lista de asientos disponibles, 
      y pagando el costo de la pelicula.
    */ 
    public String comprarPelicula(String peli, int numAsiento, Tarjeta tarjeta){
        String peliN = peli.substring(0,1).toUpperCase() + peli.substring(1).toLowerCase();
        for (Pelicula pelT : Taquilla.getPeliculasDisponibles()) {
            if (pelT.getNombre().equals(peliN)){
                if(pelT.getSala() == null){
                    return "La pelicula no tiene asignada una sala";
                }
                if(pelT.getPrecio() > this.saldo && tarjeta == null){
                    return "Saldo insuficiente";
                }
                List<Integer> asientos = pelT.getSala().getAsientosDisponibles();
                try {
                    if(asientos.contains(numAsiento)){
                        boolean resp = pelT.ocuparAsiento(numAsiento);
                        if(resp == true){
                            List<Integer> listaAct = comprasPel.getOrDefault(pelT, new ArrayList<>());
                            listaAct.add(numAsiento);
                            this.comprasPel.put(pelT, listaAct);
                            if(tarjeta == null){
                                return this.pagar(pelT.getPrecio());
                            } else {
                                String respComPelConTarj = this.pagar((pelT.getPrecio() - tarjeta.getPuntos())); 
                                tarjeta.setPuntos(0);
                                return respComPelConTarj;
                            }
                        } else {
                            return "Esta pelicula no tiene enlazada una sala";
                        }                    
                    } else {
                        return "Asiento elejido no disponible";
                    }  
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
        }  
        return "La pelicula no esta disponible";
    }

     /*funcionalidad implementada para que el cliente compre un producto, tomando en cuenta las restricciones adecuadas, entre ellas,
      analizar si el producto está disponible en la tienda y si el cliente tiene el dinero disponible para la compra del mismo, si las cumple,
      se hace efectiva la compra y se paga el precio del producto 
    */
    public String comprarProducto(String prod, Tarjeta tarjeta){
        String prodN = prod.substring(0,1).toUpperCase() + prod.substring(1).toLowerCase();
        for (Producto producto : Tienda.getProductosDisponibles()) {
            if(producto.getNombre().equals(prodN)){
                if(tarjeta == null && producto.getPrecio() <= this.saldo ){
                    this.comprasProd.add(producto);
                    return this.pagar(producto.getPrecio());
                } else if(tarjeta != null && producto.getPrecio() <= this.saldo ) {
                    this.comprasProd.add(producto);
                    tarjeta.comprar();
                    return this.pagar(tarjeta.getValorProducto(producto));
                } else {
                    return "Saldo insuficiente";
                }
            }
        }
        return "El producto no esta a la venta";
    }
  
     /*funcionalidad implementada para que el cliente pueda cancelar la compra de una pelicula, tomando en cuenta las restricciones adecuadas, entre ellas,
      analizar si el cliente efectivamente compró la pelicula que desea cancelar, y si el asiento que decide cancelar si es el que compró, si las cumple,
      se hace efectiva la devolución del dinero y el asiento antes ocupado vuelve a ser disponible.
    */
    public String cancelarCompraPelicula(String pel, int numAsiento){
        for (Pelicula pelicula : comprasPel.keySet()) {
            try{
                if(pelicula.getNombre().equals(pel)){
                double cantidad = pelicula.getPrecio()-(pelicula.getPrecio()*descuento);
                List<Integer> asientosComprados = comprasPel.get(pelicula);
                for (Integer integer : asientosComprados) {
                    if((int) integer == numAsiento){
                        comprasPel.get(pelicula).remove( (Integer) numAsiento);
                        comprobarElementosEnCompras();
                        this.depositar(cantidad);
                        boolean resp = pelicula.agregarAsiento(numAsiento);
                        if(resp == true){
                            return "Compra de la pelicula "+ pelicula.getNombre() +" cancelada con éxito.";
                        } else {
                            return "La pelicula no tiene enlazada una sala.";
                        }
                        
                    }
                }
                return "No se ha comprado este asiento";
            }
            } catch (Exception e){
                return "Error: " + e;
            }
            
        }
        return "No se ha comprado una pelicula con este nombre";
    }

    /*funcionalidad implementada para que el cliente pueda cancelar la compra de un producto, tomando en cuenta la restricción adecuada,
      analizar si el cliente efectivamente compró el producto que desea cancelar, si lo cumple,
      se hace efectiva la devolución del dinero.
    */
    public String cancelarCompraProducto(String nombre){
        for (Producto producto : comprasProd) {
            if(producto.getNombre().equals(nombre)){
                comprasProd.remove(producto);
                this.depositar(producto.getPrecio()-(producto.getPrecio()*descuento));
                return "Se ha cancelado la compra del producto "+ producto.getNombre();
            }
        }
        return "No se ha comprado este producto";
    }

    //Metodos para mostrar en pantalla en frontend
    public String mostrarAsientosCompras(Pelicula pel){
        String asientos = "Asientos: ";
        if(comprasPel.containsKey(pel)){
            List<Integer> lista = comprasPel.get(pel);
            for (Integer integer : lista) {
                asientos += integer + ", ";
            }
        }
        return asientos;
    }

    public String mostrarComprasPelicula(){
        String compras = "";
        ArrayList<String> compradas = new ArrayList<String>();
        for (Map.Entry<Pelicula, List<Integer>> Cpeliculas : comprasPel.entrySet()) {
            if(!compradas.contains(Cpeliculas.getKey().getNombre())){ //Si array compradas no contiene el nombre de la pelicula, es true
                compras += Cpeliculas.getKey().getNombre() + " | ";
            } 
            compradas.add(Cpeliculas.getKey().getNombre());
        }
        return compras;
    }

    public String mostrarComprasProducto(){
        String compras = "";
        ArrayList<String> compradas = new ArrayList<String>();
        for (Producto producto : comprasProd) {
            if(!compradas.contains(producto.getNombre())){
                compras += producto.getNombre() + " | ";
            }
            compradas.add(producto.getNombre());
        }
        return compras;
    }

    //toString
    public String toString(){
        return "Nombre: "+nombre + "\nEdad: "+ edad + "\nSaldo: " + saldo + "\n"+this.verTarjetas();
    }
}