import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        try{
            try (ServerSocket svSock = new ServerSocket(80)) {

            }catch(IOException io){
                System.out.println("IOException occurred");
                io.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
