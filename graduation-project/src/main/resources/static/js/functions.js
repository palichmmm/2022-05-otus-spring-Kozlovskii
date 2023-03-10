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