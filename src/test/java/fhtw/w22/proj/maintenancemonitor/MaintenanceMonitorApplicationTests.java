package fhtw.w22.proj.maintenancemonitor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
class MaintenanceMonitorApplicationTests {
  File file_data = new File ("api_stored.text");
  File file_time = new File ("api_update.text");
  Model model = new Model() {
    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {
      return null;
    }

    @Override
    public Model addAttribute(Object attributeValue) {
      return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {
      return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {
      return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {
      return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {
      return false;
    }

    @Override
    public Object getAttribute(String attributeName) {
      return null;
    }

    @Override
    public Map<String, Object> asMap() {
      return null;
    }
  };

  /**
   * @brief to test the return value of the function "setMsg"
   *         check "redMaintenanceMonitor" or "greenMaintenanceMonitor"
   * @throws FileNotFoundException
   */
  @Test
  void returnFileTest_setMsg() throws FileNotFoundException {
    var controller = new MaintenanceMonitorController();
    var result = controller.setMsg(model);
    if(model.toString().contains("problem with Web-Server Maintenance")){
      assertEquals("redMaintenanceMonitor", result);
    } else {
      assertEquals("greenMaintenanceMonitor", result);
    }
  }

  /**
   * @brief The time shown in "api_update.text" should not equal to the current time, but the last updated time.
   * @throws FileNotFoundException
   */
  @Test
  void showUpdateTimeTest () throws FileNotFoundException {
    String timeString = "";
    String timeString_now = "";
    // get the timeStamp now
    // get the timeStamp now
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    timeString_now = dtf.format(now);
    // Read from file
    if (file_time.exists()){
      Scanner scanner = new Scanner(file_time);
      while (scanner.hasNextLine()) {
        timeString = scanner.nextLine();
      }
      scanner.close();
      assertNotEquals(timeString_now, timeString);
    }
    //if the "api_update.text" does not exist, just OK for the file not existing
    else {
      assertFalse (file_time.exists());
    }
  }
  /**
   * @brief To test the rest function. With the reset function, there should be no file of "api_stored.text"
   * @throws IOException
   */
  @Test
  void resetMsgTest () throws IOException {
    var controller = new MaintenanceMonitorController();
    var result = controller.resetMsg(model);

    assertFalse(file_data.exists());
    assertEquals("greenMaintenanceMonitor", result);
  }
}


