/**
 * Created by Pogorelov on 9/15/2016.
 */

$(document).ready(function () {
    var showResBtn = function(id) {
        var rsltBtn =  $('#resultBtn');
        if($('#' + id).width() != 0 && rsltBtn.css('display') == 'none') {
            rsltBtn.css('display', '');
        }
    };

    var hideImg3 = function() {
        $('#img3').css('display', 'none');
    };

    var uploadImg = function(id, data, division) {
        $('#' + id).attr('src', data)
            .css('display', '')
            .width((100 / division) + '%')
            .height((100 / division) + '%');
    };

    $('#input1').change(function() {
        hideImg3();
        if(this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                uploadImg('img1', e.target.result, 1);
            };

            reader.readAsDataURL(this.files[0]);
        }
        showResBtn('img2');
    });


    $('#input2').change(function() {
        hideImg3();
        if(this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                uploadImg('img2', e.target.result, 1);
            };

            reader.readAsDataURL(this.files[0]);
        }
        showResBtn('img1');
    });

    $('#resultBtn').click(function() {
        $('#img1').css('display', 'none');
        $('#img2').css('display', 'none');
        $('#resultBtn').css('display', 'none');

        var img1 = $('#input1')[0].files[0];
        var img2 = $('#input2')[0].files[0];

        var formData = new FormData();
        formData.append('image1', img1);
        formData.append('image2', img2);

        $.ajax({
            url: '/rest/images/compare',
            data: formData,
            processData: false,
            contentType: false,
            type: 'POST',
            success: function (data) {
                var srcVal = "data:image/png;base64," + data;
                uploadImg('img3', srcVal, 2);
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

});
