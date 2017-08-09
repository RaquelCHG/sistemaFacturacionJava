package modelo;

/**
 * En esta clase gestionamos el arrayListClientes
 *
 * @author Raquel Cabanyes
 * @param nombrecliente
 * @param nif
 * @param direccion
 */
public class Cliente {
    //Declaración de variables del arrayProducto

    private String nombrecliente;
    private String nif;
    private String direccion;
    Producto prod = new Producto();

    /**
     * Creamos el constructor para almacenar el nombreCliente
     *
     * @param nombrecliente
     */
    //Creamos los métodos del arrayProducto
    public void setNombreCliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    /**
     * Creamos el constructor para devolver el nombreCliente
     *
     * @return nombreCliente
     */
    public String getNombreCliente() {
        return this.nombrecliente;
    }

    /**
     * Creamos el constructor para almacenar el nif
     *
     * @param nif
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Creamos el constructor para devolver el nif
     *
     * @return nif
     */
    public String getNif() {
        return this.nif;
    }

    /**
     * Creamos el constructor para almacenar la direccion
     *
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Creamos el constructor para devolver la dirección
     *
     * @return direccion
     */
    public String getDireccion() {
        return this.direccion;
    }

    /**
     * Constructor vacío
     */
    public Cliente() {
    }
}
