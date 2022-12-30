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
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    File f = new File ("api_stored.text");
    if(f.exists()){
      Scanner sc = new Scanner(f);
      if(!sc.hasNextLine()) {
        model.addAttribute("outputText", "Everything works as expected");
        model.addAttribute("updateTime", "last update: " + dtf.format(now));

      } else {
        while (sc.hasNextLine()){
          model.addAttribute("outputText", sc.nextLine());
        }
        sc.close();
      }
    }
    else {
      model.addAttribute("outputText", "Everything works as expected");
      model.addAttribute("updateTime", "last update: " + dtf.format(now));
    }
    return "greenMaintenanceMonitor";
  }
  @GetMapping("api/message/set")
  String setMsg (@RequestParam String m, Model model){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    // if the RequestParam is not empty, add the text in the api_stored.text
    if (!m.equals("")) {
      model.addAttribute ("outputText","OK");
      model.addAttribute("updateTime", "last update: " + dtf.format(now));
      try {

        FileWriter fw = new FileWriter ("api_stored.text");

        fw.write(m + " (last update: " + dtf.format(now) + ")");
        fw.close();

      } catch (IOException e){
        e.printStackTrace();
        return "redMaintenanceMonitor";
      }

    } else {
      model.addAttribute("outputText", "Everything works as expected");
      model.addAttribute("updateTime", "last update: " + dtf.format(now));

    }
    return "greenMaintenanceMonitor";
  }
  @GetMapping("api/message/reset")
  String resetMsg(Model model){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    File f= new File ("api_stored.text");
    if (f.exists()){
      f.delete();
    }
    model.addAttribute ("outputText", "OK");
    model.addAttribute("updateTime", "last update: " + dtf.format(now));

    return "greenMaintenanceMonitor";
  }
}
