package cn.com.siemens.novel.test;
import java.io.UnsupportedEncodingException;
public class Encode {
    public static String getUNICODEBytes(String s) {
   try {
    StringBuffer out = new StringBuffer();
    byte[] bytes = s.getBytes("unicode");
    for (int i = 2; i < bytes.length - 1; i += 2) {
     out.append("");
     String str = Integer.toHexString(bytes[i + 1] & 0xff);
     for (int j = str.length(); j < 2; j++) {
      out.append("0");
     }
     out.append(str);
     String str1 = Integer.toHexString(bytes[i] & 0xff);
     for (int j = str1.length(); j < 2; j++) {
      out.append("0");
     }
     out.append(str1);
    }
    return out.toString();
   } catch (UnsupportedEncodingException e) {
    e.printStackTrace();
    return null;
   }
}
    public static void main(String []args){
    String str = "Áã";
    System.out.println(getUNICODEBytes(str));
    }
}