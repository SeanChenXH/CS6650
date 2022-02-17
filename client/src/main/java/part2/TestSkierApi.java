package part2;

import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

public class TestSkierApi extends SkiersApi {

  private Record record;

  public TestSkierApi(Record record) {
    this.record = record;
  }

  @Override
  public ApiResponse<Void> writeNewLiftRideWithHttpInfo(LiftRide body, Integer resortID,
      String seasonID, String dayID, Integer skierID) throws ApiException {
    long startTime = System.currentTimeMillis();
    ApiResponse<Void> voidApiResponse = super.writeNewLiftRideWithHttpInfo(body, resortID, seasonID,
        dayID, skierID);
    long endTime = System.currentTimeMillis();
    int statusCode = voidApiResponse.getStatusCode();
    long latency = endTime - startTime;
    this.record.getLatencyList().add(latency);
    String[] record = new String[]{String.valueOf(startTime), "POST", String.valueOf(latency),
        String.valueOf(statusCode)};
    this.record.getRecordList().add(record);
    return voidApiResponse;
  }

}

