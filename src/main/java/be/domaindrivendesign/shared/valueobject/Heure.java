package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;


public class Heure extends ValueObject {

    protected int minutes;

    protected Heure() {
    }

    public Heure(int heure, int minute) {
        this.minutes = (heure * 60) + minute;
    }

    public Heure(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
