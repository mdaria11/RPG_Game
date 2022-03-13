package com.company;
import java.util.*;

class Inventory
{
    ArrayList<Potion> potions= new ArrayList<>();
    int maxweight, money=50;

    public Inventory(int maxweight)
    {
        this.maxweight=maxweight;
    }

    void addpotion(Potion p)
    {
        potions.add(p);
    } //adauga potiune

    void removepotion(Potion p)
    {
        potions.remove(p);
    } //remove potiune

    void addmoney(int money)
    {
        this.money=this.money+money;
    } //adauga bani

    int freeweight() //calculeaza free weight
    {
        int totalweight=0, free;

        for(Potion p : potions)
        {
            totalweight=totalweight+p.weightvalue();
        }

        free=maxweight-totalweight;
        return free;
    }
}

interface Potion
{
    void usepotion(Character c);
    int price();
    int regenerationvalue();
    int weightvalue();
    void showpotion();
    void showname();
}

class HealthPotion implements Potion
{
    private int price, weight, regeneration;

    public HealthPotion(int price, int weight, int regeneration)
    {
        this.price=price;
        this.weight=weight;
        this.regeneration=regeneration;
    }

    public void usepotion(Character c)
    {
        c.regeneratelife(regeneration);
    }

    public int price()
    {
        return price;
    }

    public int regenerationvalue()
    {
        return regeneration;
    }

    public int weightvalue()
    {
        return weight;
    }

    public void showpotion()
    {
        System.out.println("Potion of Health "+ regeneration+ ": "+price+" coins");
    }

    public void showname()
    {
        System.out.println("Potion of Health "+ regeneration);
    }
}

class ManaPotion implements Potion
{
    private int price, weight, regeneration;

    public ManaPotion(int price, int weight, int regeneration)
    {
        this.price=price;
        this.weight=weight;
        this.regeneration=regeneration;
    }

    public void usepotion(Character c)
    {
        c.regeneratemana(regeneration);
    }

    public int price()
    {
        return price;
    }

    public int regenerationvalue()
    {
        return regeneration;
    }

    public int weightvalue()
    {
        return weight;
    }

    public void showpotion()
    {
        System.out.println("Potion of Mana "+ regeneration+ ": "+price+" coins");
    }


    public void showname()
    {
        System.out.println("Potion of Mana "+ regeneration);
    }
}