import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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
            System.out.println("Server Socket created with IP: " + this.svSock.getInetAddress().getHostAddress() + " and Port: " + this.svSock.getLocalPort());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // receive HTTP GET requests over the connection
    private void acceptConn() throws IOException{
        try {
            Socket clientSocket = svSock.accept();

            // buffer for the received data
            byte[] request = new byte[1024];

            // Incoming stream
            InputStream in = clientSocket.getInputStream();

            // Receive data from the stream
            int reqSize = in.read(request);
            request = Arrays.copyOf(request, reqSize);
            String str = new String(request);
            System.out.println("Server received: " + str);
        }
        catch(IOException io){
            io.printStackTrace();
        }
    }

    private int getSocketTimeout() throws IOException {
        return svSock.getSoTimeout();
    }
    private InetAddress getInetAddress(){
        return svSock.getInetAddress();
    }

    private int getPort(){
        return svSock.getLocalPort();
    }

    public static void main(String[] args) {
        Server srv = new Server();
        try {
            srv.acceptConn();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
