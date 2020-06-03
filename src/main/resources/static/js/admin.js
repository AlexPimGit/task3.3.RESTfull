$(document).ready(function () {

    allUsers();

    function allUsers() {
        $.ajax({
            url: "/admin/allUsers",
            method: "GET",
            dataType: "json",
            success: function (data) {
                var tableBody = $('#myTbody');
                tableBody.empty();
                $(data).each(function (i, user) {//перебираем все элементы в data
                    var stringRoles = [];
                    // $(user.roles).each(function (i, role) {
                    //     stringRoles += role.valueOf();
                    // });
                    for (var y = 0; y < user.roles.length; y++) {
                        stringRoles.push(user.roles[y].name);
                    }
                    tableBody.append(`<tr id="${user.id}">
                    <td id="userId-${user.id}">${user.id}</td>
                    <td id="userName-${user.id}">${user.name}</td>
                    <td id="userPosition-${user.id}">${user.position}</td>
                    <td id="userAge-${user.id}">${user.age}</td>
                    <td id="userEmail-${user.id}">${user.email}</td>
                    <td id="userRoles-${user.id}">${stringRoles}</td>
                    <td><button type="button" class="btn btn-info edit-user" data-toggle="modal" data-target="#windowModal"
                    id="editModalButton-${user.id}">Edit</button></td>
                    <td><button type="button" class="btn btn-danger delete-user" data-toggle="modal" data-target="#windowModal"
                    id="deleteModalButton-${user.id}">Delete</button></td>
                    </tr>`);
                })
            },
            error: function (error) {
                alert(error);
            }
        })
    }

    $('#addButton').click(function (e) {
        e.preventDefault();//отмена действия браузера по умолчанию (действия на сервер) - при клике на кнопку не произойдет отправка Post, она произойдет потом
        // $('#addingNewUserDiv').html('<h4>Adding new user...</h4>').fadeIn(4000, function () {
        $('#addingNewUserDiv').html('<h4>New User added!</h4>').fadeOut(2000, function () {


            var newObject = {};
            newObject ["name"] = $("#nameTextInput").val();
            newObject["userPassword"] = $("#userPasswordTextInput").val();
            newObject["position"] = $("#positionTextInput").val();
            newObject["age"] = $("#ageTextInput").val();
            newObject["email"] = $("#emailTextInput").val();
            newObject["roles"] = $("#roleSelectNU").val();

            $.ajax({
                url: '/admin/addUser',
                type: 'POST',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(newObject),//отправляем на сервер JSON преобразовав объект newObject через метод JSON.stringify()
                dataType: 'json',
                context: document.getElementById('addingNewUserDiv'),// this = document.getElementById(..)
                success: function (data) {//data - данные с сервера (DTO)
                    // $(this).fadeOut(3000, function () {//скрывает элементы(определяющая длительность анимации, Функция по окончании выполнения анимации, вызывается для каждого соответствующего элемента)
                    //     $(this).toggleClass('alert-primary alert-success');//добавляет класс если есть, удаляет если нет
                    //     $(this).find('h4').attr('class', 'alert-heading').text('New user added!');
                    //     $(this).append(`<hr><h2>Hello, I'm user from DTO</h2><h5>User ${data.username}</h5><p>id: ${data.id}</p><p>email: ${data.email}</p><p>roles: ${data.roles}</p>`);
                    //     $(this).fadeIn(1000)//this появится за 1 сек
                    //         .delay(4000) //задержка 4 сек
                    //         .fadeOut(1000, function () {// this скрывается за 1 сек и выполняется функция
                    //             $("#addForm").trigger("reset");//выполняет "reset" для каждого класса у которого есть функция reset (инпуты)
                    //         });
                    // });
                    $("#addForm").trigger("reset");
                    var tableBody = $('#myTbody');

                    tableBody.append(`<tr id="${data.id}">
                    <td id="userId-${data.id}">${data.id}</td>
                    <td id="userName-${data.id}">${data.name}</td>
                    <td id="userPosition-${data.id}">${data.position}</td>
                    <td id="userAge-${data.id}">${data.age}</td>
                    <td id="userEmail-${data.id}">${data.email}</td>
                    <td id="userRoles-${data.id}">${data.roles}</td>
                    <td><button type="button" class="btn btn-info edit-user" data-toggle="modal" data-target="#windowModal"
                    id="editModalButton-${data.id}">Edit</button></td>
                    <td><button type="button" class="btn btn-danger delete-user" data-toggle="modal" data-target="#windowModal"
                    id="deleteModalButton-${data.id}">Delete</button></td>
                    </tr>`);
                },
                error:
                    function () {
                        alert("Error in ADD");
                    }
            });
        });
    });


//для id=myTbody, если событие поднялось по  'click' из класса '.delete-user',то работает функция
    $('#myTbody').on('click', '.delete-user', function () {
        // var id = this.id.slice(this.id.lastIndexOf("-") + 1);

        document.getElementById('ModalTitle').textContent = 'Delete User';
        document.getElementById('actionButton').textContent = 'Delete User';
        document.getElementById('actionButton').className = 'btn btn-danger';
        $('.inputs').attr('disabled', '');
        $('.passwordInput').remove();
        showModalValuesWindow(this);
    });

    $('.modal-footer').on('click', 'a.btn-danger', function () {
        var id = $("#IdInput").val();
        var delObj = $('#deleteModalButton-' + id);
        $.ajax({
            url: '/admin/deleteUser/' + id,
            type: 'DELETE',
            contentType: "application/json;charset=UTF-8",

            success: function () {
                $(delObj).closest('tr').css('background', 'lightcoral');
                $(delObj).closest('tr').fadeOut(2000, function () {
                    $(delObj).remove();
                });
                $('#closeButton').click();
            },
            error: function () {
                alert("Error Delete bleat!");
            }
        });
        $('#windowModal .closeSecondButton').click();
    });


    $('#myTbody').on('click', '.edit-user', function () {
        var currentObject = this;
        document.getElementById('ModalTitle').textContent = 'Edit User';
        document.getElementById('actionButton').textContent = 'Edit User';
        document.getElementById('actionButton').className = 'btn btn-info';
        // document.getElementById('actionButton').id = 'edit';
        $('.inputs').removeAttr('disabled');
        showModalValuesWindow(currentObject);
    });

    //делегированная обработка. Делаю модальное окно изменяю имя класса "actionButton", чтобы дальше обратиться к нему
    // надо сделать уточнитель-селектор в методе on
    //  Выборка - в классе .modal-footer для любого нового элемента a с классом btn-info будем делать следующее
    $('.modal-footer').on('click', 'a.btn-info', function () {
        var updateObject = {};
        updateObject["id"] = $("#IdInput").val();
        updateObject["name"] = $("#nameInput").val();
        updateObject["userPassword"] = $("#passwordInput").val();
        updateObject["position"] = $("#positionInput").val();
        updateObject["age"] = $("#ageInput").val();
        updateObject["email"] = $("#emailInput").val();
        updateObject["roles"] = $("#roleSelectInput").val();
        $.ajax({
            url: '/admin/update',
            type: 'PUT',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(updateObject),
            dataType: 'json',
            success: function (data) {
                var id = data.id;
                var name = data.name;
                var position = data.position;
                var age = data.age;
                var email = data.email;
                var roles = data.roles;
                $('#userId-' + id).text(id);
                $('#userName-' + id).text(name);
                $('#userPosition-' + id).text(position);
                $('#userAge-' + id).text(age);
                $('#userEmail-' + id).text(email);
                $('#userRoles-' + id).text(roles);
                $('#windowModal .closeSecondButton').click();
            },
            error: function () {
                alert("Error!");
            }
        })
    });


    function showModalValuesWindow(object) {
        //id = id из класса кнопки "delete-user" (или "edit-user") = id="deleteModalButton' + json[i].id + '"
        //т.к.  нумерация начинается с нуля, добавляем 1
        //id = deleteModalButton(id+1)
        var id = object.id.slice(object.id.lastIndexOf("-") + 1);
        $('#IdInput').attr('value', id);
        $('#nameInput').attr('value', $('#userName-' + id).text());
        $('#passwordInput').attr('value');
        $('#positionInput').attr('value', $('#userPosition-' + id).text());
        $('#ageInput').attr('value', $('#userAge-' + id).text());
        $('#emailInput').attr('value', $('#userEmail-' + id).text());

        var userRow = $("[id=" + id + "]");
        var rolesList = ["ADMIN", "USER"];
        var userRoles = userRow.find('#userRoles-' + id).text();
        $('#roleSelectInput').empty();
        rolesList.forEach(function (value) {
            if (userRoles.includes(value)) {
                $('#roleSelectInput').append('<option id="option"' + value + ' value="' + value + '" selected>' + value + '</option>')
            } else {
                $('#roleSelectInput').append('<option id="option"' + value + ' value="' + value + '">' + value + '</option>')
            }
        });
    }
});



