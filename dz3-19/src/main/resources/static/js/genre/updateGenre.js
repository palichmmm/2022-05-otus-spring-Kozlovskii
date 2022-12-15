function updateGenre(url, title, btnData) {
    var id = btnData.dataset.id;
    var genreName = btnData.dataset.genreName;
    document.querySelector('.modal-body').innerHTML = '';
    var creatForm = `<form>
                         <div class="mb-3">
                            <label for="genre-id" class="col-form-label">Id:</label>
                            <input type="text" class="form-control" id="genre-id"  value="${id}" disabled>
                         </div>
                         <div class="mb-3">
                            <label for="genre-name" class="col-form-label">Название жанра:</label>
                            <input type="text" class="form-control" id="genre-name" value="${genreName}">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;

    const btnSave = document.getElementById('subForm');
    btnSave.removeEventListener('click', runUpdateGenre);
    btnSave.addEventListener('click', runUpdateGenre, {once: true});
}

async function runUpdateGenre() {
    var genreId = document.getElementById('genre-id').value;
    var genreName = document.getElementById('genre-name').value;
    await fetch('/api/genre', {
        method: 'PUT',
        headers: {"Content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify({"id": genreId, "genreName": genreName})
    })
        .then(result => {
            if (result.ok) {
                informer('Жанр успешно обновлен', true);
            }
            return result.json();
        })
        .then(data => {
            if ('message' in data) {
                switch (data['statusCode']) {
                    case 404:
                        informer('Ошибка ' + data['statusCode'] + '. ' + data['message']['error'], false);
                        break;
                    case 406:
                        informer('Ошибка ' + data['statusCode'] + '. ' + data['message']['genreName'], false);
                        break;
                    default:
                        informer('Что то пошло не так...', false);
                }
            }
        })
        .catch(err => console.log(err));
    document.getElementById('clsForm').click();
    updateTableGenre('http://localhost:8080/api/genre');
    countObjectUpdateTextById('http://localhost:8080/api/genre/count','genre');
}
