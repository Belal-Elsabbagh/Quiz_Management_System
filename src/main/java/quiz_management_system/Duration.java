package quiz_management_system;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author marma
 */
public class Duration extends TimerTask {

    static long hr, min, sec, totalSec;


    public void convertTime() {
        Quiz q = new Quiz();
        totalSec = q.getTotalSec();
        
        min = TimeUnit.SECONDS.toMinutes(totalSec);
        sec = totalSec - (min * 60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr * 60);
        //System.out.println(hr + ":" + min + ":" + sec);
        totalSec--;
        if (totalSec < 0){
        System.out.println("----------Time's up!!----------");
        System.exit(0);
    }
    }

    @Override
    public void run() {
        convertTime();
        Timer timer = new Timer();
        TimerTask task = new Duration();
        timer.schedule(task, 1000 );
    }
}

