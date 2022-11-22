function Delete(id) {
    let result = confirm("УДАЛИТЬ ОБЪЕКТ?");
    if (result) {
        let req = new XMLHttpRequest();
        let deleteBtn = document.getElementById("del" + id);
        let url = deleteBtn.getAttribute("data-delete-url") + id;
        req.open("DELETE", url, true);
        req.addEventListener("load", function () {
            if (req.status === 200) {
                window.location.replace(req.responseText);
            } else {
                alert("Не удалось удалить объект! Код ошибки: " + req.status);
            }
        });
        req.send();
    }
}
