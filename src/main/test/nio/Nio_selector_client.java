package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Nio_selector_client {
    public static void main(String[] args) throws IOException {
        SocketChannel channel=SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8080));
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        ByteBuffer readBuffer=ByteBuffer.allocate(1024);
        channel.configureBlocking(false);
        Scanner scanner=new Scanner(System.in);
        Selector selector=Selector.open();
        boolean open=true;
        while (scanner.hasNext()){
            String msg=scanner.nextLine();
            byteBuffer.put((new Date()+":"+msg).getBytes());
            byteBuffer.flip();
            channel.write(byteBuffer);
//            channel.read(readBuffer.put(("nihaosss"+msg).getBytes()));
            byteBuffer.clear();
            while (open){
                selector.select(1000);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isValid()){
                        SocketChannel channel1 = (SocketChannel) key.channel();
                        if(key.isReadable()){
                            ByteBuffer buffer= ByteBuffer.allocate(1024);
                            int read = channel1.read(buffer);
                            if(read>0){
                                buffer.flip();
                                byte[] bytes=new byte[buffer.remaining()];
                                buffer.get(bytes);
                                String result = new String(bytes, "UTF-8");
                                System.out.println(result);
                            }
                        }
                    }
                }
            }
        }
    }
}
