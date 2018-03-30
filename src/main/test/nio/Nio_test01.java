package nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Nio_test01 {

    /**Buffer的基本用法
     使用Buffer读写数据一般遵循以下四个步骤：

     1.写入数据到Buffer
     2.调用flip()方法
     3.从Buffer中读取数据
     4.调用clear()方法或者compact()方法  clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据

     向Buffer中写数据
     写数据到Buffer有两种方式：

     1.从Channel写到Buffer。(inChannel.read(byteBuffer))
     2.通过Buffer的put()方法写到Buffer里。(byteBuffer.put(127))

     从Buffer中读取数据
     从Buffer中读取数据有两种方式：

     1.从Buffer读取数据到Channel。(outChannel.write(byteBuffer))
     2.使用get()方法从Buffer中读取数据。*/
    public static void main(String[] args) throws IOException {
        File file = new File("/home/wy/IdeaProjects/Spring/src/main/resources/app.properties");
        File outFile=new File("/home/wy/IdeaProjects/Spring/src/main/resources/write.properties");
        if (file.exists()&&outFile.exists()) {
            RandomAccessFile accessFile =null;
            RandomAccessFile outAccessFile=null;
            FileChannel inChannel = null;
            FileChannel outChannel=null;
            try {
                outAccessFile=new RandomAccessFile(outFile,"rw");
                outChannel=outAccessFile.getChannel();
                accessFile = new RandomAccessFile(file, "rw");
                //Buffer的分配
                ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                inChannel = accessFile.getChannel();
                while ((inChannel.read(byteBuffer)) != -1) {
                    byteBuffer.flip();//flip()将存数据状态的缓冲区变为一个处于准备取数据的状态(将Buffer从写模式切换到读模式)
                    //byteBuffer.hasRemaining()返回是否还有未读内容
                    while (byteBuffer.hasRemaining()){

//                        System.out.print((char) byteBuffer.get());
                        System.out.print(outChannel.write(byteBuffer));
                    }
                    //clear()一般在把数据写入Buffer前调用(清空缓冲区，让它可以再次被写入)
                    byteBuffer.clear();
                }

            }finally {
                inChannel.close();
                accessFile.close();
            }

        }
    }
}
