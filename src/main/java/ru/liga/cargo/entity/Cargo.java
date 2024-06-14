package ru.liga.cargo.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
public class Cargo {
    private final Character title;
    private final List<List<Character>> size;

    public Cargo(Character title, List<List<Character>> size) {
        log.trace("initialization");
        log.debug("new Cargo(title: {}, size: {})", title, size);
        this.title = title;
        this.size = size;
    }

    public int getWidth() {
        return size.get(0).size();
    }

    public List<List<Character>> getSize() {
        log.trace("getSize: {}", size);
        return size;
    }

    public Character getVolume() {
        log.trace("getVolume: {}", title);
        return title;
    }

    public int getHeight() {
        return size.size();
    }

    @Override
    public String toString() {
        return size.toString();
    }
}
