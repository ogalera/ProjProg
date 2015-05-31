package logica;


import interficie.FLogin;
import java.io.IOException;


/**
 * @author oscar
 * @brief
 * main del projecte, des de aquí es mostra la pantalla de login.
 */
public class Projecte {

    /**
     * @pre --
     * @post em mostrat la pantalla de login.
     */
    public static void main(String[] args) throws IOException{
        new FLogin().mostrarFrame();
    }
    
}
