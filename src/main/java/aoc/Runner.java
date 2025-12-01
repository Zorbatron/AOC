package aoc;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.time.Year;

public final class Runner {

    private static Year year = Year.parse("2025");
    private static byte day = 1;

    public static void main(String[] args) {
        String name = String.format("aoc.y%s.Day%d", year, day);

        Class<?> tryClazz;
        try {
            tryClazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Couldn't find class %s", name), e);
        }

        if (!AOCDay.class.isAssignableFrom(tryClazz)) {
            throw new ClassCastException(String.format("Class %s doesn't implement AOCDay!", tryClazz));
        }

        Class<? extends AOCDay> clazz = tryClazz.asSubclass(AOCDay.class);
        AOCDay aocDay;
        try {
            aocDay = clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Class %s didn't have a no-arg constructor!", clazz), e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("what", e);
        }

        String inputPath = String.format("inputs/%s/%d.txt", year, day);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath, StandardCharsets.UTF_8))) {
            aocDay.run(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("No input file at %s was found!", inputPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
