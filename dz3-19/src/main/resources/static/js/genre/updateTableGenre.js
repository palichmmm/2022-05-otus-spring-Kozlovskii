function updateTableGenre(url) {
    fetch(url)
        .then(result => result.json())
        .then(data => {
            if (data.length > 0) {
                if (document.getElementById('message-table') !== null) {
                    document.getElementById('message-table').remove();
                } else {
                    document.getElementById('body-table').innerHTML = '';
                }
                for (let genre of data) {
                    var creatTableLine = `<tr>
                        <td>${genre['id']}</td>
                        <td>${genre['genreName']}</td>
                        <td class="actions">
                            <button type="button" class="btn btn-outline-primary btn-sm" data-action="update" 
                                            data-id="${genre['id']}" data-genre-name="${genre['genreName']}"
                                            data-bs-toggle="modal" data-bs-target="#exampleModal">Изменить</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" data-action="delete"
                                            data-id="${genre['id']}">Удалить</button></td></tr>`;
                    document.getElementById('body-table').insertAdjacentHTML('beforeend', creatTableLine);
                }
                const actions = document.querySelectorAll('.actions');
                actions.forEach((action) => {
                    action.addEventListener('click', clickActions);
                });
            } else {
                document.getElementById('body-table').innerHTML = `<tr id="message-table">
                                                                                <td colspan="6">Данных нет...</td>
                                                                                </tr>`;
            }
        })
        .catch(error => console.log(error));
}