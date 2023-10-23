package gestorAplicacion.Usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Peliculas.Sala;
import gestorAplicacion.Peliculas.Taquilla;
import gestorAplicacion.Tarjetas.Tarjeta;
import gestorAplicacion.Tienda.Producto;
import gestorAplicacion.Tienda.Tienda;


public class Cliente extends Usuario implements Vip, Serializable{
    private double saldo = 0;
    private double descuento = 0;
    public boolean accesoLounge = false;
    public boolean accesoPreestrenos = false;
    private HashMap<Pelicula, List<Integer>> comprasPel = new HashMap<Pelicula, List<Integer>>();
    private ArrayList<Producto> comprasProd = new ArrayList<Producto>();
    private ArrayList<Tarjeta> tarjetas = new ArrayList<Tarjeta>();

    //Constructor
    public Cliente(String nombre, int edad){
        super(nombre, "NN", edad);
        comprobarNombreYEdad();
    }

    public Cliente(String nombre, String password, int edad){
        super(nombre, password, edad);
        comprobarNombreYEdad();
    }

    //Getters y setters

    public double getSaldo(){
        return this.saldo;
    }

    public ArrayList<Producto> getComprasProductos(){
        return comprasProd;
    }

    public void añadirCompraProductos(Producto producto){
        comprasProd.add(producto);
    }

    public HashMap<Pelicula, List<Integer>> getComprasPeliculas(){
        return comprasPel;
    }

    public void añadirCompraPeliculas(Pelicula pelicula,int asientoAñadir){
        if(comprasPel.containsKey(pelicula)){
            List<Integer> listaAct = comprasPel.getOrDefault(pelicula, new ArrayList<>());
            listaAct.add(asientoAñadir);
            comprasPel.put(pelicula, listaAct);
        } else {
            List<Integer> listaNueva = new ArrayList<Integer>();
            listaNueva.add(asientoAñadir);
            comprasPel.put(pelicula, listaNueva);
        }
    }

    public double getDescuento(){
        return this.descuento;
    }

    public void setDescuento(double descuento){
        this.descuento = descuento;
    }

    public ArrayList<Tarjeta> getTarjetas(){
        return this.tarjetas;
    }

    public Tarjeta obtenerTarjetaEspecifica(int index){
        if(this.tarjetas.isEmpty()){
            return null;
        } else {
            return this.tarjetas.get(index);
        }
    }

    public boolean isAccesoLounge() {
        return accesoLounge;
    }
    public boolean isAccesoPreestrenos() {
        return accesoPreestrenos;
    }

    @Override
    public String getTipo(){
        return "Administrador";
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

    public void comprobarElementosEnCompras(){
        for (Pelicula pel : comprasPel.keySet()) {
            if(comprasPel.get(pel).isEmpty()){
                comprasPel.remove(pel);
            }
            
        }
    }

    private void comprobarNombreYEdad(){
        if(this.nombre == ""){
            this.nombre = "NN";
        }
        if(this.edad <= 18){
            this.descuento += 0.2;
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

    //método para retornar el total de puntos que un cliente tiene en todas sus tarjetas
    // debe implentarse a la hora de cuadrar el log in
    public int totalPuntos(){
        int totalPuntos = 0;
        for (Tarjeta tarj : this.tarjetas){
            totalPuntos += tarj.getPuntos();
        }
        return totalPuntos;
    }

     
    

    

    /*public String añadirPeliculaTaquilla(Pelicula pelicula, Sala sala){
        for (Pelicula pel : Taquilla.getPeliculasDisponibles()) {
            if(pel.getNombre().equals(pelicula.getNombre())){
                return "La pelicula ya esta en taquilla";
            }     
        }
        pelicula.enlazarSala(sala);
        return "Agregada";
    }

    public String añadirProductoTienda(Producto producto){
        for (Producto prod: Tienda.getProductosDisponibles()) {
            if (prod.getNombre() == producto.getNombre()){
                if (prod.getPrecio() == producto.getPrecio()){
                    return "El producto ya esta en la Tienda";
                } else {
                    prod.setPrecio(producto.getPrecio());
                    return "Se ha actualizado el precio";
                }         
            }
        }
        Tienda.añadirProductoTienda(producto);
        return "Añadido";
    }*/

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

    public Sala salaDeLaPeliculaComprada(Pelicula peliACancelar){
        for (Map.Entry<Pelicula,List<Integer>> peliculaYasiento : this.getComprasPeliculas().entrySet()){
            if (peliculaYasiento.getKey() == peliACancelar){
                return peliculaYasiento.getKey().getSala();
            }
        }
        return null;
    }

    public Sala llevarProductoAsala(HashMap<Pelicula, List<Integer>> peliculasCompradas, String pel){
        if (!this.comprasProd.isEmpty() ){
            for (Map.Entry<Pelicula,List<Integer>> peliculaYasiento : this.getComprasPeliculas().entrySet()){
                Pelicula pelicula =peliculaYasiento.getKey();
                if (pelicula.getNombre().equals(pel)){
                    return pelicula.getSala();
                }
            }
        }
        return null;
    }

    //toString
    public String toString(){
        return "Nombre: "+nombre + "\nEdad: "+ edad + "\nSaldo: " + saldo + "\n"+this.verTarjetas();
    }

    //implementacion de los metodos de la interfaz
    public void accesoLounge(){
        accesoLounge = true;
    }
    public void accesoPreEstreno(){
        accesoPreestrenos = true;
    }
}
