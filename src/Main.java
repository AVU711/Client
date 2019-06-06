
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String port;
        try {
            SocketAddress socketAddress;

            while (true) {
                try {
                    System.out.println("Введите порт");
                    port = new Scanner(System.in).nextLine();
                    socketAddress = new InetSocketAddress("localhost", Integer.parseInt(port));
                    break;
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println("Неверный порт");
                } catch (NoSuchElementException e) {
                    System.exit(0);
                }

            }
            Selector selector = Selector.open();
            SocketChannel sc = SocketChannel.open(socketAddress);
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
            ClientNIO clientNIO = new ClientNIO(sc,selector, socketAddress, port);
            Parser parser = new Parser();
            String Signal = clientNIO.GetSignal();
            Scanner scanner = new Scanner(System.in);
            String inText = "";
            System.out.println(Signal);


            while (!inText.equals("end")) {
                if (clientNIO.getFlag() == 0) {
                    System.out.println("Введите команду");
                    try {

                        inText = scanner.nextLine().trim();
                        try {
                            ArrayList<Request> requests = parser.perfomanceCommands(inText);

                            for (Request request : requests) {
                                clientNIO.SendMessage(request);
                                if (clientNIO.getFlag() != 1) {
                                    System.out.println((String) clientNIO.GetMessage());
                                }
                            }
                        } catch (NullPointerException e2) {

                        } catch (NoSuchElementException e1) {

                        } catch (IllegalStateException e) {
                            System.out.println("Неверный формат ввода команды");
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Выход без сохранения.");
                        System.exit(0);
                    }
                }else{
                    clientNIO.SendMessage(clientNIO.getNotEndedRequest());
                    System.out.println(clientNIO.GetMessage());
                }
            }
        } catch (StreamCorruptedException e){
            System.out.println("Количество котов очень большое. Запустите программу ещё раз.");
        }catch (ConnectException e) {
            System.out.println("Ошибка соединения с сервером");
            Main.main(null);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ввода команды");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
