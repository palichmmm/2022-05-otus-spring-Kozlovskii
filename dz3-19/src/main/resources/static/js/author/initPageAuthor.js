countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableAuthor('http://localhost:8080/api/author');
function clickActions(e) {
    var action = e.target.dataset.action;
    var id = e.target.dataset.id;

    if (action === "creat") {

    }
    if (action === "update") {
        var authorName = e.target.dataset.authorName;
        updateAuthor('http://localhost:8080/api/author/' + id, 'Изменить Автора');
    }
    if (action === "delete") {
        deleteAuthor("http://localhost:8080/api/author", id);
        setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/author/count','author')
        setTimeout(updateTableAuthor, 50, 'http://localhost:8080/api/author');
    }
}