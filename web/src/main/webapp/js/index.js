$(document).ready(function () {
    $('#btRegistration').click(function () {
        email = $('#reg_email').val();
        password = $('#reg_password').val();
        verifyPassword = $('#verifyPassword').val();
        firstName = $('#firstName').val();
        lastName = $('#lastName').val();
        phone = $('#phone').val();

        $.ajax({
            type: "POST",
            url: "RegistrationUser",
            data: {
                email: email,
                password: password,
                verifyPassword: verifyPassword,
                firstName: firstName,
                lastName: lastName,
                phone: phone
            },
            success: function (responseData) {
                if (responseData != "") {
                    $(".errorSummary").empty().append(responseData);
                } else {
                    location.href = 'message.jsp';
                }
            }
        });
    });

});

