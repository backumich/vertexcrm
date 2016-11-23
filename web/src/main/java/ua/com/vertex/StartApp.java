package ua.com.vertex;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.vertex.context.MainContext;
import ua.com.vertex.dao.CertificateDaoInf;
import ua.com.vertex.dao.impl.Certificate;
import ua.com.vertex.dao.impl.CertificateDaoRealization;

//todo: delete this class from repo!!! you can play with test classes only locally!

public class StartApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MainContext.class);
        CertificateDaoInf certificateDaoInf =  ctx.getBean(CertificateDaoRealization.class);
        Certificate test = certificateDaoInf.getCertificateById(2);
        System.out.println(test);
        certificateDaoInf.getAllCertificateByUserId(1).forEach(System.out::println);



    }
}
