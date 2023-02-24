package com.company;

import jdk.swing.interop.SwingInterOpUtils;
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
    int gamefinished=0, maplength, mapheight;

    private Game()
    {
        gameaccounts=new ArrayList<>();
        stories= new HashMap<>();
    }

    public static Game getGame() //creates one instance game (Singleton Pattern)
    {
        if(game==null)
        {
            game= new Game();
        }
        return game;
    }

    void run() throws InvalidCommandException//run method that reads the JSON files and starts the game
    {
        InputStream is= Game.class.getResourceAsStream("accounts.json");
        JSONTokener tokener= new JSONTokener(is);
        JSONObject object= new JSONObject(tokener);
        JSONArray accounts= object.getJSONArray("accounts");

        for(int i=0; i<accounts.length(); i++)
        {
            //build accounts

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

        //build stories

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

        if(currentcharacter==null)
        {
            System.out.println("Character not found!");
            exit(1);
        }

        System.out.println("Gameplay: press WASD to move up, left, down or right on the map.");
        System.out.println("Length of the map:");
        maplength = scan.nextInt();
        System.out.println("Height of the map:");
        mapheight = scan.nextInt();
        System.out.println("All done! Your adventure is about to begin...\n");

        //The game begins here...

        String next;
        //Grid.generateMapHardcode();
        Grid.generateMap(maplength, mapheight);
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
        while(gamefinished == 0 && currentcharacter.currentlife>0)
        {
            next=scan.nextLine();

            switch(next)
            {
                case "W":
                    Grid.getMap().goNorth();
                    break;
                case "A":
                    Grid.getMap().goWest();
                    break;
                case "S":
                    Grid.getMap().goSouth();
                    break;
                case "D":
                    Grid.getMap().goEast();
                    break;
                default:
                    throw new InvalidCommandException();
            }
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
    }

    boolean login(Account account) //login account
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

    String optionstest(Cell c) throws InvalidCommandException//shows story and options for current cell
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

                    switch(next)
                    {
                        case "Attack":
                            int dmg= currentcharacter.getDamage();
                            System.out.println("You attacked with "+ dmg+"!");
                            ((Enemy)(c.element)).receiveDamage(dmg);
                            break;
                        case "Spell":
                            System.out.println("Pick a spell:");
                            aux=scan.nextInt();
                            next=scan.nextLine();
                            currentcharacter.usespell(currentcharacter.abilities.get(aux), ((Enemy)(c.element)));
                            break;
                        case "Potion":
                            System.out.println("Choose potion: ");
                            for(Potion p : currentcharacter.inventory.potions)
                            {
                                p.showname();
                            }
                            aux=scan.nextInt();
                            next=scan.nextLine();
                            currentcharacter.inventory.potions.get(aux).usepotion(currentcharacter);
                            System.out.print("You used ");
                            currentcharacter.inventory.potions.get(aux).showname();
                            currentcharacter.inventory.removepotion(currentcharacter.inventory.potions.get(aux));
                            break;
                        default:
                            throw new InvalidCommandException();
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
            System.out.println("Welcome! Type EXIT when you're done!");

            while(true)
            {
                System.out.println("Potions:");

                for(Potion p : ((Shop)c.element).potions)
                {
                    p.showpotion();
                }

                next=scan.nextLine();

                if(next.equals("EXIT"))
                {
                    break;
                }

                aux= Integer.parseInt(next);
                int succesbuy= currentcharacter.buypotion(((Shop)c.element).selectPotion(aux));

                if(succesbuy == 1)
                {
                    System.out.println("You bought ");
                    ((Shop)c.element).selectPotion(aux).showpotion();
                    System.out.println();
                    ((Shop)c.element).removepotion(((Shop)c.element).selectPotion(aux));
                }
            }
        }
        if(c.type==Type.EMPTY)
        {
            currentcharacter.receivemoney();
        }
        if(c.type==Type.FINISH)
        {
            gamefinished = 1;
        }

        return null;
    }

    void story(Cell cell) //shows story for the current cell
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


class InvalidCommandException extends Exception //exception invalid command
{
    public InvalidCommandException()
    {
        super("Invalid command!");
    }
}