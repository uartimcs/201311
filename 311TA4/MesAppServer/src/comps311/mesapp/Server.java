package comps311.mesapp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;

public class Server {
    private static ArrayList<PrintWriter> writers = new ArrayList<>();

    synchronized private static void addWriter(PrintWriter writer) {
        writers.add(writer);
    }

    synchronized private static void removeWriter(PrintWriter writer) {
        writers.remove(writer);
    }

    synchronized private static void printToAllWriters(String string) {
        for (var writer : writers) {
            writer.println(string);
            writer.flush();
        }
    }

    private static void service(Socket socket, Connection conn) {
        try (socket;
                var isr = new InputStreamReader(socket.getInputStream());
                var in = new BufferedReader(isr);
                var bos = new BufferedOutputStream(socket.getOutputStream());
                var out = new PrintWriter(bos)) {
            try {
                addWriter(out);
                DBUtil.consumeMessages(conn,
                        m -> out.println(JSONUtil.fromMessage(m)));
                out.flush();
                String line;
                while ((line = in.readLine()) != null) {
                    Message message = JSONUtil.toMessage(line);
                    DBUtil.insertMessage(conn, message);
                    printToAllWriters(line);
                }
            } finally {
                removeWriter(out);
                System.out.println("Client exited: " + socket);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAllAddresses() throws SocketException {
        System.out.println("All InetAddresses:");
        var netInterfaces = NetworkInterface.getNetworkInterfaces();
        for (var netInterface : Collections.list(netInterfaces)) {
            var inetAddresses = netInterface.getInetAddresses();
            System.out.println("  " + Collections.list(inetAddresses));
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        displayAllAddresses();
        int port = args.length >= 1 ? Integer.parseInt(args[0]) : 3311;
        var executor = Executors.newCachedThreadPool();
        try (var conn = DBUtil.getConnection();
                var ss = new ServerSocket(port)) {
            System.out.println("Server listening: " + ss);
            while (true) {
                var socket = ss.accept();
                System.out.println("Client accepted: " + socket);
                executor.execute(() -> service(socket, conn));
            }
        }
    }
}
