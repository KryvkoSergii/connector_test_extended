package connectornew;

import java.util.logging.Level;

/**
 * Created by srg on 15.09.16.
 */
public class Logger {
    private String name;
    private Level level;

    public Logger() {
    }

    public Logger(String name) {
        this.name = name;
        this.level = Level.ALL;
    }

    public Logger(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void log(Level level, String message) {
        if (level.intValue() >= this.level.intValue())
            System.out.println(name.concat(" ").concat(message).concat("\n"));
    }
}
