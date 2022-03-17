package browne.chris.conquest.controllers;

import browne.chris.conquest.geo.Circle;
import browne.chris.conquest.models.Coordinate;
import browne.chris.conquest.models.Tile;
import browne.chris.conquest.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/world/")
public class WorldController {

    private final WorldService worldService;

    @Autowired
    public WorldController(WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping(value = "tiles")
    @ResponseBody
    public List<Tile> getTilesInRadius(@RequestParam List<String> origin,
                                       @RequestParam
                                           Long radius) {
        if (origin.size() != 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Invalid coordinates, too many or too few dimensions");
        }
        try {
            Coordinate originCoordinate =
                new Coordinate(Long.parseLong(origin.get(0)),
                    Long.parseLong(origin.get(1)));
            return worldService.getTilesInCircle(new Circle(originCoordinate,
                radius));
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Invalid coordinates, coordinates were not numeric");
        }
    }
}
