function updateTableBook(url) {
    fetch(url)
        .then(result => result.json())
        .then(data => {
            if (data.length > 0) {
                if (document.getElementById('message-table') !== null) {
                    document.getElementById('message-table').remove();
                } else {
                    document.getElementById('body-table').innerHTML = '';
                }
                for (let book of data) {
                    var creatTableLine = `<tr>
                        <td>${book['id']}</td>
                        <td>${book['bookName']}</td>
                        <td>${book['author']}</td>
                        <td>${book['genre']}</td>
                        <td class="temp" data-book-id="${book['id']}">-</td>
                        <td class="actions">
                            <button type="button" class="btn btn-outline-primary btn-sm" data-action="update" 
                                            data-id="${book['id']}" data-book-name="${book['bookName']}"
                                            data-author="${book['author']}" data-genre="${book['genre']}"
                                            data-bs-toggle="modal" data-bs-target="#exampleModal">Изменить</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" data-action="delete"
                                            data-id="${book['id']}">Удалить</button></td></tr>`;
                    document.getElementById('body-table').insertAdjacentHTML('beforeend', creatTableLine);
                }
                let tdElement = document.getElementsByClassName('temp');
                for (let i = 0; i < tdElement.length; i++) {
                    let bookId = tdElement[i].getAttribute('data-book-id');
                    fetch('http://localhost:8080/api/comment/count/book/' + bookId)
                        .then(result => result.json())
                        .then(data => tdElement[i].innerHTML = `<button type="button" 
                                                                    class="comment btn btn-outline-warning btn-sm"
                                                                     data-action="comment" data-id="${bookId}">
                                                                    Комментарии - ${data}
                                                            </button>`)
                }
                const btnComment = document.querySelectorAll('.temp');
                btnComment.forEach((comment) => {
                    comment.removeEventListener('click', clickActions);
                    comment.addEventListener('click', clickActions, {once: true});
                });
                const btnAction = document.querySelectorAll('.actions');
                btnAction.forEach((action) => {
                    action.removeEventListener('click', clickActions);
                    action.addEventListener('click', clickActions,{once: true});
                });
            } else {
                document.getElementById('body-table').innerHTML = `<tr id="message-table">
                                                                                <td colspan="6">Данных нет...</td>
                                                                                </tr>`;
            }
        })
        .catch(error => console.log(error));
}