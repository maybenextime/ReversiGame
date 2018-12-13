public interface  CoordValue {
    class MoveScore implements Comparable<CoordValue.MoveScore> {
        private CoordValue.Coord move;
        private int score;

        MoveScore(CoordValue.Coord move, int score) {
            this.move = move;
            this.score = score;
        }

        int getScore() {
            return this.score;
        }

        CoordValue.Coord getMove() {
            return this.move;
        }

        public int compareTo(CoordValue.MoveScore o) {
            if (o.score > this.score) {
                return 1;
            } else {
                return o.score < this.score ? -1 : 0;
            }
        }


    }
    class Coord {
        private int row;
        private int col;

        Coord(int row, int col) {
            this.row = row;
            this.col = col;
        }

        int getRow() {
            return this.row;
        }

        int getCol() {
            return this.col;
        }

    }

}
