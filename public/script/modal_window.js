$(function(){
    // JavaScript �ŕ\��
    $('#modal-example').on('click', function(){
      $('#staticModal').modal();
    });
    // �_�C�A���O�\���O��JavaScript�ő��삷��
    $('#modal-example').on('show.bs.modal', function (event) {
      //�J���}��؂蕡���p�����[�^
      var button = $(event.relatedTarget);
      var str = button.data('whatever');     
      var Arraystr = str.split(","); 
      alert('**'+Arraystr[1]);  
  //   $(':text[name="name"]').val(Arraystr[0]);
  //   $(':text[name="Email"]').val(Arraystr[1]);
    });    
});
