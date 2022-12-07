function deleteObject(url, id) {
    fetch(url, {
        method: 'DELETE',
        headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: "id=" + id
    })
        .then(result => {
            if (result.ok) {
                info('Объект успешно удален', true);
                getAllBook(url);
            } else {
                info('Ошибка удаления - ' + result.status, false);
                console.log(result.json())
            }
        })
        .catch(err => console.log(err));
}