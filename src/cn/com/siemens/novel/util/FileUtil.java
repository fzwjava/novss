package cn.com.siemens.novel.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;


public class FileUtil {
    public static void saveFile(InputStream is, String filename)
            throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = null;
        String line = "";
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(
                    filename).getAbsolutePath())));

            while ((line = in.readLine()) != null) {
                pw.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
            pw.close();
        }

    }
    
    public static void saveFile(String is, String filename)
            throws IOException {
		BufferedReader in = new BufferedReader(new StringReader (is));
        PrintWriter pw = null;
        String line = "";
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(
                    filename).getAbsolutePath())));

            while ((line = in.readLine()) != null) {
                pw.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
            pw.close();
        }

    }

    public static String readFile(String filename,String encode) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),encode ));

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

 
}
