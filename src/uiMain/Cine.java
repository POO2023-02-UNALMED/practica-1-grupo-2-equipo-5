package uiMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import baseDatos.Serializar;
import baseDatos.Deserializar;
import gestorAplicacion.Peliculas.*;
import gestorAplicacion.Tarjetas.*;
import gestorAplicacion.Tienda.*;
import gestorAplicacion.Usuarios.*;


public class Cine {
    public static void main(String[] args) throws Exception {

        // -------------------- Pruebas --------------------
        //recordar no asignar dos peliculas con la misma hora a la misma sala que tenga la misma hora
        Taquilla taquilla = new Taquilla("Taquilla 1");
        Tienda tienda = new Tienda("Tienda Central");
        Pelicula pelicula1 = new Pelicula("Terminator", 2000);
        Pelicula pelicula2 = new Pelicula("Barbie", 2000);
        Pelicula pelicula3 = new Pelicula("Inception", 3000, "2:20 pm");
        Pelicula pelicula4 = new Pelicula("lol", 20, "1:20 pm");
        Sala s = new Sala("Sala1",20, "11:30 pm");
        Sala s2 = new Sala("Sala2",20, "2:20 pm");
        pelicula1.enlazarSala(s);
        pelicula2.enlazarSala(s2);
        pelicula2.setHora("3:40 pm");
        pelicula2.enlazarSala(s2);
        Producto prod1 = new Producto("Palomitas",2000);
        Producto prod2 = new Producto("Soda",2000);
        Combo combo1 = new Combo("Combo1", 2000);
        combo1.añadirProductos(prod1,prod2);

        System.out.println(Taquilla.getPeliculasDisponibles());

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
            if(!Taquilla.getPeliculasDisponibles().contains(pelicula)){
                if(pelicula.getSala() != null){
                    Taquilla.agregarPelicula(pelicula);
                }
                Pelicula.añadirPeliculaExistente(pelicula);
            }
        }

        for (Producto producto : productosCreados) {
            if(!Tienda.getProductosDisponibles().contains(producto)){
                Tienda.añadirProductoTienda(producto);
            }
        }

        // -------------------- Interfaz --------------------

        
        
        System.out.println("\tBienvenido\nElija alguna de las siguientes opciones (1 ò 2): ");
        System.out.println("1) Iniciar sesion. ");
        System.out.println("2) Registrarse. ");
        System.out.print("-> ");
        Scanner scan = new Scanner(System.in);
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
                        cuenta = new Administrador(nom,pass, edad);
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
            ArrayList<Pelicula> peliculasDisponibles = Taquilla.getPeliculasDisponibles();
            ArrayList<Producto> productosDisponibles = Tienda.getProductosDisponibles();
            
            System.out.println("\n\n\tBienvenido "+ cuenta.getNombre() +"! Que deseas hacer?");
            Administrador cuentaAdmin = ((Administrador) cuenta);
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
                        System.out.println("Taquilla: ");
                        System.out.println(taquilla); // Se muestran las peliculas disponibles
                        if (taquilla.toString() == "Ninguna pelicula disponible"){break;}
                        System.out.print("Escriba el nombre de la pelicula que comprara: ");
                        String respPelElegida = scan.next();
                        boolean peliculaEncontrada = false; //variable para mostrar mensaje si la pelicula no se encuentra
                        scan.nextLine();
                        for (Pelicula pel : peliculasDisponibles) {
                            if(pel.getNombre().equals(respPelElegida)){ //Se verifica que el nombre de la pelicula y el ingresado sean iguales
                                peliculaEncontrada = true; //Se cambia el valor para indicar que si se encontro
                                System.out.println(pel.getSala());
                                System.out.print("Escoja un asiento: ");
                                int respAsientoEleg = scan.nextInt();
                                    if(pel.getAsientosDisponibles().contains(respAsientoEleg)){
                                        String respCompPe;
                                        if(!cuentaAdmin.getTarjetas().isEmpty()){
                                            System.out.print("Desea usar puntos de alguna Tarjeta? (1:Si, 2:No)\n-> ");
                                            int respUsarTarjCPel = scan.nextInt();
                                            if(respUsarTarjCPel == 1){
                                                System.out.print(cuentaAdmin.verTarjetas() +"\n-> ");
                                                int respIndexTarjUsar = scan.nextInt();
                                                ArrayList<Tarjeta> tarjetasCuenta = cuentaAdmin.getTarjetas();
                                                System.out.println("Puntos antes de la compra: "+tarjetasCuenta.get(respIndexTarjUsar-1).getPuntos());
                                                respCompPe = cuentaAdmin.comprarPelicula(respPelElegida,respAsientoEleg, tarjetasCuenta.get(respIndexTarjUsar-1));
                                            } else {
                                                respCompPe = cuentaAdmin.comprarPelicula(respPelElegida,respAsientoEleg, null);
                                            }
                                                
                                        } else {
                                            respCompPe = cuentaAdmin.comprarPelicula(respPelElegida,respAsientoEleg,null);
                                        }
                                            
                                        System.out.println(respCompPe);
                                        break;
                                    } else {
                                        System.out.println("No se ha encontrado este asiento como disponible.");
                                    }
                                }
                            }

                        if(!peliculaEncontrada) { //Si negacion de peliculaEncontrada es true, se ejecuta
                            System.out.println("Esta pelicula no esta disponible.");
                        }
                        break;
                        case 2:
                        System.out.println("Tienda: ");
                        System.out.println(tienda); //Se muestran los productos con el toString del objeto
                        System.out.print("Escriba el nombre del producto a comprar: ");
                        String respNProd = scan.next();
                        boolean productoEncontrado = false;
                        for(Producto producto : productosDisponibles){
                            if(producto.getNombre().equals(respNProd)){
                                productoEncontrado = true; //Se cambia el valor para indicar que si se encontro
                                String respComProd;
                                if(!cuentaAdmin.getTarjetas().isEmpty()){
                                    System.out.print("Desea usar el descuento de alguna tarjeta? (1:Si, 2:No)\n-> ");
                                    int respUsarTarjCPel = scan.nextInt();
                                    if(respUsarTarjCPel == 1){
                                        System.out.print(cuentaAdmin.verTarjetas() + "\n->");
                                        int respIndexTarjUsar = scan.nextInt();
                                        ArrayList<Tarjeta> tarjetasCuenta = cuentaAdmin.getTarjetas();
                                        respComProd = cuentaAdmin.comprarProducto(respNProd,tarjetasCuenta.get(respIndexTarjUsar-1));
                                    } else {
                                        respComProd = cuentaAdmin.comprarProducto(respNProd, null);
                                    }              
                                } else {
                                    respComProd = cuentaAdmin.comprarProducto(respNProd, null);
                                }     
                                System.out.println(respComProd);
                            }
                        } 

                        if(!productoEncontrado) { //Si negacion de productoEncontrado es true, se ejecuta
                            System.out.println("Este producto no esta disponible.");
                        }
                        break;
                        case 3:
                        System.out.println("\tTarjetas");
                        System.out.println("Oro (1) - $"+Oro.getPrecio()+" | Platino (2) - $"+Platino.getPrecio()
                        +" | Diamante (3) - $"+Diamante.getPrecio());
                        System.out.print("-> ");
                        int respElTarj = scan.nextInt();
                        switch(respElTarj){
                            case 1:
                                String respComTarjOro = cuentaAdmin.pagar(Oro.getPrecio());
                                if(respComTarjOro.equals("Pago exitoso")){
                                    cuentaAdmin.agregarTarjeta(new Oro()); 
                                    System.out.println("Pago exitoso. Nueva tarjeta Oro!");
                                } else {
                                    System.out.println(respComTarjOro);
                                }
                                break;
                            case 2:
                                String respComTarjPlatino = cuentaAdmin.pagar(Platino.getPrecio());
                                if(respComTarjPlatino.equals("Pago exitoso")){
                                    cuentaAdmin.agregarTarjeta(new Platino()); 
                                    System.out.println("Pago exitoso. Nueva tarjeta Platino!");
                                } else {
                                    System.out.println(respComTarjPlatino);
                                }
                                break;
                            case 3:
                                String respComTarjDiamante = cuentaAdmin.pagar(Diamante.getPrecio());
                                if(respComTarjDiamante.equals("Pago exitoso")){
                                    cuentaAdmin.agregarTarjeta(new Diamante()); 
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
                        HashMap<Pelicula, List<Integer>> compras = cuentaAdmin.getComprasPelicula();
                
                        if (compras.isEmpty()) {
                            System.out.println("No ha hecho ninguna compra aún.");
                        } else {
                            System.out.print("Peliculas compradas: ");
                            System.out.println(cuentaAdmin.mostrarComprasPelicula());
                            System.out.print("Escriba el nombre de la pelicula a cancelar: ");
                            String compPel = scan.next();
                
                            for (Pelicula pel : peliculasDisponibles) {
                                if (pel.getNombre().equals(compPel) && compras.containsKey(pel)) {
                                    System.out.println("Elija el asiento a cancelar de la pelicula");
                                    System.out.println(cuentaAdmin.mostrarAsientosCompras(pel));
                                    int respAsCancelar = scan.nextInt();
                                    System.out.println("Utilizo tarjeta en esta compra? (1: Si, 2: No)");
                                    int respTarjUt = scan.nextInt();

                                    String respCan = cuentaAdmin.cancelarCompraPelicula(compPel, respAsCancelar);
                                    
                                    
                                    if(respTarjUt == 1){
                                        // Verifica si la compra se hizo con una tarjeta y devuelve los puntos si es así
                                        if (cuentaAdmin.getTarjeta() != null) {
                                            cuentaAdmin.cancelarCompraPelicula(compPel, respAsCancelar); // <-Pendiente, devolver puntos
                                        }
                                    } 
                                    System.out.println(respCan);
                                }
                            }
                        }
                    } else {
                        ArrayList<Producto> comprasPr = cuentaAdmin.getComprasProducto();
                
                        if (comprasPr.isEmpty()) {
                            System.out.println("No ha hecho ninguna compra aún.");
                        } else {
                            System.out.print("Productos comprados: ");
                            System.out.println(cuentaAdmin.mostrarComprasProducto());
                            System.out.print("Escriba el nombre del producto a cancelar.\n-> ");
                            String nombreCanProd = scan.next();
                
                            for (Producto producto : comprasPr) {
                                if (producto.getNombre().equals(nombreCanProd)) {
                                    String respCanProd = cuentaAdmin.cancelarCompraProducto(nombreCanProd);
                                    System.out.println(respCanProd);
                                    break;
                                }
                            }
                        }
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
                        System.out.println(cuentaAdmin);
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
                        for (Sala salaE : Sala.getsSalasCreadas()) {
                            if(salaE.getNombre().equals(respSalEnlazar)){
                                String respuestaAPel = cuentaAdmin.añadirPeliculaTaquilla(new Pelicula(respAnadirPel, respAnadirPelPrecio),salaE);
                                System.out.println(respuestaAPel);
                            }
                        }
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
                        if(encont == 0){ //En caso de que no se encontrara el producto entre los existentes
                            cuentaAdmin.añadirProductoTienda(new Producto(respAnadirProd, respAnadirProdPrecio));
                        }
                                
                        break;
                        default:
                        System.out.println("Opcion no valida");
                    }
                    break;
                case 5: // Caso depositar
                    System.out.print("Digite la cantidad a depositar: ");
                    int deposit = scan.nextInt();
                    cuentaAdmin.depositar(deposit);
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
