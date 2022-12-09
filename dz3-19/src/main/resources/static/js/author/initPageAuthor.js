countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableAuthor('http://localhost:8080/api/author');
function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;
    var id = button.dataset.id;

    if (action === "creat") {

    }
    if (action === "update") {
        updateAuthor('http://localhost:8080/api/author/' + id, 'Изменить Автора', button);
    }
    if (action === "delete") {
        deleteAuthor("http://localhost:8080/api/author", id);
        setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/author/count', 'author')
        setTimeout(updateTableAuthor, 50, 'http://localhost:8080/api/author');
    }
}