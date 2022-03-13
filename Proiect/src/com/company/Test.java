package com.company;
import java.util.Scanner;

public class Test
{
    public static void main(String args[])
    {
        System.out.println("Welcome!");
        System.out.println("Do you want to play here(type 'text') or with GUI(type 'GUI')?");
        Scanner scan= new Scanner(System.in);
        String mode= scan.nextLine();
        System.out.println("All right! Let us begin!");
        Game.getGame().run(mode);
    }
}
