package com.flx.ark.java8.optional;

import java.util.Optional;

public class Man {

    private Optional<Goddess> goddess = Optional.empty();

    public Man() {
    }

    public Man(Optional<Goddess> goddess) {
        this.goddess = goddess;
    }

    public Optional<Goddess> getGoddess() {
        return goddess;
    }

    public void setGoddess(Optional<Goddess> goddess) {
        this.goddess = goddess;
    }

    @Override
    public String toString() {
        return "Man{" +
                "goddess=" + goddess +
                '}';
    }
}
