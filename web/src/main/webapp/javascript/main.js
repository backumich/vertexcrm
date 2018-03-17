$(document).ready(function () {

    var $page = $('html, body');
    $('a[href*="#"]').click(function () {
        var href = $.attr(this, 'href');
        $page.animate({
            scrollTop: $(href).offset().top
        }, 400, function () {
            window.location.hash = href;
        });
        return false;
    });

    $("#allowedit").click(function () {
        if ($("#email").length) {
            document.getElementById("email").disabled = false;
        }
        if ($("#lastName").length) {
            document.getElementById("lastName").disabled = false;
        }
        if ($("#firstName").length) {
            document.getElementById("firstName").disabled = false;
        }
        if ($("#discount").length) {
            document.getElementById("discount").disabled = false;
        }
        if ($("#phone").length) {
            document.getElementById("phone").disabled = false;
        }
        if ($("#roles").length) {
            document.getElementById("roles").disabled = false;
        }
        if ($("#save").length) {
            document.getElementById("save").disabled = false;
        }

        $("#allowedit").hide();
        $("#allowedit").fadeOut();
    });

    $(window).scroll(function () {
        showNavbar();
    });
});

function clearForm(el) {
    el.find("input.form-control,textarea.form-control").val("");
}

function showNavbar() {
    $(".navbar").removeClass("navbar-hide");
}

function Toggle(options) {
    var settings = $.extend({
        container: null,
        toggler: '.toggler',
        toggle: '.toggle',
        active: 'active'
    }, options);

    $(settings.container).each(function () {
        var container = $(this);

        $(settings.toggler, container).click(function (e) {
            e.preventDefault();
            $(settings.toggle, container).slideToggle(200);
        });
    });
}