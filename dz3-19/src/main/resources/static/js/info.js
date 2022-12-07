function info(text, result) {
    if (document.getElementById('close') !== null) {
        document.getElementById('close').remove();
    }
    let button = document.createElement('button');
    button.className = 'btn-close';
    button.type = 'button';
    button.ariaLabel = 'Close';
    button.setAttribute('data-bs-dismiss', 'alert');
    let divInfo = document.createElement('div');
    divInfo.id = 'close';
    divInfo.className = `alert ${result ? 'alert-success' : 'alert-danger'} alert-dismissible fade show mt-4`;
    divInfo.role = 'alert';
    divInfo.innerHTML = text;
    divInfo.append(button);
    document.getElementById('informer').prepend(divInfo);
}