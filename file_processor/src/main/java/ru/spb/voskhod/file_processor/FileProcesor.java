package ru.spb.voskhod.file_processor;

import org.springframework.context.annotation.Configuration;
import ru.spb.voskhod.file_processor.model.ReadFile;
import ru.spb.voskhod.file_processor.model.WriteFile;

import java.util.function.Function;

@Configuration
public class FileProcesor {

    public Function<ReadFile, WriteFile> processFile() {
        return readFile -> {
            WriteFile writeFile = new WriteFile();
            int lineAvgLength = 0;
            lineAvgLength = lineAvgLength + readFile.getDate().toString().length();
            lineAvgLength = lineAvgLength + readFile.getOperation().length();
            lineAvgLength = lineAvgLength + readFile.getAmount().toString().length();
            lineAvgLength = lineAvgLength + readFile.getItemOfExpenditure().length();
            lineAvgLength = lineAvgLength + readFile.getOldBalance().toString().length();
            writeFile.setLineAvgLength(lineAvgLength / 5);

            return writeFile;
        };

    }

}
