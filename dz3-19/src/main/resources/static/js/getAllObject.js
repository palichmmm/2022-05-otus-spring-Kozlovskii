function getAllObject(url) {
    fetch(url)
        .then(result => result.json())
        .then(data => {
            if (data.length > 0) {
                if (document.getElementById('message-table') !== null) {
                    document.getElementById('message-table').remove();
                } else {
                    document.getElementById('body-table').innerHTML = '';
                }
                for (let object of data) {
                    let keys = Object.keys(object);
                    let tr = document.createElement('tr');
                    let tds = '';
                    for (let i = 0; i < keys.length; i++) {
                        tds += `<td>${object[keys[i]]}</td>`;
                    }
                    tr.innerHTML = `${tds}
                                <td class="actions">
                                    <button type="button" class="btn btn-outline-secondary btn-sm" data-action="read" data-id="${object[keys[0]]}">Просмотр</button>
                                    <button type="button" class="btn btn-outline-primary btn-sm" data-action="update" data-id="${object[keys[0]]}">Изменить</button>
                                    <button type="button" class="btn btn-outline-danger btn-sm" data-action="delete" data-id="${object[keys[0]]}">Удалить</button>
                                </td>`;
                    document.getElementById("body-table").append(tr);
                }
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