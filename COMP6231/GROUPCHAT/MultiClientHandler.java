    import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;



//Runnanle is the interface of java which implements by any class of java which objects are executed by thread
public class MultiClientHandler implements Runnable {

    public Socket newClient;
    public String ClientName ;

    //PrintWrite class is implementation of write class
    PrintWriter write = null;
    
    //BufferedReader class reads the data line by line 
    BufferedReader read = null;

    //set of ActiveUsers in System
    Set<MultiClientHandler> ActiveUsers;
    
    //Scanner class is used to take input
    Scanner sc = new Scanner(System.in);

    //Parameterised Constructor
    public MultiClientHandler(Socket newClient,Set<MultiClientHandler> ActiveUsers){
        this.newClient = newClient;
        this.ActiveUsers = ActiveUsers;
      
    }


    @Override
    public void run() {
       
        try {

            //parameter true indicates that printwriter function flushes the data everytime after calling 
            write = new PrintWriter(this.newClient.getOutputStream(),true);

            //InputStreamReader reads the byte data and convert into charaters
            //getINputStream returns the data from socket
            read = new BufferedReader(new InputStreamReader(this.newClient.getInputStream()));
            
            //read the clientname which recived from client side 
            ClientName = read.readLine(); 


            while(true){

                //reads the message from client side
                try {
                    
                    String message = Objects.requireNonNull(read.readLine());   
                    
                    //messages from client side will print on server side 
                    //System.out.println("Message from "+this.ClientName+" : "+message);

                    //broadcasting messages to all other clients except who sends
                    for(MultiClientHandler mClient : ActiveUsers){

                        //condition indentifies that we will not send message to sender and send to other clients
                        if(ClientName!=mClient.ClientName){
                        
                            //broadcasting message
                            mClient.write.println(this.ClientName+" : "+message);       
                    
                        }

                    }
              
                    /* if any client wants to exit the group chat he/she writes the exit and 
                    we will match that string and remove that perticular client from ActiveUsers set */
                    if(message.equals("exit")){
                        for(MultiClientHandler mClient : ActiveUsers){
                            if(ClientName==mClient.ClientName){
                                ActiveUsers.remove(mClient);
                            }
                        }
                    
                    }
                
                } catch (Exception e) {
                    //TODO: handle exception
                    //e.printStackTrace();
                } 
            
            }

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        
    }

    
}
