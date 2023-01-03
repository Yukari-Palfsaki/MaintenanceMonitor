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
  /**
   *
   * @param model to add the attribute to html file
   * @return the name of html file under resource/template/
   * @throws FileNotFoundException
   *
   * This function returns the message which is saved in api_stored.text (deliver the message to a client)
   * If there is no message saved yet, it returns "Everything works as expected"
   * If the saved message contains "problem with Web-Server Maintenance", this function returns the red maintenance screen.
   * Last update will be shown also through showUpdateTime(Model model)
   */
  @GetMapping("/api/message")
  String setMsg (Model model) throws FileNotFoundException {
    File f = new File ("api_stored.text");
    String showString = null;
    if(f.exists()){
      Scanner sc = new Scanner(f);
      if(!sc.hasNextLine()) {
        model.addAttribute("outputText", "Everything works as expected");
        showUpdateTime(model);

      } else {
        while (sc.hasNextLine()){
          showString = sc.nextLine();
        }
        sc.close();
        model.addAttribute("outputText", showString);
        showUpdateTime(model);
      }
    }
    else {
      model.addAttribute("outputText", "Everything works as expected");
      showUpdateTime(model);
    }
    if (showString.contains("problem with Web-Server Maintenance")){
      return "redMaintenanceMonitor";
    } else {
      return "greenMaintenanceMonitor";
    }
  }

  /**
   *
   * @param m Request param.  Which will be given to the browser
   * @param model to add the attribute to html file
   * @return the name of html file under resource/template/
   * @throws FileNotFoundException
   *
   * This functions is to save the client message to api_stored.text (to set it to a specific message).
   * Normal message will be returned with the green monitor,
   * Problem message will be returned with the red monitor.
   *
   */
  @GetMapping("api/message/set")
  String setMsg (@RequestParam String m, Model model) throws FileNotFoundException {
    // to set up the time string
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String updatedTime = null;

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
        String savedMsg = m.replace("+", " ");
        fw.write(savedMsg);
        fw_date.write ("last update: " + updatedTime);

        fw.close();
        fw_date.close();

      } catch (IOException e){
        e.printStackTrace();
      }

    } else {
      model.addAttribute("outputText", "Everything works as expected");
      showUpdateTime(model);

    }
    return "greenMaintenanceMonitor";
  }

  /**
   *
   * @param model
   * @return the name of html file under resource/template/
   * @throws IOException
   * This function is to delete the saved message in api_update.text (reset the message)
   * The update time will be shown also through showUpdateTime(Model model)
   */
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
   * @param model to add the attribute to html file
   * @throws FileNotFoundException
   * This function is to show the last updated time (if it is not updated, will return the message "Not updated yet").
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
