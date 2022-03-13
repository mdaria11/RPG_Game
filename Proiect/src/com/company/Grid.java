package com.company;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class Grid extends ArrayList<ArrayList<Cell>>
{
    static int length, height;
    int xplayer, yplayer;
    Cell current=null;
    private static Grid map = new Grid();

    private Grid()
    {
        xplayer=0;
        yplayer=0;
    }

    public static Grid getMap()
    {
        return map;
    }

    static void generateMap(int plength, int pheight) //genereaza mapa randomizata
    {
       length=plength;
       height=pheight;

       int shops= ThreadLocalRandom.current().nextInt(2, 8); //2-7 shops
       int enemies= ThreadLocalRandom.current().nextInt(4, 11); //4-10 enemies
       int finish=1;

       for(int i=0; i<length; i++)
       {
           ArrayList<Cell> aux= new ArrayList<>();
           map.add(aux);

            for(int j=0; j<height; j++)
            {
                int rand= ThreadLocalRandom.current().nextInt(1, 5); //1-4

                if(i==0 && j==0)
                {
                    map.get(i).add(getMap().generateEmptyCell(i, j));
                }
                else
                {
                    if(rand==1)
                    {
                        if(shops>0)
                        {
                            map.get(i).add(getMap().generateShopCell(i, j));
                            shops--;
                        }
                        else
                        {
                            map.get(i).add(getMap().generateEmptyCell(i, j));
                        }
                    }
                    if(rand==2)
                    {
                        if(enemies>0)
                        {
                            map.get(i).add(getMap().generateEnemyCell(i, j));
                            enemies--;
                        }
                        else
                        {
                            map.get(i).add(getMap().generateEmptyCell(i, j));
                        }
                    }
                    if(rand==3)
                    {
                        map.get(i).add(getMap().generateEmptyCell(i, j));
                    }
                    if(rand==4)
                    {
                        if(finish==1)
                        {
                            map.get(i).add(getMap().generateFinishCell(i, j));
                            finish=0;
                        }
                        else
                        {
                            map.get(i).add(getMap().generateEmptyCell(i, j));
                        }
                    }
                }
            }
       }
    }

    static void generateMapHardcode() //genereaza mapa hardcodata
    {
        length=5;
        height=5;

        map.add(new ArrayList<Cell>());
        map.get(0).add(map.generateEmptyCell(0, 0));
        map.get(0).add(map.generateEmptyCell(0, 1));
        map.get(0).add(map.generateShopCell(0, 2));
        map.get(0).add(map.generateEmptyCell(0, 3));
        map.get(0).add(map.generateEmptyCell(0, 4));
        map.add(new ArrayList<Cell>());
        map.get(1).add(map.generateEmptyCell(1, 0));
        map.get(1).add(map.generateEmptyCell(1, 1));
        map.get(1).add(map.generateEmptyCell(1, 2));
        map.get(1).add(map.generateEmptyCell(1, 3));
        map.get(1).add(map.generateEmptyCell(1, 4));
        map.add(new ArrayList<Cell>());
        map.get(2).add(map.generateEmptyCell(2, 0));
        map.get(2).add(map.generateEmptyCell(2, 1));
        map.get(2).add(map.generateEmptyCell(2, 2));
        map.get(2).add(map.generateEmptyCell(2, 3));
        map.get(2).add(map.generateEmptyCell(2, 4));
        map.add(new ArrayList<Cell>());
        map.get(3).add(map.generateShopCell(3, 0));
        map.get(3).add(map.generateShopCell(3, 1));
        map.get(3).add(map.generateEmptyCell(3, 2));
        map.get(3).add(map.generateEmptyCell(3, 3));
        map.get(3).add(map.generateEmptyCell(3, 4));
        map.add(new ArrayList<Cell>());
        map.get(4).add(map.generateEmptyCell(4, 0));
        map.get(4).add(map.generateEmptyCell(4, 1));
        map.get(4).add(map.generateEmptyCell(4, 2));
        map.get(4).add(map.generateEnemyCell(4, 3));
        map.get(4).add(map.generateFinishCell(4, 4));

    }

    void initiateplayer() //initiaza playerul pe prima casuta
    {
        currentcell(map.get(0).get(0));
        xplayer=0;
        yplayer=0;
    }

    void currentcell(Cell c)
    {
        current=c;
    } //reinitializeaza celula curenta

    Cell generateShopCell(int x, int y) //generare shop
    {
        Cell c= new Cell();
        c.x=x;
        c.y=y;
        c.element=new Shop();
        c.vizitat=false;
        c.type=Type.SHOP;

        return c;
    }

    Cell generateEnemyCell(int x, int y) //generare enemy
    {
        Cell c= new Cell();
        c.x=x;
        c.y=y;
        c.element=new Enemy();
        c.vizitat=false;
        c.type=Type.ENEMY;

        return c;
    }

    Cell generateEmptyCell(int x, int y) //generare empty
    {
        Cell c= new Cell();
        c.x=x;
        c.y=y;
        c.element=null;
        c.vizitat=false;
        c.type=Type.EMPTY;

        return c;
    }

    Cell generateFinishCell(int x, int y) //generare finish
    {
        Cell c= new Cell();
        c.x=x;
        c.y=y;
        c.element=null;
        c.vizitat=false;
        c.type=Type.FINISH;

        return c;
    }

    void goNorth()
    {
        if(xplayer-1<0)
        {
            System.out.println("It seems like there is no way up here..");
        }
        else
        {
            xplayer--;
            currentcell(map.get(yplayer).get(xplayer));
        }
    }

    void goSouth()
    {
        if(xplayer+1>=height)
        {
            System.out.println("It seems like there is no way down here..");
        }
        else
        {
            xplayer++;
            currentcell(map.get(yplayer).get(xplayer));
        }
    }

    void goWest()
    {
        if(yplayer-1<0)
        {
            System.out.println("It seems like there is no way here..");
        }
        else
        {
            yplayer--;
            currentcell(map.get(yplayer).get(xplayer));
        }
    }

    void goEast()
    {
        if(yplayer+1>=length)
        {
            System.out.println("It seems like there is no way here..");
        }
        else
        {
            yplayer++;
            currentcell(map.get(yplayer).get(xplayer));
        }
    }

    void showMap() //afiseaza mapa in functie de progres
    {
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<length; j++)
            {
                if(map.get(j).get(i).equals(current))
                {
                    System.out.print("P");
                    current.vizitat=true;
                }
                if(map.get(j).get(i).type==Type.SHOP)
                {
                    if(map.get(j).get(i).vizitat)
                    {
                        System.out.print(map.get(j).get(i).element.toCharacter()+" ");
                    }
                    else
                    {
                        System.out.print("? ");
                    }
                }
                if(map.get(j).get(i).type==Type.ENEMY)
                {
                    if(map.get(j).get(i).vizitat)
                    {
                        if(((Enemy)map.get(j).get(i).element).currentlife>0)
                        {
                            System.out.print(map.get(j).get(i).element.toCharacter()+" ");
                        }
                        else
                        {
                            if(!map.get(j).get(i).equals(current))
                            {
                                System.out.print("N ");
                            }
                            else
                            {
                                System.out.print(" ");
                            }
                        }
                    }
                    else
                    {
                        System.out.print("? ");
                    }
                }
                if(map.get(j).get(i).type==Type.EMPTY)
                {
                    if(map.get(j).get(i).vizitat)
                    {
                        if(!map.get(j).get(i).equals(current))
                        {
                            System.out.print("N ");
                        }
                        else
                        {
                            System.out.print(" ");
                        }
                    }
                    else
                    {
                        System.out.print("? ");
                    }
                }
                if(map.get(j).get(i).type==Type.FINISH)
                {
                    if(map.get(j).get(i).vizitat)
                    {
                        System.out.print("F ");
                    }
                    else
                    {
                        System.out.print("? ");
                    }
                }
            }

            System.out.println();
        }
    }
}

enum Type
{
    EMPTY, ENEMY, SHOP, FINISH
}

class Cell
{
    int x, y;
    CellElement element;
    boolean vizitat;
    Type type;
}

interface CellElement
{
    char toCharacter();
}
