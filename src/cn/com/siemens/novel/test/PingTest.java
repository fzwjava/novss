package cn.com.siemens.novel.test;

import java.net.InetAddress;

public class PingTest {
    public static void main(String[] args) {

        try {
            System.out.println(InetAddress.getByName("www.google.com.hk" + ""));
            int timeOut = 6000;
            byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 20,
                    (byte) 217 };
            int retry = 10;
            // InetAddress address = InetAddress.getByAddress(ip);
            InetAddress address = InetAddress.getByName("t.qq.com");
            for (int i = 0; i < retry; i++) {
                if (address.isReachable(timeOut)) {
                    System.out.println(i + " OK");
                } else {
                    System.out.println(i + " LOSS");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}