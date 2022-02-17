import com.google.gson.Gson;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import models.API;
import models.APIStats;
import util.Util;

@WebServlet(name = "StatisticsServlet", value = "/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {

  private Util util;

  @Override
  public void init() throws ServletException {
    super.init();
    util = new Util();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    util.response200(out, response, buildApiStatisticsResponse());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }

  private String buildApiStatisticsResponse() {
    API api0 = new API("/resorts", "GET", 22, 199);
    API api1 = new API("/resorts", "POST", 12, 89);
    APIStats apiStats = new APIStats(new ArrayList<>());
    apiStats.getEndpointStats().add(api0);
    apiStats.getEndpointStats().add(api1);
    String content = new Gson().toJson(apiStats);
    return content;
  }
}
