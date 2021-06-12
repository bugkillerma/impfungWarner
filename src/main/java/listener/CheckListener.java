package listener;

import checker.AvailableChecker;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import warner.Warner;

public class CheckListener extends TimerTask {

    @Override
    public void run() {
        AvailableChecker checker = new AvailableChecker();
        String checkResult = null;
        try {
            checkResult = checker.sendGET();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (checkResult.contains("outOfStock\":false,")){
            System.out.println("Hurry Up! There are some available Impfen Dose!");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            Warner warner = new Warner();
            warner.play();
        }
    }

    public static void main(String args[]){
        //running timer task as daemon thread
        Timer timer = new Timer();
        timer.schedule(new CheckListener(), 0, 9000);
        System.out.println("TimerTask started");
    }

}
