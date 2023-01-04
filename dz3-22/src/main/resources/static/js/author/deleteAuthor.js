function deleteAuthor(url, id) {
    fetch(url + '/' + id, {
        method: 'DELETE'
    })
        .then(result => {
            if (result.ok) {
                informer('Объект успешно удален', true);
                setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/author/count', 'author')
                setTimeout(updateTableAuthor, 50, 'http://localhost:8080/api/author');
            } else {
                return  result.json();
            }
        })
        .then(data => {
            if (data['message']) {
                informer('Ошибка удаления - ' + data['message'], false);
                setTimeout(updateTableAuthor, 50, 'http://localhost:8080/api/author');
            }
        })
        .catch(err => console.log(err['message']));
}