package utb.fai;

public class App {

    public static void main(String[] args) {
        // TODO: Implement input parameter processing
        // String server_ip = args[0];
        // int port = Integer.parseInt(args[1]);
        
        TelnetClient telnetClient = new TelnetClient("127.0.0.1" , 9999);
        telnetClient.run(); // run telnet client
    }
}
