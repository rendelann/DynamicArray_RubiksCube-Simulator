package finalproject;

public class RuntimeTest {

    private static final int FIXED_CAPACITY = 1000;
    private static final int SIMULATIONS = 5;

    private static final String[] MOVES = {
        "U", "R", "F", "D", "L",
        "B", "U'", "R'", "F'", "D'"
    };

    public static void main(String[] args) {

        
        runWarmUp();

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("       EXPERIMENT 1: SMALL-SCALE SIMULATIONS");
        System.out.println("==============================================================");

        int[] smallSizes = {3, 6, 10};

        runExperiment("FIXED-SIZE ARRAY", smallSizes, false);
        runExperiment("DYNAMIC ARRAY", smallSizes, true);

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("       EXPERIMENT 2: LARGE-SCALE BENCHMARK");
        System.out.println("==============================================================");

        int[] largeSizes = {10, 100, 1000};

        runExperiment("FIXED-SIZE ARRAY", largeSizes, false);
        runExperiment("DYNAMIC ARRAY", largeSizes, true);
    }

    private static void runWarmUp() {
        for (int i = 0; i < 3; i++) {
            testFixedArray(10);
            testDynamicArray(10);
        }
    }

    private static void runExperiment(
            String arrayType,
            int[] inputSizes,
            boolean useDynamicArray) {

        System.out.println();
        System.out.println(arrayType);
        System.out.println(
            "+-------------+---------------+---------------+---------------+"
          + "---------------+---------------+---------------+"
        );

        System.out.printf(
            "| %-11s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s |%n",
            "Input Size",
            "Simulation 1",
            "Simulation 2",
            "Simulation 3",
            "Simulation 4",
            "Simulation 5",
            "Average (ns)"
        );

        System.out.println(
            "+-------------+---------------+---------------+---------------+"
          + "---------------+---------------+---------------+"
        );

        for (int size : inputSizes) {

            long[] times = new long[SIMULATIONS];
            long total = 0;

            for (int i = 0; i < SIMULATIONS; i++) {

                if (useDynamicArray) {
                    times[i] = testDynamicArray(size);
                } else {
                    times[i] = testFixedArray(size);
                }

                total += times[i];
            }

            long average = total / SIMULATIONS;

            System.out.printf(
                "| %-11d | %-13d | %-13d | %-13d | %-13d | %-13d | %-13d |%n",
                size,
                times[0],
                times[1],
                times[2],
                times[3],
                times[4],
                average
            );
        }

        System.out.println(
            "+-------------+---------------+---------------+---------------+"
          + "---------------+---------------+---------------+"
        );
    }

    // Baseline: Fixed-Size Array
    private static long testFixedArray(int numberOfMoves) {

        Move[] moves = new Move[FIXED_CAPACITY];

        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfMoves; i++) {
            moves[i] = new Move(MOVES[i % MOVES.length]);
        }

        long endTime = System.nanoTime();

        return endTime - startTime;
  }

    // Proposed: Dynamic Array
    private static long testDynamicArray(int numberOfMoves) {

        DynamicArray<Move> moves = new DynamicArray<>();

        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfMoves; i++) {
            moves.add(new Move(MOVES[i % MOVES.length]));
     }

        long endTime = System.nanoTime();

        return endTime - startTime;
    }
}