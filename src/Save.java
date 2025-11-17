import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Save {

    public static void saveGame(String filePath, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(progress);
            System.out.println(" Сохранение записано: " + filePath);

        } catch (IOException e) {
            System.err.println(" Ошибка записи сохранения в " + filePath + ": " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> filePaths) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String filePath : filePaths) {
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println(" Файл не найден, пропущен: " + filePath);
                    continue;
                }

                String entryName = file.getName();
                zos.putNextEntry(new ZipEntry(entryName));

                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                }
                zos.closeEntry();
                System.out.println(" Упакован: " + entryName);
            }

            System.out.println(" Архив создан: " + zipPath);

        } catch (IOException e) {
            System.err.println(" Ошибка создания архива " + zipPath + ": " + e.getMessage());
        }
    }

    public static void deleteFiles(List<String> filePaths) {
        for (String path : filePaths) {
            File file = new File(path);
            if (file.delete()) {
                System.out.println(" Удалён: " + path);
            } else {
                System.out.println(" Не удалось удалить: " + path);
            }
        }
    }

    public static void main(String[] args) {
        String saveDir = "X:/Games/savegames";

        GameProgress save1 = new GameProgress(100, 5, 10, 125.3);
        GameProgress save2 = new GameProgress(80, 3, 8, 98.7);
        GameProgress save3 = new GameProgress(50, 1, 5, 45.0);

        String path1 = saveDir + "/save1.dat";
        String path2 = saveDir + "/save2.dat";
        String path3 = saveDir + "/save3.dat";
        String zipPath = saveDir + "/saves.zip";

        saveGame(path1, save1);
        saveGame(path2, save2);
        saveGame(path3, save3);

        zipFiles(zipPath, List.of(path1, path2, path3));

        deleteFiles(List.of(path1, path2, path3));

        System.out.println("\n Сохранения созданы, упакованы и очищены!");
    }
}