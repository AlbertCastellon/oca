import java.util.Scanner;
import java.util.InputMismatchException;

public class oca {

    int currentTurn = 0;

    public static void main(String[] args) {
        oca p = new oca();
        p.principal();
    }

    public void principal() {
        Scanner escaner = new Scanner(System.in);
        int numPlayers = 0;

        System.out.println("Quantes persones jugaran?");
        numPlayers = numPlayers(escaner);
        String[] playerNames = new String[numPlayers];
        int[] playerCells = new int[numPlayers];
        int[] playerPenalization = new int[numPlayers];
        boolean [] isInWell = new boolean[numPlayers];
        
        int[] goose = {5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59};


        getPlayersNames(escaner, playerNames);
        initializeGame(playerCells, playerPenalization, isInWell);

        

    }

    public int numPlayers(Scanner sc) {
        int num = 0;
        do {
            try {
                num = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error");
            }
            if (num > 4) {
                System.out.println("El nombre de jugadors ha de ser menor a 4");
                num = 0;
            } else if (num < 2) {
                System.out.println("El nombre de jugadors ha de ser com a mínim 2");
                num = 0;
            }

        } while (num == 0);

        return num;
    }

    public String getName(Scanner sc) {
        String name = "";
        do{
            try {
                name = sc.next();
            }catch(Exception e) {
                System.out.println("Error");
            }
        } while(name.equals(""));
        
        return name;
    }

    public void getPlayersNames(Scanner sc, String[] array){
        for(int i = 0; i < array.length; i++){
            System.out.println("Introdueix el nom del jugador "+ (i + 1));
            array[i] = getName(sc);
        }
    }

    public void initializeGame(int[] cell, int[] penalization, boolean[] well){
        for (int i = 0; i < cell.length; i++){
            cell[i] = 0;
            penalization[i] = 0;
            well[i] = false;
        }
    }

    public void turn(Scanner sc, String[] names, int[] cells, int[] penalization, int[] goose, boolean[] well){
        boolean finished = false;
        int player = currentTurn % names.length;
        if(penalization[player] > 0){
            penalization[player]--;
            System.out.println("Et salta el torn.");
        }else{
            System.out.println("És el torn del jugador " + (player + 1)+ ": " + names[player]);
            String throwDice = "";
            do {
                System.out.println("Tires?");
                throwDice = sc.next();
            }while(!throwDice.equals("tiro"));

            if(cells[player] > 60){
                int dice = diceThrow();
                cells[player] += dice;
                System.out.println("has tret un " + dice);
            }else {
                int dice1 = diceThrow();
                int dice2 = diceThrow();
                
                System.out.println("has tret un " + dice1 + "i un " + dice2);
                if(currentTurn < names.length && dice36(dice1, dice2)){

                    System.out.println("De dado a dado y tiro porque me ha tocado");
                    cells[player] = 26;
                    currentTurn--;

                }else if(currentTurn < names.length && dice45(dice1, dice2)){

                    System.out.println("De dado a dado y tiro porque me ha tocado");
                    cells[player] = 53;
                    currentTurn--;

                }else{
                    cells[player] += dice1 + dice2;
                    System.out.println("Avances fins la casella " + cells[player]);
                }
            }
            
            if(isGoose(goose, cells[player])){
                System.out.println("De oca en oca y tiro porque me toca");
                for(int i = 0; i < goose.length; i++){
                    if(cells[player] == goose[i]){
                        if(i+1 == goose.length){
                            cells[player] = 63;
                        }
                        else{
                            cells[player] = goose[i + 1];
                        }
                        return;    
                    }
                } 
                currentTurn--;
            }

            cells[player] = bridge(cells[player]);
            cells[player] = death(cells[player]);
            cells[player] = labyrinth(cells[player]);
            penalization[player] = prison(cells[player], well, penalization, player);

            
            
            
        }
        currentTurn++;
        
    }

    public int diceThrow() {

        int dice = (int) (Math.random() * 6) + 1;
        return dice;
    }

    public boolean isGoose(int[] array, int cell) {
        boolean result = false;
        for(int i = 0; i < array.length; i++){
            if(cell == array[i]){
                result = true;
            }
        } 
        return result;
    }

    public boolean dice36(int dice1, int dice2){
        boolean result = false;
        if((dice1 == 3 && dice2 == 6)||(dice1 == 6 && dice2 == 3)){
            result = true;
        }
        return result;
    }

    public boolean dice45(int dice1, int dice2){
        boolean result = false;
        if((dice1 == 4 && dice2 == 5)||(dice1 == 5 && dice2 == 4)){
            result = true;
        }
        return result;
    }

    public int bridge(int cell){
        if(cell == 6 || cell == 12) {
            System.out.println("De puente a puente y tiro porque me de la corriente.");
            if(cell == 6){
                cell = 12;
            }else {
                cell = 6;
            }
            currentTurn--;
        }
        return cell;
    }

    public int death(int cell) {
        if(cell == 58){
            System.out.println("Has mort, torna a començar.");
            cell = 0;
        }
        return cell;
    }

    public int labyrinth(int cell) {
        if(cell == 42){
            cell = 39;
        }
        return cell;
    }

    public int prison(int cell, boolean[] well, int[] penalization, int player) {
        int turnsPunished = 0;
        if(cell == 19){
            turnsPunished = 1;
        }else if(cell == 52){
            turnsPunished = 3;
        }else if(cell == 31){
            for(int i = 0; i < well.length; i++){
                if(well[i]){
                    well[i] = !well[i];
                    penalization[i] = 0;
                }
            }
            well[player] = !well[player];
            turnsPunished = 2;
        }
        return turnsPunished;
    }

}