public class ForLoop3 {
  public static void main(String[] args)
  {
    for (int i = 41; i >= 0; i /= 3)
    {
      System.out.printf("%d ", i);
      if (i == 0)
      {
        i = i - 3;
      }
    }
  }
}
