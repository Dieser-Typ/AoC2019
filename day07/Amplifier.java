package day07;

import intcode.IntCodeComputer;

class Amplifier extends IntCodeComputer {
    private int phaseSetting;
    private boolean settingSet;

    Amplifier(int phaseSetting, Solution s)  {
        super("inputs/day07.txt", s, s);
        this.phaseSetting = phaseSetting;
        this.settingSet = false;
    }

    int setSetting() {
       settingSet = true;
       return phaseSetting;
    }

    boolean isSettingSet() {
        return settingSet;
    }

}
