countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableBook('http://localhost:8080/api/book');
function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;
    var id = button.dataset.id;

    if (action === "comment") {
        window.location.href = 'http://localhost:8080/comment/book/' + id;
    }

    if (action === "creat") {

    }
    if (action === "update") {
        updateBook('http://localhost:8080/api/book/' + id, 'Изменить Книгу', button);
    }

    if (action === "delete") {
        deleteBook("http://localhost:8080/api/book", id);
    }
}