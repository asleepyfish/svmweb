$(function () {
    function upload(data, url, number) {
        $.ajax({
            async: false,
            method: 'post',
            url: url,//传给后端@RequestMapping
            data: {"url": data, "number": number},
            datatype: 'json',//返回格式
            success: function (data) {
                $("#show").val(data);
            }
        })
    }

    $("button").bind('click', function () {
        let url = $(this).attr('formaction');
        const data = $("input").val();
        const number = $("select").val();
        upload(data, url, number)
    })
})
