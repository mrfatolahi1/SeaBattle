package models.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.squars.Square;

import java.util.ArrayList;


public class Board {
    private Square[][] squares;
    private ArrayList<Square> fullSquars;
    private ArrayList<ArrayList<Square>> shipsSquars;
    private int randomMapNumber;

    public Board(){}

    public Board(int randomMapNumber) {
        this.squares = new Square[10][10];
        this.fullSquars = new ArrayList<>();
        this.shipsSquars = new ArrayList<>();
        this.randomMapNumber = randomMapNumber;
        initializeBoard(randomMapNumber);
    }

    public void initializeBoard(int randomMapNumber){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                squares[i][j] = new Square(i, j, false);
            }
        }


        if (randomMapNumber == 0){
            ArrayList<Square> battleshipSquars = new ArrayList<>();
            battleshipSquars.add(squares[4][2]);
            battleshipSquars.add(squares[5][2]);
            battleshipSquars.add(squares[6][2]);
            battleshipSquars.add(squares[7][2]);
            fullSquars.addAll(battleshipSquars);
            shipsSquars.add(battleshipSquars);

            ArrayList<Square> cruiser1Squars = new ArrayList<>();
            cruiser1Squars.add(squares[7][7]);
            cruiser1Squars.add(squares[7][8]);
            cruiser1Squars.add(squares[7][9]);
            fullSquars.addAll(cruiser1Squars);
            shipsSquars.add(cruiser1Squars);

            ArrayList<Square> cruiser2Squars = new ArrayList<>();
            cruiser2Squars.add(squares[0][7]);
            cruiser2Squars.add(squares[1][7]);
            cruiser2Squars.add(squares[2][7]);
            fullSquars.addAll(cruiser2Squars);
            shipsSquars.add(cruiser2Squars);

            ArrayList<Square> destroyer1Squars = new ArrayList<>();
            destroyer1Squars.add(squares[1][0]);
            destroyer1Squars.add(squares[1][1]);
            fullSquars.addAll(destroyer1Squars);
            shipsSquars.add(destroyer1Squars);

            ArrayList<Square> destroyer2Squars = new ArrayList<>();
            destroyer2Squars.add(squares[2][3]);
            destroyer2Squars.add(squares[2][4]);
            fullSquars.addAll(destroyer2Squars);
            shipsSquars.add(destroyer2Squars);

            ArrayList<Square> destroyer3Squars = new ArrayList<>();
            destroyer3Squars.add(squares[4][8]);
            destroyer3Squars.add(squares[5][8]);
            fullSquars.addAll(destroyer3Squars);
            shipsSquars.add(destroyer3Squars);

            ArrayList<Square> frigate1Squars = new ArrayList<>();
            frigate1Squars.add(squares[5][0]);
            fullSquars.addAll(frigate1Squars);
            shipsSquars.add(frigate1Squars);

            ArrayList<Square> frigate2Squars = new ArrayList<>();
            frigate2Squars.add(squares[9][2]);
            fullSquars.addAll(frigate2Squars);
            shipsSquars.add(frigate2Squars);

            ArrayList<Square> frigate3Squars = new ArrayList<>();
            frigate3Squars.add(squares[7][4]);
            fullSquars.addAll(frigate3Squars);
            shipsSquars.add(frigate3Squars);

            ArrayList<Square> frigate4Squars = new ArrayList<>();
            frigate4Squars.add(squares[9][5]);
            fullSquars.addAll(frigate4Squars);
            shipsSquars.add(frigate4Squars);
        } else
        if (randomMapNumber == 1){
            ArrayList<Square> battleshipSquars = new ArrayList<>();
            battleshipSquars.add(squares[6][7]);
            battleshipSquars.add(squares[7][7]);
            battleshipSquars.add(squares[8][7]);
            battleshipSquars.add(squares[9][7]);
            fullSquars.addAll(battleshipSquars);
            shipsSquars.add(battleshipSquars);

            ArrayList<Square> cruiser1Squars = new ArrayList<>();
            cruiser1Squars.add(squares[6][5]);
            cruiser1Squars.add(squares[7][5]);
            cruiser1Squars.add(squares[8][5]);
            fullSquars.addAll(cruiser1Squars);
            shipsSquars.add(cruiser1Squars);

            ArrayList<Square> cruiser2Squars = new ArrayList<>();
            cruiser2Squars.add(squares[6][1]);
            cruiser2Squars.add(squares[6][2]);
            cruiser2Squars.add(squares[6][3]);
            fullSquars.addAll(cruiser2Squars);
            shipsSquars.add(cruiser2Squars);

            ArrayList<Square> destroyer1Squars = new ArrayList<>();
            destroyer1Squars.add(squares[2][0]);
            destroyer1Squars.add(squares[3][0]);
            fullSquars.addAll(destroyer1Squars);
            shipsSquars.add(destroyer1Squars);

            ArrayList<Square> destroyer2Squars = new ArrayList<>();
            destroyer2Squars.add(squares[0][7]);
            destroyer2Squars.add(squares[0][8]);
            fullSquars.addAll(destroyer2Squars);
            shipsSquars.add(destroyer2Squars);

            ArrayList<Square> destroyer3Squars = new ArrayList<>();
            destroyer3Squars.add(squares[7][9]);
            destroyer3Squars.add(squares[8][9]);
            fullSquars.addAll(destroyer3Squars);
            shipsSquars.add(destroyer3Squars);

            ArrayList<Square> frigate1Squars = new ArrayList<>();
            frigate1Squars.add(squares[4][2]);
            fullSquars.addAll(frigate1Squars);
            shipsSquars.add(frigate1Squars);

            ArrayList<Square> frigate2Squars = new ArrayList<>();
            frigate2Squars.add(squares[2][5]);
            fullSquars.addAll(frigate2Squars);
            shipsSquars.add(frigate2Squars);

            ArrayList<Square> frigate3Squars = new ArrayList<>();
            frigate3Squars.add(squares[4][6]);
            fullSquars.addAll(frigate3Squars);
            shipsSquars.add(frigate3Squars);

            ArrayList<Square> frigate4Squars = new ArrayList<>();
            frigate4Squars.add(squares[4][8]);
            fullSquars.addAll(frigate4Squars);
            shipsSquars.add(frigate4Squars);
        } else
        if (randomMapNumber == 2){
            ArrayList<Square> battleshipSquars = new ArrayList<>();
            battleshipSquars.add(squares[0][5]);
            battleshipSquars.add(squares[0][6]);
            battleshipSquars.add(squares[0][7]);
            battleshipSquars.add(squares[0][8]);
            fullSquars.addAll(battleshipSquars);
            shipsSquars.add(battleshipSquars);

            ArrayList<Square> cruiser1Squars = new ArrayList<>();
            cruiser1Squars.add(squares[1][2]);
            cruiser1Squars.add(squares[2][2]);
            cruiser1Squars.add(squares[3][2]);
            fullSquars.addAll(cruiser1Squars);
            shipsSquars.add(cruiser1Squars);

            ArrayList<Square> cruiser2Squars = new ArrayList<>();
            cruiser2Squars.add(squares[7][0]);
            cruiser2Squars.add(squares[8][0]);
            cruiser2Squars.add(squares[9][0]);
            fullSquars.addAll(cruiser2Squars);
            shipsSquars.add(cruiser2Squars);

            ArrayList<Square> destroyer1Squars = new ArrayList<>();
            destroyer1Squars.add(squares[6][2]);
            destroyer1Squars.add(squares[6][3]);
            fullSquars.addAll(destroyer1Squars);
            shipsSquars.add(destroyer1Squars);

            ArrayList<Square> destroyer2Squars = new ArrayList<>();
            destroyer2Squars.add(squares[4][4]);
            destroyer2Squars.add(squares[4][5]);
            fullSquars.addAll(destroyer2Squars);
            shipsSquars.add(destroyer2Squars);

            ArrayList<Square> destroyer3Squars = new ArrayList<>();
            destroyer3Squars.add(squares[2][8]);
            destroyer3Squars.add(squares[3][8]);
            fullSquars.addAll(destroyer3Squars);
            shipsSquars.add(destroyer3Squars);

            ArrayList<Square> frigate1Squars = new ArrayList<>();
            frigate1Squars.add(squares[8][3]);
            fullSquars.addAll(frigate1Squars);
            shipsSquars.add(frigate1Squars);

            ArrayList<Square> frigate2Squars = new ArrayList<>();
            frigate2Squars.add(squares[7][5]);
            fullSquars.addAll(frigate2Squars);
            shipsSquars.add(frigate2Squars);

            ArrayList<Square> frigate3Squars = new ArrayList<>();
            frigate3Squars.add(squares[9][7]);
            fullSquars.addAll(frigate3Squars);
            shipsSquars.add(frigate3Squars);

            ArrayList<Square> frigate4Squars = new ArrayList<>();
            frigate4Squars.add(squares[7][8]);
            fullSquars.addAll(frigate4Squars);
            shipsSquars.add(frigate4Squars);
        } else
        if (randomMapNumber == 3){
            ArrayList<Square> battleshipSquars = new ArrayList<>();
            battleshipSquars.add(squares[5][4]);
            battleshipSquars.add(squares[5][5]);
            battleshipSquars.add(squares[5][6]);
            battleshipSquars.add(squares[5][7]);
            fullSquars.addAll(battleshipSquars);
            shipsSquars.add(battleshipSquars);

            ArrayList<Square> cruiser1Squars = new ArrayList<>();
            cruiser1Squars.add(squares[7][0]);
            cruiser1Squars.add(squares[8][0]);
            cruiser1Squars.add(squares[9][0]);
            fullSquars.addAll(cruiser1Squars);
            shipsSquars.add(cruiser1Squars);

            ArrayList<Square> cruiser2Squars = new ArrayList<>();
            cruiser2Squars.add(squares[1][9]);
            cruiser2Squars.add(squares[2][9]);
            cruiser2Squars.add(squares[3][9]);
            fullSquars.addAll(cruiser2Squars);
            shipsSquars.add(cruiser2Squars);

            ArrayList<Square> destroyer1Squars = new ArrayList<>();
            destroyer1Squars.add(squares[4][2]);
            destroyer1Squars.add(squares[5][2]);
            fullSquars.addAll(destroyer1Squars);
            shipsSquars.add(destroyer1Squars);

            ArrayList<Square> destroyer2Squars = new ArrayList<>();
            destroyer2Squars.add(squares[9][7]);
            destroyer2Squars.add(squares[9][8]);
            fullSquars.addAll(destroyer2Squars);
            shipsSquars.add(destroyer2Squars);

            ArrayList<Square> destroyer3Squars = new ArrayList<>();
            destroyer3Squars.add(squares[6][8]);
            destroyer3Squars.add(squares[6][9]);
            fullSquars.addAll(destroyer3Squars);
            shipsSquars.add(destroyer3Squars);

            ArrayList<Square> frigate1Squars = new ArrayList<>();
            frigate1Squars.add(squares[0][1]);
            fullSquars.addAll(frigate1Squars);
            shipsSquars.add(frigate1Squars);

            ArrayList<Square> frigate2Squars = new ArrayList<>();
            frigate2Squars.add(squares[0][4]);
            fullSquars.addAll(frigate2Squars);
            shipsSquars.add(frigate2Squars);

            ArrayList<Square> frigate3Squars = new ArrayList<>();
            frigate3Squars.add(squares[2][7]);
            fullSquars.addAll(frigate3Squars);
            shipsSquars.add(frigate3Squars);

            ArrayList<Square> frigate4Squars = new ArrayList<>();
            frigate4Squars.add(squares[2][9]);
            fullSquars.addAll(frigate4Squars);
            shipsSquars.add(frigate4Squars);
        } else
        if (randomMapNumber == 4){
            ArrayList<Square> battleshipSquars = new ArrayList<>();
            battleshipSquars.add(squares[3][6]);
            battleshipSquars.add(squares[4][6]);
            battleshipSquars.add(squares[5][6]);
            battleshipSquars.add(squares[6][6]);
            fullSquars.addAll(battleshipSquars);
            shipsSquars.add(battleshipSquars);

            ArrayList<Square> cruiser1Squars = new ArrayList<>();
            cruiser1Squars.add(squares[0][2]);
            cruiser1Squars.add(squares[1][2]);
            cruiser1Squars.add(squares[2][2]);
            fullSquars.addAll(cruiser1Squars);
            shipsSquars.add(cruiser1Squars);

            ArrayList<Square> cruiser2Squars = new ArrayList<>();
            cruiser2Squars.add(squares[3][8]);
            cruiser2Squars.add(squares[4][8]);
            cruiser2Squars.add(squares[5][8]);
            fullSquars.addAll(cruiser2Squars);
            shipsSquars.add(cruiser2Squars);

            ArrayList<Square> destroyer1Squars = new ArrayList<>();
            destroyer1Squars.add(squares[3][0]);
            destroyer1Squars.add(squares[4][0]);
            fullSquars.addAll(destroyer1Squars);
            shipsSquars.add(destroyer1Squars);

            ArrayList<Square> destroyer2Squars = new ArrayList<>();
            destroyer2Squars.add(squares[0][5]);
            destroyer2Squars.add(squares[0][6]);
            fullSquars.addAll(destroyer2Squars);
            shipsSquars.add(destroyer2Squars);

            ArrayList<Square> destroyer3Squars = new ArrayList<>();
            destroyer3Squars.add(squares[8][7]);
            destroyer3Squars.add(squares[8][8]);
            fullSquars.addAll(destroyer3Squars);
            shipsSquars.add(destroyer3Squars);

            ArrayList<Square> frigate1Squars = new ArrayList<>();
            frigate1Squars.add(squares[8][1]);
            fullSquars.addAll(frigate1Squars);
            shipsSquars.add(frigate1Squars);

            ArrayList<Square> frigate2Squars = new ArrayList<>();
            frigate2Squars.add(squares[4][3]);
            fullSquars.addAll(frigate2Squars);
            shipsSquars.add(frigate2Squars);

            ArrayList<Square> frigate3Squars = new ArrayList<>();
            frigate3Squars.add(squares[7][3]);
            fullSquars.addAll(frigate3Squars);
            shipsSquars.add(frigate3Squars);

            ArrayList<Square> frigate4Squars = new ArrayList<>();
            frigate4Squars.add(squares[0][9]);
            fullSquars.addAll(frigate4Squars);
            shipsSquars.add(frigate4Squars);
        }

        for (Square square : fullSquars){
            square.setFull(true);
        }
    }

    public Square getSquare(int x, int y){
        return squares[x][y];
    }

    public ArrayList<Square> getFullSquars() {
        return fullSquars;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public ArrayList<ArrayList<Square>> getShipsSquars() {
        return shipsSquars;
    }

    public int getRandomMapNumber() {
        return randomMapNumber;
    }

    @JsonIgnore
    public ArrayList<ArrayList<Integer>> getFullSquarsAsListOfLists(){
        ArrayList<ArrayList<Integer>> a = new ArrayList<>();

        for (Square square : fullSquars){
            ArrayList<Integer> b = new ArrayList<>();
            b.add(square.getX());
            b.add(square.getY());
            a.add(b);
        }

        return a;
    }

    @JsonIgnore
    public ArrayList<ArrayList<Integer>> getHitedSquarsAsListOfLists(){
        ArrayList<ArrayList<Integer>> a = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (squares[i][j].isHited()){
                    ArrayList<Integer> b = new ArrayList<>();
                    b.add(i);
                    b.add(j);
                    a.add(b);
                }
            }
        }

        return a;
    }

    @JsonIgnore
    public int getNumberOfRemainingShips(){
        int number = 0;
        for (ArrayList<Square> shipSquars : this.getShipsSquars()){
            boolean a = true;
            for (Square square : shipSquars){
                if (square.isHited()) {
                    a = false;
                    break;
                }
            }

            if (a){
                number++;
            }
        }

        return number;
    }

    @JsonIgnore
    public int getNumberOfDestroyedShips(){
        int number = 0;
        for (ArrayList<Square> shipSquars : this.getShipsSquars()){
            boolean a = true;
            for (Square square : shipSquars){
                if (!square.isHited()) {
                    a = false;
                    break;
                }
            }

            if (a){
                number++;
            }
        }

        return number;
    }

    public void checkShipsDestruction(){
      A:for (ArrayList<Square> shipSquars : shipsSquars){
            for (Square square : shipSquars){
                if (!square.isHited()){
                    continue A;
                }
            }
          for (Square square : shipSquars){
              for (ArrayList<Integer> neighborSquare : square.getNeighborSquarsCoordinates()){
                  getSquare(neighborSquare.get(0), neighborSquare.get(1)).setHited(true);
              }
          }

        }
    }
}
