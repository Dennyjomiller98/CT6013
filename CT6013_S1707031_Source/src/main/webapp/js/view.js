$( document ).ready(function() {
    console.log( "view.js loaded" );

    $('.newSearch').on('click', function(e){
        console.log("I am clicked")
        e.preventDefault();
        $('#data-retrieved').attr('display', 'none');
        $('#data-selection').attr('display', 'inline-block');
    });
});