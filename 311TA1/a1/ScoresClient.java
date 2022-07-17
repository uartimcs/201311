package a1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class ScoresClient {
    public static void main(String[] args) {
        String hostName;
        int portNumber;
        double[] queryOutput;

        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
            //Here I do not add error checking as it is not requested in the question.
        }
        
        else {
            hostName = "localhost";     //default host - localhost
            portNumber = 2000;          //default port - 2000
        }

        //try-with-resources
        try (Socket socket = new Socket(hostName, portNumber);
                ObjectOutputStream outputToServer = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputFromServer = new ObjectInputStream(socket.getInputStream());)
        {
            //prompt the user for double values
            System.out.print("Enter scores (separated by spaces): ");

            //try-with-resources, close scanner after use.
            try(Scanner scanner = new Scanner(System.in);)
        {
            //Split the single line input into array of primitive double values (double[])
            queryOutput = Stream.of(scanner.nextLine().split("\\s+")).mapToDouble(Double::parseDouble).toArray();
            outputToServer.writeObject(queryOutput);
            outputToServer.flush();
        }
        // send dummy to server and server detects it is not double[]
        // server will send back "error" message to client afterwards.
        // it is optional, I think. since I don't know the source of error come from ScoresClient.
        catch (NumberFormatException e) {
            String msg = "";
            outputToServer.writeObject(msg);
            outputToServer.flush();
        }

            //receive an object from Server
            Object r = inputFromServer.readObject();
            if(r instanceof double[]) {
                double[] readDataIn = (double[]) r;
                System.out.println("Pass = " + readDataIn[0]);
                System.out.println("Average = " + readDataIn[1]);
            }
            else {
                System.out.println(r);
            }

        }

        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException cfne) {
            cfne.printStackTrace();
        }
        
    }
}
