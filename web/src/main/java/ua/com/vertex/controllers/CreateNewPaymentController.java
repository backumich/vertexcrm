package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.PaymentForm;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.PaymentLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndAddToUserController.USERS;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@Controller
public class CreateNewPaymentController {

    private static final Logger logger = LogManager.getLogger(CreateNewPaymentController.class);

    private final CourseLogic courseLogic;
    private final UserLogic userLogic;
    private final PaymentLogic paymentLogic;

    static final String SELECT_COURSE_FOR_PAYMENT_JSP = "selectCourseForPayment";
    static final String SELECT_USER_FOR_PAYMENT_JSP = "selectUserForPayment";
    static final String COURSES = "courses";
    private static final String PAYMENT = "paymentForm";
    static final String USER_ID_FOR_PAY = "userIdForPayment";
    static final String COURSE_ID_FOR_PAY = "courseIdForPayment";

    @Autowired
    public CreateNewPaymentController(CourseLogic courseLogic, UserLogic userLogic, PaymentLogic paymentLogic) {
        this.courseLogic = courseLogic;
        this.userLogic = userLogic;
        this.paymentLogic = paymentLogic;
    }

    @GetMapping(value = "/createPayment")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView selectCourseForPayment() {
        logger.debug("Request to '/createPayments' . Redirect to " + SELECT_COURSE_FOR_PAYMENT_JSP);
        ModelAndView result = new ModelAndView(SELECT_COURSE_FOR_PAYMENT_JSP);
        try {
            result.addObject(COURSES, courseLogic.getAllCoursesWithDept());
        } catch (Exception e) {
            logger.warn(e);
            result.setViewName(ERROR);
        }
        return result;
    }

    @PostMapping(value = "/selectUserForPayment")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createPayment(@Validated @ModelAttribute(PAYMENT) PaymentForm payment,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName(SELECT_USER_FOR_PAYMENT_JSP);
        if (!bindingResult.hasErrors()) {
            try {
                int paymentId = paymentLogic.createNewPaymentAndUpdateAccounting(payment);
                modelAndView.setViewName(ADMIN_JSP);
                modelAndView.addObject(MSG, "Payment create successful!!!");
                logger.debug(String.format("Payment create successful, payment id = (%s)", paymentId));
            } catch (Exception e) {
                logger.warn(e);
                modelAndView.setViewName(ERROR);
            }
        } else {
            modelAndView.addObject(COURSE_ID_FOR_PAY, payment.getCourseId());
            modelAndView.addObject(USER_ID_FOR_PAY, payment.getUserID());
        }
        return modelAndView;
    }

    @PostMapping(value = "/selectCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView selectUserForPayment(@SuppressWarnings("SameParameterValue") @ModelAttribute(COURSE_ID_FOR_PAY) int courseId) {
        ModelAndView result = new ModelAndView(SELECT_USER_FOR_PAYMENT_JSP);
        try {
            result.addObject(USERS, userLogic.getCourseUsers(courseId));
            result.addObject(COURSE_ID_FOR_PAY, courseId);
            result.addObject(PAYMENT, new PaymentForm());
        } catch (Exception e) {
            logger.warn(e);
            result.setViewName(ERROR);
        }
        return result;
    }
}