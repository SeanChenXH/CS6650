package util;

import com.google.gson.Gson;
import models.ResponseMsg;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Util {

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void response200(PrintWriter out, HttpServletResponse response, String content) {
        response.setStatus(HttpServletResponse.SC_OK);
        out.print(content);
        out.flush();
        out.close();
    }

    public void response201(PrintWriter out, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        ResponseMsg responseMsg = new ResponseMsg("Write successful");
        out.print(new Gson().toJson(responseMsg));
        out.flush();
        out.close();
    }

    public void response400(PrintWriter out, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        ResponseMsg responseMsg = new ResponseMsg("Invalid inputs supplied");
        out.print(new Gson().toJson(responseMsg));
        out.flush();
        out.close();
    }

}
