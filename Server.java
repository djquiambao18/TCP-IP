import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Specifically, your Web server will
 * 1) create a TCP connection socket when contacted by a client (that plays a browser role)
 * 2) receive HTTP GET requests over the connection
 * 3) parse the request to determine the specific file being requested
 * 4) get the requested file from the server’s file system
 * 5) create an HTTP response message consisting of the requested file preceded by header
 * lines
 * 6) send the response over the TCP connection to the requesting client
 * */
public class Server {
    private ServerSocket svSock;

    public Server(){
        try{
            svSock = new ServerSocket(80);

            Socket clientSocket = svSock.accept();
            byte[] request = new byte[1024];

            InputStream in = clientSocket.getInputStream();
            int reqSize = in.read(request);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // receive HTTP GET requests over the connection
    private void acceptConn(){

    }
//    public static void main(String[] args) {
//        try{
//            try (ServerSocket svSock = new ServerSocket(80)) {
//
//            }catch(IOException io){
//                System.out.println("IOException occurred");
//                io.printStackTrace();
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
