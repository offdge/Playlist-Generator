"use strict"
$(document).ready(function () {

    // VARIABLES =============================================================
    var TOKEN_KEY = "jwtToken";
    var USER_NAME = "username";
    var AUTHORITIES = "authorities";
    var $login = $("#login");
    var $signup = $("#signup");
    var $loginNav = $("#login-nav");
    var $signupNav = $("#signup-nav");
    var $logoutNav = $("#logout-nav");
    var $adminNav = $("#admin-nav");
    var $adminPanel = $("#admin-panel");

    // FUNCTIONS =============================================================
    function getJwtToken() {
        return localStorage.getItem(TOKEN_KEY);
    }
    function getUsername() {
        return localStorage.getItem(USER_NAME);
    }
    function getAuthorities() {
        return localStorage.getItem(AUTHORITIES);
    }
    function isAdmin() {
        if(localStorage.getItem(AUTHORITIES)){
            return localStorage.getItem(AUTHORITIES).indexOf("ADMIN") !== -1;
        } else {
            return false;
        }
    }

    function setLoginParameters(token, username, authorities) {
        localStorage.setItem(TOKEN_KEY, token);
        localStorage.setItem(USER_NAME, username);
        localStorage.setItem(AUTHORITIES, authorities);
    }

    function removeLoginParameters() {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(USER_NAME);
        localStorage.removeItem(AUTHORITIES);
    }

    function createAuthorizationTokenHeader() {
        var token = getJwtToken();
        if (token) {
            return {"Authorization": "Bearer " + token};
        } else {
            return {};
        }
    }

    $("#logout-nav").click(doLogout);

    function doLogin(loginData) {
        $.ajax({
            url: "/api/auth/signin",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                setLoginParameters(data.accessToken, loginData.username, JSON.stringify(data.authorities));
                $('#user-dropdown-toggle')
                    .html(loginData.username + " <b class=\"caret\">");
                $login.hide();
                $loginNav.hide();
                $signup.hide();
                $signupNav.hide();
                $logoutNav.show();
                if(isAdmin()){
                    $adminNav.show();
                    $adminPanel.show();
                    alert("Welcome, admin!");
                } else {
                    alert("You have successfully logged in!");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Your username or password is incorrect.");
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

    function doSignup(signupData) {
        $.ajax({
            url: "/api/auth/signup",
            type: "POST",
            data: JSON.stringify(signupData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                doLogin(signupData);
                alert ("You have successfully registered in TripTones! You can now start your own adventure! :)");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // alert ("The username or email addressed you have entered is already taken. Please try with another one.");
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


    function doCreatePlaylist(playlistData) {
        $.ajax({
            url: "/playlist/createPlaylist",
            type: "POST",
            data: JSON.stringify(playlistData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                $('#playlistTable').DataTable().ajax.reload();
                alert("Your playlist was successfully created!");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401 || jqXHR.status === 403) {
                    $('#loginErrorModal')
                        .modal("show")
                        .find(".modal-body")
                        .empty()
                        .html("<p>Message from server:<br>" + jqXHR.responseText + "</p>");
                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
                alert("The playlist could not be created.");
            }

        });
    }

    function doLogout() {
        removeLoginParameters();
        $adminNav.hide();
        $adminPanel.hide();
        $login.show();
        $loginNav.show();
        $signup.show();
        $signupNav.show();
        $logoutNav.hide();
        $('#user-dropdown-toggle')
            .html("User <b class=\"caret\">");
    }

    // REGISTER EVENT LISTENERS =============================================================


    $("#playlist-form").submit(function (event) {
        event.preventDefault();

        if(!checkLength()){ return false;}

        var $form = $(this);
        var formData = {
            startPoint: $form.find('input[id="inputOrigin"]').val(),
            endPoint: $form.find('input[id="inputDestination"]').val(),
            title: $form.find('input[id="inputPlaylistName"]').val(),
            useTopRanks: $form.find('input[id="topRanked"]').is(":checked"),
            allowSameArtist: $form.find('input[id="sameArtists"]').is(":checked"),
            genres:[
                {
                    name: "rock",
                    percentage: $form.find('input[id="inputGenreRock"]').val()
                },
                {
                    name: "pop",
                    percentage: $form.find('input[id="inputGenrePop"]').val()
                },
                {
                    name: "Rap/Hip Hop",
                    percentage: $form.find('input[id="inputGenreRap"]').val()
                }]
        };

        doCreatePlaylist(formData);
    });


    $("#login-form").submit(function (event) {
        event.preventDefault();

        var $form = $(this);
        var formData = {
            username: $form.find('input[name="your_name"]').val(),
            password: $form.find('input[name="your_pass"]').val()
        };

        doLogin(formData);
    });

    $("#register-form").submit(function (event) {
        event.preventDefault();

        var $form = $(this);
        var formData = {
            username: $form.find('input[name="username"]').val(),
            name: $form.find('input[name="name"]').val(),
            email: $form.find('input[name="email"]').val(),
            password: $form.find('input[name="pass"]').val()
        };

        doSignup(formData);
    });

    function checkLength(){
        var fieldValRock = document.getElementById('inputGenreRock').value;
        var fieldValRap = document.getElementById('inputGenreRap').value;
        var fieldValPop = document.getElementById('inputGenrePop').value;

        if(+fieldValRock + +fieldValRap + +fieldValPop !== 100){
            alert("The combined percentages must equal to 100");
            return false;
        }

        return true;
    }

    // DATATABLES =============================================================
    function format ( table_id ) {
        console.log("format: " + table_id);
        // `d` is the original data object for the row
        return '<table cellpadding="5" cellspacing="0" id="'+table_id+'" class="display responsive nowrap"  style="width: 100%">'+
            '<thead>'+
            '<th>Track Title</th>'+
            '<th>Genre</th>'+
            '<th>Artist name</th>'+
            '<th>Album name</th>'+
            '<th>Rating</th>'+
            '</thead>'+
            '</table>';
    }

    function sub_DataTable(playlist_id, table_id) {
        var subtable = $('#'+table_id).DataTable({
            "responsive": true,
            "processing": true,
            ajax: {
                type: "GET",
                contentType: "application/json",
                dataType: "json",
                url: "http://localhost:8080/playlist/getTracks/" + playlist_id,
                dataSrc: "",
            },
            order: [[ 4, "desc" ]],
            columns: [
                {"data": 'title'},
                {"data": 'genre.name'},
                {"data": 'artist.name'},
                {"data": 'album.title'},
                {"data": 'rank'}],
            select: true
        });
    }

    var editor = new $.fn.dataTable.Editor({
        ajax: {
            edit: {
                type: 'POST',
                url: 'http://localhost:8080/playlist/userUpdatePlaylist',
                contentType: "application/json",
                dataType: 'html',
                headers: createAuthorizationTokenHeader(),
                "data": function (d) {
                    return JSON.stringify(d.data[editor.field("id").val()]);
                },
                success: function () {
                    console.log("success");
                    $('#playlistTable').DataTable().ajax.reload();
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log("error");
                    alert(textStatus);
                }
            }
        },
        table: "#playlistTable",
        idSrc: 'id',
        dataSrc: "",
        fields: [{
            label: "playlist id:",
            name: "id"
        }, {
            label: "Playlist title:",
            name: "playlistTitle"
        }]
    });

    $('#playlistTable').on( 'click', 'tbody td:nth-child(2)', function (e) {
        editor.inline( this, {
            submit: 'allIfChanged'
        } );
    } );

    $('#playlistTable').on( 'click', 'tbody td:nth-child(6)', function (e) {
        doDeletePlaylist(this)
    } );

    function doDeletePlaylist(td) {
        var tr = $(td).closest('tr');
        var row = playlistTable.row( tr );
        var data = row.data();

        $.ajax({
            url: "/playlist/userDeletePlaylist/" + data.id,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                console.log("success");
                $('#playlistTable').DataTable().ajax.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("error");
            }
        });
    }

    var playlistTable = $('#playlistTable').DataTable({
        "responsive": true,
        "processing": true,
        ajax: {
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            url: "http://localhost:8080/playlist/getPlaylists",
            dataSrc: "",
        },
        order: [[ 3, "asc" ]],
        columns: [
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": ''
            },
            {
                "data": 'playlistTitle',
                editField: 'playlistTitle',
                fnCreatedCell: function (nTd, cellData, rowData) {
                    if (rowData.playlistTitle) {
                        $(nTd).html("<span style='cursor:pointer; color: #67B0D1'>" + rowData.playlistTitle + "</span>")
                    }

                }
            },
            {"data": 'rating'},
            {"data": 'playlistDurationMinutes'},
            {"data": 'genresToString'},
            {"data": null,
                fnCreatedCell: function (nTd, cellData, rowData) {
                    if (rowData.playlistTitle) {
                        $(nTd).html("<span style='cursor:pointer; color: #b11f1f'>delete</span>")
                    }

                }}
        ],
        select: true
    });

    // Add event listener for opening and closing details
    $('table#playlistTable tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = playlistTable.row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {

            // Open this row
            var data = row.data();
            var virtual_task_id = data.id;
            var subtable_id = "subtable-"+virtual_task_id;
            row.child(format(subtable_id)).show(); /* HERE I format the new table */
            tr.addClass('shown');
            sub_DataTable(virtual_task_id, subtable_id); /*HERE I was expecting to load data*/
        }
    });


    $.fn.dataTable.ext.errMode = 'throw';

    var userTable = $('#userTable').DataTable({
        "responsive": true,
        "processing": true,
        ajax: {
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            url: "http://localhost:8080/getUsers",
            dataSrc: "",
            headers: createAuthorizationTokenHeader(),
        },
        // order: [[ 2, "asc" ]],
        columns: [
            {"data": 'email'},
            {"data": 'name'},
            {"data": 'username'},
            {"data": null,
                fnCreatedCell: function (nTd, cellData, rowData) {
                    if (rowData.username) {
                        $(nTd).html("<span style='cursor:pointer; color: #b11f1f'>delete</span>")
                    }

                }}
        ],
        select: true,
        buttons: [
            {extend: "edit", editor: editor},
        ]
    });

    var editorUser = new $.fn.dataTable.Editor({
        ajax: {
            edit: {
                type: 'POST',
                url: 'http://localhost:8080/playlist/adminUpdateUser',
                contentType: "application/json",
                dataType: 'html',
                headers: createAuthorizationTokenHeader(),
                "data": function (d) {
                    return JSON.stringify(d.data[editorUser.field("id").val()]);
                },
                success: function () {
                    console.log("success");
                    $('#userTable').DataTable().ajax.reload();
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log("error");
                    alert(textStatus);
                }
            }
        },
        table: "#userTable",
        idSrc: 'id',
        dataSrc: "",
        fields: [{
            label: "user id:",
            name: "id"
        }, {
            label: "email:",
            name: "email"
        }]
    });


    $('#userTable').on( 'click', 'tbody td:nth-child(1)', function (e) {
        editorUser.inline( this, {
            submit: 'allIfChanged'
        } );
    } );




    var adminPlaylistTable = $('#adminPlaylistTable').DataTable({
        "responsive": true,
        "processing": true,
        ajax: {
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            url: "http://localhost:8080/playlist/getPlaylists",
            dataSrc: "",
        },
        order: [[ 3, "asc" ]],
        columns: [
            {
                "data": 'playlistTitle',
                editField: 'playlistTitle',
                fnCreatedCell: function (nTd, cellData, rowData) {
                    if (rowData.playlistTitle) {
                        $(nTd).html("<span style='cursor:pointer; color: #67B0D1'>" + rowData.playlistTitle + "</span>")
                    }

                }
            },
            {"data": 'rating'},
            {"data": 'playlistDurationMinutes'},
            {"data": 'genresToString'},
            {"data": null,
                fnCreatedCell: function (nTd, cellData, rowData) {
                    if (rowData.playlistTitle) {
                        $(nTd).html("<span style='cursor:pointer; color: #b11f1f'>delete</span>")
                    }

                }}
        ],
        select: true
    });

    $('#adminPlaylistTable').on( 'click', 'tbody td:nth-child(5)', function (e) {
        doAdminDeletePlaylist(this)
    } );

    function doAdminDeletePlaylist(td) {
        var tr = $(td).closest('tr');
        var row = adminPlaylistTable.row( tr );
        var data = row.data();

        $.ajax({
            url: "/playlist/adminDeletePlaylist/" + data.id,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                console.log("success");
                $('#playlistTable').DataTable().ajax.reload();
                $('#adminPlaylistTable').DataTable().ajax.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("error");
            }
        });
    }

    var editorAdmin = new $.fn.dataTable.Editor({
        ajax: {
            edit: {
                type: 'POST',
                url: 'http://localhost:8080/playlist/adminUpdatePlaylist',
                contentType: "application/json",
                dataType: 'html',
                headers: createAuthorizationTokenHeader(),
                "data": function (d) {
                    return JSON.stringify(d.data[editorAdmin.field("id").val()]);
                },
                success: function () {
                    console.log("success");
                    $('#playlistTable').DataTable().ajax.reload();
                    $('#adminPlaylistTable').DataTable().ajax.reload();
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log("error");
                    alert(textStatus);
                }
            }
        },
        table: "#adminPlaylistTable",
        idSrc: 'id',
        dataSrc: "",
        fields: [{
            label: "playlist id:",
            name: "id"
        }, {
            label: "Playlist title:",
            name: "playlistTitle"
        }]
    });


    $('#adminPlaylistTable').on( 'click', 'tbody td:nth-child(1)', function (e) {
        editorAdmin.inline( this, {
            submit: 'allIfChanged'
        } );
    } );

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

    if (isAdmin()){
        $adminNav.show();
        $adminPanel.show();
    } else {
        $adminNav.hide();
        $adminPanel.hide();
    }

});