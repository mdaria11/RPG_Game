package com.company;
import java.util.*;

class InformationIncompleteException extends Exception //exception incomplete information
{
    public InformationIncompleteException()
    {
        super("Credentials and/or name not specified!");
    }
}

class Credentials
{
    private String email=null;
    private String password=null;

    void setEmail(String email)
    {
        this.email=email;
    }

    void setPassword(String password)
    {
        this.password=password;
    }

    String getEmail()
    {
        return email;
    }

    String getPassword()
    {
        return password;
    }
}

class Account
{
    class Information
    {
        private Credentials credentials; //required
        private String name, country; //name required
        private ArrayList<String> games;

        private Information(InformationBuilder builder)
        {
            this.credentials=builder.credentials;
            this.name= builder.name;
            this.country= builder.country;
            this.games= builder.games;
        }
    }
    class InformationBuilder
    {
        private Credentials credentials;
        private String name=null;
        private String country;
        private ArrayList<String> games;

        InformationBuilder name(String name)
        {
            this.name=name;
            return this;
        }

        InformationBuilder credentials(Credentials credentials)
        {
            this.credentials=credentials;
            return this;
        }

        InformationBuilder country(String country)
        {
            this.country=country;
            return this;
        }

        InformationBuilder games(ArrayList<String> games)
        {
            Collections.sort(games);
            this.games=games;
            return this;
        }

        Information build() throws InformationIncompleteException
        {
            Account.Information information= new Account.Information(this);

            if(information.name==null || information.credentials.getPassword()==null || information.credentials.getEmail()==null)
            {
                throw new InformationIncompleteException();
            }
            else
            {
                return information;
            }
        }
    }

    private Information informations;
    private ArrayList<Character> characters= new ArrayList<>();
    private int gamesPlayed=0;
    private InformationBuilder builder;

    public Account(String name, String email, String password) //creates account with mandatory information
    {
        builder= new InformationBuilder();
        Credentials credentials= new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);
        try {
            informations=builder.name(name).credentials(credentials).build();
        }
        catch (InformationIncompleteException e)
        {
            e.printStackTrace();
        }
    }

    String getName() //returns name
    {
        return informations.name;
    }

    void showCharacters() //shows account's characters
    {
        for(Character c : characters)
        {
            c.showcharacterinfo();
            System.out.println();
        }
    }

    Character getCharacter(String name) //returns the character based on their name
    {
        for(Character c : characters)
        {
            if(c.name.equals(name))
            {
                return c;
            }
        }

        return null;
    }


    Credentials getCredentials() //returns credentials
    {
        return informations.credentials;
    }

    void setCountry(String country) //set country
    {
        builder=builder.country(country);
        buildAccount();
    }

    void setGames(ArrayList<String> games) //set games
    {
        builder=builder.games(games);
        buildAccount();
    }

    void setPlayedGames(int gamesplayed)
    {
        gamesPlayed=gamesplayed;
    } //set played games

    private void buildAccount() //builds account's information
    {
        try {
            informations= builder.build();
        }
        catch (InformationIncompleteException e)
        {
            e.printStackTrace();
        }
    }

    void showaccount() //shows name and information about account
    {
        System.out.println(informations.name+" : "+informations.credentials.getEmail());
    }


    void addCharacter(Character c) //adds a character to the account
    {
        characters.add(c);
    }
}


