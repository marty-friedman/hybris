package concerttours.controller;
import concerttours.data.TourData;
import concerttours.facade.TourFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class TourController {
    @Resource
    private TourFacade tourFacade;

    @RequestMapping(value = "/tours/{tourId}")
    public String showTourDetails(@PathVariable final String tourId, final Model model) throws UnsupportedEncodingException {
        final String decodedTourId = URLDecoder.decode(tourId, "UTF-8");
        final TourData tour = tourFacade.getTourDetails(decodedTourId);
        model.addAttribute("tour", tour);
        return "tourDetails";
    }
}