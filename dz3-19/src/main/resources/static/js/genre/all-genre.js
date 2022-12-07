getAllObject('http://localhost:8080/api/genre')
function handleClick(e) {
    let action = e.target.dataset.action;
    if (action === "read") {
        console.log("Просмотр");
    }
    if (action === "update") {
        console.log("Обновление");
    }
    if (action === "delete") {
        let id = e.target.dataset.id;
        deleteObject("http://localhost:8080/api/genre", id);
    }
}