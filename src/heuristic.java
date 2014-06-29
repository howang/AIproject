
public class heuristic {

    /*
     * returns the horizontal position of the whole mouse (2 blocks)
     */
    public int getMousePosition_X(char[][] b) {
        int mouse_position = 0;
        for (int i = 0; i < b[2].length; i++) {
            if ((b[2][i] == 'B')) {
                mouse_position = i;
            }
        }
        return mouse_position + 1;
    }
    public int score(char[][] board) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}