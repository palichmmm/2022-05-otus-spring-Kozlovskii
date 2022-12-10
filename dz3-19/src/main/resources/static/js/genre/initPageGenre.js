countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableGenre('http://localhost:8080/api/genre');
function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;
    var id = button.dataset.id;

    if (action === "creat") {

    }
    if (action === "update") {
        updateGenre('http://localhost:8080/api/genre/' + id, 'Изменить Жанр', button);
    }
    if (action === "delete") {
        deleteGenre("http://localhost:8080/api/genre", id);
    }
}