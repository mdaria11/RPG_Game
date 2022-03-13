package com.company;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class Shop implements CellElement
{
    ArrayList<Potion> potions= new ArrayList<>();

    public Shop() //construieste un shop
    {
        int random= ThreadLocalRandom.current().nextInt(1, 3);

        int r1= ThreadLocalRandom.current().nextInt(1, 10);
        int r2= ThreadLocalRandom.current().nextInt(2, 9);
        int r3= ThreadLocalRandom.current().nextInt(10, 51);
        potions.add(new HealthPotion(r1, r2, r3));

        r1= ThreadLocalRandom.current().nextInt(1, 10);
        r2= ThreadLocalRandom.current().nextInt(2, 9);
        r3= ThreadLocalRandom.current().nextInt(10, 51);
        potions.add(new ManaPotion(r1, r2, r3));

        for(int i=0; i<random; i++)
        {
            int random1= ThreadLocalRandom.current().nextInt(1, 3);

            if(random1==1)
            {
                r1= ThreadLocalRandom.current().nextInt(1, 10);
                r2= ThreadLocalRandom.current().nextInt(2, 9);
                r3= ThreadLocalRandom.current().nextInt(10, 51);
                potions.add(new HealthPotion(r1, r2, r3));
            }
            else
            {
                r1= ThreadLocalRandom.current().nextInt(1, 10);
                r2= ThreadLocalRandom.current().nextInt(2, 9);
                r3= ThreadLocalRandom.current().nextInt(10, 51);
                potions.add(new ManaPotion(r1, r2, r3));
            }
        }
    }

    Potion selectPotion(int index) //selectare potiune din lista si eliminare din lista/magazin
    {
        Potion p;
        p=potions.get(index);
        potions.remove(index);
        return p;
    }

    void removepotion(Potion p) //eliminare potiune din magazin pt testul hardcodat
    {
        potions.remove(p);
    }

    public char toCharacter()
    {
        char character;
        character='S';
        return character;
    }
}

