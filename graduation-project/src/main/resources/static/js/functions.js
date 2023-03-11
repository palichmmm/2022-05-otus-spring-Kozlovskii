function sizeFileFormat(size) {
    let sizeKb = 1024;
    let sizeMb = sizeKb * sizeKb;
    if (size < sizeMb)
        return (size / sizeKb).toFixed(2) + " Kb";
    else {
        return (size / sizeMb).toFixed(2) + " Mb";
    }
}
function playTimeFile(seconds) {
    let sec = Math.floor(seconds % 60);
    let min = Math.floor((seconds % 3600) / 60);
    let hour = Math.floor(seconds / 3600);
    if (hour > 0)
        return hour + ':' + min + ':' + sec.toString().padStart(2, '0');
    else {
        return min + ':' + sec.toString().padStart(2, '0');
    }
}
function createTableRow(data) {
    if (data.length > 0) {
        $('#t_body').empty();
        let i = 1;
        for (let file of data) {
            let link = $('<a class="ms-3 text-decoration-none" href="/download/' + file['fileName'] + '"></a>')
                .append('<i class="bi bi-download text-secondary"></i>');
            let tdAction = $('<td></td>')
                .append('<i class="ms-0 bi bi-arrows-expand text-info between" role="button" data-id="' + file['id'] + '"></i>')
                .append('<i class="ms-2 bi bi-arrow-down-up text-success goto" role="button" data-id="' + file['id'] + '"></i>')
                .append(link)
                .append('<i class="ms-2 bi bi-trash text-danger delete" role="button" data-id="' + file['id'] + '"></i>');
            let tdPlay = $('<td></td>')
                .append('<i class="bi bi-caret-right-fill play" role="button" data-url="' + file['url'] + '"></i>')
                .append('<i class="bi bi-pause-fill text-danger pause d-none" role="button"></i>');
            let creatTableLine = $('<tr id="' + file['id'] + '" className="line"></tr>')
                .append('<td>' + i++ + '</td>')
                .append(tdPlay)
                .append('<td>' + file['serialNumber'] + '</td>')
                .append('<td class="name" data-id="' + file['id'] + '">' + file['outputName'] + '</td>')
                .append('<td>' + file['extension'] + '</td>')
                .append('<td>' + playTimeFile(file['tag']['playTime']) + '</td>')
                .append('<td>' + sizeFileFormat(file['size']) + '</td>')
                .append('<td>' + file['tag']['bitrate'] + ' kbps</td>')
                .append(tdAction);
            $('#t_body').append(creatTableLine);
        }
    }
}