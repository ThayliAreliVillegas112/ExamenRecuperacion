package mx.edu.utez.client;

import mx.edu.utez.server.Handler;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class JavaClient {

    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        System.out.println(read(client));
    }

    public static String read(XmlRpcClient client) throws XmlRpcException{
        Scanner sc = new Scanner(System.in);
        String res = "";
        int opc =0;
        do{
            System.out.println("Iniciando mi sistema");
            System.out.println("Menú\n1.- Registrar Usuario\n2.- Ver todos\n3.- Actualizar\n4.- Eliminar");
            opc = sc.nextInt();
            switch (opc){
                case 1:
                    // Registrar
                    System.out.println("Ingresa tu nombre");
                    String name = sc.next();
                    System.out.println("Ingresa tu apellido");
                    String lastname = sc.next();
                    System.out.println("Ingresa tu correo");
                    String email = sc.next();
                    System.out.println("Ingresa tu password");
                    String password = sc.next();

                    Object[] params1 = { name, lastname, email, password };
                    boolean result = (Boolean) client.execute("Handler.createUser" , params1);
                    res = result ? "Se ha registrado correctamente" : "No se ha registrado correctamente";
                    return res;
                case 2:
                    // Ver todos
                    System.out.println("Ver a todos los usuarios");
                    Handler handler =  new Handler();
                    for(User user : handler.findAll()){
                        System.out.println("-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
                        System.out.println("1.- Id: " + user.getId());
                        System.out.println("2.- Nombre: " + user.getName());
                        System.out.println("3.- Apellido: " + user.getLastname());
                        System.out.println("4.- Email (correo): " + user.getEmail());
                        System.out.println("5.- Password: " + user.getPassword());
                        System.out.println("6.- Fecha de registro: " + user.getDate());

                        if(user.getStatus()==1){
                            System.out.println("Status.... ¡Activo!");
                        }else{
                             System.out.println("Status... ¡Inactivo!");
                        }
                        res = "Se ha listado correctamente";
                        System.out.println("-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
                    }

                    break;
                case 3:
                    // Actualizar
                    System.out.println("Ingresa tu nombre");
                    String name2 = sc.next();
                    System.out.println("Ingresa tu apellido");
                    String lastname2 = sc.next();
                    System.out.println("Ingresa tu correo");
                    String email2 = sc.next();
                    System.out.println("Ingresa tu password");
                    String password2 = sc.next();
                    System.out.println("Ingresa el ID");
                    int id = sc.nextInt();

                    Object[] params2 = { id, name2, lastname2, email2, password2 };
                    boolean result2 = (Boolean) client.execute("Handler.updateUser" , params2);
                    res = result2 ? "Se ha actualizado correctamente" : "No se ha actualizado correctamente";
                    return res;
                case 4:
                    // Eliminar
                    System.out.println("Ingresa el ID");
                    int id2 = sc.nextInt();
                    Object[] params3 = { id2 };
                    boolean result3 = (Boolean) client.execute("Handler.deleteUser" , params3);
                    res = result3 ? "Se ha eliminado correctamente" : "No se ha eliminado correctamente";
                    return res;
                case 5:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }
        }while(opc != 5);

        return res;
    }
}
