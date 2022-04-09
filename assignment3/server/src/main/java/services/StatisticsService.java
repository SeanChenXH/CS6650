package services;

import com.google.gson.Gson;
import daos.StatisticsDao;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.APIStats;
import util.Util;

/**
 * The StatisticsService is a service for the logic of handling statistics APIs, including URL
 * validation, RequestBody parsing, data calculating, and data retrieving from StatisticsDao
 */
public class StatisticsService {

  private StatisticsDao statisticsDao = new StatisticsDao();

  /**
   * Handle statistics get request.
   *
   * @param request  the request
   * @param response the response
   */
  public void handleStatisticsGetRequest(HttpServletRequest request, HttpServletResponse response) {
    APIStats apiStats = statisticsDao.selectAllStatistics();
    Util.response200(response, new Gson().toJson(apiStats));
  }

}
