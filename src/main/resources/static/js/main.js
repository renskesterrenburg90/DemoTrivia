window.onload = (event) => {
    var container = document.getElementById("container"),
        btn = document.getElementById("check-answers-button");

    if (container) {
        container.addEventListener('change', function () {

            if (document.querySelectorAll('input[type="radio"]:checked').length === 5) {
                btn.disabled = false;
            }
        }, false);
    }
}
