package ru.main.managementsystem.admin.entity;

import java.time.LocalDate;
import java.util.Set;

public record RequestFilter (
        String searchText,
        RequestType type,
        Set<String> statuses,
        LocalDate fromDate,
        LocalDate toDate,
        Integer assignedTo,
        Priority priority
)  {
}
