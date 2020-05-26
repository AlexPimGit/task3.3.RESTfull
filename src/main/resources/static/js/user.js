//обращение ко всему документу (странице - DOM). когда код этой страницы уже выполнился
//обращаемся ко всем div-ам. обрабатываем события ("событие", функция) - при клике вызывается incSize
$(document).ready(function () {
    $("div.myColor").bind("click", incSize);
    $(document).on("click", "p", function () { // видит <p> после того как они были созданы функцией
        alert("нажал на координаты, не на цвета");
    })
})

//параметр функции - событие
function incSize(event) {
    var width = Number(event.target.style.width.substring(0, event.target.style.width.length - 2));//при каком событии происходит. объект. стиль. св-во ширины
    event.target.style.width = (width + 20) + "px";
    // $(event.target).unbind();//отмена функции bind()
    // bind () не видит тэги (<p>,<div>...), которые создаются пользователем в функции,
    // для этого есть функция on ()
    $("<p>Координаты мышки при нажатии: x =" + event.screenX + ", y = " + event.screenY + "</p>").insertAfter("div:last");
}


