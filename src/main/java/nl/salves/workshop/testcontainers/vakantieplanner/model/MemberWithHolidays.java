package nl.salves.workshop.testcontainers.vakantieplanner.model;

import java.util.List;

public record MemberWithHolidays (Member member, List<Holiday> holidays) {
}
