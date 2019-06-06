import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.regex.Matcher;

public class ClientNIO {

    private static ByteBuffer buf = ByteBuffer.allocate(1024*1024*5);
    private static ByteBuffer inBuf = ByteBuffer.allocate(1024*1024*5);
    private static ByteBuffer inBufForSignal = ByteBuffer.allocate(1024);

    private  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private  ByteArrayInputStream inputStream;
    private  ByteArrayInputStream inputStreamForSignal;
    private  ObjectOutputStream out;
    private  ObjectInputStream in;
    private ObjectInputStream inForSignal;

    private Request NotEndedRequest;
    private  int flag = 0;

    private SocketChannel sc;
    private Selector selector;
    private SocketAddress socketAddress;
    private  String port;

    public ClientNIO(SocketChannel sc, Selector selector,SocketAddress socketAddress, String port){
        this.sc = sc;
        this.selector = selector;
        this.socketAddress = socketAddress;
        this.port = port;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public Request getNotEndedRequest() {
        return NotEndedRequest;
    }

    public  void SendMessage(Request request){
        try {
            if (out == null) {
                out = new ObjectOutputStream(outputStream);
            } else if (flag == 1) {
                outputStream.reset();
                out = new ObjectOutputStream(outputStream);
                flag++;
            } else
                outputStream.reset();
            out.writeObject(request);

            out.flush();
            buf.flip();
            buf = ByteBuffer.wrap(outputStream.toByteArray());
            if (GetSignal().equals("false")) {
                sc.write(buf);
            }else{
                throw new IOException();
            }
        }catch (IOException e) {
            try {
                System.out.println("Произошёл разрыв соединения, происхолит переподключение.");
                NotEndedRequest = request;
                Thread.sleep(10000);
                flag ++;
                sc = SocketChannel.open(socketAddress);

            }catch (InterruptedException e1) {

            }catch (IOException e1){
                System.out.println("Переподключение не удалось.");
                Main.main(null);
            }
        }

    }
    public  String GetSignal(){
        try {
            int i = 0;
            int j = 0;
            inBufForSignal.clear();
            while (i < 1) {
                int count = selector.select(1000);
                if (count == 0) break;
                i ++;
                j ++;
                Set keys = selector.selectedKeys();
                Iterator it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    Thread.sleep(100);
                    SocketChannel chn = (SocketChannel) key.channel();
                    chn.read(inBufForSignal);
                    it.remove();
                }
            }
            if (j != 0) {

                if (inputStreamForSignal == null) {
                    inputStreamForSignal = new ByteArrayInputStream(inBufForSignal.array());
                    inForSignal = new ObjectInputStream(inputStreamForSignal);
                } else
                    inputStreamForSignal.reset();
                return inForSignal.readObject();
            }else{
                return "false";
            }
        }catch (StreamCorruptedException e) {
            return "false";
        }catch (NullPointerException e ){
            e.printStackTrace();
            return "true";
        } catch (IOException e) {
            e.printStackTrace();
            return "true";
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    public  Object GetMessage(){
        try {

            inBuf.clear();
            if (flag == 2){
                selector = Selector.open();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
            }

            while (true) {
                int count = selector.select(1000);
                if (count == 0) break;
                Set keys = selector.selectedKeys();
                Iterator it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    Thread.sleep(100);
                    SocketChannel chn = (SocketChannel) key.channel();
                    chn.read(inBuf);
                    it.remove();
                }
            }

            if (inputStream == null) {
                inputStream = new ByteArrayInputStream(inBuf.array());
                in = new ObjectInputStream(inputStream);
            }else if(flag == 2){
                inputStream.reset();
                in = new ObjectInputStream(inputStream);
            } else
                inputStream.reset();
            setFlag(0);
            return in.readObject();
        }catch (StreamCorruptedException e) {
        }catch (NullPointerException e ){


        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){

        }catch (InterruptedException e){}
        return "";
    }
}
