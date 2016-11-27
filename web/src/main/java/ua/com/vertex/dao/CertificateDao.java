package ua.com.vertex.dao;

import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificateDao implements CertificateDaoInf {
    private final DataSource dataSource = DataSourceManager.getInstance();

    public List<Certificate> getAllCertificates() {
        List<Certificate> certificates = new ArrayList<>();
        String query = "SELECT certification_id, user_id, certification_date, course_name, language " +
                "FROM Certificate";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Certificate certificate;
            while (resultSet.next()) {
                certificate = new Certificate.Builder()
                        .setCertificationId(resultSet.getInt("certification_id"))
                        .setUserId(resultSet.getInt("user_id"))
                        .setCertificationDate(resultSet.getDate("certification_date").toLocalDate())
                        .setCourseName(resultSet.getString("course_name"))
                        .setLanguage(resultSet.getString("language"))
                        .getInstance();
                certificates.add(certificate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificates;
    }

    public Certificate getCertificate(long id) {
        Certificate certificate = new Certificate.Builder().getInstance();
        String query = "SELECT certification_id, user_id, certification_date, course_name, language " +
                "FROM Certificate WHERE certification_id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //noinspection JpaQueryApiInspection
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    certificate = new Certificate.Builder()
                            .setCertificationId(resultSet.getInt("certification_id"))
                            .setUserId(resultSet.getInt("user_id"))
                            .setCertificationDate(resultSet.getDate("certification_date").toLocalDate())
                            .setCourseName(resultSet.getString("course_name"))
                            .setLanguage(resultSet.getString("language"))
                            .getInstance();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificate;
    }

    public void deleteCertificate(long id) {
        String query = "DELETE FROM Certificate WHERE certification_id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCertificate(Certificate certificate) {
        String query = "INSERT INTO Certificate (user_id, certification_date, course_name, language)" +
                " VALUES (?, ?, ?, ?)";

        UserDao userDao = new UserDao();
        User user = userDao.getUser(certificate.getUserId());
        if (user.getUserId() != 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setLong(1, certificate.getUserId());
                statement.setDate(2, Date.valueOf(certificate.getCertificationDate()));
                statement.setString(3, certificate.getCourseName());
                statement.setString(4, certificate.getLanguage());

                statement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else throw new RuntimeException("There is no user with such ID in data base!");
    }

    public static void main(String[] args) throws IOException {
        CertificateDao certificateDao = new CertificateDao();

//        userDao.getAllCertificates().forEach(System.out::println);

        System.out.println(certificateDao.getCertificate(7));

//        Certificate certificate = new Certificate.Builder()
//                .setUserId(5)
//                .setCertificationDate(LocalDate.of(2016, 11, 1))
//                .setCourseName("Java Start")
//                .setLanguage("Java")
//                .getInstance();
//
//        userDao.addCertificate(certificate);

//        userDao.deleteCertificate(8);

    }

    @Override
    public Certificate getCertificateById(int certificateId) {
        return null;
    }

    @Override
    public List<Certificate> getAllCertificateByUserId(int userId) {
        return null;
    }
}
