function creatComment(url, title, btnData) {
    var bookId = btnData.dataset.bookId;
    var bookName = btnData.dataset.bookName;
    document.querySelector('.modal-body').innerHTML = '';
    var creatForm = `<form>
                         <div class="mb-3">
                            <input type="hidden" class="form-control" id="book-id"  value="${bookId}">
                         </div>
                         <div class="mb-3">
                            <label for="comment" class="col-form-label">Комментарий:</label>
                            <input type="text" class="form-control" id="comment" value="">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title + bookName;

    const btnSave = document.getElementById('subForm');
    btnSave.removeEventListener('click', runCreatComment);
    btnSave.addEventListener('click', runCreatComment, {once: true});
}

async function runCreatComment() {
    var bookId = document.getElementById('book-id').value;
    var comment = document.getElementById('comment').value;
    await fetch('/api/comment/book/' + bookId, {
        method: 'POST',
        headers: {"Content-type": "application/json; charset=UTF-8"},
        body: JSON.stringify({"comment": comment})
    })
        .then(result => {
            if (result.ok) {
                informer('Комментарий успешно создан', true);
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
                        informer('Ошибка ' + data['statusCode'] + '. ' + data['message']['comment'], false);
                        break;
                    default:
                        informer('Что то пошло не так...', false);
                }
            }
        })
        .catch(err => console.log(err));
    document.getElementById('clsForm').click();
    updateTableComment('http://localhost:8080/api/comment/book/' + bookId);
}
