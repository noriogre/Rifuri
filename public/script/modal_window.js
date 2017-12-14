$(function(){
    // JavaScript で表示
    $('#modal-example').on('click', function(){
      $('#staticModal').modal();
    });
    // ダイアログ表示前にJavaScriptで操作する
    $('#modal-example').on('show.bs.modal', function (event) {
      //カンマ区切り複数パラメータ
      var button = $(event.relatedTarget);
      var str = button.data('whatever');     
      var Arraystr = str.split(","); 
      alert('**'+Arraystr[1]);  
  //   $(':text[name="name"]').val(Arraystr[0]);
  //   $(':text[name="Email"]').val(Arraystr[1]);
    });    
});
