package com.bugworm.pjdi;

import java.io.*;
import java.nio.charset.Charset;

public class InputStream2PrintStream implements Runnable{

    private final InputStream inputStream;
    private final PrintStream writer;
    private final Charset charset;

    public InputStream2PrintStream(InputStream in, PrintStream out){
        this(in, out, Charset.defaultCharset());
    }

    public InputStream2PrintStream(InputStream in, PrintStream out, Charset cs){
        inputStream = in;
        writer = out;
        charset = cs;
    }

    public void run(){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))){
            String ln = reader.readLine();
            while(ln != null){
                writer.println(ln);
                ln = reader.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

