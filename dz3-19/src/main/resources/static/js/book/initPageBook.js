countObjectUpdateTextById('http://localhost:8080/api/author/count', 'author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count', 'genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count', 'book');
updateTableBook('http://localhost:8080/api/book');
document.getElementById('creat').removeEventListener('click', clickActions);
document.getElementById('creat').addEventListener('click', clickActions);

function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;

    if (action === "creat") {
        creatBook('http://localhost:8080/api/book/', 'Создать Книгу')
    }

    var id = button.dataset.id;
    if (action === "comment") {
        window.location.href = 'http://localhost:8080/comment/book/' + id;
    }
    if (action === "update") {
        updateBook('http://localhost:8080/api/book/' + id, 'Изменить Книгу', button);
    }
    if (action === "delete") {
        deleteBook("http://localhost:8080/api/book", id);
    }
}