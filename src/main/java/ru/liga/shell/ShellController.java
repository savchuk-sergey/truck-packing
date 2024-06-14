package ru.liga.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.truck.service.LoaderService;
import ru.liga.truck.type.TruckLoaderType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Этот класс представляет собой обработчик грузов,
 * который обрабатывает данные о грузах из текстовых и JSON файлов.
 */
@Slf4j
@ShellComponent
public class ShellController {
    private final LoaderService loaderService;

    @Autowired
    public ShellController(LoaderService loaderService) {
        this.loaderService = loaderService;
    }

    @ShellMethod(value = "Обрабатывает данные о грузах из текстового файла.", key = "process-txt")
    public String processTxt(@ShellOption(value = {"-f", "--filepath"}, help = "Path to file with cargos") String filepath,
                             @ShellOption(value = {"-c", "--max-truck-number"}, help = "Max number of trucks that can be used", defaultValue = "-1") int maxTruckNumber,
                             @ShellOption(value = {"-l", "--truck-loader-type"}, help = "Place cargos in a truck using sort algorithm") TruckLoaderType truckLoaderType)
            throws IOException {
        log.info("filepath: {}, maxTruckNumber: {}, truckLoaderType: {}", filepath, maxTruckNumber, truckLoaderType);
        try {
            return loaderService.getLoadedTrucksStringFromTxtFile(Files.readString(Paths.get(filepath)), maxTruckNumber, truckLoaderType);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Обрабатывает данные о грузах из JSON файла.", key = "process-json")
    public void processJson(String filepath) throws IOException {
    }
}
