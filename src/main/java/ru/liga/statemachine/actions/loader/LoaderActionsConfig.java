package ru.liga.statemachine.actions.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.service.MessageService;
import ru.liga.bot.type.BotMessageType;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.mapper.CargoTypeMapper;
import ru.liga.cargo.service.CargoTypeService;
import ru.liga.statemachine.service.ActionsService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.State;
import ru.liga.statemachine.type.Variable;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.entity.TruckType;
import ru.liga.truck.mapper.TruckTypeMapper;
import ru.liga.truck.service.TruckLoader;
import ru.liga.truck.service.TruckTypeService;
import ru.liga.truck.type.TruckLoaderType;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class LoaderActionsConfig {
    private final ActionsService actionsService;
    private final CargoTypeService cargoTypeService;
    private final CargoTypeMapper cargoTypeMapper;
    private final TruckTypeMapper truckTypeMapper;
    private final TruckTypeService truckTypeService;
    private final MessageService messageService;

    @Bean
    public Action<State, Event> startLoadingTrucksAction() {
        return context -> {
            context.getStateMachine()
                    .getExtendedState()
                    .getVariables()
                    .put(
                            Variable.LOADER_BOT_DTO_BUILDER,
                            LoaderBotDto.builder()
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.LOADER_PROVIDE_TRUCK_TYPE.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputTruckTypeLoaderAction() {
        return context -> {
            actionsService.getLoaderBotDtoBuilderFromVariables(context)
                    .truckName(
                            actionsService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.LOADER_PROVIDE_TRUCK_NUMBER.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputTruckNumberLoaderAction() {
        return context -> {
            try {
                actionsService.getLoaderBotDtoBuilderFromVariables(context)
                        .truckNumber(
                                actionsService.getIntValueFromMessageHeader(
                                        context.getMessage(), MessageKey.DATA
                                )
                        );
                actionsService.setBotResponseMessageToVariable(context, BotMessageType.LOADER_PROVIDE_TYPE.getText());
                log.debug("State Machine Variables: {}", context.getStateMachine()
                        .getExtendedState()
                        .getVariables());
            } catch (Exception e) {
                log.error("inputTruckNumberLoaderAction Error: {}", e.getMessage());
            }
        };
    }

    @Bean
    public Action<State, Event> inputTypeLoaderAction() {
        return context -> {
            actionsService.getLoaderBotDtoBuilderFromVariables(context)
                    .algorithmType(
                            actionsService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.LOADER_PROVIDE_CARGOS.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputCargosLoaderAction() {
        return context -> {
            actionsService.getLoaderBotDtoBuilderFromVariables(context)
                    .cargoNames(actionsService.getListStringValueFromMessageHeader(
                            context.getMessage(), MessageKey.DATA
                    ));
            actionsService.sendEvent(context, Event.COMPLETE);
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> completeLoaderAction() {
        return context -> {
            try {
                List<Truck> trucks = loadTrucks(actionsService.getLoaderBotDtoBuilderFromVariables(context).build());
                actionsService
                        .setBotResponseMessageToVariable(context,
                                messageService.getMarkdownCodeBlock(
                                        trucks.stream()
                                                .map(Truck::toString)
                                                .collect(Collectors.joining("\n\n")))
                        );
                log.debug("State Machine Variables: {}", context.getStateMachine()
                        .getExtendedState()
                        .getVariables());
            } catch (RuntimeException e) {
                actionsService.setBotResponseMessageToVariable(context, e.getMessage());
            }

        };
    }

    private List<Truck> loadTrucks(LoaderBotDto loaderBotDto) {
        TruckLoader truckLoader = TruckLoaderType.findByTypeName(loaderBotDto.getAlgorithmType().toUpperCase());
        List<CargoType> cargoTypes = cargoTypeService.findByNameIn(loaderBotDto.getCargoNames());
        List<Cargo> cargos = cargoTypes.stream()
                .map(cargoTypeMapper::cargoTypeToCargo)
                .toList();

        TruckType truckType = truckTypeMapper.toEntity(truckTypeService.findByName(loaderBotDto.getTruckName()));
        return truckLoader.createLoadedTrucks(
                cargos,
                truckType.getHeight(),
                truckType.getWidth(),
                loaderBotDto.getTruckNumber()
        );
    }
}
