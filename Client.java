
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/***
 1) create a connection socket given the server info (IP and Port 80)
 2) send an HTTP GET request asking for a file specified by a user input
 3) receive the HTTP response message from the server
    a) If it is an error message, show the error
    b) If it is a requested file, it saves the file into a local directory
 *
 */

public class Client {
    Socket clientSocket;
    public Client(){
        try{
            clientSocket = new Socket("localhost", 80);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void sendRequest(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the file you would like to get (WARNING: avoid whitespaces):- ");
            String in = scanner.next();
            OutputStream os = clientSocket.getOutputStream();
            String req = "GET http://" + clientSocket.getInetAddress().getHostAddress() + "/" + in;
            os.write(req.getBytes());
            os.flush();
            os.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.sendRequest();
    }
}
