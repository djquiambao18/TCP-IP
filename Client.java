
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
            clientSocket = new Socket(InetAddress.getLocalHost(), 80);
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
            // read server response into client:
            InputStream is = clientSocket.getInputStream();
            byte[] responseHeader = new byte[1024];
            int size = is.read(responseHeader);
            responseHeader = Arrays.copyOf(responseHeader, size);
            System.out.println(new String(responseHeader));

            // for reading in the data sent over the network.
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            // read in data size:
            int dataSize = dis.readInt();

            if(dataSize > 0) {
                // read in the data/file:
                byte [] data = new byte[dataSize];
                dis.readFully(data, 0, dataSize);

                // for parsing out the forward slash from requested file pathing
                String[] parseDir;
                String fileName = "";
                if (in.contains("/")) {
                    parseDir = in.split("/");
                    fileName = parseDir[parseDir.length - 1];
                }
                if (!fileName.isEmpty()) {
                    // create the file as per filename
                    File file = new File(fileName);
                    // try to write the actual data into the file:
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(data);
                        fos.flush();
                    } catch (FileNotFoundException fe) {
                        fe.printStackTrace();
                    }
                }
                os.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Client client = new Client();
        client.sendRequest();
    }
}
