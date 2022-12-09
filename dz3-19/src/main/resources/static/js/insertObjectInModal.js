function insertObjectInModal(url, header, body, footer) {
    var exampleModal = document.getElementById('exampleModal')
    exampleModal.addEventListener('show.bs.modal', function (event) {

        // Кнопка, которая активировала модальное окно
        var button = event.relatedTarget
        // Извлечь информацию из атрибутов data-bs-*
        var id = button.getAttribute('data-id')


        // При необходимости здесь можно инициировать AJAX-запрос
        // а затем выполнить обновление в обратном вызове
        var modalTitle = exampleModal.querySelector('.modal-title')
        var modalBodyInput = exampleModal.querySelector('.modal-body input')

        modalTitle.textContent = 'New message to ' + id
        modalBodyInput.value = id
    })
}