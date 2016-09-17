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

    var changeDisplayImg = function(id, value) {
        var elem = $('#' + id);
        if(elem.css('display') !== value)
            $('#' + id).css('display', value);
    };

    var uploadImg = function(id, data, division) {
        $('#' + id).attr('src', data)
            .css('display', '')
            .width((100 / division) + '%')
            .height((100 / division) + '%');
    };

    var loadingIcon = function(shouldStop) {
        var loading = $('#loader');
        var result = shouldStop ? 'none' : '';
        loading.css('display', result);
    };

    $('#input1').click(function() {
        changeDisplayImg('img3', 'none');
        changeDisplayImg('img1', '');
        changeDisplayImg('img2', '');
    });

    $('#input2').click(function() {
        changeDisplayImg('img3', 'none');
        changeDisplayImg('img2', '');
        changeDisplayImg('img1', '');
    });

    $('#input1').change(function() {
        console.log('change1');
        changeDisplayImg('img3', 'none');
        if(this.files && this.files[0]) {
            console.log('change1');
            var reader = new FileReader();
            reader.onload = function(e) {
                console.log('change1');
                uploadImg('img1', e.target.result, 1);
            };

            reader.readAsDataURL(this.files[0]);
        }
        showResBtn('img2');
    });


    $('#input2').change(function() {
        changeDisplayImg('img3', 'none');
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

        loadingIcon(false);

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
                setTimeout(function() {
                    loadingIcon(true);
                    var srcVal = "data:image/png;base64," + data;
                    uploadImg('img3', srcVal, 2);
                }, 1000);
            },
            error: function (err) {
                setTimeout(function() {
                    loadingIcon(true);
                    console.log(err);
                }, 1000);
            }
        });
    });

});
