function getAllBook() {
    fetch('http://localhost:8080/api/book')
        .then(result => result.json())
        .then(data => {
            if (data.length > 0) {
                if (document.getElementById('message-table') !== null) {
                    document.getElementById('message-table').remove();
                } else {
                    document.getElementById('body-table').innerHTML = '';
                }
                for (let book of data) {
                    let tr = document.createElement('tr');
                    tr.innerHTML = `<td>${book.id}</td>
                                <td>${book.bookName}</td>
                                <td>${book.author}</td>
                                <td>${book.genre}</td>
                                <td class="temp" data-book-id="${book.id}">-</td>
                                <td class="actions">
                                    <button type="button" class="btn btn-outline-secondary btn-sm" data-action="read" data-id="${book.id}">Просмотр</button>
                                    <button type="button" class="btn btn-outline-primary btn-sm" data-action="update" data-id="${book.id}">Изменить</button>
                                    <button type="button" class="btn btn-outline-danger btn-sm" data-action="delete" data-id="${book.id}">Удалить</button>
                                </td>`;
                    document.getElementById("body-table").append(tr);
                }
                let tdElement = document.getElementsByClassName('temp');
                let tdCount = tdElement.length;
                for (let i = 0; i < tdCount; i++) {
                    let bookId = tdElement[i].getAttribute('data-book-id');
                    fetch('http://localhost:8080/api/comment/count/book/' + bookId)
                        .then(result => result.json())
                        .then(data => tdElement[i].innerHTML = `<button type="button" 
                                                                    class="comment btn btn-outline-warning btn-sm"
                                                                     data-action="comment">
                                                                    Комментарии(${data})
                                                            </button>`)
                }
                const buttons = document.querySelectorAll('.temp');
                buttons.forEach((button) => {
                    button.addEventListener('click', handleClick);
                });
                const actions = document.querySelectorAll('.actions');
                actions.forEach((action) => {
                    action.addEventListener('click', handleClick);
                });
            } else {
                document.getElementById('body-table').innerHTML = `<tr id="message-table">
                                                                                <td colspan="6">Данных нет...</td>
                                                                                </tr>`;
            }
        })
        .catch(error => console.log(error));
}