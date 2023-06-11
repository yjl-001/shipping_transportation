function transView(url){
    $.ajax({
        url: url,
        type: 'get',
        dataType:'html',
        headers: { 'token': document.token },
        success:function(responce){
            $("html").html(responce);
        }
    })
}