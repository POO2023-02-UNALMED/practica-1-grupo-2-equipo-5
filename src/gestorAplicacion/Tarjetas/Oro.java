package gestorAplicacion.Tarjetas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Tienda.Producto;
import gestorAplicacion.Usuarios.Cliente;

public class Oro extends Tarjeta {
    private double precio = 500;
    private HashMap<Pelicula, Double> compras = new HashMap<Pelicula, Double>();
    private double costoEnvioCasaOro = 3000;
    //private 

    //constructor
    public Oro(){
        this.descuentoProducto = 0.1;
    }

    //getters y setters
    public String getNombre(){
        return "Oro";
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public double getDescuentoProducto(){
        return this.descuentoProducto;
    }

    public void setDescuentoProducto(double descuento){
        this.descuentoProducto = descuento;
    }

    public double getPuntos(){
        return this.puntos;
    }

    public void setPuntos(double puntos){
        this.puntos = puntos;
    }

    public double getEnvioCasaOro (){
        return this.costoEnvioCasaOro;
    }

    public void setEnvioAcasaOro (int nuevoCosto){
        this.costoEnvioCasaOro = nuevoCosto;
    }

    // métodos 

    public double ValorProducto(Producto producto){
        //Te devuelve el precio del producto con el descuento incluido
        double valorProducto = producto.getPrecio() - (producto.getPrecio()*this.descuentoProducto);
        return valorProducto;
    }

    public HashMap<Pelicula, Double> getCompras(){
        return compras;
    }

    public void quitarCompra(Pelicula pelicula){
        compras.remove(pelicula);
    }

    public Oro envioACasa (Cliente cli) {
        if (cli.getSaldo() >= this.getEnvioCasaOro() + this.getPrecio()){
            Oro oroEnvioAcasa = new Oro();
            double cantidadApagar = this.getEnvioCasaOro() + this.getPrecio();
            //System.out.println(cantidadApagar);
            cli.pagar(cantidadApagar);
            cli.agregarTarjeta(oroEnvioAcasa);
            return oroEnvioAcasa;
        }
        return null;
    }

    //sobrecarga del metodo
    public void comprar(Pelicula pelicula, double puntosUsados){
        compras.put(pelicula, puntosUsados);
    }

    public void comprar(){
        puntos += 50;
    }



    public String toString(){
        return "-- Tarjeta Oro -- : puntos: " + this.puntos + "\n"+
        "descuento productos tienda: " + this.descuentoProducto;
    }
}
