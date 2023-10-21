package uiMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import baseDatos.Serializar;
import baseDatos.Deserializar;
import gestorAplicacion.Peliculas.*;
import gestorAplicacion.Peliculas.Genero;
import gestorAplicacion.Tarjetas.*;
import gestorAplicacion.Tienda.*;
import gestorAplicacion.Usuarios.*;


public class Cine {
    static Taquilla taquilla = new Taquilla("Taquilla 1");
    static Tienda tienda = new Tienda("Tienda Central");


    
    public static void comprarPelicula(Cliente cuenta){
        /*funcionalidad implementada para que el cliente compre una pelicula, tomando en cuenta las restricciones adecuadas, entre ellas,
        analizar si la pelicula está disponible en la taquilla, si el numero de asiento que desea el cliente esta disponible
        y si el cliente tiene el dinero suficiente para pagar la pelicula, en caso de cumplirlas, se hace efectiva la compra, retirando el asiento de la lista de asientos disponibles, 
        y pagando el costo de la pelicula.
        */ 

        try (Scanner scan = new Scanner(System.in)) {
            ArrayList<Pelicula> peliculasDisponibles = taquilla.PeliculasDisponibles();
            boolean peliculaEncontrada = false; //variable para mostrar mensaje si la pelicula no se encuentra

            //String peli, int numAsiento, Tarjeta tarjeta

            
            System.out.print("\nCuantas boletas desea comprar? \n->");
            int respCantBoletas = scan.nextInt();

            System.out.println("Taquilla: ");
            ArrayList<Pelicula> peliculasNAsientosD = taquilla.obtenerPeliculaConNumeroDeAsientosDisponibles(respCantBoletas);
            System.out.println(peliculasNAsientosD); // Se muestran las peliculas disponibles

            if (taquilla.toString() == "Ninguna pelicula disponible"){return;}
            System.out.print("Escriba el nombre de la pelicula que comprara: ");
            String respPelElegida = scan.next();
            String peliN = respPelElegida.substring(0,1).toUpperCase() + respPelElegida.substring(1).toLowerCase();
            scan.nextLine();

            for (Pelicula pel : peliculasDisponibles) {
                if(pel.getNombre().equals(peliN)){ //Se verifica que el nombre de la pelicula y el ingresado sean iguales
                    if(pel.getSala() == null){
                        System.out.println("La pelicula no tiene asignada una sala");
                        return; 
                    }

                    peliculaEncontrada = true; //Se cambia el valor para indicar que si se encontro
                    
                    System.out.println(pel.getSala());
                    List<Integer> asientos = pel.getSala().AsientosDisponibles(); //Verificar que el numero de asientos sea mayor o igual al que se comprara
                    System.out.print("Escoja un asiento: ");
                    int respAsientoEleg = scan.nextInt();
                    if(asientos.contains(respAsientoEleg)){
                        if(!cuenta.getTarjetas().isEmpty()){
                            System.out.print("Desea usar puntos de alguna Tarjeta? (1:Si, 2:No)\n-> ");
                            int respUsarTarjCPel = scan.nextInt();
                                if(respUsarTarjCPel == 1){
                                    System.out.print(cuenta.verTarjetas() +"\n-> ");
                                    int respIndexTarjUsar = scan.nextInt();
                                    ArrayList<Tarjeta> tarjetasCuenta = cuenta.getTarjetas();
                                    Tarjeta tarjeta = tarjetasCuenta.get(respIndexTarjUsar-1);
                                    double total = pel.getPrecio() - tarjeta.getPuntos();
                                    if(cuenta.getSaldo() >= pel.getPrecio()-tarjeta.getPuntos()){
                                        pel.ocuparAsiento(respAsientoEleg);
                                        System.out.println("Puntos antes: " + tarjeta.getPuntos());
                                        if (total < 0){
                                            cuenta.pagar(0);
                                            tarjeta.setPuntos((pel.getPrecio()-tarjeta.getPuntos())*-1);
                                        } else if (total == 0){
                                            tarjeta.setPuntos(0);
                                            cuenta.pagar(0);
                                        } else {
                                            tarjeta.setPuntos(0);
                                            cuenta.pagar(total);
                                        }
                                        System.out.println("Puntos actuales: " + tarjeta.getPuntos());
                                        return;
                                    } else {
                                        System.out.println("Saldo insuficiente");
                                        return;
                                    }
                                } else {
                                    if(pel.getPrecio() <= cuenta.getSaldo()){
                                        if(pel.ocuparAsiento(respAsientoEleg)){
                                            cuenta.pagar(pel.getPrecio());
                                            cuenta.añadirCompraPeliculas(pel, respAsientoEleg);
                                            return;
                                        } else{
                                            System.out.println("Asiento no disponible");
                                            return;
                                        }
                                        
                                    } else {
                                        System.out.println("Saldo insuficiente");
                                        return;
                                    }
                                }                  
                        } else {
                            if(pel.getPrecio() <= cuenta.getSaldo()){
                                if(pel.ocuparAsiento(respAsientoEleg)){
                                    cuenta.pagar(pel.getPrecio());
                                    cuenta.añadirCompraPeliculas(pel, respAsientoEleg);
                                    return;
                                } else {
                                    System.out.println("Asiento no disponible");
                                            return;
                                }
                            } else {
                                System.out.println("Saldo insuficiente");
                                return;
                            }
                        }           
                    } else {
                        System.out.println("No se ha encontrado este asiento como disponible.");
                        return;
                    }
                }
            }

            if(!peliculaEncontrada) { //Si negacion de peliculaEncontrada es true, se ejecuta
                System.out.println("Esta pelicula no esta disponible.");
                return;
            }     
            scan.close();
        }
        System.out.println("La pelicula no esta disponible");
        return; 
    }

    public static void comprarProducto(Cliente cuenta){
        /*funcionalidad implementada para que el cliente compre un producto, tomando en cuenta las restricciones adecuadas, entre ellas,
        analizar si el producto está disponible en la tienda y si el cliente tiene el dinero disponible para la compra del mismo, si las cumple,
        se hace efectiva la compra y se paga el precio del producto 
        */
        try (Scanner scan = new Scanner(System.in)) {
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
                            ArrayList<Tarjeta> tarjetasCuenta = cuenta.getTarjetas();
                            Tarjeta tarjeta = tarjetasCuenta.get(respIndexTarjUsar-1);
                            double descuentoTarjeta = tarjeta.getDescuentoProducto();
                            if(producto.getPrecio() <= cuenta.getSaldo()-(cuenta.getSaldo()*descuentoTarjeta)) {
                                cuenta.añadirCompraProductos(producto);
                                tarjeta.comprar();
                                cuenta.pagar(tarjeta.getValorProducto(producto));
                                return;
                            } else {
                                System.out.println("Saldo insuficiente");
                                return;
                            }
                        } else {
                            if(producto.getPrecio() <= cuenta.getSaldo()){
                                cuenta.añadirCompraProductos(producto);
                                cuenta.pagar(producto.getPrecio());
                                return ;
                            } else {
                                System.out.println("Saldo insuficiente");
                                return;
                            }
                        }              
                    } else {
                        if(producto.getPrecio() <= cuenta.getSaldo()){
                            cuenta.añadirCompraProductos(producto);
                            cuenta.pagar(producto.getPrecio());
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
        }
        return;
    }


    public static void main(String[] args) throws Exception {

        // -------------------- Pruebas --------------------
        //recordar no asignar dos peliculas con la misma hora a la misma sala que tenga la misma hora
        
        Pelicula pelicula1 = new Pelicula("Terminator", 2000, Genero.ACCION);
        Pelicula pelicula2 = new Pelicula("Barbie", 2000, Genero.COMEDIA);
        Pelicula pelicula3 = new Pelicula("Inception", 3000, "2:20 pm",Genero.TERROR);
        Pelicula pelicula4 = new Pelicula("lol", 20, "1:20 pm", Genero.CIENCIA_FICCION);
        Sala s = new Sala("Sala1",20, "11:30 pm", taquilla);
        Sala s2 = new Sala("Sala2",20, "3:40 pm", taquilla);
        pelicula1.enlazarSala(s);
        pelicula2.enlazarSala(s2);
        pelicula2.setHora("3:40 pm");
        pelicula2.enlazarSala(s2);
        Producto prod1 = new Producto("Palomitas",2000, tienda);
        Producto prod2 = new Producto("Soda",2000, tienda);
        Combo combo1 = new Combo("Combo1", 2000, tienda);
        combo1.añadirProductos(prod1,prod2);

        System.out.println(taquilla.PeliculasDisponibles());

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
        ArrayList<Usuario> usuariosCreados = baseDatos.Deserializar.deserializarUsuario();
        ArrayList<Pelicula> peliculasCreadas = baseDatos.Deserializar.deserializarPelicula();
        ArrayList<Producto> productosCreados = baseDatos.Deserializar.deserializarProducto();

        for (Pelicula pelicula : peliculasCreadas) {
            if(!taquilla.PeliculasDisponibles().contains(pelicula)){
                if(pelicula.getSala() != null){
                    Taquilla.agregarPelicula(pelicula);
                }
                Pelicula.añadirPeliculaExistente(pelicula);
            }
        }

        for (Producto producto : productosCreados) {
            if(!tienda.ProductosDisponibles().contains(producto)){
                tienda.añadirProductoTienda(producto);
            }
        }

        // -------------------- Interfaz --------------------

        
        Scanner scan = new Scanner(System.in);
        System.out.println("\tBienvenido\nElija alguna de las siguientes opciones (1 ò 2): ");
        System.out.println("1) Iniciar sesion. ");
        System.out.println("2) Registrarse. ");
        System.out.print("-> ");
        
        int resp = scan.nextInt();
        scan.nextLine();
        Usuario cuenta = new Usuario();
        int estado = 1;

        switch(resp){
            case 1:
                    int encont3 = 0;
                    System.out.println("\tIniciar sesion");
                    System.out.print("\nDijite su nombre: ");
                    String nomIn = scan.nextLine();
                    System.out.print("Dijite su password: ");
                    String passIn = scan.nextLine();
                    for (Usuario usuarioB : usuariosCreados) {
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
                    
                    for (Usuario usuario : usuariosCreados) {
                        if(usuario.getNombre().equals(nom) && usuario.getPassword().equals(pass)){
                            System.out.println("Ya se ha creado esta cuenta");
                            estado = 0;
                            encont4 = 1;
                        }
                    } 
                    if(encont4 == 0){
                        cuenta = new Cliente(nom,pass, edad);
                        System.out.println("Cuenta creada con exito.");
                        
                        usuariosCreados.add(cuenta);
                        Serializar.serializarUsuario(usuariosCreados);
                    }
                    
                }
                    
                      
                    //Aqui hay que revisar que no haya un usuario creado con estas caracteristicas! <-----------
                    
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
            System.out.print("-> ");
            int respCli = scan.nextInt(); //Ingresa un int
            scan.nextLine();
            switch(respCli){
                case 0: // Salida
                    System.out.println("Hasta luego!");
                    estado = 0; // Se termina el bucle si estado es diferente de 1
                    break;
                case 1: // Caso comprar
                    System.out.println("\tComprar");
                    System.out.println("Pelicula (1) | Producto (2) | Producto (3)");
                    System.out.print("-> ");
                    int respCompPPT = scan.nextInt();
                    switch(respCompPPT){
                        case 1:
                            comprarPelicula(cuentaCliente);
                        break;
                        case 2: //Caso comprar Producto
                            comprarProducto(cuentaCliente);
                        break;
                        case 3:
                        System.out.println("\tTarjetas");
                        System.out.println("Oro (1) - $"+Oro.getPrecio()+" | Platino (2) - $"+Platino.getPrecio()
                        +" | Diamante (3) - $"+Diamante.getPrecio());
                        System.out.print("-> ");
                        int respElTarj = scan.nextInt();
                        switch(respElTarj){
                            case 1:
                                String respComTarjOro = cuentaCliente.pagar(Oro.getPrecio());
                                if(respComTarjOro.equals("Pago exitoso")){
                                    cuentaCliente.agregarTarjeta(new Oro()); 
                                    System.out.println("Pago exitoso. Nueva tarjeta Oro!");
                                } else {
                                    System.out.println(respComTarjOro);
                                }
                                break;
                            case 2:
                                String respComTarjPlatino = cuentaCliente.pagar(Platino.getPrecio());
                                if(respComTarjPlatino.equals("Pago exitoso")){
                                    cuentaCliente.agregarTarjeta(new Platino()); 
                                    System.out.println("Pago exitoso. Nueva tarjeta Platino!");
                                } else {
                                    System.out.println(respComTarjPlatino);
                                }
                                break;
                            case 3:
                                String respComTarjDiamante = cuentaCliente.pagar(Diamante.getPrecio());
                                if(respComTarjDiamante.equals("Pago exitoso")){
                                    cuentaCliente.agregarTarjeta(new Diamante()); 
                                    System.out.println("Pago exitoso. Bienvenido a Diamante!");
                                } else {
                                    System.out.println(respComTarjDiamante);
                                }
                                break;
                            default:
                                System.out.println("Este número no esta en las opciones");
                                break;
                        }
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
                        HashMap<Pelicula, List<Integer>> compras = cuentaCliente.getComprasPeliculas();
                
                        if (compras.isEmpty()) {
                            System.out.println("No ha hecho ninguna compra aún.");
                        } else {
                            System.out.print("Peliculas compradas: ");
                            System.out.println(cuentaCliente.mostrarComprasPelicula());
                            System.out.print("Escriba el nombre de la pelicula a cancelar: ");
                            String compPel = scan.next();
                
                            for (Pelicula pel : peliculasDisponibles) {
                                if (pel.getNombre().equals(compPel) && compras.containsKey(pel)) {
                                    System.out.println("Elija el asiento a cancelar de la pelicula");
                                    System.out.println(cuentaCliente.mostrarAsientosCompras(pel));
                                    int respAsCancelar = scan.nextInt();
                                    System.out.println("Utilizo tarjeta en esta compra? (1: Si, 2: No)");
                                    int respTarjUt = scan.nextInt();

                                    //String respCan = cuentaCliente.cancelarCompraPelicula(compPel, respAsCancelar);
                                    
                                    
                                    if(respTarjUt == 1){
                                        // Verifica si la compra se hizo con una tarjeta y devuelve los puntos si es así
                                        if (cuentaCliente.getTarjeta() != null) {
                                            //cuentaCliente.cancelarCompraPelicula(compPel, respAsCancelar); // <-Pendiente, devolver puntos
                                        }
                                    } 
                                    //System.out.println(respCan);
                                }
                            }
                        }
                    } /*else {
                        ArrayList<Producto> comprasPr = cuentaCliente.getComprasProducto();
                
                        if (comprasPr.isEmpty()) {
                            System.out.println("No ha hecho ninguna compra aún.");
                        } else {
                            System.out.print("Productos comprados: ");
                            System.out.println(cuentaCliente.mostrarComprasProducto());
                            System.out.print("Escriba el nombre del producto a cancelar.\n-> ");
                            String nombreCanProd = scan.next();
                
                            for (Producto producto : comprasPr) {
                                if (producto.getNombre().equals(nombreCanProd)) {
                                    String respCanProd = cuentaCliente.cancelarCompraProducto(nombreCanProd);
                                    System.out.println(respCanProd);
                                    break;
                                }
                            }
                        }
                    }*/
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
                        System.out.println("--Oro--  \nPrecio:" + Oro.getPrecio() + "\nDescuento Producto: "+oro.getDescuentoProducto()*100+"%");
                        System.out.println("--Platino--  \nPrecio:" + Platino.getPrecio() + "\nDescuento Producto: "+platino.getDescuentoProducto()*100+"%");
                        System.out.println("--Diamante--  \nPrecio:" + Diamante.getPrecio() + "\nDescuento Producto: "+diamante.getDescuentoProducto()*100+"%");
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
    } 
    
}
