package finalproject;

public class MoveHistory {

    private DynamicArray<Move> moves;

    public MoveHistory() {
        moves = new DynamicArray<>();
    }

    public void addMove(String notation) {
        moves.add(new Move(notation));
    }

    public Move getLastMove() {

        if (moves.isEmpty()) {
            return null;
        }

        return moves.get(moves.size() - 1);
    }

    public Move removeLastMove() {

        if (moves.isEmpty()) {
            return null;
        }

        return moves.removeLast();
    }

    public int size() {
        return moves.size();
    }

    public int capacity() {
        return moves.capacity();
    }

    public void clear() {
        moves.clear();
    }

    public String getHistoryText() {

        if (moves.isEmpty()) {
            return "No moves yet.";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < moves.size(); i++) {

            result.append(i + 1)
                  .append(". ")
                  .append(moves.get(i))
                  .append("\n");
        }

        return result.toString();
    }

	public boolean isEmpty() {
		return moves.isEmpty();
		// TODO Auto-generated method stub
	}
}
