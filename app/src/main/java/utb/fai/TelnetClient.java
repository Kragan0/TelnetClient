package utb.fai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class TelnetClient {

    private final String serverIp;
    private final int port;

    public TelnetClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }
    
    public void run() {
        try {
            Socket socket = new Socket(serverIp, port);            
            
            // Implementation of receiving and sending data
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
            
            // Implement processing of input from the user and sending data to the server
            Thread firstThread = new Thread() {
                @Override
                public void run() {
                    try {
                        String userInput ;
                        while ((userInput = userReader.readLine()) != null) {
                            if (userInput.equals("/QUIT")) {
                                socket.close();
                                break;     
                            }
                            output.write((userInput + "\n").getBytes());
                            output.flush();
                           
                        }
                    } catch (IOException e) {
                        e.printStackTrace();        
                    }
                }
            };            
            // Implement response processing from the server and output to the console
            Thread secondThread = new Thread() {
                @Override
                public void run() {
                    try {
                        String serverResponse;
                        while ((serverResponse = reader.readLine()) != null)
                        {
                            System.out.println(serverResponse);
                        }
                    } catch (IOException e){
                        if (!socket.isClosed()) { 
                            e.printStackTrace();
                        }
                    } 
                    
                }
            };

            firstThread.start(); 
            secondThread.start();

            firstThread.join();
            secondThread.join();

            reader.close();
            userReader.close();
            output.close();
            
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();    
        }
        
    }
}
