package concerttours.controllers;

import concerttours.data.TourData;
import concerttours.facades.TourFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class TourController {
    @Resource
    private TourFacade tourFacade;

    @GetMapping("tour/{tourId}")
    public String showTourDetailsPage(@PathVariable final String tourId, final Model model) {
        TourData tourData = tourFacade.getTourByCode(tourId);
        model.addAttribute("tour", tourData);
        return "TourDetails";
    }
}
