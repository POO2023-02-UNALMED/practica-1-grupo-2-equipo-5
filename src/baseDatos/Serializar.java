package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import uiMain.Cine;
import gestorAplicacion.Peliculas.*;
import gestorAplicacion.Tienda.*;
import gestorAplicacion.Usuarios.*;

public class Serializar {

    private static final String BASE_PATH = "src" + File.separator + "baseDatos" + File.separator + "temp" + File.separator;

    // Método genérico para la serialización
    private static <T> boolean serializeObject(ArrayList<T> lista, String filename) {
        try {
            String rutaArchivo = BASE_PATH + filename;
            FileOutputStream f = new FileOutputStream(new File(rutaArchivo));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(lista);
            o.close();
            f.close();

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            return false;
        }
    }

    public static void serializarPelicula(ArrayList<Pelicula> lista) {
        serializeObject(lista, "Peliculas.txt");
    }

    public static void serializarSala(ArrayList<Sala> lista) {
        serializeObject(lista, "Salas.txt");
    }

    public static void serializarTaquilla(ArrayList<Taquilla> lista) {
        serializeObject(lista, "Taquilla.txt");
    }

    public static void serializarTienda(ArrayList<Tienda> lista) {
        serializeObject(lista, "Tienda.txt");
    }

    public static void serializarProductos(ArrayList<Producto> lista) {
        serializeObject(lista, "Producto.txt");
    }

    public static void serializarCombos(ArrayList<Combo> lista) {
        serializeObject(lista, "Combo.txt");
    }

    public static boolean serializarCliente(ArrayList<Cliente> lista) {
        return serializeObject(lista, "Cliente.txt");
    }

    public static boolean serializarUsuario(ArrayList<Usuario> lista) {
        return serializeObject(lista, "Usuario.txt");
    }

    public static void serializarCine(ArrayList<Cine> lista) {
        if (serializeObject(lista, "Cine.txt")) {
            System.out.println("La serialización se ha completado correctamente.");
        }
    }
}
