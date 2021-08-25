package models.squars;

import java.util.ArrayList;

public class Square {
    private int x;
    private int y;
    private boolean full;
    private boolean hited;
    private ArrayList<ArrayList<Integer>>  neighborSquarsCoordinates;

    public Square() {
    }

    public Square(int x, int y, boolean full) {
        this.x = x;
        this.y = y;
        this.full = full;

        this.neighborSquarsCoordinates = new ArrayList<>();

        if (x != 9){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x+1);
            a.add(y);
            neighborSquarsCoordinates.add(a);
        }
        if (y != 9){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x);
            a.add(y+1);
            neighborSquarsCoordinates.add(a);
        }
        if (x !=  0){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x-1);
            a.add(y);
            neighborSquarsCoordinates.add(a);
        }
        if (y !=  0){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x);
            a.add(y-1);
            neighborSquarsCoordinates.add(a);
        }
        if (x != 9 && y  != 9){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x+1);
            a.add(y+1);
            neighborSquarsCoordinates.add(a);
        }
        if (x != 9 && y  !=  0){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x+1);
            a.add(y-1);
            neighborSquarsCoordinates.add(a);
        }
        if (x !=  0  && y != 9){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x-1);
            a.add(y+1);
            neighborSquarsCoordinates.add(a);
        }
        if (x !=  0  && y !=  0){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(x-1);
            a.add(y-1);
            neighborSquarsCoordinates.add(a);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFull() {
        return full;
    }

    public boolean isHited() {
        return hited;
    }

    public void setHited(boolean hited) {
        this.hited = hited;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public ArrayList<ArrayList<Integer>> getNeighborSquarsCoordinates() {
        return neighborSquarsCoordinates;
    }

    public ArrayList<Integer> getCoordinatesAsList(){
        ArrayList<Integer> a = new ArrayList<>();
        a.add(x);
        a.add(y);
        return a;
    }
}
