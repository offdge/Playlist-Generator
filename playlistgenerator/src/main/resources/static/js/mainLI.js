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
});