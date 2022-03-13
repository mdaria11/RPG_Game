package com.company;

import org.json.*;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.System.exit;

class Game
{
    private static Game game=null;
    ArrayList<Account> gameaccounts;
    HashMap<Type, ArrayList<String>> stories;
    private Character currentcharacter;

    private Game()
    {
        gameaccounts=new ArrayList<>();
        stories= new HashMap<>();
    }

    public static Game getGame() //creare instanta unica Game
    {
        if(game==null)
        {
            game= new Game();
        }
        return game;
    }

    void run(String mode) //metoda run care citeste fisierele JSON+ruleaza CLI si GUI
    {
        InputStream is= Game.class.getResourceAsStream("accounts.json");
        JSONTokener tokener= new JSONTokener(is);
        JSONObject object= new JSONObject(tokener);
        JSONArray accounts= object.getJSONArray("accounts");
        for(int i=0; i<accounts.length(); i++)
        {
            //construire conturi

            String country=((JSONObject)accounts.get(i)).get("country").toString();
            String name=((JSONObject)accounts.get(i)).get("name").toString();
            int mapsCompleted=Integer.parseInt(((JSONObject)accounts.get(i)).get("maps_completed").toString());
            String email= ((JSONObject)((JSONObject)accounts.get(i)).get("credentials")).get("email").toString();
            String password= ((JSONObject)((JSONObject)accounts.get(i)).get("credentials")).get("password").toString();
            JSONArray favgames=((JSONObject)accounts.get(i)).getJSONArray("favorite_games");

            Account account= new Account(name, email, password);
            account.setCountry(country);
            account.setPlayedGames(mapsCompleted);
            ArrayList<String> games= new ArrayList<>();

            for(int j=0; j<favgames.length(); j++)
            {
                games.add(favgames.get(j).toString());
            }

            account.setGames(games);

            JSONArray characters= ((JSONObject)((JSONObject)accounts.get(i))).getJSONArray("characters");

            CharacterMaker maker= new CharacterMaker();

            for(int j=0; j<characters.length(); j++)
            {
                String charname= ((JSONObject)(characters.get(j))).get("name").toString();
                String charprofession= ((JSONObject)(characters.get(j))).get("profession").toString();
                int lvl= Integer.parseInt(((JSONObject)(characters.get(j))).get("level").toString());
                int exp= Integer.parseInt(((JSONObject)(characters.get(j))).get("experience").toString());

                Character c= maker.getCharacter(charprofession, charname, lvl, exp);

                account.addCharacter(c);
            }

            gameaccounts.add(account);
        }

        //construire povesti

        InputStream iss= Game.class.getResourceAsStream("stories.json");
        JSONTokener tokenerr= new JSONTokener(iss);
        JSONObject objectt= new JSONObject(tokenerr);
        JSONArray storiesjson= objectt.getJSONArray("stories");

        for(int i=0; i<storiesjson.length(); i++)
        {
            String type=((JSONObject)storiesjson.get(i)).get("type").toString();
            String value= ((JSONObject)storiesjson.get(i)).get("value").toString();
            Type key= Type.valueOf(type);

            if(!stories.containsKey(key))
            {
                ArrayList<String> aux= new ArrayList<>();
                aux.add(value);
                stories.put(key, aux);
            }
            else
            {
                stories.get(key).add(value);
            }
        }

        if(mode.equals("text")) //CLI
        {
            //choose account
            System.out.println("Choose an account:\n");

            for(Account account : gameaccounts)
            {
                account.showaccount();
            }

            Scanner scan= new Scanner(System.in);
            String nameaccount= scan.nextLine();
            Account currentaccount=null;

            for(Account account : gameaccounts)
            {
                if(account.getName().equals(nameaccount))
                {
                    currentaccount=account;
                    break;
                }
            }
            if(currentaccount==null)
            {
                System.out.println("Account name not found!");
                exit(1);
            }

            while(!login(currentaccount)){}

            //choose character

            System.out.println("Choose your character:\n");
            currentaccount.showCharacters();
            String charactername= scan.nextLine();
            currentcharacter=null;
            currentcharacter= currentaccount.getCharacter(charactername);

            if(currentaccount==null)
            {
                System.out.println("Character not found!");
                exit(1);
            }

            System.out.println("All done! Your adventure is about to begin...\n");

            //The game begins here...

            String next;
            Grid.generateMapHardcode();
            Grid.getMap().initiateplayer();
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goEast();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goEast();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goEast();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goEast();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goSouth();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goSouth();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goSouth();//
            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }

            if(currentcharacter.currentlife<=0)
            {
                return;
            }
            currentcharacter.showstats();
            Grid.getMap().showMap();
            next=scan.nextLine();
            Grid.getMap().goSouth();//

            try
            {
                optionstest(Grid.getMap().current);
            }
            catch (InvalidCommandException e)
            {
                e.printStackTrace();
            }

            currentcharacter.showstats();
            Grid.getMap().showMap();

        }
        else //GUI stuff here
        {
            GUI start= new GUI();
            start.start();
        }

    }

    boolean login(Account account) //login cont
    {
        System.out.print("Password: ");
        Scanner scan= new Scanner(System.in);
        String password= scan.nextLine();

        if(password.equals(account.getCredentials().getPassword()))
        {
            System.out.println("Welcome "+account.getName()+"!");
            return true;
        }

        System.out.println("Incorrect password!");
        return false;
    }

    String optionstest(Cell c) throws InvalidCommandException//afiseaza poveste si optiuni +returneaza urmatorul move hardcodat
    {
        int aux;
        String next;
        Scanner scan= new Scanner(System.in);

        if(!c.vizitat)
        {
            story(c);
        }

        if(c.type==Type.ENEMY)
        {
            int turn = 1;

            while(currentcharacter.currentlife>0 && ((Enemy)(c.element)).currentlife>0)
            {
                System.out.println();
                currentcharacter.showstats();
                ((Enemy)(c.element)).showenemystats();

                if(turn == 1)//character turn
                {
                    System.out.println();
                    System.out.println("Attack | Use a spell: "+ currentcharacter.abilities+" | Use potion");

                    next=scan.nextLine();

                    if(!next.equals("P") && !next.equals("p"))
                    {
                        throw new InvalidCommandException();
                    }

                    if(currentcharacter.inventory.potions.size()>0)
                    {
                        if(currentcharacter.currentlife<=350 || currentcharacter.currentmana<=60)
                        {
                            System.out.println("Choose potion: ");
                            for(Potion p : currentcharacter.inventory.potions)
                            {
                                p.showname();
                            }
                            aux=ThreadLocalRandom.current().nextInt(0, currentcharacter.inventory.potions.size());
                            currentcharacter.inventory.potions.get(aux).usepotion(currentcharacter);
                            next=scan.nextLine();
                            System.out.print("You used ");
                            currentcharacter.inventory.potions.get(aux).showname();
                            currentcharacter.inventory.removepotion(currentcharacter.inventory.potions.get(aux));
                            turn=0;
                            continue;
                        }
                    }

                    if(currentcharacter.abilities.size()>0)
                    {
                        aux= ThreadLocalRandom.current().nextInt(0, currentcharacter.abilities.size());
                        currentcharacter.usespell(currentcharacter.abilities.get(aux), ((Enemy)(c.element)));
                    }
                    else
                    {
                        int dmg= currentcharacter.getDamage();
                        System.out.println("You attacked with "+ dmg+"!");
                        ((Enemy)(c.element)).receiveDamage(dmg);
                    }
                    turn = 0;
                }
                else//enemy turn
                {
                    System.out.println();
                    ((Enemy)(c.element)).attack(currentcharacter);
                    turn = 1;
                }
            }

            if(currentcharacter.currentlife<=0) //fail
            {
                System.out.println("You died!");
                return null;
            }
            else //success
            {
                currentcharacter.receivemoneyenemy(((Enemy)(c.element)));
                currentcharacter.addprogress(10);
                currentcharacter.checkprogress();
            }
        }
        if(c.type==Type.SHOP)
        {
            System.out.println("Potions:");

            for(Potion p : ((Shop)c.element).potions)
            {
                p.showpotion();
            }

            next= scan.nextLine();

            if(!next.equals("P") && !next.equals("p"))
            {
                throw new InvalidCommandException();
            }

            for(Potion p : ((Shop)c.element).potions)
            {
                if(p.getClass()==HealthPotion.class)
                {
                    currentcharacter.buypotion(p);
                    System.out.println("You bought ");
                    p.showpotion();
                    System.out.println();
                    ((Shop)c.element).removepotion(p);
                    break;
                }
            }

            System.out.println("Potions:");

            for(Potion p : ((Shop)c.element).potions)
            {
                p.showpotion();
            }

            next= scan.nextLine();

            if(!next.equals("P") && !next.equals("p"))
            {
                throw new InvalidCommandException();
            }

            for(Potion p : ((Shop)c.element).potions)
            {
                if(p.getClass()==ManaPotion.class)
                {
                    currentcharacter.buypotion(p);
                    System.out.println("You bought ");
                    p.showpotion();
                    System.out.println();
                    ((Shop)c.element).removepotion(p);
                    break;
                }
            }

            System.out.println("Potions:");

            for(Potion p : ((Shop)c.element).potions)
            {
                p.showpotion();
            }

            next= scan.nextLine();

            if(!next.equals("P") && !next.equals("p"))
            {
                throw new InvalidCommandException();
            }
        }
        if(c.type==Type.EMPTY)
        {
            currentcharacter.receivemoney();
        }

        return null;
    }

    void story(Cell cell) //afiseaza o poveste random pt tipul de celula
    {
        if(!cell.vizitat)
        {
            ArrayList<String> s;
            s=stories.get(cell.type);
            int nr= s.size();
            int index= ThreadLocalRandom.current().nextInt(0, nr);
            System.out.println(s.get(index));
        }
    }
}


class InvalidCommandException extends Exception //exceptie comanda invalida
{
    public InvalidCommandException()
    {
        super("Invalid command!");
    }
}