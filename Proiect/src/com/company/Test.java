package com.company;

public class Test
{
    public static void main(String args[])
    {
        System.out.println("Welcome! Let us begin!");

        try {
            Game.getGame().run();
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }
    }
}
