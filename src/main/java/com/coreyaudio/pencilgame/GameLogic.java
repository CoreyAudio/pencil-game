package com.coreyaudio.pencilgame;

public class GameLogic
{
    private static StringBuilder sb;
    private static int count;
    
    public static void storePencils(int num){
        count = num;
        sb = new StringBuilder();
        sb.append("| ".repeat(num));
    }
    
    public static String getPencils(){
        return sb.length() / 2 + "<br/>" + sb;
    }
    public static int getCount(){
        return count;
    }
}
