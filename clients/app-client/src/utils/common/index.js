const getUrlParams = function (url) {
    const params = {};
    if (url.indexOf("?") !== -1) {
        const str = url.split('?')[1];
        str.split("&").forEach(s => {
            if(s.includes('=')) {
                const arr = s.split('=');
                params[arr[0]] = unescape(arr[1]);
            }
        })
    }
    return params;
}

export default {
    getUrlParams
}