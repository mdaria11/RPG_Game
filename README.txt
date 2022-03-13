--------------------World of Marcel--------------------
Nume: Moldovan Daria
Grupa: 324CC
Gradul de dificultate(1-easy, 10-tema la sd): 4
Timp alocat temei: in jur de o saptamana jumate

---------------------Implementare---------------------

Clasa Game:

  Avem mai intai obiectul "game" care reprezinta unica
instanta a clasei (Singleton Pattern), o lista de conturi,
un hashmap cu povestile celulelor si caracterul curent.
Constructorul clasei este privat si initiaza lista si
dictionarul. Mai avem o metoda "getGame" care intoarce
obiectul Game cu instantiere intarziata (adica daca 
nu am mai apelat metoda inainte creeaza obiectul game,
altfel il doar returneaza). Metoda run este cea care
mai intai formeaza lista de conturi si disctionarul 
cu povesti din fisierele JSON. Dupa se alege modul de joc
(text/GUI). Pentru text mai intai se alege un cont din cele
afisate, se introduce parola respectiva si daca totul este
in regula se alege un personaj din cont. Dupa se genereaza
mapa si se pune personajul pe mapa. Dupa urmeaza tooate
comenzile hardcodate din test, schema principala fiind:
apelam metoda option pt celula curenta, se afiseaza informatiile
despre personaj si mapa, se aseapta urmatoarea mutare. In metoda
optiontest pt fiecare tip de celula se va parcurge un anumit
cod hardcodat; 
spre exemplu pentru ENEMY lupta va tine 
pana cand ori personajul ori enemy-ul va muri. Am folosit o variabila
"turn" ca sa se verifice a cui e randul sa atace. Cand e randul
personajului, mai intai se printeaza ce optiuni are de ales: attack,
use spell, use potion. Am hardcodat ca atunci cand viata sau mana
personajului scade sub un anumit nivel sa se foloseasca optiunea
"use potion" si se alege o potiune random din inventory-ul personajului.
Dupa verificam daca avem spells si daca da atunci se vor folosi toate
pe rand randomly. Dupa ce se epuizeaza toate astea atunci personajul
va ataca normal. Cand e tura enemy-ului, acesta va apela metoda attack()
care are sansa 40% sa foloseasca spell, in rest ataca normal. Daca
personajul moare atunci se va afisa un mesaj corespunzator si jocul
se va inchide, altfel personajul poate primi bani, i se adauga un 
progres de 10 puncte si va creste cu un nivel.
Pentru celulele de tip SHOP se afiseaza potiunile din magazin si am
hardcodat sa se cumpere 2 potiuni.
Pentru EMPTY si FINISH doar se afiseaza povestea respectiva.

Clasele Account, Information si Credentials:

La credentials avem settere si gettere (incapsularea datelor).
Pt information mai avem si clasa InformationBuilder care
construieste un obiect builder cu aceleasi campuri ca 
information si niste metode care initiaza aceste campuri.
Cand terminam folosim metoda build() care construieste obiectul
information cu builderul respectiv si da o exceptie daca unul
dintre campurile obligatorii nu este initializat.
La account, pe langa campurile din cerinta, mai avem si un
obiect de tip informationbuilder care se actualizeaza
de fiecare daca cand adaugam valori noi pentru obiectul
information. adica metoda buildaccount() care construieste
information si handle exception.

Clasa Grid:

Am folosit si aici un Singleton Pattern pentru ca constructorul
este privat si avem doar un singur obiect de tip Grid (adica
mapa basically). Avem aici o metoda generateMap() care imi genereaza
o mapa random, teoretic ar fi fost folosita pentru GUI dar...
Dupa avem o metoda generateMapHardcode() care imi genereaza
harta din test. Mai avem niste metode care imi genereaza un anumit
tip de celula si metodele de traversare a casutelor care basically
imi schimba coordonatele playerului si celula curenta. Dupa avem metoda
showMap() care imi afiseaza mapa in functie de progresul playerului
si cam atat.

Clasa Entity:

Avem metoda usespell() care verifica daca se poate folosi abilitatea
si daca da atunci se apeleaza metoda accept() al entitatii (din
Visitor Pattern).

Clasa Character:

Avem mai multe metode de afisare(showstats, showcharacterinfo etc), 
o metoda receivemoneyenemy() care calculeaza sansa ca dupa ce
un enemy este invins acesta sa ofere niste coins, o metoda care
adauga progres la jucator(la 10 exp se mareste nivelul). Mai avem
o metoda receivemoney() care calculeaza sansa ca la o celula empty
sa se gaseasca coins. Avem metoda buypotion() care verifica ca
in inventar sa exista suficiente monede si suficient spatiu ca sa
se cumpere potiunea primita ca parametru si in cazul in care se 
poate cumpara este adaugata in inventarul personajului.
Mai avem clasa CharacterMaker care in functie de tipul personajului
(mage, warrior, rogue) returneaza un character cu numele, nivelul
si exp primite ca parametru.

Clasele Warrior, Mage, Rogue:

Am pus niste valori randomish initiale la atribute in functie de nivel
si 2-4 random spells. Avem metodele de receivedamage() care in functie
de cele doua atribute secundare damage-ul poate fi injumatatit sau
primeste 1/3 din damage-ul initial. Metoda getDamage() are o anumita
formula formata din cele 3 atribute si in functie de atributul principal
damage-ul poate fi dublat.

Clasa Enemy:

Constructorul genereaza un enemy random cu niste valori din niste intervale.
Metoda attack() calculeaza sansa ca enemy-ul sa foloseasca un spell sau sa
atace normal. Metoda receivedamage() calculeaza sansa ca enemy-ul sa 
evite atacul si verifica viata acestuia. Metoda givemoney() da personajului
un anumit nr de coins. Metoda getDamage() ia un nr random ca damage si
calculeaza sansa ca acest damage sa fie dublat.

Clasa Spell:

Am rescris functia toString() ca sa mi afiseze potiunea frumos.

Clasele Ice, Fire, Earth:

Avem metoda visit() de la Visitor Pattern care mai intai vede ce fel
de entity avem (personaj sau enemy) si in functie de protectiile entitatilor
se vor afisa mesaje corespunzatoare sau se va da damage-ul respectiv.

Clasa Shop:

Constructorul face un obiect cu un nr random de potiuni random, putin hardcodata
ca sa ma asigur ca exista cel putin o potiune de mana si una de health.
Am mai implementat cele doua metode, cea din cerinta (selectPotion) care 
primeste indexul potiunii din magazin dar pentru testul hardcodat am 
implementat metoda removepotion care elimina potiunea aleasa din magazin.