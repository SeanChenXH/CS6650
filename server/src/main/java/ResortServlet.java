import com.google.gson.Gson;
import models.*;
import util.Util;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "ResortServlet", value = "/ResortServlet")
public class ResortServlet extends HttpServlet {

  private Util util;

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
    if (isValidGetResortsUrl(urlPath)) {
      util.response200(out, response, buildGetResortsResponse());
    } else if (isValidGetNumberOfSkiersUrl(urlPath)) {
      util.response200(out, response, buildGetNumberOfSkiersResponse());
    } else if (isValidGetSeasonsByResortUrl(urlPath)) {
      util.response200(out, response, buildGetSeasonsByResortResponse());
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
    if (isValidPostSeasonUrl(urlPath) && isValidPostSeasonParameters(request)) {
      util.response201(out, response);
    } else {
      util.response400(out, response);
    }
  }

  // handle urlPath = "/resorts"
  private boolean isValidGetResortsUrl(String urlPath) {
    return urlPath == null || urlPath.isEmpty();
  }

  // handle urlPath = "/resorts/{resortID}/seasons/{seasonID}/day/{dayID}/skiers"
  private boolean isValidGetNumberOfSkiersUrl(String urlPath) {
    String regex = "\\/[0-9]+\\/seasons\\/[0-9]+\\/day\\/[0-9]+\\/skiers";
    return urlPath.matches(regex);
  }

  // handle urlPath = /resorts/{resortID}/seasons
  private boolean isValidGetSeasonsByResortUrl(String urlPath) {
    String regex = "\\/[0-9]+\\/seasons";
    return urlPath.matches(regex);
  }

  // handle urlPath = /resorts/{resortID}/seasons/
  private boolean isValidPostSeasonUrl(String urlPath) {
    String regex = "\\/[0-9]+\\/seasons";
    return urlPath.matches(regex);
  }

  private boolean isValidPostSeasonParameters(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    String s = null;
    try {
      while ((s = request.getReader().readLine()) != null) {
        sb.append(s);
      }
      Season season = new Gson().fromJson(sb.toString(), Season.class);
      if (season.getSeason() == null || season.getSeason().length() != 4) {
        return false;
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  private String buildGetResortsResponse() {
    Resort resort0 = new Resort(1, "whistler");
    Resort resort1 = new Resort(2, "Vail");
    ResortsList resorts = new ResortsList(new ArrayList<>());
    resorts.getResorts().add(resort0);
    resorts.getResorts().add(resort1);
    String content = new Gson().toJson(resorts);
    return content;
  }

  private String buildGetNumberOfSkiersResponse() {
    ResortSkiers resortSkiers0 = new ResortSkiers("Mission Ridge", 78999);
    String content = new Gson().toJson(resortSkiers0);
    return content;
  }

  private String buildGetSeasonsByResortResponse() {
    SeasonsList seasonsList0 = new SeasonsList(new ArrayList<>());
    seasonsList0.getSeasons().add("2018");
    seasonsList0.getSeasons().add("2019");
    String content = new Gson().toJson(seasonsList0);
    return content;
  }
}
