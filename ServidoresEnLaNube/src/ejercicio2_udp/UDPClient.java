package ejercicio2_udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6001;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress serverIP = InetAddress.getByName(SERVER_ADDRESS);

            byte[] buffer = new byte[4096];

            while (true) {
                System.out.print("Introduce un número entero positivo (o '0' para salir): ");
                String input = scanner.nextLine();

                if (input.equals("0")) {
                    break;
                }

                byte[] data = input.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverIP, SERVER_PORT);
                socket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Primos hasta " + input + ": " + serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}