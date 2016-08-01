package cn.saymagic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by saymagic on 16/6/2.
 *
 * Copied!
 */
public class ShellUtil {

    public static String run(String cmd){
        Process process = null;
        BufferedReader bri = null;
        try {
            System.out.print(cmd);
            process = Runtime.getRuntime().exec(cmd);
            bri = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            CzStreamOutput output = new CzStreamOutput(bri);
            output.start();
            process.waitFor();
            String result = output.getResult();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bri.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            process.destroy();
        }
        return "error";
    }

}


class CzStreamOutput extends Thread{

    public BufferedReader br;

    private StringBuffer buffer;

    public CzStreamOutput() {
    }

    public CzStreamOutput(BufferedReader br) {
        this.br = br;
        buffer = new StringBuffer();
//        run();
    }

    public void run() {
        String line;
        try {
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return buffer.toString();
    }
}
