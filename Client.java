
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    Socket clientSocket;
    public Client(){
        try{
            clientSocket = new Socket("localhost", 80);
            OutputStream os = clientSocket.getOutputStream();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

    }
}
