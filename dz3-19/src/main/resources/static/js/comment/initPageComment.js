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
function clickActions(e) {
    var button = e.target;
    var action = button.dataset.action;
    var id = button.dataset.id;

    if (action === "creat") {

    }
    if (action === "update") {
        var authorName = e.target.dataset.comment;
        updateComment('http://localhost:8080/api/comment/' + id, 'Изменить Комментарий', button);
    }
    if (action === "delete") {
        deleteComment("http://localhost:8080/api/comment", id);
    }
}