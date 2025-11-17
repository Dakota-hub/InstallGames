import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Install {
    public static void main(String[] args) {
        String baseDirPath = "X:\\Games";

        File gamesDir = new File(baseDirPath);

        StringBuilder log = new StringBuilder();
        log.append("Установка игры...\n");
        log.append("Базовая директория: ").append(gamesDir.getAbsolutePath()).append("\n\n");

        boolean gamesCreated = gamesDir.exists() || gamesDir.mkdir();
        log.append("[Games] ").append(gamesCreated ? "создано" : "уже существует").append("\n");

        String[] dirsToCreate = {
                "src",
                "res",
                "savegames",
                "temp",
                "src/main",
                "src/test",
                "res/drawables",
                "res/vectors",
                "res/icons"
        };

        for (String dirPath : dirsToCreate) {
            File dir = new File(gamesDir, dirPath);
            boolean created = dir.exists() || dir.mkdir();
            log.append("[").append(dirPath).append("] ")
                    .append(created ? "создано" : "уже существует")
                    .append("\n");
        }

        String[] filesToCreate = {"src/main/Main.java", "src/main/Utils.java"};
        for (String filePath : filesToCreate) {
            File file = new File(gamesDir, filePath);
            try {
                boolean created = file.exists() || file.createNewFile();
                log.append("[").append(filePath).append("] ")
                        .append(created ? "создан" : "уже существует")
                        .append("\n");
            } catch (IOException e) {
                log.append("[ERROR] Не удалось создать файл ").append(filePath)
                        .append(": ").append(e.getMessage()).append("\n");
            }
        }

        File tempLogFile = new File(gamesDir, "temp/temp.txt");
        try {
            boolean logFileCreated = tempLogFile.exists() || tempLogFile.createNewFile();
            if (logFileCreated) {
                try (FileWriter writer = new FileWriter(tempLogFile)) {
                    writer.write(log.toString());
                    writer.write("\n Установка завершена успешно!");
                }
                System.out.println(" Лог записан в: " + tempLogFile.getAbsolutePath());
            } else {
                System.err.println(" Не удалось создать файл лога: " + tempLogFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println(" Ошибка записи лога в temp.txt: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n Структура установлена.");
        System.out.println(" Подробный лог — в файле: " + tempLogFile.getAbsolutePath());
    }
}
