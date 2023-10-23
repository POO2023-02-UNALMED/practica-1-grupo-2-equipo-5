package gestorAplicacion.Tarjetas;

import java.util.HashMap;

import gestorAplicacion.Peliculas.Pelicula;
import gestorAplicacion.Tienda.Producto;
import gestorAplicacion.Usuarios.Cliente;

public class Diamante extends Tarjeta {
    private double precio = 5000;
    private HashMap<Pelicula, Double> compras = new HashMap<Pelicula, Double>();
    private int costoEnvioCasaDiam = 3000;

    //constructor
    public Diamante(){
        this.puntos = 100;
        this.descuentoProducto = 0.2;
    }

    //getters y setters
    public String getNombre(){
        return "Diamante";
    }

    public double getPrecio(){
        return this.precio;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

        public double getPuntos(){
        return this.puntos;
    }

    public void setPuntos(double puntos){
        this.puntos = puntos;
    }

    public int getEnvioCasaDiam(){
        return this.costoEnvioCasaDiam;
    }

    public void setEnvioCasaDiam(int nuevoCosto){
        this.costoEnvioCasaDiam = nuevoCosto;
    }
    public double getDescuentoProducto(){
        return this.descuentoProducto;
    }

    public void setDescuentoProducto(double descuento){
        this.descuentoProducto = descuento;
    }

    //metodos
    public double ValorProducto(Producto producto){
        //Te devuelve el precio del producto con el descuento incluido
        double valorProducto = producto.getPrecio() - (producto.getPrecio()*this.descuentoProducto);
        return valorProducto;
    }

    public void comprar(Pelicula pelicula, double puntosUsados){
        compras.put(pelicula, puntosUsados);
    }

    public HashMap<Pelicula, Double> getCompras(){
        return compras;
    }

    public void quitarCompra(Pelicula pelicula){
        compras.remove(pelicula);
    }

    public void comprar(){
        puntos += 100;
    }

    public Diamante envioACasa (Cliente cli) {
        if (cli.getSaldo() >= this.getEnvioCasaDiam() + this.getPrecio()){
            Diamante diamanteEnvioAcasa = new Diamante();
            double cantidadApagar = this.getEnvioCasaDiam() + this.getPrecio();
            //System.out.println(cantidadApagar);
            cli.pagar(cantidadApagar);
            cli.agregarTarjeta(diamanteEnvioAcasa);
            return diamanteEnvioAcasa;
        }
        return null;
    }

    public String toString(){
        return "-- Tarjeta Diamante -- : puntos: " + this.puntos + "\n"+
        "Descuento productos tienda: " + this.descuentoProducto;
    }

}
