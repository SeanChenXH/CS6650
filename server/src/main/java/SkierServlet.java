import com.google.gson.Gson;
import java.util.ArrayList;
import models.LiftRide;
import models.SkierVertical;
import models.SkierVerticals;
import util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {

  private Util util;
  private int cnt = 0;

  @Override
  public void init() throws ServletException {
    super.init();
    util = new Util();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String urlPath = request.getPathInfo();
    if (isValidGetVerticalByDayUrl(urlPath)) {
      util.response200(out, response, "34507");
    } else if (isValidGetVerticalByResortUrl(urlPath) && isValidGetVerticalByResortParas(request)) {
      util.response200(out, response, buildVerticalByResortResponse());
    } else {
      util.response400(out, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String urlPath = request.getPathInfo();
    if (isValidPostLiftRideUrl(urlPath) && isValidPostLiftRideBody(request)) {
      util.response201(out, response);
//      System.out.println("write");
//      cnt++;
//      System.out.println(cnt);
    } else {
      util.response400(out, response);
    }
  }

  // handle urlPath = /{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
  private boolean isValidGetVerticalByDayUrl(String urlPath) {
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/days\\/[0-9]+\\/skiers\\/[0-9]+";
    return urlPath.matches(regex);
  }

  // handle urlPath = /{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}
  private boolean isValidPostLiftRideUrl(String urlPath) {
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/days\\/[0-9]+\\/skiers\\/[0-9]+";
    return urlPath.matches(regex);
  }

  // handle urlPath = /{skierID}/vertical
  private boolean isValidGetVerticalByResortUrl(String urlPath) {
    String regex = "\\/[0-9]+\\/vertical";
    return urlPath.matches(regex);
  }

  // handle urlPath = /{skierID}/vertical to validate parameters
  private boolean isValidGetVerticalByResortParas(HttpServletRequest request) {
    String resort = request.getParameter("resort");
    if (resort == null) {
      return false;
    }
    return true;
  }

  private boolean isValidPostLiftRideBody(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    String s = null;
    try {
      while ((s = request.getReader().readLine()) != null) {
        sb.append(s);
      }
      if (sb.toString() == null) {
        return false;
      }
      LiftRide liftRide = new Gson().fromJson(sb.toString(), LiftRide.class);
      if (liftRide == null || liftRide.getTime() == null || liftRide.getLiftID() == null
          || liftRide.getWaitTime() == null) {
        return false;
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  private String buildVerticalByResortResponse() {
    SkierVertical skierVertical0 = new SkierVertical("2017", 12345677);
    SkierVertical skierVertical1 = new SkierVertical("2018", 787888);
    SkierVerticals skierVerticals = new SkierVerticals(new ArrayList<>());
    skierVerticals.getVerticals().add(skierVertical0);
    skierVerticals.getVerticals().add(skierVertical1);
    String content = new Gson().toJson(skierVerticals);
    return content;
  }
}
