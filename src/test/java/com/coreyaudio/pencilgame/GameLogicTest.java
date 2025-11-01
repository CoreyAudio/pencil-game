package com.coreyaudio.pencilgame;
import java.util.Random;
import java.util.Scanner;

public class GameLogicTest
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int nPencil;
        String input;
        
        // Choose how many pencils there will be at play
        System.out.println("How many pencils would you like to use:");
        while (true)
        {
            input = scan.nextLine();
            try
            {
                nPencil = Integer.parseInt(input);
                if (nPencil <= 0)
                {
                    System.out.println("The number of pencils should be positive");
                }
                else
                {
                    break;
                }
            } catch (NumberFormatException e)
            {
                System.out.println("The number of pencils should be numeric");
            }
        }
        
        // Choose who goes first
        System.out.println("Who will be the first (John, Jack):");
        while (true)
        {
            input = scan.nextLine();
            if (input.equals("John") || input.equals("Jack"))
            {
                break;
            }
            else
            {
                System.out.println("Choose between 'John' and 'Jack'");
            }
        }
        
        // Build the pencils
        StringBuilder sb = new StringBuilder();
        sb.append("|".repeat(nPencil));
        // Game Start
        int removePencil;
        Random randNum = new Random();
        // Store name choice
        String name = input;
        while (true)
        {
            // Display the pencils
            System.out.println(sb);
            
            // Current player's turn
            if (name.equals("John"))
            {
                System.out.println(name + "'s turn!");
                while (true)
                {
                    // Update the pencil input
                    input = scan.nextLine();
                    try
                    {
                        removePencil = Integer.parseInt(input);
                        if (removePencil < 1 || removePencil > 3)
                        {
                            System.out.println("Possible values: '1', '2' or '3'");
                        }
                        else if (removePencil > nPencil)
                        {
                            System.out.println("Too many pencils were taken");
                        }
                        else
                        {
                            break;
                        }
                    } catch (NumberFormatException e)
                    {
                        System.out.println("Possible values: '1', '2' or '3'");
                    }
                }
            }
            else
            {
                // Bot logic
                System.out.println("Jack's turn:");
                if ((nPencil - 2) % 4 == 0 || nPencil == 1)
                {
                    System.out.println("1");
                    removePencil = 1;
                }
                else if ((nPencil - 3) % 4 == 0)
                {
                    System.out.println("2");
                    removePencil = 2;
                }
                else if (nPencil % 4 == 0)
                {
                    System.out.println("3");
                    removePencil = 3;
                }
                else
                {
                    removePencil = randNum.nextInt(3) + 1;
                    System.out.println(removePencil);
                }
            }
            // Update the number of pencils
            nPencil -= removePencil;
            sb.setLength(nPencil);
            
            // Switch the player if the game is not over
            // If the number of pencils is 0, current player won!
            name = name.equals("John") ? "Jack" : "John";
            if (nPencil == 0)
            {
                System.out.println(name + " won!");
                break;
            }
        }
    }
}