function informer(text, result) {
    if (document.getElementById('info') !== null) {
        document.getElementById('info').remove();
    }
    const creatInformerHtml = `<div id="info" class="alert ${result ? 'alert-success' : 'alert-danger'} alert-dismissible fade show mt-4" role="alert">
                              ${text}<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>`;
    document.getElementById('pointStartInformer').insertAdjacentHTML('afterbegin', creatInformerHtml);
}