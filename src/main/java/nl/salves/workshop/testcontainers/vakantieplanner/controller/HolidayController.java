package nl.salves.workshop.testcontainers.vakantieplanner.controller;

import nl.salves.workshop.testcontainers.vakantieplanner.dao.VakantieRepository;
import nl.salves.workshop.testcontainers.vakantieplanner.model.Holiday;
import nl.salves.workshop.testcontainers.vakantieplanner.model.MemberWithHolidays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/holiday")
public class HolidayController extends AbstractController {

    Logger logger = LoggerFactory.getLogger(HolidayController.class);

    @Autowired
    VakantieRepository vakantieRepository;

    @DeleteMapping(path = "/{user}/{holiday-id}")
    public void deleteHoliday(@PathVariable("user") String user,
                              @PathVariable("holiday-id") int holidayId
    ) {
        logger.info("Endpoint DELETE /holiday/{}/{} is called", user, holidayId);
        vakantieRepository.deleteHoliday(holidayId);
    }

    @PostMapping(path = "/{user}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Holiday addHoliday(
            @PathVariable("user") String user,
            @RequestBody Holiday holiday) {
        logger.info("Endpoint POST /holiday/{} is called", user);
        return vakantieRepository.addHoliday(user, holiday);
    }

    @GetMapping(path = "/{user}", produces = APPLICATION_JSON_VALUE)
    public List<Holiday> getHolidays(@PathVariable("user") String user
    ) {
        logger.info("Endpoint GET /holiday/{} is called", user);
        return vakantieRepository.retrieveHolidaysForMemberWithName(user);
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public List<MemberWithHolidays> getHolidays() {
        logger.info("Admin Endpoint GET /holidays/all is called");
        return vakantieRepository.retrieveAllHolidays();
    }

    @GetMapping(path = "/{user}/{holiday-id}", produces = APPLICATION_JSON_VALUE)
    public Holiday getSingleHoliday(@PathVariable("user") String user,
            @PathVariable("holiday-id") int holidayId
    ) {
        logger.info("Endpoint GET /holiday/{}/{} is called", user, holidayId);
        return vakantieRepository.retrieveHoliday(holidayId);
    }

    @PutMapping(path = "/{user}/{holiday-id}", consumes = APPLICATION_JSON_VALUE)
    public void updateSingleHoliday(
            @PathVariable("user") String user,
            @PathVariable("holiday-id") int holidayId,
            @RequestBody Holiday holiday
    ) {
        logger.info("Endpoint PUT /holiday/{}/{} is called", user, holidayId);
        if (holidayId != holiday.id()) {
            logger.error("Id of holiday does not match request path param");
            throw new IllegalArgumentException("Id of holiday does not match request path param");
        }
        vakantieRepository.updateHoliday(user, holiday);
    }
}
