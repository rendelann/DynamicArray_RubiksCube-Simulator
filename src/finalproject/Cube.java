package finalproject;

public class Cube {

    public enum Face {
        U, D, F, B, L, R
    }

    /*
     * Each sticker has:
     * - a position on the cube
     * - a direction showing which face it belongs to
     * - a color
     */
    private static class Sticker {

        int x;
        int y;
        int z;

        int nx;
        int ny;
        int nz;

        char color;

        Sticker(
                int x, int y, int z,
                int nx, int ny, int nz,
                char color
        ) {
            this.x = x;
            this.y = y;
            this.z = z;

            this.nx = nx;
            this.ny = ny;
            this.nz = nz;

            this.color = color;
        }
    }

    private Sticker[] stickers;

    public Cube() {
        reset();
    }

    // =========================================================
    // CREATE SOLVED CUBE
    // =========================================================

    public void reset() {

        stickers = new Sticker[54];

        int index = 0;

        /*
         * U = White
         */
        index = createFace(
                index,
                0, 1, 0,
                1, 0, 0,
                0, 0, 1,
                'W'
        );

        /*
         * D = Yellow
         */
        index = createFace(
                index,
                0, -1, 0,
                1, 0, 0,
                0, 0, -1,
                'Y'
        );

        /*
         * F = Green
         */
        index = createFace(
                index,
                0, 0, 1,
                1, 0, 0,
                0, -1, 0,
                'G'
        );

        /*
         * B = Blue
         */
        index = createFace(
                index,
                0, 0, -1,
                -1, 0, 0,
                0, -1, 0,
                'B'
        );

        /*
         * L = Orange
         */
        index = createFace(
                index,
                -1, 0, 0,
                0, 0, 1,
                0, -1, 0,
                'O'
        );

        /*
         * R = Red
         */
        createFace(
                index,
                1, 0, 0,
                0, 0, -1,
                0, -1, 0,
                'R'
        );
    }

    private int createFace(
            int index,
            int nx, int ny, int nz,
            int ux, int uy, int uz,
            int vx, int vy, int vz,
            char color
    ) {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                int localX = col - 1;
                int localY = row - 1;

                int x =
                        nx
                        + localX * ux
                        + localY * vx;

                int y =
                        ny
                        + localX * uy
                        + localY * vy;

                int z =
                        nz
                        + localX * uz
                        + localY * vz;

                stickers[index++] =
                        new Sticker(
                                x, y, z,
                                nx, ny, nz,
                                color
                        );
            }
        }

        return index;
    }

    // =========================================================
    // MOVES
    // =========================================================

    public void move(String notation) {

        boolean inverse =
                notation.endsWith("'");

        char face =
                notation.charAt(0);

        switch (face) {

            case 'U':
                rotate(0, 1, 0, inverse);
                break;

            case 'D':
                rotate(0, -1, 0, inverse);
                break;

            case 'F':
                rotate(0, 0, 1, inverse);
                break;

            case 'B':
                rotate(0, 0, -1, inverse);
                break;

            case 'L':
                rotate(-1, 0, 0, inverse);
                break;

            case 'R':
                rotate(1, 0, 0, inverse);
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid move: " + notation
                );
        }
    }

    // =========================================================
    // ROTATE ONE LAYER
    // =========================================================

    private void rotate(
            int ax,
            int ay,
            int az,
            boolean inverse
    ) {

        for (Sticker sticker : stickers) {

            int positionOnAxis =
                    sticker.x * ax
                    + sticker.y * ay
                    + sticker.z * az;

            /*
             * Only stickers on the outer layer
             * are rotated.
             */
            if (positionOnAxis == 1) {

                int[] newPosition =
                        rotateVector(
                                sticker.x,
                                sticker.y,
                                sticker.z,
                                ax, ay, az,
                                inverse
                        );

                int[] newNormal =
                        rotateVector(
                                sticker.nx,
                                sticker.ny,
                                sticker.nz,
                                ax, ay, az,
                                inverse
                        );

                sticker.x = newPosition[0];
                sticker.y = newPosition[1];
                sticker.z = newPosition[2];

                sticker.nx = newNormal[0];
                sticker.ny = newNormal[1];
                sticker.nz = newNormal[2];
            }
        }
    }

    // =========================================================
    // ROTATE VECTOR 90 DEGREES
    // =========================================================

    private int[] rotateVector(
            int x,
            int y,
            int z,
            int ax,
            int ay,
            int az,
            boolean inverse
    ) {

        /*
         * Cross product:
         *
         * A × B
         */
        int crossX =
                ay * z - az * y;

        int crossY =
                az * x - ax * z;

        int crossZ =
                ax * y - ay * x;

        int dot =
                ax * x
                + ay * y
                + az * z;

        int sign =
                inverse ? 1 : -1;

        int newX =
                sign * crossX
                + ax * dot;

        int newY =
                sign * crossY
                + ay * dot;

        int newZ =
                sign * crossZ
                + az * dot;

        return new int[] {
                newX,
                newY,
                newZ
        };
    }

    // =========================================================
    // GET FACE COLORS
    // =========================================================

    public char[][] getFaceColors(Face face) {

        char[][] result =
                new char[3][3];

        int[] normal =
                getNormal(face);

        int[] right =
                getRightVector(face);

        int[] down =
                getDownVector(face);

        for (Sticker sticker : stickers) {

            if (
                    sticker.nx == normal[0]
                    && sticker.ny == normal[1]
                    && sticker.nz == normal[2]
            ) {

                int relativeX =
                        sticker.x - normal[0];

                int relativeY =
                        sticker.y - normal[1];

                int relativeZ =
                        sticker.z - normal[2];

                int col =
                        relativeX * right[0]
                        + relativeY * right[1]
                        + relativeZ * right[2];

                int row =
                        relativeX * down[0]
                        + relativeY * down[1]
                        + relativeZ * down[2];

                result[row + 1][col + 1] =
                        sticker.color;
            }
        }

        return result;
    }

    // =========================================================
    // FACE DIRECTIONS
    // =========================================================

    private int[] getNormal(Face face) {

        switch (face) {

            case U:
                return new int[] {0, 1, 0};

            case D:
                return new int[] {0, -1, 0};

            case F:
                return new int[] {0, 0, 1};

            case B:
                return new int[] {0, 0, -1};

            case L:
                return new int[] {-1, 0, 0};

            case R:
                return new int[] {1, 0, 0};

            default:
                return new int[] {0, 0, 0};
        }
    }

    private int[] getRightVector(Face face) {

        switch (face) {

            case U:
                return new int[] {1, 0, 0};

            case D:
                return new int[] {1, 0, 0};

            case F:
                return new int[] {1, 0, 0};

            case B:
                return new int[] {-1, 0, 0};

            case L:
                return new int[] {0, 0, 1};

            case R:
                return new int[] {0, 0, -1};

            default:
                return new int[] {1, 0, 0};
        }
    }

    private int[] getDownVector(Face face) {

        switch (face) {

            case U:
                return new int[] {0, 0, 1};

            case D:
                return new int[] {0, 0, -1};

            case F:
                return new int[] {0, -1, 0};

            case B:
                return new int[] {0, -1, 0};

            case L:
                return new int[] {0, -1, 0};

            case R:
                return new int[] {0, -1, 0};

            default:
                return new int[] {0, -1, 0};
        }
    }

    // =========================================================
    // CHECK IF SOLVED
    // =========================================================

    public boolean isSolved() {

        for (Face face : Face.values()) {

            char[][] colors =
                    getFaceColors(face);

            char center =
                    colors[1][1];

            for (int row = 0; row < 3; row++) {

                for (int col = 0; col < 3; col++) {

                    if (
                            colors[row][col]
                            != center
                    ) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}