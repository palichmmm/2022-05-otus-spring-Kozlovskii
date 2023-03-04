function upload(files, csrf_token) {
    let formData = new FormData();
    formData.append('file', files);
    formData.append('_csrf', csrf_token);
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: '/api/upload/form',
        body: formData,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        beforeSend: function () {
            $('section').removeClass('dd');

            // Перед загрузкой файла удалить старые ошибки и показать индикатор
            $('.error').text('').hide();
            $('.progress').show();

            // Установить прогресс-бар на 0
            $('.progress-bar').css('width', '0');
            $('.progress-value').text('0 %');
        },
        success: function (data) {
            if (data.Error) {
                $('.error').text(data.Error).show();
                $('.progress').hide();
            } else {
                $('.progress-bar').css('width', '100%');
                $('.progress-value').text('100 %');
            }
        },
        xhrFields: { // Отслеживаем процесс загрузки файлов
            onprogress: function (e) {
                if (e.lengthComputable) {
                    // Отображение процентов и длины прогресс бара
                    var perc = e.loaded / 100 * e.total;
                    $('.progress-bar').css('width', perc + '%');
                    $('.progress-value').text(perc + ' %');
                }
            }
        },
    });
}