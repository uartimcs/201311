package a1;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ClientService implements Runnable {
    private Socket s;

    public ClientService(Socket s) {
        this.s = s;
    }

    public void run() {
        //try-with-resources
        try (Socket socket = s;
                ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());)
        {
            Object r = inputFromClient.readObject();
            if (r instanceof double[]) {
                double[] readDataIn = (double[]) r;

                double pass = 0;
                double sum = 0;

                for (int i = 0; i < readDataIn.length; i++) {
                    sum += readDataIn[i];
                    if (readDataIn[i] >= 40) {
                        pass += 1;
                    }
                }
                double average = sum / readDataIn.length;
                double[] writeOutData = {pass, average};
                outputToClient.writeObject(writeOutData);
                outputToClient.flush();
            }
            else {
                String msg = "error";
                outputToClient.writeObject(msg);
                outputToClient.flush();
            }

        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cfne) {
            cfne.printStackTrace();
        }

    }
}

public class ScoresServer {
    public static void main(String[] args) {
        int portNumber;

        if(args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
             //Here I do not add error checking as it is not requested in the question.
        }

        else {
            portNumber = 2000;  //by default
        }

        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening: " + ss);
            ExecutorService executor = Executors.newCachedThreadPool();

            while (true) {
                Socket socket = ss.accept();
                System.out.println("Client: " + socket);
                executor.execute(new ClientService(socket));
            }

        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
