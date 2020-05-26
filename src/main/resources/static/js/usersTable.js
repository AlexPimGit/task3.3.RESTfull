$(document).ready(function () {
    $.getJSON('/admin/allUsers', function (json) {
        var tr = [];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].id + '</td>');
            tr.push('<td>' + json[i].name + '</td>');
            tr.push('<td>' + json[i].position + '</td>');
            tr.push('<td>' + json[i].age + '</td>');
            tr.push('<td>' + json[i].email + '</td>');
            tr.push('<td>');
            for (var y = 0; y < json[i].roles.length; y++) {
                tr.push(json[i].roles[y].name);
                if (json[i].roles.length === 1) {
                    tr.push('');
                } else if ((json[i].roles.length - y) !== 1) {
                    tr.push(', ');
                } else {
                    tr.push('');
                }
            }
            tr.push('</td>');
            tr.push('<td><button class=\'edit\'>Edit</button>&nbsp;&nbsp;<button class=\'delete\' id=' + json[i].id + '>Delete</button></td>');
            tr.push('</tr>');
        }
        //добавляем в конец table массив tr (join создает строку из элементов массива tr в строку с разделителем '')
        $('table').append($(tr.join('')));
    })
        .done(function () {
            alert('getJSON request succeeded!');
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert('getJSON request failed! ' + textStatus);
        })
        .always(function () {
            alert('getJSON request ended!');
        });

    $(document).delegate('#addNewUser', 'click', function (event) {
        event.preventDefault();
        var name = $('#name').val();
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/admin/addUser",
            data: JSON.stringify({'name': name}),
            cache: false,
            success: function (result) {
                $("#msg").html("<span style='color: green'>Company added successfully</span>");
                window.setTimeout(function () {
                    location.reload()
                }, 1000)
            },
            error: function (err) {
                $("#msg").html("<span style='color: red'>Name is required</span>");
            }
        });
    });

});

