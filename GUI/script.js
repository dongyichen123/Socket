// JavaScript 函数，用于发送 HTTP 请求
function sendRequest() {
    // 获取用户输入的请求方法、URL 和 POST 数据
    var method = document.getElementById('method').value;
    var url = document.getElementById('url').value;
    var data = document.getElementById('data').value;
    var responseArea = document.getElementById('response');

    // 创建 XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open(method, url, true);

    // 监听请求状态变化
    xhr.onreadystatechange = function() {
        // 当请求完成时（状态码为 4），更新响应文本区域
        if (xhr.readyState == 4) {
            responseArea.value = 'Status Code: ' + xhr.status + '\n' + xhr.responseText;
        }
    };

    // 如果是 POST 请求，设置请求头和发送 POST 数据
    if (method === 'POST') {
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send(data);
    } else {
        // 对于 GET 请求，直接发送请求
        xhr.send();
    }
}
