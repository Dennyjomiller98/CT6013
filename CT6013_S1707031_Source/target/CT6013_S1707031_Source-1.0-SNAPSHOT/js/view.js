$( document ).ready(function() {
    console.log( "view.js loaded" );

    $('.newSearch').on('click', function(e){
        e.preventDefault();
        $('#data-retrieved').attr('style', 'display:none');
        $('#data-selection').attr('style', 'display:inline-block');
    });

    $('#select').on('change', function(e){
        console.log("I am clicked")
        e.preventDefault();
        let selectedVal = $(this).val();
        if (selectedVal === "q1") {
            showCourseYear();
        }
        else if (selectedVal === "q2") {
            showCourseYear();
        }
        else if (selectedVal === "q3") {
            showCourseYear();
        }
        else if (selectedVal === "q4") {
            showCourseYearTutor();
        }
        else if (selectedVal === "q5") {
            showCourseYear();
        }
        else if (selectedVal === "q6") {
            showYear();
        }
        else if (selectedVal === "q7") {
            showYear();
        }
        else if (selectedVal === "q8") {
            showCourseYear();
        }
        else if (selectedVal === "q9") {
            showCourseYear();
        }
        else if (selectedVal === "q10") {
            showYear();
        }
    });
});

function showCourseYear() {
    $('.yearSelect').attr('style', 'display:inline-block');
    $('.courseSelect').attr('style', 'display:inline-block');
    $('.tutorSelect').attr('style', 'display:none');
}

function showYear() {
    $('.yearSelect').attr('style', 'display:inline-block');
    $('.courseSelect').attr('style', 'display:none');
    $('.tutorSelect').attr('style', 'display:none');
}

function showCourseYearTutor() {
    $('.yearSelect').attr('style', 'display:inline-block');
    $('.courseSelect').attr('style', 'display:inline-block');
    $('.tutorSelect').attr('style', 'display:inline-block');
}