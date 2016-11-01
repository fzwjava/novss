package cn.com.siemens.novel.spider.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class CharesetUtil {

    private final List<Byte> bytes;

    public List<Byte> getBytes() {
        return bytes;
    }

    public CharesetUtil() {
        bytes = new ArrayList<Byte>();
    }

    private InputStream in;

    public InputStream getIn() {
        return in;
    }

    public String convertInputStream(InputStream is, String encode) {

        byte[] te = new byte[1024];
        StringBuffer st = new StringBuffer();
        String ct = null;
        int num = 0;
        try {
            if (encode != null && encode.equals("gzip")) {
                is = new GZIPInputStream(is);

            }
            in = is;
            while ((num=is.read(te)) != -1) {
            	if(num<te.length){
            		te =Arrays.copyOf(te, num);
            	}
                st.append(new String(te, "UTF-8"));
                for (byte t : te) {
                    bytes.add(t);
                }
                ct = getCharset(st.toString());
                if (ct != null || interrupt(ct)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ct;

    }

    public boolean interrupt(String s) {
        boolean re = false;
        if (s != null) {
            Pattern pattern = Pattern.compile("<\\s*body\\s*>");
            Matcher matcher = pattern.matcher(s);

            if (matcher.find()) {
                re = true;
            }
        }
        return re;
    }



    public String getCharset(String s) {
        String re = null;
        if (s != null) {
            Pattern pattern = Pattern.compile("charset=([^\">;]+)");
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                re = matcher.group(1);
            }
        }
        
        return re;
    }
}
