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

    public void turn(Scanner sc, String[] names, int[] cells, int[] penalization){
        int player = currentTurn % 4;
        System.out.println("És el torn del jugador " + (player + 1)+ ": " + names[player]);
        String throwDice = "";
        do {
            System.out.println("Tires?");
            throwDice = sc.next();
        }while(!throwDice.equals("tiro"));

        if(cells[player] > 60){
            int dice = (int) (Math.random() * 6) + 1;
            System.out.println("has tret un " + dice);
        }else {
            int dice1 = (int) (Math.random() * 6) + 1;
            int dice2 = (int) (Math.random() * 6) + 1;
            System.out.println("has tret un " + dice1 + "i un " + dice2);
        }


        currentTurn++;
    }

}