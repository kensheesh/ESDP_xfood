package kg.attractor.xfood.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum Status {
    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String status;

    public static Status getStatusEnum(String status) {
        return switch (status.toUpperCase()) {
            case "NEW" -> NEW;
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "DONE" -> DONE;
            default -> throw new NoSuchElementException(String.format("No such status: '%s'", status));
        };
    }

    @Override
    public String toString() {
        return status;
    }
}
