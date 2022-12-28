function updateAuthor(url, title, btnData) {
    var id = btnData.dataset.id;
    var authorName = btnData.dataset.authorName;
    document.querySelector('.modal-body').innerHTML = '';
    var creatForm = `<form>
                         <div class="mb-3">
                            <label for="author-id" class="col-form-label">Id:</label>
                            <input type="text" class="form-control" id="author-id"  value="${id}" disabled>
                         </div>
                         <div class="mb-3">
                            <label for="author-name" class="col-form-label">Имя автора:</label>
                            <input type="text" class="form-control" id="author-name" value="${authorName}">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;

    const btnSave = document.getElementById('subForm');
    btnSave.removeEventListener('click', runUpdateAuthor);
    btnSave.addEventListener('click', runUpdateAuthor, {once: true});
}

async function runUpdateAuthor() {
    var authorId = document.getElementById('author-id').value;
    var authorName = document.getElementById('author-name').value;
    await fetch('/api/author', {
        method: 'PUT',
        headers: {"Content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify({"id": authorId, "authorName": authorName})
    })
        .then(result => {
            if (result.ok) {
                informer('Автор успешно обновлен', true);
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
                        informer('Ошибка ' + data['statusCode'] + '. ' + data['message']['authorName'], false);
                        break;
                    default:
                        informer('Что то пошло не так...', false);
                }
            }
        })
        .catch(err => console.log(err));
    document.getElementById('clsForm').click();
    updateTableAuthor('http://localhost:8080/api/author');
    countObjectUpdateTextById('http://localhost:8080/api/author/count','author');
}