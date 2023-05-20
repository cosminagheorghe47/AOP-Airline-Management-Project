package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Audit
{
    private Audit(){}
    public static void logAction(String s) throws IOException
    {
        BufferedWriter br=new BufferedWriter(new FileWriter("data/log.csv",true));
        SimpleDateFormat data=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=new Date();
        br.write(s+","+data.format(d));
        br.newLine();
        br.close();
    }
}