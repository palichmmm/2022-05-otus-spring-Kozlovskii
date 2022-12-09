function updateComment(url, title, btnData) {
    var id = btnData.dataset.id;
    var comment = btnData.dataset.comment;
    document.querySelector('.modal-body').innerHTML = '';
    var creatForm = `<form>
                         <div class="mb-3">
                            <label for="comment-id" class="col-form-label">Id:</label>
                            <input type="text" class="form-control" id="comment-id"  value="${id}" disabled>
                         </div>
                         <div class="mb-3">
                            <label for="comment-name" class="col-form-label">Название:</label>
                            <input type="text" class="form-control" id="comment-name" value="${comment}">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal')
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;
}