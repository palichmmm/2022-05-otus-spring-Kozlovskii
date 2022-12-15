function creatAuthor(url, title) {
    document.querySelector('.modal-body').innerHTML = '';
    var creatForm = `<form>
                         <div class="mb-3">
                            <label for="author-name" class="col-form-label">Имя автора:</label>
                            <input type="text" class="form-control" id="author-name" value="">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;

    const btnSave = document.getElementById('subForm');
    btnSave.removeEventListener('click', runCreatAuthor);
    btnSave.addEventListener('click', runCreatAuthor, {once: true});
}
async function runCreatAuthor() {
    var authorName = document.getElementById('author-name').value;
    await fetch('/api/author', {
        method: 'POST',
        headers: {'Content-type': 'application/json; charset=UTF-8'},
        body: JSON.stringify({"authorName": authorName})
    })
        .then(result => {
            if (result.ok) {
                informer('Автор успешно создан', true);
            }
            return result.json();
        })
        .then(data => {
            if ('message' in data) {
                switch (data['statusCode']) {
                    case 404:
                        informer('Ошибка ' +data['statusCode'] + '. ' + data['message']['error'], false);
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