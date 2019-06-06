import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Loading {

    public void loading(Parser parser, ObjectOutputStream out, ByteArrayOutputStream outputStream, ByteArrayInputStream inputStream, SocketChannel socketChannel, ObjectInputStream in) throws IOException, ClassNotFoundException {
        try {
            System.out.println("Введите команду");
            Scanner scanner = new Scanner(System.in);
            String inText = scanner.nextLine().trim();
            try {
                ArrayList<Request> requests = parser.perfomanceCommands(inText);
                for (Request request : requests) {
                    out.writeObject(request);
                    out.flush();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());
                    socketChannel.write(byteBuffer);
                    outputStream.reset();
                    System.out.println(in.readObject());
                }
            } catch (NullPointerException e) {
            }catch (JsonSyntaxException e){
                System.out.println("Неверный формат ввода команды.");

            } catch (NoSuchElementException e) {
                out.writeObject(new Request("save"));
                out.flush();
                ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());
                socketChannel.write(byteBuffer);
                outputStream.reset();
                System.out.println(in.readObject());
                System.exit(0);
            }
            while (!inText.equals("end")) {
                System.out.println("Введите команду");
                scanner = new Scanner(System.in);
                inText = scanner.nextLine().trim();
                try {
                    ArrayList<Request> requests = parser.perfomanceCommands(inText);

                    for (Request request : requests) {

                        out.writeObject(request);
                        out.flush();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());
                        socketChannel.write(byteBuffer);
                        outputStream.reset();
                        System.out.println(in.readObject());
                    }
                } catch (NullPointerException e2) {

                } catch (NoSuchElementException e1) {
                    out.writeObject(new Request("save"));
                    out.flush();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());
                    socketChannel.write(byteBuffer);
                    outputStream.reset();
                    System.out.println(in.readObject());
                    System.exit(0);
                } catch (IllegalStateException e) {
                    System.out.println("Неверный формат ввода команды");
                }
            }
            socketChannel.close();
            in.close();
            inputStream.close();
            out.close();
            outputStream.close();
            System.exit(0);
        }catch (NoSuchElementException e){
            out.writeObject(new Request("save"));
                        out.flush();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(outputStream.toByteArray());
                        socketChannel.write(byteBuffer);
                        outputStream.reset();
                        System.out.println(in.readObject());
                        System.exit(0);
        }
    }
}
