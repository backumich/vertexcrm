$(document).ready(function ($) {


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

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        $('.slider-person').slick('setPosition');
    });

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
        console.log("YTPTime");
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
    });

    $('.tile-slider').each(function () {
        var thEl = $(this);

        if (thEl.find('.slick-arrow').length) {
            thEl.find('.btns-split-slider').addClass('active');
        }
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
                    alert("Письмо успешно отправлено");
                    $('#myModal').modal('hide');
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
                    alert("Письмо успешно отправлено");
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
                console.log(html);
                if (html = "done") {
                    alert("Вы подписаны на новости!");
                    clearForm($('#subscribe-form'));
                } else {
                    alert("Произошла ошибка. Повторите позже.")
                }
            }
        });
        return false;
    });
    
    // Search certificate field
    $('#show-certificate').on('keypress', function () {
        let content = $(this).val();
        let clean = content.replace(/-/g,'');

        if(clean.length>11){
            clean = clean.substr(0, 12)+'-'+clean.substr(12, clean.length-12);
            clean = clean.substr(0, 8)+'-'+clean.substr(8, clean.length-8);
            clean = clean.substr(0, 4)+'-'+clean.substr(4, clean.length-4);
        }else if(clean.length>7){
            clean = clean.substr(0, 8)+'-'+clean.substr(8, clean.length-8);
            clean = clean.substr(0, 4)+'-'+clean.substr(4, clean.length-4);
        }else if(clean.length>3){

            clean = clean.substr(0, 4)+'-'+clean.substr(4, clean.length-4);
        }

        $(this).val(clean);
    })

});

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




