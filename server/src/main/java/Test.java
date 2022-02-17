public class Test {

  public static void main(String[] args) {
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/days\\/[0-9]+\\/skiers\\/[0-9]+";
    String input = "/9/seasons/5/days/10/skiers/a";
    boolean matches = input.matches(regex);
    System.out.println(matches);
  }
}
