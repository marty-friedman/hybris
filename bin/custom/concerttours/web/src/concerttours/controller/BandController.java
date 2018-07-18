package concerttours.controller;
import concerttours.data.BandData;
import concerttours.facade.BandFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class BandController {
    @Resource
    private BandFacade bandFacade;

    @RequestMapping(value = "/bands")
    public String showBands(final Model model) {
        final List<BandData> bands = bandFacade.getBands();
        model.addAttribute("bands", bands);
        return "bandList";
    }

    @RequestMapping(value = "/bands/{bandId}")
    public String showBandDetails(@PathVariable final String bandId, final Model model) throws UnsupportedEncodingException {
        final String decodedBandId = URLDecoder.decode(bandId, "UTF-8");
        final BandData band = bandFacade.getBand(decodedBandId);
        model.addAttribute("band", band);
        return "bandDetails";
    }
}