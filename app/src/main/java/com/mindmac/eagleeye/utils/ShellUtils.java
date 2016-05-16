package com.mindmac.eagleeye.utils;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Fridge on 16/5/11.
 */
public class ShellUtils {
    public static void execSuCmd(String cmd){
        Log.d("shitshit", "[ShellUtils] execSuCmd() called with: " + "cmd = [" + cmd + "]");
        Process process = null;
        OutputStream out = null;

        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            out.write((cmd + "\n").getBytes());
            out.write("exit\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * test app in the list by monkeys
     * @param randomTimes -vvv randomTimes
     */
    public static void monkeyApp(int randomTimes){
        String cmd = "for app in $(ls /data/data)\ndo\nmonkey -p $app -v " + randomTimes + "\ndone";
        execSuCmd(cmd);
    }

    public static void clearApp() {
        String cmd = "rm -rf " + System.getenv("EXTERNAL_STORAGE") + "/*";
        execSuCmd(cmd);
    }

    public static void setProp(String key, Object value){
        String cmd = String.format("setprop %s %s",key,value);
        execSuCmd(cmd);
    }
}
