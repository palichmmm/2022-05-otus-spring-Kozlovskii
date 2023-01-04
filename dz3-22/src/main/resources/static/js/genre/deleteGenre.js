function deleteGenre(url, id) {
    fetch(url + '/' + id, {
        method: 'DELETE'
    })
        .then(result => {
            if (result.ok) {
                informer('Объект успешно удален', true);
                setTimeout(countObjectUpdateTextById, 50,'http://localhost:8080/api/genre/count','genre')
                setTimeout(updateTableGenre, 50, 'http://localhost:8080/api/genre');
            } else {
                return result.json();
            }
        })
        .then(data => {
            if (data['message']) {
                informer('Ошибка удаления - ' + data['message'], false);
                setTimeout(updateTableGenre, 50, 'http://localhost:8080/api/genre');
            }
        })
        .catch(err => console.log(err));
}