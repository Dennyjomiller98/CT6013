$( document ).ready(function() {
    console.log( "view.js loaded" );

    $('.newSearch').on('click', function(e){
        console.log("I am clicked")
        e.preventDefault();
        $('#data-retrieved').attr('style', 'display:none');
        $('#data-selection').attr('style', 'display:inline-block');
    });
});