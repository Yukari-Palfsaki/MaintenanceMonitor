package fhtw.w22.proj.maintenancemonitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class MaintenanceMonitorController {
  @GetMapping("/api/message")
  String setMsg (Model model) throws FileNotFoundException {
    File f = new File ("api_stored.text");
    if(f.exists()){
      Scanner sc = new Scanner(f);
      if(!sc.hasNextLine()) {
        model.addAttribute("outputText", "Everything works as expected");
        showUpdateTime(model);

      } else {
        while (sc.hasNextLine()){
          model.addAttribute("outputText", sc.nextLine());
        }
        sc.close();
        showUpdateTime(model);
      }
    }
    else {
      model.addAttribute("outputText", "Everything works as expected");
      showUpdateTime(model);
    }
    return "greenMaintenanceMonitor";
  }
  @GetMapping("api/message/set")
  String setMsg (@RequestParam String m, Model model) throws FileNotFoundException {
    // to set up the time string
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String updatedTime = "";

    // if the RequestParam is not empty, add the text in the api_stored.text
    if (!m.equals("")) {
      model.addAttribute ("outputText","OK");

      // the updated time should be shown in the monitor
      updatedTime = dtf.format(now);
      model.addAttribute("updateTime", "last update: " + updatedTime);

      try {
        // as we have no data bank, the message data and the update time data is saved in separate files.
        FileWriter fw = new FileWriter ("api_stored.text");
        FileWriter fw_date = new FileWriter("api_update.text");

        fw.write(m);
        fw_date.write ("last update: " + updatedTime);

        fw.close();
        fw_date.close();

      } catch (IOException e){
        e.printStackTrace();
        return "redMaintenanceMonitor";
      }

    } else {
      model.addAttribute("outputText", "Everything works as expected");
      showUpdateTime(model);

    }
    return "greenMaintenanceMonitor";
  }
  @GetMapping("api/message/reset")
  String resetMsg(Model model) throws IOException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String updatedTime = "";

    File f= new File ("api_stored.text");
    if (f.exists()){
      updatedTime = dtf.format(now);
      model.addAttribute("updateTime", "last update: " + updatedTime);
      FileWriter fw_date = new FileWriter("api_update.text");
      fw_date.write ("last update: " + updatedTime);
      fw_date.close();

      f.delete();
    }
    model.addAttribute ("outputText", "OK");

    showUpdateTime(model);

    return "greenMaintenanceMonitor";
  }

  /**
   * @param model
   * @throws FileNotFoundException
   * This function is to show the last updated time (if it is not updated, will return the message "Not updated yet".
   * Last updated time is saved in the file "api_update.text"
   */
  void showUpdateTime (Model model) throws FileNotFoundException {
    File data_time = new File("api_update.text");
    if (!data_time.exists()) {
      model.addAttribute("updateTime", "Not updated yet");
    } else {
      Scanner sc_time = new Scanner(data_time);
      while (sc_time.hasNextLine()) {
        model.addAttribute("updateTime", sc_time.nextLine());
      }
      sc_time.close();
    }
  }
}
