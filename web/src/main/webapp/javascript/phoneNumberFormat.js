$("#phone").intlTelInput({
    initialCountry: "auto",
    preferredCountries: ['ua'],
    utilsScript: "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/12.1.8/js/utils.js",
    geoIpLookup: function (callback) {
        $.get('https://ipinfo.io', function () {
        }, "jsonp").always(function (resp) {
            var countryCode = (resp && resp.country) ? resp.country : "";
            callback(countryCode);
        });
    }
});

$("form").submit(function () {
    $("#phoneHidden").val($("#phone").intlTelInput("getNumber"));
});
