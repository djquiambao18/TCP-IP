import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
    private void acceptConn(){
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
            System.out.println(str);
            in.close();
            // Split the received string for parsing
            String [] reqMsg = str.split(" ");
            // input validation:
            if(reqMsg.length > 0 && reqMsg[0].equals("GET")){
                if(!reqMsg[1].isEmpty()) {
                    String address = reqMsg[1];
                    String [] fileDir = address.split("http://\\d+.\\d+\\.\\d+.\\d+/");
                    String [] serverAddr = address.split("\\.");
                    StringBuilder strBuilder = new StringBuilder();
                    for(int i = 0; i < serverAddr.length-1; i++){
                        strBuilder.append(serverAddr[i]).append(".");
                    }
                    strBuilder.append(serverAddr[serverAddr.length-1]);
                    // reassign String address to string representation of IP address
                    address = strBuilder.toString();
                    //verify that address is same as server address.
                    if(address.equals(svSock.getInetAddress().getHostAddress())) {
                        String fileName = fileDir[1];
                        sender(fileName, clientSocket);
                        clientSocket.close();
                    }
                    else{
                        System.out.println("Invalid IP");
                    }
                }
                // if address portion is missing:
                else{
                    errResponse(clientSocket);
                }
            }
            // if req Message is empty or does not equals 'GET'
            else{
                errResponse(clientSocket);
            }
            if(clientSocket.isClosed()){
                System.out.println("socket closed");
            }
        }
        catch(IOException io){
            io.printStackTrace();
        }

    }
    private void errResponse(Socket s) throws IOException{
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
        String response = "Unrecognized Command.";
        dOut.writeInt(response.length());
        dOut.write(response.getBytes(StandardCharsets.UTF_8));
    }
    //sends data over the TCP connection provided the filename and socket.
    protected void sender(String fName, Socket socket) {
            try {
                // Obtain the file using the path passed in as an argument
                File file = new File(fName);
                // var for length of file
                int fileLength;
                // variables:
                byte[] fileBytes;
                String responseMsg;
                // for reading file contents

                // verify file exists:
                if (!file.exists()) {
                        responseMsg = "HTTP/1.1 404 Not Found";
                        System.out.println(responseMsg);
                }
                 else {
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    // length of file and allocate byte array for sending file over TCP/IP
                    fileLength = (int) file.length();

                    // store file's data into byte array
                    fileBytes = new byte[fileLength];
                    // index counter for storing data into byte array
                    int index = 0;
                    // stores returned int from .read():
                    int bt;

                    while ((bt = is.read()) != -1) {
                        // assign the byte into the byte array for sending data over network
                        fileBytes[index] = (byte) bt;
                        index++;
                    }
                    responseMsg = "HTTP/1.1 200 OK";
                    StringBuilder strBuilder = new StringBuilder();
                    strBuilder.append(responseMsg).append("[");
                    strBuilder.append(Arrays.toString(fileBytes)).append("]");
                    responseMsg = strBuilder.toString();
                }
                System.out.println("Server out: " + responseMsg);
                OutputStream os = socket.getOutputStream();
                os.write(responseMsg.getBytes());
                os.flush();
                os.close();

            } catch(IOException f){
                f.printStackTrace();
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
