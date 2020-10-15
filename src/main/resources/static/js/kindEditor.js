function initEditor(id) {
	var keditor = KindEditor.create('textarea[id=' + id + ']', {
		cssPath : '/plugins/kindeditor/plugins/code/prettify.css',
		uploadJson : '/ueditor/imgUpdate',
        fileManagerJson: '',
		allowFileManager : true,
		height:370
	});
	prettyPrint();
	return keditor;
}
