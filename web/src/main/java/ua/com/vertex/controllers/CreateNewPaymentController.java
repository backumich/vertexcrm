package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.PaymentForm;
import ua.com.vertex.logic.interfaces.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndAddToUser.USERS;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@Controller
public class CreateNewPaymentController {

    private static final Logger LOGGER = LogManager.getLogger(CreateNewPaymentController.class);


    private final CourseLogic courseLogic;
    private final AccountingLogic accountingLogic;
    private final PaymentLogic paymentLogic;

    static final String SELECT_COURSE_FOR_PAYMENT_JSP = "selectCourseForPayment";
    static final String SELECT_USER_FOR_PAYMENT_JSP = "selectUserForPayment";
    static final String COURSES = "courses";
    private static final String PAYMENT = "paymentForm";
    static final String USER_ID_FOR_PAY = "userIdForPayment";
    static final String COURSE_ID_FOR_PAY = "courseIdForPayment";






    @PostMapping(value = "/createPayment")
    public ModelAndView selectCourseForPayment() {
        LOGGER.debug("Request to '/createPayments' . Redirect to " + SELECT_COURSE_FOR_PAYMENT_JSP);
        ModelAndView result = new ModelAndView(SELECT_COURSE_FOR_PAYMENT_JSP);
        try {
            result.addObject(COURSES, courseLogic.getAllCoursesWithDept());
        } catch (Exception e) {
            LOGGER.warn(e);
            result.setViewName(ERROR);
        }
        return result;
    }

    @PostMapping(value = "/selectCourse")
    public ModelAndView selectUserForPayment(@SuppressWarnings("SameParameterValue") @ModelAttribute(COURSE_ID_FOR_PAY) int courseId) {
        ModelAndView result = new ModelAndView(SELECT_USER_FOR_PAYMENT_JSP);
        try {
            result.addObject(USERS, accountingLogic.getCourseUsers(courseId));
            result.addObject(COURSE_ID_FOR_PAY, courseId);
            result.addObject(PAYMENT, new PaymentForm());
        } catch (Exception e) {
            LOGGER.warn(e);
            result.setViewName(ERROR);
        }
        return result;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);

        decimalFormat.setMaximumIntegerDigits(5);
        decimalFormat.setMaximumFractionDigits(2);
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(
                BigDecimal.class, decimalFormat, true));
    }

    @PostMapping(value = "/selectUserForPayment")
    public ModelAndView createPayment(@Validated @ModelAttribute(PAYMENT) PaymentForm payment,
                                      BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName(SELECT_USER_FOR_PAYMENT_JSP);
        if (!bindingResult.hasErrors()) {
            try {
                int paymentId = paymentLogic.createNewPaymentAndUpdateAccounting(payment);
                modelAndView.setViewName(ADMIN_JSP);
                modelAndView.addObject(MSG, "Payment create successful!!!");
                LOGGER.debug(String.format("Payment create successful, payment id = (%s)", paymentId));
            } catch (Exception e) {
                LOGGER.warn(e);
                modelAndView.setViewName(ERROR);
            }
        } else {
            modelAndView.addObject(COURSE_ID_FOR_PAY, payment.getCourseId());
            modelAndView.addObject(USER_ID_FOR_PAY, payment.getUserID());
        }
        return modelAndView;
    }

    @Autowired
    public CreateNewPaymentController(CourseLogic courseLogic, AccountingLogic accountingLogic, PaymentLogic paymentLogic) {
        this.courseLogic = courseLogic;
        this.accountingLogic = accountingLogic;
        this.paymentLogic = paymentLogic;
    }
}
