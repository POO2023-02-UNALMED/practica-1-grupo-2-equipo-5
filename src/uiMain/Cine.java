package uiMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import baseDatos.Serializar;
import baseDatos.Deserializar;
import gestorAplicacion.Peliculas.*;
import gestorAplicacion.Tarjetas.*;
import gestorAplicacion.Tienda.*;
import gestorAplicacion.Usuarios.*;


public class Cine {
    static Taquilla taquilla = new Taquilla("Taquilla 1");
    static Tienda tienda = new Tienda("Tienda Central");
    ArrayList<Usuario> lista_usuario = new ArrayList<Usuario>();
    ArrayList<Pelicula> lista_peliculas = new ArrayList<Pelicula>();
    ArrayList<Sala> lista_salas = new ArrayList<Sala>();
    ArrayList<Taquilla> lista_taquilla = new ArrayList<Taquilla>();
    ArrayList<Tienda> lista_tienda = new ArrayList<Tienda>();
    ArrayList<Producto> lista_productos = new ArrayList<Producto>();
    ArrayList<Combo> lista_combo = new ArrayList<Combo>();
    ArrayList<Cliente> lista_cliente = new ArrayList<Cliente>();




    
    public static void comprarPelicula(Cliente cuenta){
        /*funcionalidad implementada para que el cliente compre una pelicula, tomando en cuenta las restricciones adecuadas, entre ellas,
        analizar si la pelicula está disponible en la taquilla, si el numero de asiento que desea el cliente esta disponible
        y si el cliente tiene el dinero suficiente para pagar la pelicula, en caso de cumplirlas, se hace efectiva la compra, retirando el asiento de la lista de asientos disponibles, 
        y pagando el costo de la pelicula.
        */ 
        
        Scanner scan1 = new Scanner(System.in); 
        boolean peliculaEncontrada = false; //variable para mostrar mensaje si la pelicula no se encuentra
            
        System.out.print("\nCuantas boletas desea comprar? \n->");
        int respCantBoletas = scan1.nextInt();

        System.out.println("Taquilla: ");
        ArrayList<Pelicula> peliculasDisponibles = taquilla.obtenerPeliculaNAsientosDisponibles(respCantBoletas);
        String peliculasNAsientosD = taquilla.PeliculasConNumeroDeAsientosDisponibles(respCantBoletas);
        System.out.println(peliculasNAsientosD); // Se muestran las peliculas disponibles
        if(peliculasNAsientosD.equals("No hay peliculas disponibles")){
            return;
        }

        if (taquilla.toString() == "Ninguna pelicula disponible"){return;}
        System.out.print("Escriba el nombre de la pelicula que comprara: ");
        String respPelElegida = scan1.next();
        String peliN = respPelElegida.substring(0,1).toUpperCase() + respPelElegida.substring(1).toLowerCase();
        scan1.nextLine();

        for (Pelicula pel : peliculasDisponibles) {
            if(pel.getNombre().equals(peliN)){ //Se verifica que el nombre de la pelicula y el ingresado sean iguales
                if(pel.getSala() == null){
                    System.out.println("La pelicula no tiene asignada una sala");
                    return; 
                }
                peliculaEncontrada = true; //Se cambia el valor para indicar que si se encontro
                int respUsarTarjCPel = 2;
                int totalPuntosEnTarjetas = cuenta.totalPuntos();
                if(totalPuntosEnTarjetas != 0){
                    System.out.print("Desea usar puntos de alguna Tarjeta? (1:Si, 2:No)\n-> ");
                    respUsarTarjCPel = scan1.nextInt();
                }

                ArrayList<Integer> asientosAComprar = new ArrayList<Integer>();
                int cantidadAPagar = 0;
                for (int i = 0; i < respCantBoletas; i++) {
                    System.out.print(pel.getSala());
                    if(cuenta.isAccesoLounge()){
                        System.out.print(pel.getSala().verAsientosPrivados());
                    }
                    System.out.println();
                    List<Integer> asientos = pel.getSala().AsientosDisponibles(cuenta.getTarjetas()); //Verificar que el numero de asientos sea mayor o igual al que se comprara
                    System.out.print("Escoja un asiento: ");
                    int respAsientoEleg = scan1.nextInt();
                    if(!asientos.contains(respAsientoEleg)){
                        System.out.println("El asiento no esta disponible");
                        return;
                    }
                    scan1.nextLine();
                    pel.ocuparAsiento(respCantBoletas-1);
                    asientosAComprar.add(respAsientoEleg);
                    System.out.println("Precio: "+pel.getPrecio());
                    cantidadAPagar += pel.getPrecio();
                }
                
                if(!cuenta.getTarjetas().isEmpty()){
                    if(respUsarTarjCPel == 1){
                        System.out.print(cuenta.verTarjetas() +"\n-> ");
                        int respIndexTarjUsar = scan1.nextInt(); 
                        Tarjeta tarjeta = cuenta.obtenerTarjetaEspecifica(respIndexTarjUsar-1);
                        double total = cantidadAPagar - tarjeta.getPuntos();
                        if(cuenta.getSaldo() >= cantidadAPagar-tarjeta.getPuntos()){
                            System.out.println("Puntos antes: " + tarjeta.getPuntos());
                            if (total < 0){
                                tarjeta.comprar(pel, tarjeta.getPuntos()-cantidadAPagar);
                                System.out.println(cuenta.pagar(0));
                                tarjeta.setPuntos((cantidadAPagar-tarjeta.getPuntos())*-1);
                            } else if (total == 0){
                                tarjeta.comprar(pel, tarjeta.getPuntos());
                                tarjeta.setPuntos(0);
                                System.out.println(cuenta.pagar(0));
                            } else {
                                String respCompPel = cuenta.pagar(total);
                                System.out.println(respCompPel);
                                if(respCompPel.equals("Pago exitoso")){
                                    tarjeta.comprar(pel, tarjeta.getPuntos());
                                    tarjeta.setPuntos(0);
                                }
                            }
                            for (Integer asiento : asientosAComprar) {
                                cuenta.añadirCompraPeliculas(pel, asiento);
                            }
                            System.out.println("Puntos actuales: " + tarjeta.getPuntos());
                        } else {
                            System.out.println("Saldo insuficiente");
                            return;
                        }
                    } else {
                        if(cantidadAPagar <= cuenta.getSaldo()){
                            String respCompPel = cuenta.pagar(cantidadAPagar);
                            System.out.println(respCompPel);
                            if(respCompPel.equals("Pago exitoso")){
                                for (Integer asiento : asientosAComprar) {
                                    cuenta.añadirCompraPeliculas(pel, asiento);
                                }
                            }
                                            
                        } else {
                            System.out.println("Saldo insuficiente");
                            return;
                        }
                    }                  
                } else {
                    if(cantidadAPagar <= cuenta.getSaldo()){
                        String respCompPel = cuenta.pagar(cantidadAPagar);
                        if(respCompPel.equals("Pago exitoso")){
                            for (Integer asiento : asientosAComprar) {
                                cuenta.añadirCompraPeliculas(pel, asiento);
                            }
                        }
                                
                    } else {
                        System.out.println("Saldo insuficiente");
                        return;
                    }
                }           
            }
        }
            
        if(!peliculaEncontrada) { //Si negacion de peliculaEncontrada es true, se ejecuta
            System.out.println("Esta pelicula no esta disponible.");
            return;
        }      
            
        System.out.println("Pago exitoso");
        return;
    }

    public static void comprarProducto(Cliente cuenta){
        /*funcionalidad implementada para que el cliente compre un producto, tomando en cuenta las restricciones adecuadas, entre ellas,
        analizar si el producto está disponible en la tienda y si el cliente tiene el dinero disponible para la compra del mismo, si las cumple,
        se hace efectiva la compra y se paga el precio del producto 
        */
        Scanner scan = new Scanner(System.in);
        ArrayList<Producto> productosDisponibles = tienda.ProductosDisponibles();
        boolean productoEncontrado = false;

        System.out.println("Tienda: ");
        System.out.println(tienda); //Se muestran los productos con el toString del objeto
        System.out.print("Escriba el nombre del producto a comprar: ");
        String respNProd = scan.next();
        //Se coloca el producto con la primera en mayuscula y lo demas en minuscula
        String prod = respNProd.substring(0,1).toUpperCase() + respNProd.substring(1).toLowerCase();
            
        for(Producto producto : productosDisponibles){
            if(producto.getNombre().equals(prod)){
                productoEncontrado = true; //Se cambia el valor para indicar que si se encontro
                if(!cuenta.getTarjetas().isEmpty()){
                    System.out.print("Desea usar el descuento de alguna tarjeta? (1:Si, 2:No)\n-> ");
                    int respUsarTarjCPel = scan.nextInt();
                    if(respUsarTarjCPel == 1){
                        System.out.print(cuenta.verTarjetas() + "\n->");
                        int respIndexTarjUsar = scan.nextInt();
                        Tarjeta tarjeta = cuenta.obtenerTarjetaEspecifica(respIndexTarjUsar-1);
                        double descuentoTarjeta = tarjeta.getDescuentoProducto();
                        if(producto.getPrecio() <= cuenta.getSaldo()-(cuenta.getSaldo()*descuentoTarjeta)) {
                            String pago = cuenta.pagar(tarjeta.ValorProducto(producto));
                            System.out.println(pago);
                            if(pago.equals("Pago exitoso")){
                                cuenta.añadirCompraProductos(producto);
                                tarjeta.comprar();
                            }
                            return;
                        } else {
                            System.out.println("Saldo insuficiente");
                            return;
                        }
                    } else {
                        if(producto.getPrecio() <= cuenta.getSaldo()){
                            String pago = cuenta.pagar(producto.getPrecio());
                            System.out.println(pago);
                            if(pago.equals("Pago exitoso")){
                                cuenta.añadirCompraProductos(producto);
                            }
                            return ;
                        } else {
                            System.out.println("Saldo insuficiente");
                            return;
                        }
                    }              
                    } else {
                        if(producto.getPrecio() <= cuenta.getSaldo()){
                            String pago = cuenta.pagar(producto.getPrecio());
                            System.out.println(pago);
                            if(pago.equals("Pago exitoso")){
                                cuenta.añadirCompraProductos(producto);
                            }
                            
                            return;
                        } else {
                            System.out.println("Saldo insuficiente");
                            return;
                        } 
                    }     
                } 
            } 

            if(!productoEncontrado) { //Si negacion de productoEncontrado es true, se ejecuta
                System.out.println("Este producto no esta disponible.");
            }
            
        scan.close();
        scan.nextLine();
        return;
    }

    public static void comprarTarjeta(Cliente cuenta){
        Scanner scan = new Scanner(System.in);
        Oro oro = new Oro();
        Platino platino = new Platino();
        Diamante diamante = new Diamante();
        System.out.println("\tTarjetas");
        System.out.println("Oro (1) - $"+oro.getPrecio()+" | Platino (2) - $"+platino.getPrecio()
        +" | Diamante (3) - $"+diamante.getPrecio());
        System.out.print("-> ");
        int respElTarj = scan.nextInt();
        switch(respElTarj){
            case 1:
                String respComTarjOro = cuenta.pagar(oro.getPrecio());
                if(respComTarjOro.equals("Pago exitoso")){
                    cuenta.agregarTarjeta(new Oro()); 
                    System.out.println("Pago exitoso. Nueva tarjeta Oro!");
                } else {
                    System.out.println(respComTarjOro);
                }
                break;
            case 2:
                String respComTarjPlatino = cuenta.pagar(platino.getPrecio());
                if(respComTarjPlatino.equals("Pago exitoso")){
                    cuenta.agregarTarjeta(new Platino()); 
                    System.out.println("Pago exitoso. Nueva tarjeta Platino!");
                } else {
                    System.out.println(respComTarjPlatino);
                }
                break;
            case 3:
                String respComTarjDiamante = cuenta.pagar(diamante.getPrecio());
                if(respComTarjDiamante.equals("Pago exitoso")){
                    cuenta.agregarTarjeta(new Diamante()); 
                    System.out.println("Pago exitoso. Bienvenido a Diamante!");
                    System.out.println("Desea adquirir una membresia VIP? (1:Si, 2:No)");
                    int respCompVip = scan.nextInt();
                    if(respCompVip == 1){
                        String pago = cuenta.pagar(Vip.precio);
                        if(pago.equals("Pago exitoso")){
                            System.out.println("Bienvenido a la membresia vip, ahora tienes acceso a asientos privados y a preestrenos");
                            cuenta.accesoLounge();
                            cuenta.accesoPreEstreno();
                        } else {
                            System.out.println(pago);
                        }
                    } else {
                        System.out.println("Gracias por su compra!");
                    }
                } else {
                    System.out.println(respComTarjDiamante);
                }
                break;
            default:
                System.out.println("Este número no esta en las opciones");
                break;
        }
        oro = null;
        platino = null;
        diamante = null;
        scan.nextLine();
        return;
    }

    public static void cancelarCompraPelicula(Cliente cuenta){
        
     /*funcionalidad implementada para que el cliente pueda cancelar la compra de una pelicula, tomando en cuenta las restricciones adecuadas, entre ellas,
      analizar si el cliente efectivamente compró la pelicula que desea cancelar, y si el asiento que decide cancelar si es el que compró, si las cumple,
      se hace efectiva la devolución del dinero y el asiento antes ocupado vuelve a ser disponible.
    */
        Scanner scan =  new Scanner(System.in);
        HashMap<Pelicula, List<Integer>> compras = cuenta.getComprasPeliculas();
        ArrayList<Pelicula> peliculas = taquilla.getTotalPeliculas();

        if (compras.isEmpty()) {
            System.out.println("No ha hecho ninguna compra aún.");
            return;
        } else {
            System.out.print("Peliculas compradas: ");
            System.out.println(cuenta.mostrarComprasPelicula());
            System.out.print("Escriba el nombre de la pelicula a cancelar: ");
            String compPel = scan.next();
            for (Pelicula pel : peliculas) {
                if (pel.getNombre().equals(compPel) && compras.containsKey(pel)) {
                    System.out.println("Elija el asiento a cancelar de la pelicula");
                    System.out.println(cuenta.mostrarAsientosCompras(pel));
                    int respAsCancelar = scan.nextInt();
                    int respTarjUt = 0;
                    if(!cuenta.getTarjetas().isEmpty()){
                        System.out.println("Utilizo tarjeta en esta compra? (1: Si, 2: No)");
                        respTarjUt = scan.nextInt();
                        System.out.println("Para la devolucion, solo sera posible recuperar los puntos utilizados para la ultimo asiento comprado de la pelicula");
                        System.out.print("Quiere continuar con la devolucion de puntos?(1:Si, 2:No)\n ->");
                        int respContoNo = scan.nextInt();
                        if(respContoNo == 2){
                            respTarjUt = 0;
                        }
                    }
                        
                    if(respTarjUt != 0){
                        System.out.println("Cual tarjeta usò?");
                        System.out.print(cuenta.verTarjetas()+ "\n->");
                        int respQTUso = scan.nextInt();
                        Tarjeta tarjeta = cuenta.obtenerTarjetaEspecifica(respQTUso-1);
                        for (Pelicula pelicula : compras.keySet()) {
                            if(pelicula.getNombre().equals(pel.getNombre())){
                                double cantidad = pelicula.getPrecio()-(pelicula.getPrecio()*cuenta.getDescuento());
                                List<Integer> asientosComprados = cuenta.getComprasPeliculas().get(pelicula);
                                for (Integer integer : asientosComprados) {
                                    if((int) integer == respAsCancelar){
                                        cuenta.getComprasPeliculas().get(pelicula).remove( (Integer) respAsCancelar);
                                        cuenta.comprobarElementosEnCompras();
                                        boolean resp = pelicula.agregarAsiento(respAsCancelar);
                                        if(resp == true){
                                            cuenta.depositar(cantidad);
                                            HashMap<Pelicula, Double> comprasConTarjeta = tarjeta.getCompras();
                                            if(comprasConTarjeta.containsKey(pel)){
                                                tarjeta.setPuntos(tarjeta.getPuntos() + comprasConTarjeta.get(pel));
                                                System.out.println("Puntos devueltos");
                                            } else {
                                                System.out.println("No aparece esta compra");
                                                return;
                                            }
                                            System.out.println("Compra de la pelicula "+ pelicula.getNombre() +" cancelada con éxito.");
                                            return; 
                                        } else {
                                            System.out.println("La pelicula no tiene enlazada una sala.");
                                            return; 
                                        }                      
                                    }
                                }
                                System.out.println("No se ha comprado este asiento");
                                return; 
                            }
                        }

                    } else {
                        for (Pelicula pelicula : compras.keySet()) {
                            if(pelicula.getNombre().equals(pel.getNombre())){
                                double cantidad = pelicula.getPrecio()-(pelicula.getPrecio()*cuenta.getDescuento());
                                List<Integer> asientosComprados = cuenta.getComprasPeliculas().get(pelicula);
                                for (Integer integer : asientosComprados) {
                                    if((int) integer == respAsCancelar){
                                        cuenta.getComprasPeliculas().get(pelicula).remove( (Integer) respAsCancelar);
                                        cuenta.comprobarElementosEnCompras();
                                        cuenta.depositar(cantidad);
                                        boolean resp = pelicula.agregarAsiento(respAsCancelar);
                                        if(resp == true){
                                            System.out.println("Compra de la pelicula "+ pelicula.getNombre() +" cancelada con éxito.");
                                            return; 
                                        } else {
                                            System.out.println("La pelicula no tiene enlazada una sala.");
                                            return; 
                                        }
                                        
                                    }
                                }
                                System.out.println("No se ha comprado este asiento");
                                return; 
                                }
                        }
                        System.out.print("No se ha comprado esta pelicula");
                        return;
                    }  
                }
            }
        }
        scan.close();
        return;
    }

    public static void cancelarCompraProducto(Cliente cuenta){
        /*funcionalidad implementada para que el cliente pueda cancelar la compra de un producto, tomando en cuenta la restricción adecuada,
        analizar si el cliente efectivamente compró el producto que desea cancelar, si lo cumple,
        se hace efectiva la devolución del dinero.
        */

        ArrayList<Producto> comprasPr = cuenta.getComprasProductos();
        Scanner scan =  new Scanner(System.in);
                
        if (comprasPr.isEmpty()) {
            System.out.println("No ha hecho ninguna compra aún.");
        } else {
            System.out.print("Productos comprados: ");
            System.out.println(cuenta.mostrarComprasProducto());
            System.out.print("Escriba el nombre del producto a cancelar.\n-> ");
            String nombreCanProd = scan.next();

            Tarjeta tarjeta = null;
            int respUtTarjCProd = 2;
            if(!cuenta.getTarjetas().isEmpty()){
                System.out.println("Utilizo tarjeta en esta compra? (1:Si, 2:No)");
                respUtTarjCProd = scan.nextInt();
                if(respUtTarjCProd == 1){
                    System.out.println("Cúal tarjeta utilizo? (Escriba el numero)");
                    System.out.print(cuenta.verTarjetas() + "\n->");
                    int respQTUso = scan.nextInt();
                    tarjeta = cuenta.getTarjetas().get(respQTUso-1);
                }
            }
            
            for (Producto producto : comprasPr) {
                if (producto.getNombre().equals(nombreCanProd)) {
                    cuenta.getComprasProductos().remove(producto);
                    double cantidadNormal = producto.getPrecio()-(producto.getPrecio()*cuenta.getDescuento());
                    if(tarjeta != null){
                        double decuentoProd = tarjeta.getDescuentoProducto();
                        cuenta.depositar(cantidadNormal - (cantidadNormal*decuentoProd));
                    } else {
                        cuenta.depositar(cantidadNormal);
                    }
                    System.out.println("Se ha cancelado la compra del producto "+ producto.getNombre());
                    return;
                }
            }
        }
    }

    public static void sugerirPelicula(Cliente cuenta){
        System.out.println("\tSugerencias pelicula");
        HashMap<Pelicula,List<Integer>> peliculasVistas = cuenta.getComprasPeliculas();
        HashMap<Genero, Integer> generosVistos = new HashMap<Genero, Integer>();

        //Se obtiene la pelicula con más asientos comprados
        Pelicula peliculaMasV = null;
        int cantidadMaxDeVecesPel = 0;

        for (Map.Entry<Pelicula, List<Integer>> pelicula : peliculasVistas.entrySet()) {
            int cantidad = 0;
            Genero genero = pelicula.getKey().getCategoria();
            if(!generosVistos.containsKey(genero)){
                generosVistos.put(genero,0);
            }

            for (int asiento : pelicula.getValue()) {
                generosVistos.put(genero,generosVistos.get(genero)+1);
                cantidad += 1;
            }
            if(cantidad > cantidadMaxDeVecesPel){
                cantidadMaxDeVecesPel = cantidad;
                peliculaMasV = pelicula.getKey();
            }

        }

        //Se obtiene el genero más comprado
        
        Genero generoMasComprado = null;
        int cantidadMaxDeVecesG = 0;

        for (Map.Entry<Genero, Integer> entry : generosVistos.entrySet()) {
            if(entry.getValue() > cantidadMaxDeVecesG){
                generoMasComprado = entry.getKey();
                cantidadMaxDeVecesG = entry.getValue();
            }
        }
        if(peliculaMasV != null){
            System.out.println("La pelicula más vista fue: "+peliculaMasV.getNombre());
            System.out.println("El genero más comprado es: " + generoMasComprado);
        } else {
            System.out.println("Ninguna compra todavia.");
        }
            
    }


    public static void main(String[] args) throws Exception {

        // -------------------- Pruebas --------------------
        //recordar no asignar dos peliculas con la misma hora a la misma sala que tenga la misma hora
        
        Pelicula pelicula1 = new Pelicula("Terminator", 2000, "6:00 pm",Genero.ACCION, taquilla);
        Pelicula pelicula2 = new Pelicula("Barbie", 2000, Genero.COMEDIA, taquilla);
        Pelicula pelicula3 = new Pelicula("Inception", 3000, "2:20 pm",Genero.TERROR, taquilla);
        Pelicula pelicula4 = new Pelicula("lol", 20, "1:20 pm", Genero.CIENCIA_FICCION, taquilla);
        Sala s = new Sala("Sala1",20, "6:00 pm", taquilla);
        Sala s2 = new Sala("Sala2",20, "3:40 pm", taquilla);
        pelicula1.enlazarSala(s);
        pelicula2.enlazarSala(s2);
        pelicula2.setHora("3:40 pm");
        pelicula2.enlazarSala(s2);
        Producto prod1 = new Producto("Palomitas",2000, tienda);
        Producto prod2 = new Producto("Soda",2000, tienda);
        Combo combo1 = new Combo("Combo1", 2000, tienda);
        combo1.añadirProductos(prod1,prod2);


        //System.out.println(Taquilla.totalpeliculasTaquilla());
        //System.out.println("peliculas con sala disponibles "+Taquilla.getPeliculasDisponibles());
        //System.out.println(pelicula3.getSala());
        //System.out.println("probando");


        /* //Prueba ocupar asientos
        System.out.println(s.getAsientosDisponibles());
        s.ocupar(2);
        s.ocupar(9);
        System.out.println(s.getAsientosDisponibles());
        System.out.println(emp1);
        Producto prod1 = new Producto("Palomitas");

        //Cancelar compras pelicula
        System.out.println(cliente1.comprarPelicula("Terminator", 1));
        System.out.println(taquilla);
        System.out.println(cliente1.getComprasPelicula());
        System.out.println(cliente1.cancelarCompraPelicula("Terminator", 1));
        System.out.println(cliente1.getComprasPelicula());
        System.out.println(taquilla);
        
        //añadir productos
        Combo combo1 = new Combo("Combo 1", 20);
        combo1.añadirProductos(prod1,prod2, prod1);

        System.out.println(combo1);
        
        //Pruebas compras pelicula y productos
        System.out.println(taquilla);
        cliente1.depositar(2000);
        cliente2.depositar(2000);
        System.out.println(cliente2);
        System.out.println(cliente1.comprarPelicula("Terminator",2));
        System.out.println(cliente1.comprarPelicula("Terminator", 9));
        System.out.println(cliente1);
        System.out.println(cliente2);
        System.out.println(taquilla);
        System.out.println(pelicula1);
        System.out.println(cliente2.comprarProducto("soda"));
        System.out.println(cliente2);
        System.out.println(s.getAsientosDisponibles());*/

        // -------------------- Rellenar los datos --------------------
        ArrayList<Usuario> lista_usuario = baseDatos.Deserializar.deserializarUsuario();
        ArrayList<Pelicula> lista_peliculas = baseDatos.Deserializar.deserializarPelicula();
        ArrayList<Producto> lista_productos = baseDatos.Deserializar.deserializarProducto();

        for (Pelicula pelicula : lista_peliculas) {
            if(!taquilla.PeliculasDisponibles().contains(pelicula)){
                if(pelicula.getSala() != null){
                    taquilla.agregarPelicula(pelicula);
                }
                Pelicula.añadirPeliculaExistente(pelicula);
            }
        }

        for (Producto producto : lista_productos) {
            if(!tienda.ProductosDisponibles().contains(producto)){
                tienda.añadirProductoTienda(producto);
            }
        }

        // -------------------- Interfaz --------------------

        try (Scanner scan = new Scanner(System.in);) {
        System.out.println("\tBienvenido\nElija alguna de las siguientes opciones (1 ò 2): ");
        System.out.println("1) Iniciar sesion. ");
        System.out.println("2) Registrarse. ");
        System.out.print("-> ");
        
        int resp = scan.nextInt();
        Usuario cuenta = new Usuario();
        int estado = 1;
        scan.nextLine();
        switch(resp){
            case 1:
                    int encont3 = 0;
                    System.out.println("\tIniciar sesion");
                    System.out.print("\nDijite su nombre: ");
                    String nomIn = scan.nextLine();
                    System.out.print("Dijite su password: ");
                    String passIn = scan.nextLine();
                    for (Usuario usuarioB : lista_usuario) {
                        if(usuarioB.getNombre().equals(nomIn) && usuarioB.getPassword().equals(passIn)){
                            System.out.println("Ingreso éxitoso!");
                            cuenta = usuarioB;
                            encont3 = 1;
                            break;
                        } 
                    } 
                    if(encont3 == 0){
                        System.out.println("\nNombre o password incorrecto.");
                        estado = 0;
                    }
                    
                break; //Parte de verificar que el usuario tenga una cuenta con el archivo txt
            case 2:
                try{
                    int encont4 = 0;
                    System.out.println("\tRegistrarse");
                    System.out.print("\nDijite su nombre: ");
                    String nom = scan.nextLine();
                    System.out.print("Dijite su password: ");
                    String pass = scan.nextLine();
                    System.out.print("Dijite su edad: ");
                    int edad = scan.nextInt();
                    
                    for (Usuario usuario : lista_usuario) {
                        if(usuario.getNombre().equals(nom) && usuario.getPassword().equals(pass)){
                            System.out.println("Ya se ha creado esta cuenta");
                            estado = 0;
                            encont4 = 1;
                        }
                    } 
                    if(encont4 == 0){
                        cuenta = new Cliente(nom,pass, edad);
                        boolean respReg = Serializar.serializarUsuario(lista_usuario);
                        if(respReg){
                            System.out.println("Cuenta creada con exito.");
                            lista_usuario.add(cuenta);
                        } else {
                            System.out.println("Problemas al crear el usuario");
                            estado = 0;
                        }
                    }
                }
                    
                 catch(Exception e) {
                    System.out.println("Error:" + e);
                }
                break;
            default:
                System.out.println("Opcion incorrecta.");
                estado = 0;
                break;
            }

            

        while(estado == 1){
            try {
            
            //Variables a utilizar
            ArrayList<Pelicula> peliculasDisponibles = taquilla.PeliculasDisponibles();
            ArrayList<Producto> productosDisponibles = tienda.ProductosDisponibles();
            
            System.out.println("\n\n\tBienvenido "+ cuenta.getNombre() +"! Que deseas hacer?");
            Cliente cuentaCliente = ((Cliente) cuenta);
            System.out.println("0) Salir");
            System.out.println("1) Comprar");
            System.out.println("2) Cancelar compra");
            System.out.println("3) Ver (detalles, peliculas, productos)");
            System.out.println("4) Añadir");
            System.out.println("5) Depositar");
            scan.nextLine();
            System.out.print("-> ");
            int respCli = scan.nextInt(); //Ingresa un int
            switch(respCli){
                case 0: // Salida
                    System.out.println("Hasta luego!");
                    estado = 0; // Se termina el bucle si estado es diferente de 1
                    break;
                case 1: // Caso comprar
                    System.out.println("\tComprar");
                    System.out.println("Pelicula (1) | Producto (2) | Tarjeta (3)");
                    System.out.print("-> ");
                    int respCompPPT = scan.nextInt();

                    switch(respCompPPT){
                        case 1: //Caso comprar pelicula
                            sugerirPelicula(cuentaCliente);
                            comprarPelicula(cuentaCliente);
                            System.out.println(cuentaCliente.getComprasPeliculas());
                            break;
                        case 2: //Caso comprar Producto
                            comprarProducto(cuentaCliente);
                            break;
                        case 3:
                            comprarTarjeta(cuentaCliente);
                            break;
                        default:
                            System.out.println("Opción no disponible.");
                            break;
                    }
                    break;
                    case 2: // Caso de cancelar compra
                    System.out.println("\tCancelar compra.");
                    System.out.println("Pelicula (1) | Producto (2)");
                    System.out.print("-> ");
                    int respCanPelOProd = scan.nextInt();
                
                    if (respCanPelOProd == 1) {
                        cancelarCompraPelicula(cuentaCliente);
                    } else {
                        cancelarCompraProducto(cuentaCliente);
                    }
                    break;
                
                case 3: //Caso ver
                    System.out.println("\tVer");
                    System.out.println("Perfil (1) | Taquilla (2) | Tienda (3) | Tarjetas (4) | Detalles pelicula (5) ");
                    System.out.print("-> ");
                    int respVerDetPTTTD = scan.nextInt();
                    switch(respVerDetPTTTD){
                        case 1:
                        System.out.println("\tPerfil");
                        System.out.println(cuentaCliente);
                        break;
                        case 2:
                        System.out.println("\tTaquilla");
                        System.out.println(taquilla);
                        break;
                        case 3:
                        System.out.println("\tTienda");
                        System.out.println(tienda);
                        break;
                        case 4:
                        Oro oro = new Oro();
                        Platino platino = new Platino();
                        Diamante diamante = new Diamante();
                        System.out.println("\tTarjetas:");
                        System.out.println("--Oro--  \nPrecio:" + oro.getPrecio() + "\nDescuento Producto: "+oro.getDescuentoProducto()*100+"%");
                        System.out.println("--Platino--  \nPrecio:" + platino.getPrecio() + "\nDescuento Producto: "+platino.getDescuentoProducto()*100+"%");
                        System.out.println("--Diamante--  \nPrecio:" + diamante.getPrecio() + "\nDescuento Producto: "+diamante.getDescuentoProducto()*100+"%");
                        oro = null;
                        platino = null;
                        diamante = null;
                        break;
                        case 5:
                        int encont2 = 0;
                        System.out.println("\tDetalles");
                        System.out.print("Pelicula a buscar. \n-> ");
                        String pelBusDet = scan.next();
                        for (Pelicula pelicula : Pelicula.getPeliculasExistentes()) {
                            if(pelicula.getNombre().equals(pelBusDet)){
                                System.out.println();
                                encont2 = 1;
                                System.out.println(pelicula);
                            }
                        }
                        if(encont2 == 0){
                            System.out.println("\nPelicula no encontrada");
                        }  
                        break;
                        default:
                        System.out.println("Opcion no valida");
                        break;
                    }
                    break;
                
                case 4: 
                    System.out.println("\tAñadir");
                    System.out.println("Pelicula (1) | Producto (2)");
                    System.out.print("-> ");
                    int respAddPelProd = scan.nextInt();
                    switch(respAddPelProd){
                        case 1:
                        System.out.println("\tAñadir pelicula");
                        System.out.print("Nombre de la pelicula: ");
                        String respAnadirPel = scan.next();
                        System.out.print("Precio de la pelicula: ");
                        int respAnadirPelPrecio = scan.nextInt();
                        System.out.print("Sala a enlazar a la pelicula, recuerde que si desea añadir una sala a la pelicula que va a crear, estas deben tener la misma hora, de lo contrario no se efectuará el enlace\n");
                        System.out.println(Sala.mostrarSalasCreadas());
                        System.out.print("Hora de la pelicula: ");
                        String respAnadirPelHora = scan.next();
                        System.out.print("-> elija una sala entre las disponibles:  ");
                        String respSalEnlazar = scan.next();
                        /*for (Sala salaE : Sala.getsSalasCreadas()) {
                            if(salaE.getNombre().equals(respSalEnlazar)){
                                String respuestaAPel = cuentaCliente.añadirPeliculaTaquilla(new Pelicula(respAnadirPel, respAnadirPelPrecio),salaE);
                                System.out.println(respuestaAPel);
                            }
                        }*/
                        break;    
                        case 2:
                        int encont = 0;
                        System.out.println("\tAñadir producto");
                        System.out.print("Nombre del producto: ");
                        String respAnadirProd = scan.next();
                        System.out.print("Precio del producto: ");
                        int respAnadirProdPrecio = scan.nextInt();
                        for (Producto producto : productosDisponibles) {
                            if(producto.getNombre().equals(respAnadirProd)){
                                encont = 1;
                                producto.setPrecio(respAnadirProdPrecio);
                                System.out.println("Se ha actualizado el precio del producto");
                            } 
                        }
                        /*if(encont == 0){ //En caso de que no se encontrara el producto entre los existentes
                            cuentaCliente.añadirProductoTienda(new Producto(respAnadirProd, respAnadirProdPrecio, tienda));
                        }*/
                                
                        break;
                        default:
                        System.out.println("Opcion no valida");
                    }
                    break;
                case 5: // Caso depositar
                    System.out.print("Digite la cantidad a depositar: ");
                    int deposit = scan.nextInt();
                    cuentaCliente.depositar(deposit);
                    System.out.println("Deposito ejecutado correctamente");
                    break;
                default: // Caso por defecto
                        System.out.println("Opcion no valida.");
                        break;
                } 
            } catch (InputMismatchException e) { // Caso de error por texto ingresado del tipo incorrecto
                System.out.println("Lo ingresado no coincide con el tipo de dato a ingresar.");
                estado = 0;
            } catch (Exception e){
                System.out.println("Error: "+e);
                estado = 0;
            }    

        
            }
            scan.close();
        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }
         
    } 
}
