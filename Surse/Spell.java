package com.company;
import java.util.concurrent.ThreadLocalRandom;

interface Visitor<T extends Entity>
{
    void visit(T entity);
}

abstract class Spell implements Visitor
{
    int damage, cost;

    public String toString()
    {
        String rez;

        rez=this.getClass().getSimpleName()+" (cost:"+cost+") damage:"+damage;

        return rez;
    }

}

class Ice extends Spell
{
    public Ice()
    {
        this.damage=ThreadLocalRandom.current().nextInt(10, 80);
        this.cost=ThreadLocalRandom.current().nextInt(10, 30);
    }

    public void visit(Entity entity)
    {
        if(entity.getClass()==Mage.class)
        {
            System.out.println("The spell failed! Seems like you are ice-protected.");
        }
        if(entity.getClass()==Warrior.class)
        {
            entity.receiveDamage(this.damage);
        }
        if(entity.getClass()==Rogue.class)
        {
            entity.receiveDamage(this.damage);
        }
        if(entity.getClass()==Enemy.class)
        {
            System.out.println("You used Ice spell with "+ damage+ " damage.");

            if(entity.iceprotection)
            {
                System.out.println("The spell failed! Seems like the enemy is ice-protected.");
            }
            else
            {
                entity.receiveDamage(this.damage);
            }
        }
    }
}

class Fire extends Spell
{
    public Fire()
    {
        this.damage=ThreadLocalRandom.current().nextInt(10, 80);
        this.cost=ThreadLocalRandom.current().nextInt(10, 30);
    }

    public void visit(Entity entity)
    {
        if(entity.getClass()==Warrior.class)
        {
            System.out.println("The spell failed! Seems like you are fire-protected.");
        }
        if(entity.getClass()==Mage.class)
        {
            entity.receiveDamage(this.damage);
        }
        if(entity.getClass()==Rogue.class)
        {
            entity.receiveDamage(this.damage);
        }
        if(entity.getClass()==Enemy.class)
        {
            System.out.println("You used Fire spell with "+ damage+ " damage.");

            if(entity.fireprotection)
            {
                System.out.println("The spell failed! Seems like the enemy is fire-protected.");
            }
            else
            {
                entity.receiveDamage(this.damage);
            }
        }
    }
}

class Earth extends Spell
{
    public Earth()
    {
        this.damage=ThreadLocalRandom.current().nextInt(10, 80);
        this.cost=ThreadLocalRandom.current().nextInt(10, 30);
    }

    public void visit(Entity entity)
    {
        if(entity.getClass()==Rogue.class)
        {
            System.out.println("The spell failed! Seems like you are earth-protected.");
        }
        if(entity.getClass()==Warrior.class)
        {
            entity.receiveDamage(this.damage);
        }
        if(entity.getClass()==Mage.class)
        {
            entity.receiveDamage(this.damage);
        }
        if(entity.getClass()==Enemy.class)
        {
            System.out.println("You used Earth spell with "+ damage+ " damage.");

            if(entity.earthprotection)
            {
                System.out.println("The spell failed! Seems like the enemy is earth-protected.");
            }
            else
            {
                entity.receiveDamage(this.damage);
            }
        }
    }
}
