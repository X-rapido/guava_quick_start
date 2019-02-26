package com.example.guava.basic;

import com.google.common.collect.DiscreteDomain;

public class LowerCaseDomain extends DiscreteDomain<Character> {
    private static LowerCaseDomain domain = new LowerCaseDomain();

    public static DiscreteDomain letters() {
        return domain;
    }

    @Override
    public Character next(Character value) {
        return (char) (value + 1);
    }

    @Override
    public Character previous(Character value) {
        return (char) (value - 1);
    }

    @Override
    public long distance(Character start, Character end) {
        return end - start;
    }

    @Override
    public Character minValue() {
        return 'a';
    }

    @Override
    public Character maxValue() {
        return 'z';
    }
}
