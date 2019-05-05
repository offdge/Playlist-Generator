"use strict"
$(document).ready(function () {

    $('#playlistTable').DataTable({
        dom: "Bfrtip",
        "responsive": true,
        "processing": true,
        ajax: {
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            url: "http://localhost:8080/playlist/getPlaylists",
            dataSrc: "",
        },
        order: [[ 2, "asc" ]],
        columns: [
            {
                "data": 'playlistTitle',
                fnCreatedCell: function (nTd, cellData, rowData) {
                    if (rowData.playlistTitle) {
                        $(nTd).html("<span style='cursor:pointer; color: #9B4B0E'>" + rowData.playlistTitle + "</span>")
                    }

                }
            },
            {"data": 'rating'},
            {"data": 'playlistDuration'},
            {"data": 'genresToString'},
            // etc
        ],
        select: true
    });


    // VARIABLES =============================================================
    var TOKEN_KEY = "jwtToken";
    var USER_NAME = "username";
    var $login = $("#login");
    var $signup = $("#signup");
    var $loginNav = $("#login-nav");
    var $signupNav = $("#signup-nav");
    var $logoutNav = $("#logout-nav");

    // FUNCTIONS =============================================================
    function getJwtToken() {
        return localStorage.getItem(TOKEN_KEY);
    }
    function getUsername() {
        return localStorage.getItem(USER_NAME);
    }

    function setJwtToken(token, username) {
        localStorage.setItem(TOKEN_KEY, token);
        localStorage.setItem(USER_NAME, username);
    }

    function removeJwtToken() {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(USER_NAME);
    }

    $("#logout-nav").click(doLogout);

    function doLogin(loginData) {
        console.log(loginData);
        $.ajax({
            url: "/api/auth/signin",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                setJwtToken(data.token, loginData.username);
                $('#user-dropdown-toggle')
                    .html(loginData.username + " <b class=\"caret\">");
                $login.hide();
                $loginNav.hide();
                $signup.hide();
                $signupNav.hide();
                $logoutNav.show();
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

    function doLogout() {
        removeJwtToken();
        $login.show();
        $loginNav.show();
        $signup.show();
        $signupNav.show();
        $logoutNav.hide();
        $('#user-dropdown-toggle')
            .html("User <b class=\"caret\">");
    }

    // REGISTER EVENT LISTENERS =============================================================
    $("#login-form").submit(function (event) {
        console.log("login form submit");
        event.preventDefault();

        var $form = $(this);
        var formData = {
            username: $form.find('input[name="your_name"]').val(),
            password: $form.find('input[name="your_pass"]').val()
        };

        doLogin(formData);
    });

    // INITIAL CALLS =============================================================
    if (getJwtToken()) {
        $login.hide();
        $loginNav.hide();
        $signup.hide();
        $signupNav.hide();
        $logoutNav.show();
        $('#user-dropdown-toggle')
            .html(getUsername() + " <b class=\"caret\">");
    } else {
        $logoutNav.hide();
        $('#user-dropdown-toggle')
            .html("User <b class=\"caret\">");
    }
});