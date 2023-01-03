function deleteBook(url, id) {
    fetch(url + '/' + id, {
        method: 'DELETE'
    })
        .then(result => {
            if (result.ok) {
                informer('Объект успешно удален', true);
                setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/book/count', 'book')
                setTimeout(updateTableBook, 50, 'http://localhost:8080/api/book');
            } else {
                informer('Ошибка удаления - ' + result.status, false);
                return result.json();
            }
        }).then(data => console.log(data['message']['error']))
        .catch(err => console.log(err));
}