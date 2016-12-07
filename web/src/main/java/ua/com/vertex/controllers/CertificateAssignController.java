package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.CertificateAssignLogic;

@Controller
@RequestMapping("/assigncertificate")
@SessionAttributes("userId")
public class CertificateAssignController {

    @Autowired
    private CertificateAssignLogic certificateAssignLogic;


    @RequestMapping(value = "/assigncertificate", params = {"UserID", "CertificateID"}, method = RequestMethod.GET)
    public ModelAndView assingnCertificate(Model model) {
        ModelAndView result = new ModelAndView("redirect:assigncertificate.jsp");
        model.
                certificateAssignLogic.assignCertificateToUser(UserID, CertificateID);
        result.getparameter

        return result;
    }


//    @GetMapping
//    public ModelAndView showAssignCertificatePage() {
//        ModelAndView result = new ModelAndView("redirect:assigncertificate.jsp");
////        result.addObject("userIds", userLogic.getAllUserIds());
//
//        return result;
//
//    }
}

