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
                            <label for="author-name" class="col-form-label">Название:</label>
                            <input type="text" class="form-control" id="author-name" value="${authorName}">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;

    const btnSave = document.getElementById('subForm');
    btnSave.addEventListener('click', async function () {
        var authorId = document.getElementById('author-id').value;
        var authorName = document.getElementById('author-name').value;
        await fetch('/api/author', {
            method: 'POST',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: JSON.stringify({"id": authorId,"authorName": authorName})
        })
            .then(result => {
                if (result.ok) {
                    informer('Объект успешно обновлен', true);
                } else {
                    informer('Ошибка обновления - ' + result.status, false);
                    return result.json();
                }
            }).then(data => console.log(data))
            .catch(err => console.log(err));
        document.getElementById('clsForm').click();
        updateTableAuthor('http://localhost:8080/api/author');
    });
}