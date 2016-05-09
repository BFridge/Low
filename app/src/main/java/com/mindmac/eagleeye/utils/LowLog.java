package com.mindmac.eagleeye.utils;

import android.util.Log;

import com.gc.materialdesign.utils.Utils;
import com.mindmac.eagleeye.Util;
import com.mindmac.eagleeye.service.Launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Fridge on 16/5/9.
 */
public class LowLog {

    private static File logFile = null;
    public static void i(String tag, String s) {
        if(s.contains(UtilsApp.LOW_PATH)){
            //filter out Low self
            return;
        }
        if(logFile == null) {
            logFile = Util.currentLogFile;
        }
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(tag + " : " +s);
            writer.newLine();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(writer!= null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

}
