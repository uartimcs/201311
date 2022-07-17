public class TestIntegerGuess {
  public static void main(String[] args)
  {
    int secretInteger =  1 + (int) Math.random() * 9;

    IntegerGuess guessGame = new IntegerGuess();
    guessGame.setIntegerGuess(secretInteger);
    guessGame.play();
  }
}
