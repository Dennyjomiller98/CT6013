$( document ).ready(function() {
    console.log( "view.js loaded" );

    $('.newSearch').on('click', function(e){
        e.preventDefault();
        $('#data-retrieved').attr('display', 'none');
        $('#data-selection').attr('display', 'inline-block');
    });
});