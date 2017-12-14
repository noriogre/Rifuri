$(function() {
    $(".modal-open").on("click", function() {
//------------------------------------------------------------------
// �L�[�{�[�h����Ȃǂɂ��A�I�[�o�[���C�����d�N������̂�h�~����
//------------------------------------------------------------------
    // �{�^������t�H�[�J�X���O��
        $(this).blur();
    // �V�������[�_���E�B���h�E���N�����Ȃ�
        if($("#modal-overlay")[0]) return false;
    // ���݂̃��[�_���E�B���h�E���폜���ĐV�����N������
        // if($("#modal-overlay")[0]) $("#modal-overlay").remove();
    // �I�[�o�[���C�p��HTML�R�[�h���A[body]���̍Ō�ɐ�������
        $("body").append('<div id="modal-overlay"></div>');
    // #modal-overlay �y�� #modalcontent ���t�F�[�h�C��������
        $("#modal-overlay, #modal-content").fadeIn("normal");
        centeringModalSyncer();

//------------------------------------------------------------------
// #modal-overlay �܂��� #modal-close �̃N���b�N���Ɏ��s���鏈��
//------------------------------------------------------------------
        $("#modal-overlay, #modal-close").off().on("click", function() {
        // #modal-overlay �y�� #modal-close ���t�F�[�h�A�E�g����
            $("#modal-content, #modal-overlay").fadeOut("slow", function() {
            // �t�F�[�h�A�E�g��A #modal-overlay ��HTML(DOM)�ォ��폜
                $("#modal-overlay").remove();
            });
        });

//------------------------------------------------------------------
// ���T�C�Y����������ۂɁA���[�_���E�B���h�E�𒆉��񂹂ɂ���
//------------------------------------------------------------------
    // Case.1 ���T�C�Y����̓x�Ɏ��s����ꍇ
        // $(window).resize(centeringModalSyncer);
    // Case.2 ���T�C�Y���삪�I�������Ƃ��̂ݎ��s����ꍇ
        var timer = false;
        $(window).resize(function() {
            if (timer !== false) clearTimeout(timer);
            timer = setTimeout(function() {
                centeringModalSyncer();
            }, 200);
        });

    //------------------------------------------------------------------
    // ���[�_���E�B���h�E�𒆉��񂹂���֐�
    //------------------------------------------------------------------
        function centeringModalSyncer() {
        // ���(�E�B���h�E)�̕��A�������擾
            var w = $(window).width();
            var h = window.innerHeight;

        // �R���e���c(#modal-content)�̕��A�������擾
        // Case.1 margin �܂߂�ꍇ
            // var cw = $("#modal-content").outerWidth(true);
            // var ch = $("#modal-content").outerHeight(true);
        // Case.2 margin �܂߂Ȃ��ꍇ
            var cw = $("#modal-content").outerWidth();
            var ch = $("#modal-content").outerHeight();

        // #modal-content ��^�񒆂ɔz�u����̂ɁA���[�Ə㕔���牽�s�N�Z�������΂������H���v�Z����CSS�̃|�W�V������ݒ肷��
        // Case.1 left �� top �ŕϐ��𕪂���
        /*
            var pxleft = ((w - cw) / 2);
            var pxtop  = ((h - ch) / 2);
            $("#modal-content").css({
                "left":pxleft + "px",
                "top":pxtop + "px"
            });
        */
        // Case.2 �v���p�e�B���������Ĉ�̕ϐ��ɓZ�߂�
            var p_prop = {
                left:((w - cw) / 2),
                top:((h - ch) / 2)
            };
            $("#modal-content").css(p_prop);
        }
    });
});
