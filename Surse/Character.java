package com.company;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

interface Element
{
    void accept(Visitor visitor);
}

abstract class Entity implements Element
{
    ArrayList<Spell> abilities=new ArrayList<>();
    int currentlife, maxlife;
    int currentmana, maxmana;
    boolean fireprotection, iceprotection, earthprotection;

    void regeneratelife(int life)
    {
        currentlife=currentlife+life;

        if(currentlife>maxlife)
        {
            currentlife=maxlife;
        }
    }

    void regeneratemana(int mana)
    {
        currentmana=currentmana+mana;

        if(currentmana>maxmana)
        {
            currentmana=maxmana;
        }
    }

    boolean checkmana(Spell ability) //verifica daca exista suficienta mana pt abilitate
    {
        if(currentmana>=ability.cost)
        {
            return true;
        }
        return false;
    }

    void usespell(Spell ability, Entity e) //foloseste abilitatea pe entitate
    {
        if(checkmana(ability))
        {
            e.accept(ability);
            currentmana=currentmana-ability.cost;
            abilities.remove(ability);
        }
        else
        {
            System.out.println("You don't have that much mana! Choose something else...");
        }
    }

    abstract void receiveDamage(int damage);
    abstract int getDamage();
}

abstract class Character extends Entity
{
    String name;
    int x, y;
    Inventory inventory;
    int exp;
    int lvl;
    int strenght, charisma, dexterity;
    int progress=0;

    void showstats() //afiseaza stats in joc
    {
        System.out.println("lvl: "+lvl+" | exp: "+exp+" | coins: "+inventory.money+" | life: "+currentlife+" | mana: "+currentmana);
    }

    void showcharacterinfo() //afiseaza toate character stats
    {
        System.out.println("Name: "+name);
        System.out.println("Type: "+ this.getClass().getSimpleName());
        System.out.println("lvl: "+lvl);
        System.out.println("exp: "+exp);
    }

    void receivemoneyenemy(Enemy e) //primeste bani de la enemy 80% sanse sa primeasca
    {
        int moneyrn=ThreadLocalRandom.current().nextInt(1, 6);

        if(moneyrn!=5)
        {
            e.givemoney(this);
        }
    }

    void addprogress(int progress)
    {
        this.progress=this.progress+progress;
        exp=exp+progress;
    }


    void receivemoney()
    {
        int moneyrn=ThreadLocalRandom.current().nextInt(1, 6);

        if(moneyrn==1)
        {
            int moneyfound=ThreadLocalRandom.current().nextInt(10, 31);
            inventory.money= inventory.money+moneyfound;
            System.out.println("You found "+moneyfound+" coins on the ground!");
        }
    }

    void buypotion(Potion potion) //cumpara potiune
    {
        if(inventory.money>=potion.price())
        {
            if(inventory.freeweight()>=potion.weightvalue())
            {
                inventory.addpotion(potion);
                inventory.money= inventory.money- potion.price();
            }
            else
            {
                System.out.println("You are carrying too much!");
            }
        }
        else
        {
            System.out.println("You don't have enough money to buy this potion.");
        }
    }

    void checkprogress() //verifica daca se face lvling up
    {
        if(progress>=10)
        {
            lvl++;
            System.out.println("You leveled up!");
            charisma=charisma+5;
            dexterity=dexterity+10;
            strenght=strenght+4;
            progress=0;
        }
    }

    public void accept(Visitor visitor) //visitor=spell
    {
        //System.out.println("accept the spell");
        visitor.visit(this);
    }
}

class Warrior extends Character
{
    public Warrior(String name, int lvl, int exp)
    {
        this.name=name;
        x=0;
        y=0;
        this.exp=exp;
        this.lvl=lvl;
        inventory=new Inventory(80);
        strenght=lvl*5;
        charisma=lvl;
        dexterity=lvl*3;
        maxlife=400;
        currentlife=400;
        maxmana=80;
        currentmana=80;
        fireprotection=true;
        iceprotection=false;
        earthprotection=false;
        int rn=ThreadLocalRandom.current().nextInt(2, 5);
        for(int i=0; i<rn; i++)
        {
            int rn1=ThreadLocalRandom.current().nextInt(1, 4);
            if(rn1==1)
            {
                abilities.add(new Ice());
            }
            if(rn1==2)
            {
                abilities.add(new Fire());
            }
            if(rn1==3)
            {
                abilities.add(new Earth());
            }
        }
    }

    void receiveDamage(int damage) //primeste damage
    {
        if(dexterity>=30)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 3);

            if(random==1)
            {
                currentlife=currentlife-damage/2;
                System.out.println("It dealt "+damage/2+" damage!");
                return;
            }
        }
        if(charisma>=20)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 4);

            if(random==1)
            {
                currentlife=currentlife-damage/3;
                System.out.println("It dealt "+damage/3+" damage!");
                return;
            }
            else
            {
                currentlife=currentlife-damage;
                return;
            }
        }

        currentlife=currentlife-damage;
    }

    int getDamage() //returneaza damage pt normal attacks
    {
        int damage;

        if(strenght>=10)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 3);

            if(random==1)
            {
                damage=(strenght+dexterity/4+charisma/2)*2;
                return damage;
            }
        }

        damage=strenght+dexterity/4+charisma/2;
        return damage;
    }
}

class Mage extends Character
{
    public Mage(String name, int lvl, int exp)
    {
        this.name=name;
        x=0;
        y=0;
        this.exp=exp;
        this.lvl=lvl;
        inventory=new Inventory(50);
        strenght=lvl*5;
        charisma=lvl;
        dexterity=lvl*3;
        maxlife=400;
        currentlife=400;
        maxmana=100;
        currentmana=100;
        fireprotection=false;
        iceprotection=true;
        earthprotection=false;
        int rn=ThreadLocalRandom.current().nextInt(2, 5);
        for(int i=0; i<rn; i++)
        {
            int rn1=ThreadLocalRandom.current().nextInt(1, 4);
            if(rn1==1)
            {
                abilities.add(new Ice());
            }
            if(rn1==2)
            {
                abilities.add(new Fire());
            }
            if(rn1==3)
            {
                abilities.add(new Earth());
            }
        }
    }

    void receiveDamage(int damage) //primeste damage
    {
        if(dexterity>=30)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 3);

            if(random==1)
            {
                currentlife=currentlife-damage/2;
                System.out.println("It dealt "+damage/2+" damage!");
            }
            else
            {
                currentlife=currentlife-damage;
            }
            return;
        }
        if(strenght>=20)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 4);

            if(random==1)
            {
                currentlife=currentlife-damage/3;
                System.out.println("It dealt "+damage/3+" damage!");
            }
            else
            {
                currentlife=currentlife-damage;
            }
            return;
        }

        currentlife=currentlife-damage;
    }

    int getDamage() //normal attack
    {
        int damage;

        if(charisma>=10)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 3);

            if(random==1)
            {
                damage=(strenght+dexterity/2+charisma/4)*2;
                return damage;
            }
        }

        damage=strenght+dexterity/2+charisma/4;
        return damage;
    }
}

class Rogue extends Character
{
    public Rogue(String name, int lvl, int exp)
    {
        this.name=name;
        x=0;
        y=0;
        this.exp=exp;
        this.lvl=lvl;
        inventory=new Inventory(70);
        strenght=lvl*5;
        charisma=lvl;
        dexterity=lvl*3;
        maxlife=400;
        currentlife=400;
        maxmana=90;
        currentmana=90;
        fireprotection=false;
        iceprotection=false;
        earthprotection=true;
        int rn=ThreadLocalRandom.current().nextInt(2, 5);
        for(int i=0; i<rn; i++)
        {
            int rn1=ThreadLocalRandom.current().nextInt(1, 4);
            if(rn1==1)
            {
                abilities.add(new Ice());
            }
            if(rn1==2)
            {
                abilities.add(new Fire());
            }
            if(rn1==3)
            {
                abilities.add(new Earth());
            }
        }
    }

    void receiveDamage(int damage)//primeste damage
    {
        if(strenght>=30)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 3);

            if(random==1)
            {
                currentlife=currentlife-damage/2;
                System.out.println("It dealt "+damage/2+" damage!");
            }
            else
            {
                currentlife=currentlife-damage;
            }
            return;
        }
        if(charisma>=20)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 4);

            if(random==1)
            {
                currentlife=currentlife-damage/3;
                System.out.println("It dealt "+damage/3+" damage!");
            }
            else
            {
                currentlife=currentlife-damage;
            }
            return;
        }

        currentlife=currentlife-damage;
    }

    int getDamage() //normal attack
    {
        int damage;

        if(dexterity>=10)
        {
            int random=ThreadLocalRandom.current().nextInt(1, 3);

            if(random==1)
            {
                damage=(strenght+dexterity/2+charisma/4)*2;
                return damage;
            }
        }

        damage=strenght+dexterity/2+charisma/4;
        return damage;
    }
}

class Enemy extends Entity implements CellElement
{
    public Enemy()
    {
        currentlife=ThreadLocalRandom.current().nextInt(80, 150);
        currentmana=ThreadLocalRandom.current().nextInt(60, 80);
        Random rd= new Random();
        iceprotection=rd.nextBoolean();
        fireprotection=rd.nextBoolean();
        earthprotection=rd.nextBoolean();
        int rn=ThreadLocalRandom.current().nextInt(2, 5);
        for(int i=0; i<rn; i++)
        {
            int rn1=ThreadLocalRandom.current().nextInt(1, 4);
            if(rn1==1)
            {
                abilities.add(new Ice());
            }
            if(rn1==2)
            {
                abilities.add(new Fire());
            }
            if(rn1==3)
            {
                abilities.add(new Earth());
            }
        }
    }

    void showenemystats()
    {
        System.out.println("Enemy life: "+ currentlife);
    }

    void attack(Character c) //ataca personajul
    {
        int atk=ThreadLocalRandom.current().nextInt(1, 6);

        if(atk==4 || atk==5)
        {
            for(Spell ability : abilities)
            {
                if(checkmana(ability))
                {
                    System.out.println("The enemy used "+ ability.getClass().getSimpleName()+" with "+ ability.damage+ " damage.");
                    usespell(ability, c);
                    return;
                }
            }
        }

        int dmg=getDamage();
        System.out.println("Enemy attacked you with "+dmg+" damage.");
        c.receiveDamage(dmg);
    }

    void receiveDamage(int damage) //primeste damage
    {
        int random=ThreadLocalRandom.current().nextInt(1, 3);

        if(random==1)
        {
            currentlife=currentlife-damage;
        }
        else
        {
            System.out.println("The enemy dodged the attack!");
        }

        if(currentlife<=0)
        {
            System.out.println("The enemy was defeated!");
        }
    }


    void givemoney(Character c) //da bani caracterului
    {
        int moneyf=ThreadLocalRandom.current().nextInt(10, 31);
        System.out.println("Enemy dropped "+moneyf+" coins.");
        c.inventory.addmoney(moneyf);
    }

    int getDamage() //normal attack
    {
        int damage=ThreadLocalRandom.current().nextInt(5, 20);
        int random=ThreadLocalRandom.current().nextInt(1, 3);

        if(random==1)
        {
            damage=damage*2;
        }

        return damage;
    }

    public char toCharacter()
    {
        char character;
        character='E';
        return character;
    }

    public void accept(Visitor visitor) //visitor=spell
    {
        visitor.visit(this);
    }

}

class CharacterMaker //maker pt factory pattern
{
    public Character getCharacter(String type, String name, int lvl, int exp)
    {
        if(type.equals("Mage"))
        {
            return new Mage(name, lvl, exp);
        }
        if(type.equals("Warrior"))
        {
            return new Warrior(name, lvl, exp);
        }

        return new Rogue(name, lvl, exp);
    }
}