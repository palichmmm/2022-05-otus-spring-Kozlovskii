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
                            <label for="genre-name" class="col-form-label">Название:</label>
                            <input type="text" class="form-control" id="genre-name" value="${genreName}">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;

    const btnSave = document.getElementById('subForm');
    btnSave.addEventListener('click', async function () {
        var genreId = document.getElementById('genre-id').value;
        var genreName = document.getElementById('genre-name').value;
        await fetch('/api/genre', {
            method: 'POST',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: JSON.stringify({"id": genreId,"genreName": genreName})
        })
            .then(result => {
                if (result.ok) {
                    informer('Объект успешно обновлен', true);
                } else {
                    return result.json();
                }
            }).then(data => {
                if (data['statusCode'] === 406) {
                    informer('Ошибка ' + data['statusCode'] + '. ' + data['message']['genreName'], false);
                } else {
                    informer('Что то пошло не так...', false)
                }
            })
            .catch(err => console.log(err));
        document.getElementById('clsForm').click();
        updateTableGenre('http://localhost:8080/api/genre');
    });
}