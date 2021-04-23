$(function () {
    function upload(data, url) {
        $.ajax({
            async: false,
            method: 'post',
            url: url,//传给后端@RequestMapping
            data: data,//参数可以有多种写法
            datatype: 'json',//返回格式
            success: function (data) {
                $("#show").val(data);
            }
        })
    }

    $("button").bind('click', function () {
        let url = $(this).attr('formaction');
        const data = $("#Form").serialize();
        upload(data, url)
    })
})
