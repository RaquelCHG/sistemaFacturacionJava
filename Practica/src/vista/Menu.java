package vista;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * En esta clase alberga todas las ventanas que se muestran al usuario.
 *
 * @author: Raquel Cabanyes
 */
public class Menu {

    /**
     * Este método muestra las opciones del menu
     */
    public void menu() {         //Muestra al usuario el menú principal.
        for (int i = 0; i < 70; i++) {
            System.out.print("*");
        }
        System.out.println("");
        System.out.println("1. Agregar factura.");
        System.out.println("2. Consultar factura.");
        System.out.println("3. Generar factura en HTML.");
        System.out.println("4. Salir.");
        System.out.print("Elija una opcion: ");
    }

    /**
     * En este método se almacena la opción elegida por el usuario
     *
     * @return opcion
     * @throws InputMismatchException
     */
    public int pedirOpcion() {       //Guarda la opción elegida por el usuario.
        Scanner teclado = new Scanner(System.in);

        try {

            int opcion = 0;
            opcion = teclado.nextInt();
            return opcion;

        } catch (InputMismatchException e) {       //Recoge un error si intentamos meter en un entero cualquier valor no valido.
            System.out.println("");
            System.out.println("Se ha producido un error al introducir un valor no valido");
            return 4;
        }
    }

    /**
     * Este método muestra un mensaje al no ser válida la opción introducida por
     * el usuario
     *
     * @param opcion
     */
    public void error(int opcion) {      //Muestra un mensaje de error, si la opción elegida no esta dentro de las posibles en el menú.
        for (int i = 0; i < 70; i++) {
            System.out.print("*");
        }
        System.out.println("");
        if (opcion < 1 || opcion > 4) {
            System.out.println("La opción no es válida.");
        }
    }
}
