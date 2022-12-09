countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableBook('http://localhost:8080/api/book');
function clickActions(e) {
    var action = e.target.dataset.action;
    var id = e.target.dataset.id;

    if (action === "comment") {
        window.location.href = 'http://localhost:8080/comment/book/' + id;
    }

    if (action === "creat") {

    }
    if (action === "update") {
        var authorName = e.target.dataset.authorName;
        updateBook('http://localhost:8080/api/book/' + id, 'Изменить Книгу');
    }
    if (action === "delete") {
        deleteBook("http://localhost:8080/api/book", id);
        setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/book/count', 'book')
        setTimeout(updateTableBook, 50, 'http://localhost:8080/api/book');
    }
}