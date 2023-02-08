$('#picModal').on('show.bs.modal', function (e) {
    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
    $('#modal-deleteHiddenId').val($(this).find('.btn-ok').attr('href'));
});