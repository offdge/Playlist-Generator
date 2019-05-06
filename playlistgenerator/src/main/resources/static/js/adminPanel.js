"use strict"
$(document).ready(function () {

    // VARIABLES =============================================================
    var TOKEN_KEY = "jwtToken";
    var USER_NAME = "username";
    var $login = $("#login");
    var $signup = $("#signup");
    var $loginNav = $("#login-nav");
    var $signupNav = $("#signup-nav");
    var $logoutNav = $("#logout-nav");

    // FUNCTIONS =============================================================

    function goToAdminPanel(data) {
        console.log(data);
        $.ajax({
            url: "/adminPanel",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                console.log("yeah");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("asdasdasdasdasdasd");
                if (jqXHR.status === 401 || jqXHR.status === 403) {
                    $('#loginErrorModal')
                        .modal("show")
                        .find(".modal-body")
                        .empty()
                        .html("<p>Message from server:<br>" + jqXHR.responseText + "</p>");
                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
            }
        });
    }

    // INITIAL CALLS =============================================================
    if (getJwtToken()) {

    } else {
    }
});