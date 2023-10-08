package Frontend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Logic.Peliculas.*;
import Logic.Tienda.*;
import Logic.Usuarios.*;


public class Cine {
    public static void main(String[] args) throws Exception {

        // -------------------- Pruebas --------------------
        Taquilla taquilla = new Taquilla("Taquilla 1");
        Tienda tienda = new Tienda("Tienda Central");
        Pelicula pelicula1 = new Pelicula("Terminator", 2000);
        Pelicula pelicula2 = new Pelicula("Barbie", 2000);
        Sala s = new Sala("Sala1",20);
        Sala s2 = new Sala("Sala2",20);
        pelicula1.enlazarSala(s);
        pelicula2.enlazarSala(s2);
        Producto prod1 = new Producto("Palomitas",2000);
        Producto prod2 = new Producto("Soda",2000);
        Combo combo1 = new Combo("Combo1", 2000);
        combo1.añadirProductos(prod1,prod2);


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
                        cuenta = new Usuario(nom,pass, edad);
                        System.out.println("Cuenta creada con exito.");
                    }    
                }    //Aqui hay que revisar que no haya un usuario creado con estas caracteristicas! <-----------
                    
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
            //Variables a utilizar
            ArrayList<Pelicula> peliculasDisponibles = Taquilla.getPeliculasDisponibles();
            ArrayList<Producto> productosDisponibles = Tienda.getProductosDisponibles();
            
            System.out.println("\n\n\tBienvenido "+ cuenta.getNombre() +"! Que deseas hacer?");
            switch(cuenta.getTipo()){
                case "Usuario": // Caso inicial, con tipo de cuenta usuario
                    try { // Try usado por posibles errores al ingresar texto por consola
                        System.out.println("0) Salir");
                        System.out.println("1) Definir tipo de la cuenta (Cliente o Trabajador)");
                        System.out.print("-> ");
                        int respTipo = scan.nextInt();
                        scan.nextLine();
                        switch(respTipo){
                            case 0:
                                System.out.println("Hasta luego!");
                                estado = 0;
                                break;
                            case 1:
                                System.out.println("\nElejir: \n1)Cliente\n2)Trabajador");
                                System.out.print("-> ");
                                int respTipo2 = scan.nextInt();
                                if(respTipo2 == 1){
                                    cuenta = new Cliente(cuenta.getNombre(), cuenta.getEdad());
                                } else if(respTipo2 == 2){
                                    cuenta = new Trabajador(cuenta.getNombre(), cuenta.getEdad(), "Ninguno");
                                }
                                System.out.println("Cambio realizado con exito.");
                                break;
                            default:
                                System.out.println("Se elijio una opción incorrecta.");
                            break;
                        }
                    } catch (InputMismatchException e) { // Caso de error por texto ingresado del tipo incorrecto
                        System.out.println("Lo ingresado no coincide con el tipo de dato a ingresar.");
                        estado = 0;
                    }
                    break;
                case "Cliente": // Caso en que el tipo de cuenta sea Cliente
                try { // Try usado por posibles errores al ingresar texto por consola
                    Cliente cuentaCliente = ((Cliente) cuenta);
                    System.out.println("0) Salir");
                    System.out.println("1) Comprar pelicula");
                    System.out.println("2) Comprar producto de la tienda");
                    System.out.println("3) Cancelar compra");
                    System.out.println("4) Depositar");
                    System.out.println("5) Ver perfil");

                    System.out.print("-> ");
                    int respCli = scan.nextInt(); //Ingresa un int
                    scan.nextLine();
                    switch(respCli){
                        case 0: // Salida
                            System.out.println("Hasta luego!");
                            estado = 0; // Se termina el bucle si estado es diferente de 1
                            break;
                        case 1: // Caso comprar pelicula
                            System.out.println("Taquilla: ");
                            System.out.println(taquilla); // Se muestran las peliculas disponibles
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
                                        String respCompPe = cuentaCliente.comprarPelicula(respPelElegida,respAsientoEleg);
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
                        case 2: // Caso de comprar Producto
                            System.out.println("Tienda: ");
                            System.out.println(tienda); //Se muestran los productos con el toString del objeto
                            System.out.print("Escriba el nombre del producto a comprar: ");
                            String respNProd = scan.next();
                            boolean productoEncontrado = false;
                            for(Producto producto : productosDisponibles){
                                if(producto.getNombre().equals(respNProd)){
                                    productoEncontrado = true; //Se cambia el valor para indicar que si se encontro
                                    String respComProd = cuentaCliente.comprarProducto(respNProd);
                                    System.out.println(respComProd);
                                }
                            } 

                            if(!productoEncontrado) { //Si negacion de productoEncontrado es true, se ejecuta
                                System.out.println("Este producto no esta disponible.");
                            }
                            break;
                        case 3: // Caso de cancelar compra
                            System.out.println("\tCancelar compra.");
                            System.out.println("Pelicula (1) | Producto (2)");
                            System.out.print("-> ");
                            int respCanPelOProd = scan.nextInt();
                            if(respCanPelOProd == 1){
                                HashMap<Pelicula,List<Integer>> compras = cuentaCliente.getComprasPelicula();
                                if(compras.isEmpty()){
                                    System.out.println("No ha hecho ninguna compra aún.");
                                } else {
                                    System.out.print("Peliculas compradas: ");
                                    System.out.println(cuentaCliente.mostrarComprasPelicula());
                                    System.out.print("Escriba el nombre de la pelicula a cancelar: ");
                                    String compPel = scan.next();
                                    for (Pelicula pel : peliculasDisponibles) {
                                        if(pel.getNombre().equals(compPel) && compras.containsKey(pel)){
                                            System.out.println("Elija el asiento a cancelar de la pelicula");
                                            System.out.println(cuentaCliente.mostrarAsientosCompras(pel));
                                            int respAsCancelar = scan.nextInt();
                                            
                                            String respCan = cuentaCliente.cancelarCompraPelicula(compPel, respAsCancelar);
                                            System.out.println(respCan);
                                        }
                                    }
                                }
                            } else {
                                ArrayList<Producto> comprasPr = cuentaCliente.getComprasProducto();
                                if(comprasPr.isEmpty()){
                                    System.out.println("No ha hecho ninguna compra aún.");
                                } else {
                                    System.out.print("Productos comprados: ");
                                    System.out.println(cuentaCliente.mostrarComprasProducto());
                                    System.out.print("Escriba el nombre del producto a cancelar.\n-> ");
                                    String nombreCanProd = scan.next();
                                    for (Producto producto : comprasPr) {
                                        if(producto.getNombre().equals(nombreCanProd)){
                                            String respCanProd = cuentaCliente.cancelarCompraProducto(nombreCanProd);
                                            System.out.println(respCanProd);
                                            break;
                                        }
                                    }
                                }
                            } 
                            
                            break;
                        case 4: // Caso depositar
                            System.out.print("Digite la cantidad a depositar: ");
                            int deposit = scan.nextInt();
                            cuentaCliente.depositar(deposit);
                            System.out.println("Deposito ejecutado correctamente");
                            break;
                        case 5: // Caso ver perfil
                            System.out.print(cuentaCliente);
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
                    break; 
                case "Trabajador": // Caso en que el tipo de cuenta sea Trabajador
                    Trabajador cuentaTrabajador = ((Trabajador) cuenta);
                    System.out.println("0) Salir");  
                    System.out.println("1) Añadir pelicula taquilla");
                    System.out.println("2) Ver detalles pelicula");
                    System.out.println("3) Añadir producto tienda");
                    System.out.println("4) Ver peliculas en taquilla");
                    System.out.println("5) Ver productos en tienda");
                    System.out.println("6) Ver perfil");
                    
                    System.out.print("-> ");
                    int respInTrab = scan.nextInt();
                    switch(respInTrab){
                        case 0:
                            System.out.println("Hasta luego!");
                            estado = 0; //Se termina el bucle cuando estado es diferente de 1
                            break;
                        case 1:
                            System.out.println("\tAñadir pelicula");
                            System.out.print("Nombre de la pelicula: ");
                            String respAnadirPel = scan.next();
                            System.out.print("Precio de la pelicula: ");
                            int respAnadirPelPrecio = scan.nextInt();
                            System.out.print("Sala a enlazar a la pelicula");
                            System.out.println(Sala.mostrarSalasCreadas());
                            System.out.print("-> ");
                            String respSalEnlazar = scan.next();
                            for (Sala salaE : Sala.getsSalasCreadas()) {
                                if(salaE.getNombre().equals(respSalEnlazar)){
                                    String respuestaAPel = cuentaTrabajador.añadirPeliculaTaquilla(new Pelicula(respAnadirPel, respAnadirPelPrecio),salaE);
                                    System.out.println(respuestaAPel);
                                }
                            }
                        case 2:
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
                        case 3:
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
                                cuentaTrabajador.añadirProductoTienda(new Producto(respAnadirProd, respAnadirProdPrecio));
                            }
                            
                            break;
                        case 4:
                            System.out.println("\tTaquilla");
                            System.out.println(taquilla);
                            break;
                        case 5:
                            System.out.println(tienda);
                            break;
                        case 6:
                            System.out.println("\tPerfil");
                            System.out.println(cuentaTrabajador);
                            break;
                        default:
                            System.out.println("Opcion no valida.");
                            break;
                    }
                    break;
                default: // Caso por default (No se reconozca el tipo)
                    System.out.println(cuenta.getTipo());
                    System.out.println("Sin opciones, tipo desconocido.");
                    estado = 0;
                    break;
                }
                    
            }
            scan.close();
        } 
    }
