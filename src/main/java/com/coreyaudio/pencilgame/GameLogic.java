package com.coreyaudio.pencilgame;

import java.util.Random;

public class GameLogic
{
    private static StringBuilder sb;
    private static int count;
    public static Boolean player;
    
    public static void storePencils(int num){
        count = num;
        sb = new StringBuilder();
        sb.append("| ".repeat(num));
    }
    
    public static void removePencils(int num){
        count -= num;
        sb.setLength(count * 2);
    }
    
    public static int compLogic(){
        Random rand = new Random();
        int num;
        if ((count - 2) % 4 == 0 || count == 1)
        {
            count -= 1;
            num = 1;
        }
        else if ((count - 3) % 4 == 0)
        {
            count -= 2;
            num = 2;
        }
        else if (count % 4 == 0)
        {
            count -= 3;
            num = 3;
        }
        else
        {
            num = rand.nextInt(3) + 1;
            count -= num;
        }
        sb.setLength(count * 2);
        return num;
    }
    
    public static String getPencils(){
        
        return sb.length() / 2 + "<br/>" + sb;
    }
    
    public static int getCount(){
        if (count == 1){
            player = null;
        }
        return count;
    }
}
