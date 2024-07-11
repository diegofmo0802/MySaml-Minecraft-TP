package com.mysaml.mc.tp;

import com.mysaml.mc.api.MySamlAddon;

public class Main extends MySamlAddon {
    private static Main instance;
    public static Main getInstance() { return instance; }
    public void onEnabled() {
        Main.instance = this;
        this.addCommand("tp", new TPCommand());
        this.addCommand("tpwp", new TPWPCommand());
    }
    public void onDisabled() {}
}
