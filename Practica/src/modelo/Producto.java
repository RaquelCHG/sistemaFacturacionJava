package modelo;

/**
 * En esta clase gestionamos el arrayListProducto
 *
 * @author Raquel Cabanyes
 * @param nombreProducto
 * @param pvp
 * @param unidades
 */
public class Producto {

    //Declaración de variables del arrayProducto
    private String nombreProducto;
    private int pvp;
    private int unidades;

    /**
     * Creamos el constructor para almacenar el nombreProducto
     *
     * @param nombreProducto
     */
    //Creamos los métodos del arrayProducto
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Creamos el constructor para devolver el nombreProduto
     *
     * @return nombreProducto
     */
    public String getNombreProducto() {
        return this.nombreProducto;
    }

    /**
     * Creamos el constructor para almacenar el pvp
     *
     * @param pvp
     */
    public void setPvp(int pvp) {
        this.pvp = pvp;
    }

    /**
     * Creamos el constructor para devolver el pvp
     *
     * @return pvp
     */
    public int getPvp() {
        return this.pvp;
    }

    /**
     * Creamos el constructor para almacenar las unidades
     *
     * @param unidades
     */
    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    /**
     * Creamos el constructor para devolver las unidades
     *
     * @return unidades
     */
    public int getUnidades() {
        return this.unidades;
    }

    /**
     * Constructor vacío
     */
    public Producto() {
    }
}
