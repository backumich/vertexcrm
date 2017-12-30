$("#phone").intlTelInput({
    initialCountry: "auto",
    preferredCountries: ['ua'],
    utilsScript: "../../javascript/utils.js",
    geoIpLookup: function (callback) {
        $.get('https://ipinfo.io', function () {
        }, "jsonp").always(function (resp) {
            var countryCode = (resp && resp.country) ? resp.country : "";
            callback(countryCode);
        });
    }
});

$("form").submit(function () {
    document.querySelector("#phone").value = $("#phone").intlTelInput("getNumber");
});
