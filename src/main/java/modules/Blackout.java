package modules;

import java.util.Objects;

public class Blackout {
    private int id;
    private boolean lights;

    public Blackout(boolean lights) {
        this.lights = lights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blackout blackout = (Blackout) o;
        return id == blackout.id && lights == blackout.lights;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lights);
    }
}