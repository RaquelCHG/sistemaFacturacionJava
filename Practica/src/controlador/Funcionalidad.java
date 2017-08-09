package controlador;

import vista.Menu;
import modelo.Factura;

/**
 * En esta clase se controla la ejecución del programa y van llamando a los
 * métodos
 *
 * @author: Raquel Cabanyes
 */
public class Funcionalidad {

    /**
     * Controla el orden de ejecución del programa
     *
     * @see Menu
     * @see Factura
     * @see menu
     * @see pedirOpcion
     * @see error
     * @see agregarFactura
     * @see crearFactura
     * @see sql
     * @see buscarCliente
     * @see mostrarFactura
     * @see generarHtml
     * @param opcion
     */
    public static void main(String[] args) {
        //Cargamos los paquetes con las clases correspondientes que vamos a utilizar.
        Menu v = new Menu();            //Este paquete corresponde al vista y es la clase Menú
        Factura m = new Factura();      //Este paquete corresponde al modelo y es la clase Factura

        int opcion = 0;

        do {
            v.menu();
            opcion = v.pedirOpcion();
            v.error(opcion);

            switch (opcion) {
                case 1:
                    if (m.agregarFactura()) {    //Introduce los datos de clientes y productos en el arrayList.
                        m.crearFactura();       //Crea el archivo .factura.
                        m.sql();                //Crea el archivo .sql, creando las tablas sino existe, e inserta la línea con la nueva factura.
                    }
                    break;
                case 2:
                    if (m.buscarCliente()) {      //Se realiza la búsqueda del archivo por medio del NIF del cliente, si el archivo existe devuelve un true y continua con el funcionamiento, si por el contrario no existe, devuelve un false y vuelve al menú principal.
                        m.mostrarFactura();     //Muestra por pantalla una factura con los datos del cliente que hemos buscado anteriormente en el método m.buscarCliente()
                    }
                    break;
                case 3:
                    if (m.buscarCliente()) {      //Se realiza la búsqueda del archivo por medio del NIF del cliente.
                        m.generarHtml();        //Crea el archivo .html con los datos del cliente que hemos buscado anteriormente en el método m.buscarCliente(), si por el contrario el archivo ya existía no lo sobrescribe.
                    }
                    break;
            }
        } while (opcion != 4);
    }
}
