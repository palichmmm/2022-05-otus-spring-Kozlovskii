function updateComment(url, title) {
    fetch(url)
        .then(result => result.json())
        .then(dto => {
            var exampleModal = document.getElementById('exampleModal');
            var modalTitle = exampleModal.querySelector('.modal-title');
            var modalForm = exampleModal.querySelector('form');
            modalForm.innerHTML = '';
            var keys = Object.keys(dto);
            var disabled = /id/i;
            for (let key of keys) {

                var div = document.createElement('div');
                div.className = 'md-3';

                var label = document.createElement('label');
                label.htmlFor = 'object-' + key;
                label.className = 'col-form-label';
                label.innerText = key + ':'

                var input = document.createElement('input');
                input.type = 'text';
                input.className = 'form-control';
                input.id = 'object-' + key;
                input.value = dto[key];
                input.disabled = disabled.test(key);


                modalTitle.textContent = title;
                div.append(label, input)
                modalForm.append(div);
            }
        })
        .catch(err => console.log(err));
}