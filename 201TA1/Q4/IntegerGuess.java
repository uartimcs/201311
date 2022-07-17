import javax.swing.JOptionPane;
public class IntegerGuess {
  private int secretInteger;
  private int guessCount = 0;

  // increase the count by 1
    public void increaseCount()
    {
      guessCount = guessCount + 1;
    }
   // Counter value Getter
    public int getCount()
    {
      return guessCount;
    }


  // Secret Integer Setter
    public void setIntegerGuess(int setInteger)
    {
      secretInteger = setInteger;
    }
    // Secret Integer Getter
    public int getIntergerGuess()
    {
      return secretInteger;
    }



// Add count and analysis of result
  public int oneGuess(int anInteger) {
    increaseCount();
    int temp;
    if (secretInteger == anInteger)
    {
      temp = 0;
    }
    else if (secretInteger > anInteger)
    {
      temp = 1;
    }
    else
    {
      temp = -1; //Mutually exclusive =, > and <
    }
      return temp;
  }

  // main play method of the game
  public void play()
  {
      int gameFlag = 1; //Set Original gameFlag = 1, no game = 0
      while (gameFlag != 0)
      {
        String guessInput = JOptionPane.showInputDialog("Guess a secret integer in the range of 1 to 9 inclusively (-1 to terminate) ");
        int inputValue = Integer.parseInt(guessInput);
        if (inputValue == -1)
        {
          System.exit(0);
        }
        else
        {
          gameFlag = oneGuess(inputValue);
          if (gameFlag == 0)
          {
            JOptionPane.showMessageDialog(null, "Congratulations! You are correct. Number of guesses = " + getCount() + ". Bye.");
          }
          else if (gameFlag == -1)
          {
            JOptionPane.showMessageDialog(null, "The secret number is smaller than your guessed number. Please retry.");
          }
          else if (gameFlag == 1)
          {
            JOptionPane.showMessageDialog(null, "The secret number is larger than your guessed number. Please retry.");
          }
          else
          {
            JOptionPane.showMessageDialog(null, "Error");
          }
        }
      }
        System.exit(0);
    }
}
