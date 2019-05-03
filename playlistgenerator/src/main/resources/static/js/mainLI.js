// // Get the beerModal
// var signUpModal = document.getElementById('signUpModal');
//
// // Get the button that opens the beerModal
// var btn = document.getElementById("signup");
//
// // Get the <span> elements that closes the modals
// var signUpSpan = document.getElementById("singUpClose");
//
// // When the user clicks on <span> (x), close the beerModal
// signUpSpan.onclick = function () {
//     signUpModal.style.display = "none";
// }
//
// // When the user clicks anywhere outside of the beerModal, close it
// window.onclick = function (event) {
//     if (event.target == signUpModal) {
//         signUpModal.style.display = "none";
//     }
// }
//
// $(document).keyup(function (e) {
//     if (e.key === "Escape") {
//         signUpModal.style.display = "none";
//     }
// });
//
// function displaySignUpModal() {
//     signUpModal.style.display = "block";
// }


$(function(){
    $("#signup").click(function(){
        $(".signup").modal('show');
    });
    $(".signup").modal({
        closable: true
    });
});