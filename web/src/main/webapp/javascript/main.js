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
        document.getElementById("email").disabled = false;
        document.getElementById("lastName").disabled = false;
        document.getElementById("firstName").disabled = false;
        document.getElementById("discount").disabled = false;
        document.getElementById("phone").disabled = false;
        document.getElementById("role").disabled = false;
        document.getElementById("save").disabled = false;
        // document.getElementById("email").disabled = false;
        // document.getElementById("email").disabled = false;
        // document.getElementById("email").disabled = false;
        // document.getElementById("email").disabled = false;
        // document.getElementById("email").disabled = false;
        // document.getElementById("email").disabled = false;
    });

    $(".typing").typed({
        strings: ["Стань IT-специалистом", "И открой для себя весь мир"],
        typeSpeed: 1,
        backSpeed: 1,
        startDelay: 0,
        backDelay: 2000,
        loop: true
    });

    $(window).scroll(function () {
        showNavbar();
    })

    $('.slider-logo').slick({
        infinite: true,
        speed: 300,
        slidesToShow: 6,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 1100,
                settings: {
                    slidesToShow: 5
                }
            },
            {
                breakpoint: 925,
                settings: {
                    slidesToShow: 4,
                }
            },
            {
                breakpoint: 725,
                settings: {
                    slidesToShow: 3
                }
            },
            {
                breakpoint: 525,
                settings: {
                    slidesToShow: 2
                }
            },
            {
                breakpoint: 400,
                settings: {
                    slidesToShow: 1
                }
            }
        ]
    });


    $('.slider-post').slick({
        infinite: true,
        speed: 300,
        slidesToShow: 4,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 1100,
                settings: {
                    slidesToShow: 3
                }
            },
            {
                breakpoint: 725,
                settings: {
                    slidesToShow: 2
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1
                }
            }
        ]
    });


    $('.slider-person').slick({
        infinite: true,
        speed: 300,
        slidesToShow: 6,
        slidesToScroll: 1,
        responsive: [
            {
                breakpoint: 1100,
                settings: {
                    slidesToShow: 4
                }
            },
            {
                breakpoint: 725,
                settings: {
                    slidesToShow: 3
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 2
                }
            }
        ]
    });

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        $('.slider-person').slick('setPosition');
    })


    $('.c-a-slider').slick({
        centerMode: true,

        slidesToShow: 3,
        responsive: [
            {
                breakpoint: 800,
                settings: {
                    centerMode: true,

                    slidesToShow: 3
                }
            },
            {
                breakpoint: 480,
                settings: {
                    centerMode: true,

                    slidesToShow: 1
                }
            }
        ]
    });

    // $('.c-a-slider').slick({
    // 	infinite: true,
    // 	slidesToShow: 3,
    // 	slidesToScroll: 1,
    // 	dots: true,
    // 	arrows: true,
    // 	speed: 300,
    // 	autoplaySpeed: 0,
    // 	adaptiveHeight: true,
    // 	responsive: [
    // 		{
    // 			breakpoint: 768,
    // 			settings: {
    // 				slidesToShow: 1,
    // 				slidesToScroll: 1,
    // 				infinite: true,
    // 				dots: true,
    // 				arrows: false
    //         	}
    // 		}
    // 	]

    // });


    Toggle({container: '.js-toggle'});


    if (!$("body").hasClass("inside")) {
        $(".navbar-nav li a,.btn-more-slide").on("click", "a", function (event) {
            $(".navbar-collapse").removeClass("in");
            // $(".navbar-nav a").removeClass("active");
            // $(this).addClass("active");
            if (!$(this).hasClass("link")) {
                event.preventDefault();
                // var id  = $(this).attr('href'),
                if (!$(this).hasClass("load-popup")) {
                    var id = $(this).attr('href'),
                        top = $(id).offset().top - 0;
                    $('body,html').animate({scrollTop: top}, 500);
                }
            }
        });
    }
    if ($("body").hasClass("index")) {
        if (!device.tablet() && !device.mobile()) {
            if ($(".box-video").length) {
                $('.box-video').mb_YTPlayer();
            }
            $("body").addClass("desct");
        } else {
            $(".player").mb_YTPlayer();
        }
    }
    // $('.box-video').on("YTPReady",function(e){
    // 	// console.log("YTPReady")
    // });
    // $('.box-video').on("YTPBuffering",function(e){
    // 	// console.log("YTPBuffering")
    // });
    $('.box-video').on("YTPStart", function (e) {
        $(".video").fadeIn(800);
    });
    $('.box-video').on("YTPTime", function (e) {
        console.log("YTPTime")
        var currentTime = e.time;
        if (currentTime == 16) {
            $(".video").fadeOut(800);
        }
    });


    $('.js-tg-sl-join').on('click', function () {
        tileSliderToggle($(this));
    });
    $('.js-tg-sl-join--hid').on('click', function (e) {
        e.preventDefault();
        topSc = $(this).parents('.tile-slider').offset().top - 100;
        $('body,html').animate({scrollTop: topSc}, 500);
    });
    $('a[data-toggle="tab"]').on('shown.bs.tab', function () {
        tileSliderJoin();
    })

    $('.tile-slider').each(function () {
        var thEl = $(this);

        if (thEl.find('.slick-arrow').length) {
            thEl.find('.btns-split-slider').addClass('active');
        }
        ;
    });

    // $(".load-popup").click(function(){
    // 	return false;
    // })

    $("#sendmessage").submit(function () {
        $.ajax({
            url: "mail.php",
            data: $('#sendmessage').serialize(),
            type: 'POST',
            success: function (html) {
                if (html = "done") {
                    alert("Письмо успешно отправлено")
                    $('#myModal').modal('hide')
                    clearForm($('#myModal'));
                } else {
                    alert("Произошла ошибка. Повторите позже.")
                }
            }
        });
        return false;
    });

    $("#sendmessage-footer").submit(function () {

        $.ajax({
            url: "mail.php",
            data: $('#sendmessage-footer').serialize(),
            type: 'POST',
            success: function (html) {
                if (html = "done") {
                    alert("Письмо успешно отправлено")
                    clearForm($('#sendmessage-footer'));
                } else {
                    alert("Произошла ошибка. Повторите позже.")
                }
            }
        });

        return false;
    });

    $("#subscribe-form").submit(function () {
        $.ajax({
            url: "subscribe.php",
            data: $('#subscribe-form').serialize(),
            type: 'POST',
            success: function (html) {
                console.log(html)
                if (html = "done") {
                    alert("Вы подписаны на новости!")
                    clearForm($('#subscribe-form'));
                } else {
                    alert("Произошла ошибка. Повторите позже.")
                }
            }
        });
        return false;
    });

})

// function hideSly(){
// 	console.log("hide");
// 	$(".home-page .sly").fadeOut(1000);
// 	setTimeout(function(){
// 		showSly();
// 	},16000)
// }
// function showSly(){
// 	console.log("show");
// 	$(".home-page .sly").fadeIn(1000);
// 	setTimeout(function(){
// 		hideSly();
// 	},2500)
// }

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

function tileSliderToggle(thisEl) {
    thisEl.parents('.tile-slider').toggleClass('active');
}
function tileSliderJoin() {
    $('.tile-slider').removeClass('active');
}

// function typing(el){
// 	var text = ["My lololo","first lol:)","Bugaga da da da!"]
// }




