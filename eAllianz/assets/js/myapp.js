var load = function () {
    //alert('hi');
    $('.side-collapse').sideNav({
        menuWidth: 300, // Default is 240
        edge: 'left', // Choose the horizontal origin
        closeOnClick: false, // Closes side-nav on <a> clicks, useful for Angular/Meteor
        draggable: true // Choose whether you can drag to open on touch screens
    }
    );

    $('.button-collapse').sideNav({
        edge: 'right'
    });


    // to initialize select 
    $('select').material_select();

    // initialize date picker
    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 100, // Creates a dropdown of 15 years to control year
        format: 'yyyy-mm-dd'
    });
   
    
    // form with pre-filled value to be updated so label is above text-field
    Materialize.updateTextFields(); 

}


$(function() {
    load();    
})
