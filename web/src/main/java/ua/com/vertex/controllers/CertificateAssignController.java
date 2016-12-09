package ua.com.vertex.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.CertificateAssignLogic;

import java.util.Map;

@Controller
@RequestMapping("/assigncertificate.jsp")
@SessionAttributes("userId")
public class CertificateAssignController {
    private static final Logger LOGGER = LogManager.getLogger(CertificateAssignController.class);
    @Autowired
    private CertificateAssignLogic certificateAssignLogic;

    //@RequestMapping(value = "/assigncertificate", method = RequestMethod.POST)
    @GetMapping
    public ModelAndView showAssignCertificatePage(@RequestParam() Map<String, Integer> userIdcertId, ModelAndView result) {
        //       ModelAndView result = new ModelAndView("redirect:assigncertificate.jsp");
        assert result.hasView();

        Integer updatedColumCount = 0;
        updatedColumCount = certificateAssignLogic.assignCertificateToUser(userIdcertId.get("2"), userIdcertId.get("2"));

        //       result.addObject("updatedColumCount", updatedColumCount);
        LOGGER.info("render jsp");
        return new ModelAndView("assigncertificate", "assingncertificate", updatedColumCount);
//
//    }
    }
}

