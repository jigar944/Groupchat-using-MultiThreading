import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


//Main Server class
public class GroupChatServer{

    //List of active clients in the system
    //MultiClientHandler is the class which handles the multiple clients using Multithreading Concepts
    private static Set<MultiClientHandler> ActiveUsers = new HashSet<>(); 

    public static void main(String[] args) {

        //Server is running on port number 9898
        int port = 9898;

        try {

            //Serverscoket class is used to connect client and server on specific port
            //Serversocket is used on Server side programming
            ServerSocket GroupServerSocket = new ServerSocket(port);


            System.out.println("**********************************************************************************");
            System.out.println("******  GroupServer is started and now waiting for client to join groupChat ******");
            System.out.println("******  Listening ......");

            while(true){
                

                //Accept the connection which received on port 9898 from client side
                Socket NewClientsocket = GroupServerSocket.accept();

                //getInetAddress returns the Inet address of socket
                InetAddress ClientInetAdress = NewClientsocket.getInetAddress(); 

                //getLocalPort returns the port number of socket
                Integer ClientPort = NewClientsocket.getLocalPort();

                //getRemoteSocketAddress rerurns the address of the endpoint with port number of connected socket
                System.out.println("New Client connection established :" + NewClientsocket.getRemoteSocketAddress());

                //MutiClientHandler is the class to handle the multiple client 
                //intialize the construnctor of MultiClientHanler with NewClientSocket and ActiverUsers list
                MultiClientHandler clientHandler = new MultiClientHandler(NewClientsocket,ActiveUsers);

                //we add new connected Client into ActiverUsers set by simply calling add method
                ActiveUsers.add(clientHandler);

                //starting Thread for each new client to handle multiple client so clients are able to perform read and write continuously or parallelly
                new Thread(clientHandler).start();

            }
            
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }       
    }
}