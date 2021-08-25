package events.serverevents;

import java.util.ArrayList;

public class BoardsInfoSE extends ServerEvent{
    private final ArrayList<ArrayList<Integer>> board1FilledSquarsCoordinates;
    private final ArrayList<ArrayList<Integer>> board2FilledSquarsCoordinates;
    private final ArrayList<ArrayList<Integer>> board1HitedSquarsCoordinates;
    private final ArrayList<ArrayList<Integer>> board2HitedSquarsCoordinates;
    private final boolean update;

    public BoardsInfoSE(ArrayList<ArrayList<Integer>> board1FilledSquarsCoordinates, ArrayList<ArrayList<Integer>> board2FilledSquarsCoordinates, ArrayList<ArrayList<Integer>> board1HitedSquarsCoordinates, ArrayList<ArrayList<Integer>> board2HitedSquarsCoordinates, boolean update) {
        this.board1FilledSquarsCoordinates = board1FilledSquarsCoordinates;
        this.board2FilledSquarsCoordinates = board2FilledSquarsCoordinates;
        this.board1HitedSquarsCoordinates = board1HitedSquarsCoordinates;
        this.board2HitedSquarsCoordinates = board2HitedSquarsCoordinates;
        this.update = update;
    }

    public ArrayList<ArrayList<Integer>> getBoard1FilledSquarsCoordinates() {
        return board1FilledSquarsCoordinates;
    }

    public ArrayList<ArrayList<Integer>> getBoard2FilledSquarsCoordinates() {
        return board2FilledSquarsCoordinates;
    }

    public ArrayList<ArrayList<Integer>> getBoard1HitedSquarsCoordinates() {
        return board1HitedSquarsCoordinates;
    }

    public ArrayList<ArrayList<Integer>> getBoard2HitedSquarsCoordinates() {
        return board2HitedSquarsCoordinates;
    }

    public boolean isUpdate() {
        return update;
    }
}
