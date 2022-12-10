countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableAuthor('http://localhost:8080/api/author');
document.getElementById('creat').removeEventListener('click', clickActions);
document.getElementById('creat').addEventListener('click', clickActions);
function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;

    if (action === "creat") {
        creatAuthor('http://localhost:8080/api/author/', 'Создать Автора');
    }

    var id = button.dataset.id;
    if (action === "update") {
        updateAuthor('http://localhost:8080/api/author/' + id, 'Изменить Автора', button);
    }
    if (action === "delete") {
        deleteAuthor("http://localhost:8080/api/author", id);
    }
}