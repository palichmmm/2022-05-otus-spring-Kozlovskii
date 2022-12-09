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
}