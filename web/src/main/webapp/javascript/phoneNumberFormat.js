$("#phone").intlTelInput({
    initialCountry: "auto",
    geoIpLookup: function (callback) {
        $.get('https://ipinfo.io', function () {
        }, "jsonp").always(function (resp) {
            var countryCode = (resp && resp.country) ? resp.country : "";
            callback(countryCode);
        });
    },
    preferredCountries: ['ua'],
    utilsScript: "../../javascript/utils.js"
});

$("form").submit(function () {
    $("#phoneHidden").val($("#phone").intlTelInput("getNumber"));
});
