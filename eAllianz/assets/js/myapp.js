var load = function() {
    alert('hi');
    $('.side-collapse').sideNav({
        menuWidth: 300, // Default is 240
        edge: 'left', // Choose the horizontal origin
        closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
        draggable: true // Choose whether you can drag to open on touch screens
        }
    );
    
    $('.button-collapse').sideNav({
        edge: 'right'
    });      
}
load();