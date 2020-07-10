package ch.dams333.taupeGun.tasks;

import ch.dams333.taupeGun.TaupeGun;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderTask extends BukkitRunnable {
    TaupeGun main;
    public BorderTask(TaupeGun taupeGun) {
        this.main = taupeGun;
        border = main.preBorder;
        blockPerSec = (main.preBorder - main.postBorder)/main.reductionTime;
    }

    int border;
    int blockPerSec;

    @Override
    public void run() {
        Bukkit.getWorld("world").getWorldBorder().setSize((border - blockPerSec)*2);
        border = (int) (Bukkit.getWorld("world").getWorldBorder().getSize()/2);
    }
}
