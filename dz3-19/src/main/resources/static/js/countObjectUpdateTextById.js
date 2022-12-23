function countObjectUpdateTextById(url, id) {
    fetch(url)
        .then(result => result.json())
        .then(data => document.getElementById(id).innerHTML = data)
        .catch(error => console.log(error));
}