package nio;

import com.config.LogConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LogConfig.class})
public class Nio_selector_server {
    private static final int BUF_SIZE = 256;
    private static final Logger logger = LoggerFactory.getLogger(Nio_selector_server.class);
    @Test
    public void server() throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
//        new InetSocketAddress("127.0.0.1",8080);
        serverSocketChannel.configureBlocking(false);
        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        boolean flag=true;
        while (flag) {
            selector.select(1000);
//            5. 获取监听器上所有的监听事件值
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                    channel.configureBlocking(false);
                    channel.register(key.selector(), SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(BUF_SIZE));

                }
                if (key.isReadable()) {
//                    logger.info("readable");
                    SocketChannel channel = ((SocketChannel) key.channel());
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    int len = 0;
                    while ((len = channel.read(buffer)) > 0) {
                        buffer.flip();
                        byte[] bytes = new byte[1024];
                        buffer.get(bytes, 0, len);
                        String read = new String(bytes, 0, len);
                        if(read.equals("stop")){
                            flag=false;
                        }
                        logger.debug("readServer:"+read);
                    }

                    doAsk(channel,"ask:"+new Date());

                }
                /*if (key.isValid() && key.isWritable()) {
//                    logger.info("writable");
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    buffer.flip();
                    SocketChannel channel = (SocketChannel) key.channel();

                    while (buffer.hasRemaining()) {
                        byte b = buffer.get();
//                        int write = channel.write(buffer);
                        logger.warn("writeServer:"+(char)b);
                    }
                    buffer.compact();
                }*/
                iterator.remove();
            }
        }
    }
    public void doAsk(SocketChannel socketChannel,String response) throws IOException {
        byte[] bytes = response.getBytes();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

    }

}
