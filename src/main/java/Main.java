public class Main {
    public static void main(String[] args) {
        ScoreBoard scoreBoard  = new ScoreBoard();
        scoreBoard.startAMatch("new york", "barcelona");
        scoreBoard.startAMatch("iev", "samara");
        scoreBoard.startAMatch("ccc", "samara");//exc
        scoreBoard.startAMatch("kaev", "ddd");
        scoreBoard.startAMatch("baku", "tbilisi");
        scoreBoard.startAMatch("anagano", "kharkiv");

        scoreBoard.updateScore("iEv", 1, 0);
        scoreBoard.updateScore("kaEv", 1, 0);
        scoreBoard.updateScore("kaev", 2, 0);
        scoreBoard.updateScore("anagano", 1, 0);
        scoreBoard.updateScore("kharkiv", -1, 1);
        scoreBoard.updateScore("new york", 0, 1);
        scoreBoard.updateScore("new york", 0, 2);
        scoreBoard.updateScore("baku", 1, 0);

        scoreBoard.finishMatch("kiev");
        scoreBoard.finishMatch("tBilisi");
        scoreBoard.updateScore("iEv", 2, 0);


        scoreBoard.finishMatch("kkk");

        scoreBoard.getSummary();

    }
}
