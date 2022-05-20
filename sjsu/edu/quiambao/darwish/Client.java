package sjsu.edu.quiambao.darwish;

import java.io.OutputStream;
import java.net.Socket;

public class Client {
    Socket clientSocket;
    public Client(){
        try{
            clientSocket = new Socket("localhost", 80);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        try{
            client.send
        }
    }
}
