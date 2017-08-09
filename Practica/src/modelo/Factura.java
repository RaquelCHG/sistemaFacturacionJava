package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import vista.Menu;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * En esta clase controlamos todos los métodos, constructores y variables del
 * programa
 *
 * @author Raquel Cabanyes
 *
 * @see Cliente
 * @see Producto
 *
 * @param nombreCliente
 * @param nif
 * @param direccion
 * @param nombreProducto
 * @param pvp
 * @param unidades
 * @param numproductos
 * @param static final iva
 */
public class Factura {

    Scanner teclado = new Scanner(System.in);
    Menu v = new Menu();

    ArrayList<Cliente> arrayListCliente = new ArrayList<Cliente>();
    String nombreCliente = "", nif = "", direccion = "";
    ArrayList<Producto> arrayListProducto = new ArrayList<Producto>();
    String nombreProducto = "";
    int pvp = 0, unidades = 0;
    static final int numproductos = 3;      //Número fijo, que almacena el número de productos que comprara cada cliente.
    static final int iva = 21;          //Número fijo, que almacena el IVA aplicado a los productos.

    /**
     * Introducimos todos los datos de cliente y produtco, almacenandolos en el
     * arrayListCliente y arrayListProducto respectívamente.
     *
     * @see Cliente
     * @see Producto
     * @param nombreCliente
     * @param nif
     * @param direccion
     * @param nombreProducto
     * @param static final numproductos
     * @param pvp
     * @param unidades
     * @throws InputMismatchException
     *
     * @return boolean
     */
    public boolean agregarFactura() {           //Pide los datos de la factura al usuario y los guarda en dos arrayList, uno para Clientes y otro para Productos

        try {
            Cliente cli = new Cliente();       //Llama a la clase Cliente y crea un nuevo objeto

            System.out.print("Indique el nombre del cliente: ");        //Pide los datos de Cliente
            nombreCliente = teclado.nextLine();
            cli.setNombreCliente(nombreCliente);
            System.out.print("Indique el NIF: ");
            nif = teclado.nextLine();
            cli.setNif(nif.toUpperCase());
            System.out.print("Indique la dirección fiscal: ");
            direccion = teclado.nextLine();
            cli.setDireccion(direccion);

            // Añadimos al ArrayList el objeto Teclado
            arrayListCliente.add(0, cli);

            for (int i = 0; i < numproductos; i++) {         //Pide los datos de Producto
                Producto prod = new Producto();

                System.out.print("Indique el nombre del producto: ");
                nombreProducto = teclado.nextLine();
                prod.setNombreProducto(nombreProducto);
                System.out.print("Indique el precio unitario: ");
                pvp = teclado.nextInt();
                prod.setPvp(pvp);
                teclado.nextLine();    //Soluciona el problema del nextLine(); al meter un nextInt(); o nextDouble();
                System.out.print("Indique el número de unidades: ");
                unidades = teclado.nextInt();
                prod.setUnidades(unidades);
                teclado.nextLine();

                arrayListProducto.add(i, prod);
            }
            return true;
        } catch (InputMismatchException e) {       //Recoge un error si intentamos meter en un entero cualquier valor no valido.
            System.out.println("");
            System.out.println("Se ha producido un error al introducir un valor no valido");
            return false;
        }
    }

    /**
     * Crea el archivo .factura, almacenando todos los datos del cliente y de
     * los productos adquiridos.
     *
     * @see Producto
     * @see Cliente
     * @throws IOException
     */
    public void crearFactura() {         //Guarda los datos introducidos en un .factura

        try {
            Iterator<Cliente> iteradorCliente = arrayListCliente.iterator();
            Cliente clienteActual = (Cliente) iteradorCliente.next();
            Iterator<Producto> iteradorProducto = arrayListProducto.iterator();

            String linea = "";

            FileWriter fichero = new FileWriter(clienteActual.getNif() + ".factura");       //Abrimos y creamos el fichero .factura.
            PrintWriter escribir = new PrintWriter(fichero);

            linea = clienteActual.getNombreCliente() + "_" + clienteActual.getNif() + "_" + clienteActual.getDireccion() + "_";         //Concatenamos las variables de Cliente con _, para posteriormente al leerlas del archivo guardarlas en las variables correspondientes.

            while (iteradorProducto.hasNext()) {
                Producto productoActual = (Producto) iteradorProducto.next();

                linea = linea + productoActual.getNombreProducto() + "_";
                linea = linea + productoActual.getUnidades() + "_";
                linea = linea + productoActual.getPvp() + "_";
            }

            escribir.println(linea);            //Escribe en el archivo la línea con todas las variables que se desean guardar

            fichero.close();        //Cerramos el fichero

            System.out.println("");
            System.out.println("El archivo " + clienteActual.getNif() + ".factura se ha creado correctamente.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al crear e introducir los datos en el archivo seleccionado.");
        }
    }

    /**
     * Crea el archivo .sql, si el archivo no existe almacenara las tablas y la
     * información de los campos, si ya existe almacera únicamente la
     * información de los campos
     *
     * @see Cliente
     * @see Producto
     *
     * @param static final numproductos
     *
     * @throws IOException
     */
    public void sql() {           //Crea el archivo .sql

        try {        //Bloque de codigo, se capturara si hay un error
            String archivo = "clientes.sql";
            File fichero = new File(archivo);

            if (fichero.exists()) {          //Si el fichero ya existe, inserta la nueva línea con la información del cliente
                FileWriter ficherosql = new FileWriter("clientes.sql", true);       //Con el valor true después de la ruta del fichero, le indicamos que no sobrescriba el fichero, sino que agregue líneas de texto
                PrintWriter escribir = new PrintWriter(ficherosql);

                escribir.println("insert into cliente values(idcliente.nextval,'" + arrayListCliente.get(0).getNombreCliente() + "','" + arrayListCliente.get(0).getNif() + "','" + arrayListCliente.get(0).getDireccion() + "');");

                for (int i = 0; i < numproductos; i++) {
                    escribir.println("insert into producto values('" + arrayListProducto.get(i).getNombreProducto() + "'," + arrayListProducto.get(i).getPvp() + "," + arrayListProducto.get(i).getUnidades() + ",'" + arrayListCliente.get(0).getNif() + "');");
                }

                ficherosql.close();

                System.out.println("Se ha añadido la linea al fichero SQL correctamente.");
            } else {          //Si el fichero no existe, agregamos la tabla Clientes con los campos correspondientes y la línea con la información almacena en el arrayList.
                FileWriter ficherosql = new FileWriter("clientes.sql");
                PrintWriter escribir = new PrintWriter(ficherosql);

                escribir.println("create table cliente(");      //Crea el codigo para la tabla cliente en sql
                escribir.println("\tidcliente varchar2(4),");
                escribir.println("\tnombre varchar2(50),");
                escribir.println("\tnif varchar2(9) constraint cli_nif_pk primary key,");
                escribir.println("\tdireccion varchar2(50)");
                escribir.println(");");
                escribir.println("create sequence idcliente increment by 1 start with 1;");

                escribir.println("create table producto(");     //Crea el codigo para la tabla producto en sql
                escribir.println("\tnombre varchar2(50),");
                escribir.println("\tpvp number(5,2),");
                escribir.println("\tunidades number(5),");
                escribir.println("\tnif varchar2(9)constraint prod_nif_fk references cliente(nif)");
                escribir.println(");");

                //Añade la informacion a la tabla cliente
                escribir.println("insert into cliente values(idcliente.nextval,'" + arrayListCliente.get(0).getNombreCliente() + "','" + arrayListCliente.get(0).getNif() + "','" + arrayListCliente.get(0).getDireccion() + "');");

                //Añade la informacion de los 3 productos a la tabla producto, asociados a cada cliente
                for (int i = 0; i < numproductos; i++) {
                    escribir.println("insert into producto values('" + arrayListProducto.get(i).getNombreProducto() + "'," + arrayListProducto.get(i).getPvp() + "," + arrayListProducto.get(i).getUnidades() + ",'" + arrayListCliente.get(0).getNif() + "');");
                }

                System.out.println("El fichero SQL se ha creado correctamente.");

                ficherosql.close();
            }
        } catch (IOException e) {      //Captura las excepcione, por si ocurre algun error
            System.out.println("Ha ocurrido un error al crear e introducir los datos en el archivo seleccionado.");
        }
    }

    /**
     * Se pide al usuario el NIF de la factura a consultar, se realiza la
     * búsqueda entre los archivos .factura, para comprobar si el archivo existe
     * o no.
     *
     * @see leerFactura
     * @param nifbusqueda
     * @return boolean
     */
    public boolean buscarCliente() {     //Busca el archivo .factura de un cliente por su DNI.
        String nifbusqueda = "";

        System.out.print("Indique el NIF del cliente que desea consultar: ");
        nifbusqueda = teclado.nextLine();
        System.out.println("");

        String archivo = nifbusqueda.toUpperCase() + ".factura";
        File fichero = new File(archivo);

        if (fichero.exists()) {          //Si el NIF del cliente es el nombre de un archivo . factura, envía el NIF del cliente para abrir el archivo.
            if (leerFactura(nifbusqueda)) {
                return true;
            } else {
                return false;
            }
        } else {          //Si el NIF no corresponde con el nombre de un archivo .factura, nos devuelve al menú.
            System.out.println("El NIF introducido no corresponde con ninguna factura resgistrada.");
            return false;
        }
    }

    /**
     * Carga el .factura en el correspondiente arrayListProducto y
     * arrayListCliente
     *
     * @param nifbusqueda
     * @param nombre
     * @param precio
     * @param cantidad
     * @param static final numproductos
     * @param nombreCliente
     * @param nif
     * @param direccion
     * @param campos
     * @param nombreProducto
     * @param pvp
     * @param unidades
     * @throws Exception
     * @return boolean
     */
    public boolean leerFactura(String nifbusqueda) {         //Con la variable nifbusqueda, abre el archivo y lo guarda en el arrayList.

        try {
            FileReader fichero = new FileReader(nifbusqueda + ".factura");
            BufferedReader br = new BufferedReader(fichero);
            String[] nombre = new String[numproductos];
            String[] precio = new String[numproductos];
            String[] cantidad = new String[numproductos];
            int j = 0;

            String linea = br.readLine();           //Guarda el texto del archivo en la variable línea.
            String[] campos = linea.split("_");     //Separa el texto del archivo separado por "_", y lo guarda en un array.

            nombreCliente = campos[0];              //Guarda el array en cada variable correspondiente.
            nif = campos[1];
            direccion = campos[2];

            for (int i = 3; i < 12; i = i + numproductos) { 3/0 - 6/3 - 9/3 - 12
                nombre[j] = campos[i];                      3   - 6   - 9
                precio[j] = campos[i + 1];                  4   - 7   - 10
                cantidad[j] = campos[i + 2];                5   - 8   - 11
                j++;
            }

            Cliente cli = new Cliente();

            cli.setNombreCliente(nombreCliente);        //Pasa las variables a los métodos set para ser almacena en el objeto.
            cli.setNif(nif);
            cli.setDireccion(direccion);
            arrayListCliente.add(0, cli);               //Añade al objeto

            for (int i = 0; i < numproductos; i++) {
                Producto pro = new Producto();
                nombreProducto = nombre[i];
                pvp = Integer.parseInt(precio[i]);          //Convertimos en un int la información correspondiente almacenada en el String campos.
                unidades = Integer.parseInt(cantidad[i]);
                pro.setNombreProducto(nombreProducto);
                pro.setPvp(pvp);
                pro.setUnidades(unidades);
                arrayListProducto.add(i, pro);
            }

            fichero.close();

            return true;
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer archivo seleccionado.");
            return false;
        }
    }

    /**
     * Muestra la información almacenada en el arraylistProducto y
     * arrayListCliente
     *
     * @see Cliente
     * @see Producto
     * @param static final numproductos
     * @param pvp
     * @param unidades
     * @param rdounidades
     * @param base
     * @param total
     * @param ivatotal
     * @param static final iva
     *
     */
    public void mostrarFactura() {           //Mostramos la informacion almacena en el arrayList   

        int pvp = 0, unidades = 0, rdounidades = 0, base = 0, total = 0, ivatotal = 0;

        for (int i = 0; i < 70; i++) {
            System.out.print("=");
        }
        System.out.println("");

        System.out.println("Nombre: " + arrayListCliente.get(0).getNombreCliente());
        System.out.println("NIF: " + arrayListCliente.get(0).getNif());
        System.out.println("Dirección fiscal: " + arrayListCliente.get(0).getDireccion());

        for (int i = 0; i < 70; i++) {
            System.out.print("=");
        }
        System.out.println("");

        for (int i = 0; i < numproductos; i++) {

            pvp = arrayListProducto.get(i).getPvp();
            unidades = arrayListProducto.get(i).getUnidades();
            rdounidades = pvp * unidades;

            if (arrayListProducto.get(i).getNombreProducto().length() < 8) {
                System.out.print(arrayListProducto.get(i).getNombreProducto() + "\t\t\t\t\t");
            } else if (arrayListProducto.get(i).getNombreProducto().length() < 16) {
                System.out.print(arrayListProducto.get(i).getNombreProducto() + "\t\t\t\t");
            } else if (arrayListProducto.get(i).getNombreProducto().length() < 24) {
                System.out.print(arrayListProducto.get(i).getNombreProducto() + "\t\t\t");
            } else if (arrayListProducto.get(i).getNombreProducto().length() < 32) {
                System.out.print(arrayListProducto.get(i).getNombreProducto() + "\t\t");
            } else if (arrayListProducto.get(i).getNombreProducto().length() < 40) {
                System.out.print(arrayListProducto.get(i).getNombreProducto() + "\t");
            } else if (arrayListProducto.get(i).getNombreProducto().length() > 39) {
                System.out.print(arrayListProducto.get(i).getNombreProducto());
            }

            String caracteres = Integer.toString(arrayListProducto.get(i).getUnidades());

            if (caracteres.length() < 2) {
                System.out.print("*    " + arrayListProducto.get(i).getUnidades() + " Ud.  ");
            } else if (caracteres.length() < 3) {
                System.out.print("*   " + arrayListProducto.get(i).getUnidades() + " Ud.  ");
            } else if (caracteres.length() < 4) {
                System.out.print("*  " + arrayListProducto.get(i).getUnidades() + " Ud.  ");
            } else if (caracteres.length() > 3) {
                System.out.print("* " + arrayListProducto.get(i).getUnidades() + " Ud.  ");
            }

            caracteres = Integer.toString(arrayListProducto.get(i).getPvp());

            if (caracteres.length() < 2) {
                System.out.print("*    " + arrayListProducto.get(i).getPvp() + "€  ");
            } else if (caracteres.length() < 3) {
                System.out.print("*   " + arrayListProducto.get(i).getPvp() + "€  ");
            } else if (caracteres.length() < 4) {
                System.out.print("*  " + arrayListProducto.get(i).getPvp() + "€  ");
            } else if (caracteres.length() > 3) {
                System.out.print("* " + arrayListProducto.get(i).getPvp() + "€  ");
            }

            caracteres = Integer.toString(rdounidades);

            if (caracteres.length() < 2) {
                System.out.println("*    " + rdounidades + "€");
            } else if (caracteres.length() < 3) {
                System.out.println("*   " + rdounidades + "€");
            } else if (caracteres.length() < 4) {
                System.out.println("*  " + rdounidades + "€");
            } else if (caracteres.length() > 3) {
                System.out.println("* " + rdounidades + "€");
            }

            base = base + rdounidades;
        }

        for (int i = 0; i < 70; i++) {
            System.out.print("=");
        }
        System.out.println("");

        System.out.print("Base\t\t\t\t\t\t\t");
        String caracteres = Integer.toString(base);
        if (caracteres.length() < 2) {
            System.out.println("     *    " + base + "€");
        } else if (caracteres.length() < 3) {
            System.out.println("     *   " + base + "€");
        } else if (caracteres.length() < 4) {
            System.out.println("     *  " + base + "€");
        } else if (caracteres.length() > 3) {
            System.out.println("     * " + base + "€");
        }
        ivatotal = (base * iva) / 100;

        System.out.print("IVA " + iva + "%\t\t\t\t\t\t\t");
        caracteres = Integer.toString(ivatotal);
        if (caracteres.length() < 2) {
            System.out.println("     *    " + ivatotal + "€");
        } else if (caracteres.length() < 3) {
            System.out.println("     *   " + ivatotal + "€");
        } else if (caracteres.length() < 4) {
            System.out.println("     *  " + ivatotal + "€");
        } else if (caracteres.length() > 3) {
            System.out.println("     * " + ivatotal + "€");
        }
        total = base + ivatotal;

        System.out.print("Total\t\t\t\t\t\t\t");
        caracteres = Integer.toString(total);
        if (caracteres.length() < 2) {
            System.out.println("     *    " + total + "€");
        } else if (caracteres.length() < 3) {
            System.out.println("     *   " + total + "€");
        } else if (caracteres.length() < 4) {
            System.out.println("     *  " + total + "€");
        } else if (caracteres.length() > 3) {
            System.out.println("     * " + total + "€");
        }

        for (int i = 0; i < 70; i++) {
            System.out.print("=");
        }
        System.out.println("");
        System.out.println("");
    }

    /**
     * Crea el .html con al información almacena en el arraylistCliente y
     * arraylistProducto
     *
     * @see Cliente
     * @see Producto
     * @param static final numproductos
     * @param pvp
     * @param unidades
     * @param rdounidades
     * @param base
     * @param total
     * @param ivatotal
     * @param static final iva
     * @throws IOException
     */
    public void generarHtml() {           //Creamos el html con la factura del cliente por el NIF.

        try {
            int pvp = 0, unidades = 0, rdounidades = 0, base = 0, ivatotal = 0, total = 0;

            File ficherohtml = new File(arrayListCliente.get(0).getNif() + ".html");

            if (ficherohtml.exists()) {
                System.out.println("El cliente ya tiene una factura creada en html.");
            } else {
                FileWriter fichero = new FileWriter(arrayListCliente.get(0).getNif() + ".html");
                PrintWriter escribir = new PrintWriter(fichero);

                escribir.println("<!DOCTYPE html>");
                escribir.println("<html>");
                escribir.println("<head>");
                escribir.println("<meta name ='Content-Language' content='es-ES'>");
                escribir.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
                escribir.println("<meta name='author' content='Raquel'>");
                escribir.println("<title>Factura</title>");
                escribir.println("<style>");
                escribir.println("*{margin: 0 auto;}");
                escribir.println("h1,p,h2{color: #2E2E2E;line-height: 35px;}");
                escribir.println("#superior{width: 87%;height: 120px;padding: 20px 38px;position: relative;}");
                escribir.println("#facturacion{width: 87%;height: 200px;padding: 20px 38px;position: relative;}");
                escribir.println("#descripcion{width: 87%;height: 350px;padding: 20px 20px;position: relative;}");
                escribir.println("#importe{width: 87%;height: 180px;padding: 20px 38px;position: relative;}");
                escribir.println("</style>");
                escribir.println("</head>");
                escribir.println("<body>");
                escribir.println("<div id='superior'>");
                escribir.println("<h1 style='text-align: right; font-family: arial; font-size: 38px; margin-right: 36px;'>FACTURA</h1>");
                escribir.println("<h2 style='text-align: right; font-family: arial; font-size: 25px; color: #FE2E2E; margin-right: 36px;'>Nº 0093581H</h2>");
                escribir.println("<h2 style='text-align: right; font-family: arial; font-size: 25px; margin-right: 36px;'>FECHA: 16/03/2017</h2>");
                escribir.println("<div><hr style='position: absolute; width: 87%; left: 75px; top: 155px; height: 3px; background-color: #585858; margin-right: 36px;'/></div>");
                escribir.println("</div>");
                escribir.println("<div id='facturacion'>");
                escribir.println("<div style='position: absolute; width: 46.6%; height: 60%; left: 74px; top: 40px;'>");
                escribir.println("<h3 style='text-align: left; font-family: arial; font-size: 20px;'>Dirección de entrega</h3>");
                escribir.println("<p style='text-align: left; font-family: arial; font-size: 15px;'>" + arrayListCliente.get(0).getNombreCliente() + "</p>");
                escribir.println("<p style='text-align: left; font-family: arial; font-size: 15px;'>" + arrayListCliente.get(0).getDireccion() + "</p>");
                escribir.println("<p style='text-align: left; font-family: arial; font-size: 15px;'>" + arrayListCliente.get(0).getNif() + "</p>");
                escribir.println("</div>");
                escribir.println("<div style='position: absolute; width: 40.6%; height: 60%; left: 53%; top: 40px;'>");
                escribir.println("<h3 style='text-align: left; font-family: arial; font-size: 20px;'>Dirección de Facturación</h3>");
                escribir.println("<p style='text-align: left; font-family: arial; font-size: 15px;'> " + arrayListCliente.get(0).getDireccion() + "</p>");
                escribir.println("<p style='text-align: left; font-family: arial; font-size: 15px;'> " + arrayListCliente.get(0).getNif() + "</p>");
                escribir.println("</div>");
                escribir.println("</div>");
                escribir.println("<div id='descripcion'>");
                escribir.println("<table style='width: 93.2%; border-collapse: collapse;'>");
                escribir.println("<tr style='margin-bottom: 20px;'>");
                escribir.println("<td style='text-align: left; font-family: arial; font-size: 20px; width: 55%; height: 75px; border-top: 2px solid #585858; border-bottom: 2px solid #585858 '>DESCRIPCIÓN</td>");
                escribir.println("<td style='text-align: right; font-family: arial; font-size: 20px;width: 15%; border-top: 2px solid #585858; border-bottom: 2px solid #585858 '>CANTIDAD</td>");
                escribir.println("<td style='text-align: right; font-family: arial; font-size: 20px;width: 15%; border-top: 2px solid #585858; border-bottom: 2px solid #585858 '>PRECIO</td>");
                escribir.println("<td style='text-align: right; font-family: arial; font-size: 20px;width: 15%; border-top: 2px solid #585858; border-bottom: 2px solid #585858 '>IMPORTE</td>");
                escribir.println("</tr>");

                for (int i = 0; i < numproductos; i++) {
                    pvp = arrayListProducto.get(i).getPvp();
                    unidades = arrayListProducto.get(i).getUnidades();
                    rdounidades = pvp * unidades;

                    escribir.println("<tr>");
                    escribir.println("<td style='text-align: left; font-family: arial; font-size: 15px; width: 55%; height: 50px; border-bottom: 2px dashed #585858'>" + arrayListProducto.get(i).getNombreProducto() + "</td>");
                    escribir.println("<td style='text-align: right; font-family: arial; font-size: 15px; width: 15%; border-bottom: 2px dashed #585858'> " + arrayListProducto.get(i).getUnidades() + "</td>");
                    escribir.println("<td style='text-align: right; font-family: arial; font-size: 15px; width: 15%; border-bottom: 2px dashed #585858'> " + arrayListProducto.get(i).getPvp() + "€</td>");
                    escribir.println("<td style='text-align: right; font-family: arial; font-size: 15px; width: 15%; border-bottom: 2px dashed #585858'> " + rdounidades + "€</td>");
                    escribir.println("</tr>");

                    base = base + rdounidades;
                }

                ivatotal = (base * iva) / 100;
                total = base + ivatotal;

                escribir.println("</table>");
                escribir.println("</div>");
                escribir.println("<div id='importe'>");
                escribir.println("<div><hr style='position: absolute; width: 87%; left: 75px; height: 3px; background-color: #585858; bottom: 215px;'/></div>");
                escribir.println("<div style='position: absolute; width: 76%; height: 84%; top: 40px;'>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px;'>Base imponible</p>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px;'>Tipo impositivo - IVA</p>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px;'>Importe con IVA</p>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px;'>Total</p>");
                escribir.println("</div>");
                escribir.println("<div style='position: absolute; width: 18%; height: 84%; left: 78%; top: 40px;'>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px; margin-right: 36px;'>" + base + "€</p>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px; margin-right: 36px;'>" + iva + "%</p>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px; margin-right: 36px;'>" + ivatotal + "€</p>");
                escribir.println("<p style='text-align: right; font-family: arial; font-size: 15px; margin-right: 36px;'>" + total + "€</p>");
                escribir.println("</div>");
                escribir.println("<div><hr style='position: absolute; width: 87%; left: 75px; top: 215px; height: 3px; background-color: #585858;'/></div>");
                escribir.println("</div>");
                escribir.println("</body>");
                escribir.println("</html>");

                fichero.close();

                System.out.println("El fichero html se ha creado correctamente");
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al crear e introducir los datos en el archivo seleccionado.");
        }

    }

}
