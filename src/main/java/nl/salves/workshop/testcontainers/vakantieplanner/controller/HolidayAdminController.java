package nl.salves.workshop.testcontainers.vakantieplanner.controller;

import io.swagger.v3.oas.annotations.Parameter;
import nl.salves.workshop.testcontainers.vakantieplanner.dao.VakantieRepository;
import nl.salves.workshop.testcontainers.vakantieplanner.model.MemberWithHolidays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/holiday-admin")
public class HolidayAdminController extends AbstractController {

    Logger logger = LoggerFactory.getLogger(HolidayAdminController.class);

    @Autowired
    VakantieRepository vakantieRepository;

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public List<MemberWithHolidays> getHolidays() {
        logger.info("Admin Endpoint GET /holidays/all is called");
        return vakantieRepository.retrieveAllHolidays();
    }
}
