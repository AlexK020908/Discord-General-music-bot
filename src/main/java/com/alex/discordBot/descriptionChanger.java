package com.alex.discordBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
;

import java.util.TimerTask;

public class descriptionChanger extends TimerTask {
    private JDA jda = bot.getJda();
    private String[] descriptions = {"Youtube", "Spider man", "Thanos"};
    private int count;
    @Override
    public void run() {
        if (jda != null) {
            jda.getPresence().setActivity(Activity.watching(descriptions[count]));
            count = (count + 1) % descriptions.length;

            //1 % 3 = 1
            // 2 % 3 = 2;
            // 3 % 3 = 0; --> this means when it hits 3 it gets set back to zero
        }



    }
}
