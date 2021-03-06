package controllers;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import models.ResponseMsg;
import org.apache.commons.pool2.impl.GenericObjectPool;
import services.RabbitMqAsynchronousServiceImpl;
import services.SkierService;
import util.RabbitMQChannelFactory;
import util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * The SkierServlet is to create skiers APIs, including writing new lift ride API, getting the total
 * vertical for the skier for the specified ski day API, and getting the total vertical for the
 * skier for specified seasons at the specified API
 */
@WebServlet(name = "controllers.SkierServlet", value = "/controllers.SkierServlet")
public class SkierServlet extends HttpServlet {

  private static final String INVALID_URL_MSG = "invalid url";
  private SkierService skierService;
  private static final int DEFAULT_MIN_IDLE = 5;
  private static final int DEFAULT_MAX_IDLE = 20;
  private GenericObjectPool<Channel> channelFactory;

  @Override
  public void init() throws ServletException {
    super.init();
    this.channelFactory = new GenericObjectPool<>(new RabbitMQChannelFactory());
//    this.channelFactory.setMaxIdle(DEFAULT_MAX_IDLE);
//    this.channelFactory.setMinIdle(DEFAULT_MIN_IDLE);
//    this.channelFactory.setMaxTotal(DEFAULT_MAX_IDLE);
    this.skierService = new SkierService(new RabbitMqAsynchronousServiceImpl(this.channelFactory));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (this.skierService.isValidPostLiftRideUrl(request)) {
      // handle urlPath = /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID} post
      this.skierService.handleLiftRidePostRequest(request, response);
      return;
    }
    Util.response400(response, new Gson().toJson(new ResponseMsg(INVALID_URL_MSG)));
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // handle urlPath = /skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID} get
    if (this.skierService.isValidGetVerticalByDayUrl(request)) {
      this.skierService.handleVerticalByDayGetRequest(request, response);
      return;
    }

    // handle urlPath = /skiers/{skierID}/vertical get
    if (this.skierService.isValidGetVerticalByResortUrl(request)) {
      this.skierService.handleVerticalByResortGetRequest(request, response);
      return;
    }
    Util.response400(response, new Gson().toJson(new ResponseMsg(INVALID_URL_MSG)));
  }
}
