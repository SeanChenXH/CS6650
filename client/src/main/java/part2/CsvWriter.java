package part2;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import part1.Counter;
import part1.Parameters;

public class CsvWriter {

  public void writeCsvRecord(Parameters parameters, Record record) {
    try {
      CSVWriter csvWriter = null;
      csvWriter = new CSVWriter(new FileWriter("stats/csv_threads_" + parameters.getNumThreads() + ".csv"));
      csvWriter.writeAll(record.getRecordList());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
