function save_image(chartId) {
    openImageWindow(chartId); 
}

function openImageWindow(src) {  
    var img_win = window.open('', 'Charts: Export as Image')
    with (img_win.document) {
        write('<html><head><title>Charts: Export as Image<\/title><\/head><body>' + image(src) + '<\/body><\/html>') 
    }
    // stop the 'loading...' message
    img_win.document.close();
}               

function image(src) {
    return "<img src='data:image/png;base64," + $('#' + src)[0].get_img_binary() + "' />"
}
