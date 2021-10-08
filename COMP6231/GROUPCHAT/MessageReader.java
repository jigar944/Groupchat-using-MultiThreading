import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


//client class using this class to read contionusly from server side
public class MessageReader implements Runnable{


    BufferedReader read = null;
    public Socket Client;
    


    //parametrized constructor
    public MessageReader(Socket client){

        this.Client = client;

        try {
            read = new BufferedReader(new InputStreamReader(this.Client.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        try {
            System.out.println("Reading stared");
            while(true){
                //it reads the string from server side
                System.out.println(read.readLine());
            }
            
             

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
            
        }
        
    }

}
