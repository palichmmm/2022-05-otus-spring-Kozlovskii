let id = 1;
fetch('http://localhost:8080/api/comment/book/' + id,
    {
        method: 'GET'
    })
    .then(result => result.json())
    .then(data => {
        let tableRow = '';
        let count = data.length;
        for (let i = 0; i < count; i++) {
            tableRow = tableRow + '<tr>' +
                '<td>' + data[i].id + '</td>' +
                '<td>' + data[i].authorName + '</td>' +
                '<td>' +
                '<button type="button" class="btn btn-link text-secondary">Просмотр</button>' +
                '<button type="button" class="btn btn-link text-primary">Изменить</button>' +
                '<button type="button" class="btn btn-link text-danger">Удалить</button>' +
                '</td>' +
                '</tr>'
        }
        document.getElementById("all").innerHTML = tableRow
    })
    .catch(error => console.log(error));
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
        deleteObject("http://localhost:8080/api/comment", id);
    }
}