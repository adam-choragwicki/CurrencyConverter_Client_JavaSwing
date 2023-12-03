package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLoader
{
    public static String loadFileContent(String pathToFile)
    {
        try
        {
            Path filePath = Paths.get(pathToFile);
            byte[] fileBytes = Files.readAllBytes(filePath);
            return new String(fileBytes);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not open " + pathToFile + " file");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
