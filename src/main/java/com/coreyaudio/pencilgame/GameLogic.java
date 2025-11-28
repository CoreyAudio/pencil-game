package com.coreyaudio.pencilgame;

public class GameLogic
{
    private static StringBuilder sb;
    private static int count;
    public static boolean player;
    
    public static void storePencils(int num){
        count = num;
        sb = new StringBuilder();
        sb.append("| ".repeat(num));
    }
    
    public static void removePencils(int num){
        count -= num;
        sb.setLength(count * 2);
    }
    
    public static String getPencils(){
        
        return sb.length() / 2 + "<br/>" + sb;
    }
    
    public static int getCount(){
        
        return count;
    }
}
