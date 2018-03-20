import io.swagger.models.auth.In;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException {
        String a="ss";
        String b=new String ("ss");
        System.out.println(b==a);
        List<Integer> a2= new ArrayList<Integer>();
        a2.add(2);
        a2.add(4);
        List<Integer> a3= new ArrayList<Integer>();
//        a3.add(1);
        a3.add(2);
        a3.add(4);
        boolean b1 = a2.retainAll(a3);
        System.out.println(a2);
        System.out.println(b1);
        for (Integer i :a3){
            System.out.println(i);
        }
        Set <String> set=new HashSet<String>();
        set.add("a");
        set.add("b");
        for (String i:set){
            System.out.println(i);
        }
        Map<String ,Integer> map=new HashMap<String, Integer>();
        map.put("a",1);
        map.put("b",2);

        for (Map.Entry<String ,Integer> i: map.entrySet()){
            System.out.println(i.getKey()+""+i.getValue());
        }
        Integer add = add(1, 2, 2);
        System.out.println(add);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK)-1);
        File sourc=new File("C:\\Users\\K\\Desktop\\常规操作.txt");
        File dest=new File("C:/Users/K/Desktop/常规操作2.txt");
        copy(sourc,dest);
        InputStreamReader streamReader=new InputStreamReader(new FileInputStream(sourc),"GBK");
    }
    public static Integer add(int ... a) throws FileNotFoundException {

        return a.length;
    }
    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
    public static void copy(File source,File dest) throws IOException {
        BufferedReader reader=new BufferedReader(new FileReader(source),1024);
        BufferedWriter writer=new BufferedWriter(new FileWriter(dest),1024);
        int a=-1;
        try {
            while ((a=reader.read())!=-1){
                writer.write(a);
            }
        }finally {
            reader.close();
            writer.flush();
            writer.close();
        }


    }
}
