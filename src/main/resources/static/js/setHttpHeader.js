function setHeader(){
    var request = new XMLHttpRequest;
    request.setRequestHeader("token", document.cookie);
}