function updateBook(url, title, btnData) {
    var id = btnData.dataset.id;
    var bookName = btnData.dataset.bookName;
    var authorName = btnData.dataset.author;
    var genreName = btnData.dataset.genre;
    document.querySelector('.modal-body').innerHTML = '';
    var creatForm = `<form>
                         <div class="mb-3">
                            <label for="book-id" class="col-form-label">Id:</label>
                            <input type="text" class="form-control" id="book-id"  value="${id}" disabled>
                         </div>
                         <div class="mb-3">
                            <label for="book-name" class="col-form-label">Название:</label>
                            <input type="text" class="form-control" id="book-name" value="${bookName}">
                         </div>
                         <div id="book-author-form" class="mb-3">
                            <label for="book-author" class="col-form-label">Автор:</label>
                            <input type="text" class="form-control" id="book-author" value="${authorName}">
                         </div>
                         <div id="book-genre-form" class="mb-3">
                            <label for="book-genre" class="col-form-label">Жанр:</label>
                            <input type="text" class="form-control" id="book-genre" value="${genreName}">
                         </div>
                    </form>`;
    document.querySelector('.modal-body').insertAdjacentHTML('beforeend', creatForm);
    var exampleModal = document.getElementById('exampleModal');
    var modalTitle = exampleModal.querySelector('.modal-title');
    modalTitle.textContent = title;

    fetch('http://localhost:8080/api/author')
        .then(result => result.json())
        .then(data => {
            var options = '';
            for (var author of data) {
                options += `<option ${author['authorName'] === authorName ? 'selected' : ''} 
                                ${author['authorName'] === authorName ? 'style="color: green;"' : ''}
                                value="${author['authorName']}">${author['authorName']}</option>`;
            }
            document.getElementById('book-author-form').innerHTML = '';
            document.getElementById('book-author-form').insertAdjacentHTML('beforeend',
                `<label for="book-author" class="col-form-label">Автор:</label>
                                   <select id="book-author" class="form-select">${options}</select>`);
        })
        .catch(error => console.log(error));

    fetch('http://localhost:8080/api/genre')
        .then(result => result.json())
        .then(data => {
            var options = '';
            for (var genre of data) {
                options += `<option ${genre['genreName'] === genreName ? 'selected' : ''} 
                                ${genre['genreName'] === genreName ? 'style="color: green;"' : ''}
                                value="${genre['genreName']}">${genre['genreName']}</option>`;
            }
            document.getElementById('book-genre-form').innerHTML = '';
            document.getElementById('book-genre-form').insertAdjacentHTML('beforeend',
                `<label for="book-genre" class="col-form-label">Жанр:</label>
                                   <select id="book-genre" class="form-select">${options}</select>`);
            const btnSave = document.getElementById('subForm');
            btnSave.addEventListener('click', function () {

            });
        })
        .catch(error => console.log(error));
}