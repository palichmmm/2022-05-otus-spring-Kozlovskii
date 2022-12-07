fetch('http://localhost:8080/api/author/count',
    {
        method: 'GET'
    })
    .then(result => result.json())
    .then(data => document.getElementById("author").innerHTML = 'Авторы <span class="badge bg-secondary">' + data + '</span>')
    .catch(error => console.log(error));
fetch('http://localhost:8080/api/genre/count',
    {
        method: 'GET'
    })
    .then(result => result.json())
    .then(data => document.getElementById("genre").innerHTML = 'Жанры <span class="badge bg-secondary">' + data + '</span>')
    .catch(error => console.log(error));
fetch('http://localhost:8080/api/book/count',
    {
        method: 'GET'
    })
    .then(result => result.json())
    .then(data => document.getElementById("book").innerHTML = 'Книги <span class="badge bg-secondary">' + data + '</span>')
    .catch(error => console.log(error));
