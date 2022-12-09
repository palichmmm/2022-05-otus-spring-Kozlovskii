function deleteAuthor(url, id) {
    fetch(url, {
        method: 'DELETE',
        headers: {"Content-type": "application/x-www-form-urlencoded; charset=UTF-8"},
        body: "id=" + id
    })
        .then(result => {
            if (result.ok) {
                informer('Объект успешно удален', true);
            } else {
                informer('Ошибка удаления - ' + result.status, false);
                return result.json();
            }
        }).then(data => console.log(data['message']['error']))
        .catch(err => console.log(err));
}