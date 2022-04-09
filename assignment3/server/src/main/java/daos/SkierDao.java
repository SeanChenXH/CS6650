package daos;

import java.util.ArrayList;
import models.SkierVertical;
import models.SkierVerticals;

/**
 * The SkierDao is to get skier Data from database or other persistence mechanism
 */
public class SkierDao {

  /**
   * Select verticals by day int.
   *
   * @return the int
   */
  public int selectVerticalsByDay() {
    return 1000000;
  }

  /**
   * Select verticals by resort id skier verticals.
   *
   * @param resortId the resort id
   * @return the skier verticals
   */
  public SkierVerticals selectVerticalsByResortId(int resortId) {
    if (resortId == 100) {
      SkierVerticals skierVerticals = new SkierVerticals(new ArrayList<>());
      SkierVertical skierVertical0 = new SkierVertical("2017", 12345677);
      SkierVertical skierVertical1 = new SkierVertical("2018", 787888);
      skierVerticals.getVerticals().add(skierVertical0);
      skierVerticals.getVerticals().add(skierVertical1);
      return skierVerticals;
    }
    return null;
  }
}
