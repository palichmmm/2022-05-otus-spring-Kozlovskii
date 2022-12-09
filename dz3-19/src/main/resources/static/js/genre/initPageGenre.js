countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableGenre('http://localhost:8080/api/genre');
function clickActions(e) {
    var action = e.target.dataset.action;
    var id = e.target.dataset.id;

    if (action === "creat") {

    }
    if (action === "update") {
        var authorName = e.target.dataset.authorName;
        updateGenre('http://localhost:8080/api/genre/' + id, 'Изменить Жанр');
    }
    if (action === "delete") {
        deleteGenre("http://localhost:8080/api/genre", id);
        setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/genre/count','genre')
        setTimeout(updateTableGenre, 50, 'http://localhost:8080/api/genre');
    }
}