package CSV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    private String filePath;
    private BufferedWriter writer;

    public CSVWriter(String filePath) throws IOException {
        this.filePath = filePath;
        this.writer = new BufferedWriter(new FileWriter(filePath));
        // Write the header row
        writer.write("Day; Total Animals; Average Age; Total Grass; Total Kids; Total Energy of Living Animals\n");
    }

    public void writeStats(int day, int totalAnimals, double averageAge, int totalGrass, double totalKids, double totalEnergyOfLiving) throws IOException {
        writer.write(day + "; " + totalAnimals + "; " + averageAge + "; " + totalGrass + "; " + totalKids + "; " + totalEnergyOfLiving + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}