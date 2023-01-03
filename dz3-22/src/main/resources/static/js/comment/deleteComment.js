function deleteComment(url, id) {
    fetch(url +'/' + id, {
        method: 'DELETE'
    })
        .then(result => {
            if (result.ok) {
                informer('Объект успешно удален', true);
                setTimeout(updateTableComment, 50, 'http://localhost:8080/api/comment/book/' + takeBookIdFromUrl());
            } else {
                return result.json();
            }
        })
        .then(data => {
            if (data['message']) {
                informer('Ошибка удаления - ' + data['message'], false);
            }
        })
        .catch(err => console.log(err));
}