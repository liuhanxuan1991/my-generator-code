/**
 * Created by Qinghua on 2018/1/4 16:56.
 */
CKEDITOR.editorConfig = function (config) {
    config.skin = 'moono-lisa';
    config.toolbar = [
        {name: 'document', items: ['Undo', 'Redo']},
        {name: 'basicstyles', items: ['Bold', 'Italic', 'Underline', 'Strike']},
        {name: 'colors', items: ['TextColor', 'BGColor']},
        {name: 'list', items: ['NumberedList', 'BulletedList']},
        {name: 'links', items: ['Link', 'Unlink', 'Anchor', 'Image']},
        {name: 'paragraph', items: ['Outdent', 'Indent', 'Blockquote']},
        {name: 'justify', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']},
        {name: 'insert', items: ['Table', 'HorizontalRule']},
        {name: 'styles', items: ['Format', 'Font', 'FontSize']},
        {name: 'clipboard', items: ['Templates']}
    ];
    config.height = 300;

    config.extraPlugins = 'uploadimage,image';
    config.filebrowserUploadUrl = '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images';
};

var globalEditorConfig = {
    skin: 'moono-lisa',
    extraPlugins: 'uploadimage,image',
    toolbar: [
        {name: 'document', items: ['Undo', 'Redo', 'PasteCode']},
        {name: 'basicstyles', items: ['Bold', 'Italic', 'Underline', 'Strike']},
        {name: 'colors', items: ['TextColor', 'BGColor']},
        {name: 'list', items: ['NumberedList', 'BulletedList']},
        {name: 'links', items: ['Link', 'Unlink', 'Anchor', 'Image']},
        {name: 'paragraph', items: ['Outdent', 'Indent', 'Blockquote']},
        {name: 'justify', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']},
        {name: 'insert', items: ['Table', 'HorizontalRule']},
        {name: 'styles', items: ['Format', 'Font', 'FontSize']},
        {name: 'clipboard', items: ['Templates']}
    ],
    height: 300,
    filebrowserUploadUrl: '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files',
    filebrowserImageUploadUrl: '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images',
};

CKEDITOR.on('instanceReady', function (evt) {
    var editor = evt.editor;
    var cfg = chainGlobalConfig || {};
    editor.on('fileUploadRequest', function (evt) {
        var fileLoader = evt.data.fileLoader;
        var formData = new FormData();
        var xhr = fileLoader.xhr;

        xhr.open('POST', cfg.imgUploadUrl, true);
        formData.append("file", fileLoader.file);
        xhr.send(formData);
        //Prevent default event
        evt.stop();
    }, null, null, 4);

    editor.on('fileUploadResponse', function (evt) {
        // Prevent the default response handler.
        evt.stop();
        // Get XHR and response.
        var data = evt.data,
            xhr = data.fileLoader.xhr,
            response = xhr.responseText.split('|');
        if (response[1]) {
            // An error occurred during upload.
            data.message = response[1];
            evt.cancel();
        } else {
            data.url = JSON.parse(response[0]).url;
        }

    });
});
