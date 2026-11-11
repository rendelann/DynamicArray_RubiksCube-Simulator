package finalproject;

public class Move {

    private String notation;

    public Move(String notation) {
        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }

    public String getInverse() {

        if (notation.endsWith("'")) {
            return notation.substring(
                    0,
                    notation.length() - 1
            );
        }

        return notation + "'";
    }

    @Override
    public String toString() {
        return notation;
    }
}