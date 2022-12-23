function takeBookIdFromUrl() {
    const strUrl = document.location.href;
    const keyUrl = '/';
    const pos = strUrl.lastIndexOf(keyUrl);
    return strUrl.substring(pos + 1);
}
countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
countObjectUpdateTextById('http://localhost:8080/api/book/count','book');
updateTableComment('http://localhost:8080/api/comment/book/' + takeBookIdFromUrl());
document.getElementById('creat').setAttribute('data-book-id', takeBookIdFromUrl());
document.getElementById('creat').removeEventListener('click', clickActions);
document.getElementById('creat').addEventListener('click', clickActions);
function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;

    if (action === "creat") {
        creatComment('http://localhost:8080/api/comment/', 'Создать комментарий на книгу - ', button)
    }

    var id = button.dataset.id;
    if (action === "update") {
        updateComment('http://localhost:8080/api/comment/' + id, 'Изменить комментарий книги - ', button);
    }
    if (action === "delete") {
        deleteComment("http://localhost:8080/api/comment", id);
    }
}