package org.nessus.controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NameGenerator {
    private static final ConcurrentHashMap<String, AtomicInteger> nameCounters = new ConcurrentHashMap<>();

    private NameGenerator() {}

    public static String GenerateName(String prefix) {
        nameCounters.putIfAbsent(prefix, new AtomicInteger(0));
        int id = nameCounters.get(prefix).incrementAndGet();

        return prefix + id;
    }

    public static void AddName(String name) {
        String prefix = name.replaceAll("\\d+", "");
        nameCounters.putIfAbsent(prefix, new AtomicInteger(0));
        int id = Integer.parseInt(name.replaceAll("\\D+", ""));
        nameCounters.get(prefix).set(Math.max(nameCounters.get(prefix).get(), id));
    }
}
