import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



//Client class of groupchat system
public class Client{
    public static void main(String[] args) {
        

        //implementation of write class
        PrintWriter write = null;
 
        Scanner sc = new Scanner(System.in);


        //
        System.out.println("**********  Welcome to the GroupChat  ***********");

        //First we take the client name to start group chat
        System.out.println("Enter Client name : ");
        String ClientName = sc.nextLine();

        System.out.println("******  Welcome "+ClientName+" in group  ******");
        System.out.println("******  Now you are active in GroupChat  ******");
        System.out.println("******  Start Messaging in group  ******");
        System.out.println("******  Please type 'exit' to remove from group ");
        int port = 9898; 
        
        try {

            //socket will connect to server by using port number and Inetadresss

            //client and server communicate with each other by socket
            Socket client = new Socket("localhost",port);

            
            write = new PrintWriter(client.getOutputStream(),true);

            //MessageReader is a class that implements runnable
            MessageReader mReader = new MessageReader(client);

            //we are using thread to read continuously from server
            Thread t = new Thread(mReader);

            //staring thread
            t.start();

            //it prints the clientname on stream
            write.println(ClientName);
            write.println(ClientName+" has joined the Group !");

            while(!client.isClosed()){

                    String message;     

                    //taking input message from client
                    //System.out.println("Type a message : ");
                    message = sc.nextLine();
                  
                    //if client enters the exit message means he/she wants to leave the groupchat
                    if(message.equals("exit")){

                        System.out.println("Connection Closed !");
                        write.println("Connection Closed");;
                        write.println(ClientName+" has left the Group");;
                        //it will stop thread
                        t.stop();
                        
                        //close scanner class
                        sc.close();
                        
                       
                        //socket closed
                        client.close();
                        
                      
                        //breaks the loop
                        break;
                    }
                    
                    write.println(message);
      
            }
        
        }
         catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        
    }
}