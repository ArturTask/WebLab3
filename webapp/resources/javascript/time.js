{
    window.onload = function () {
        let date = document.getElementById("date");
        let time = document.getElementById("time");
        date.innerHTML = new Date().toLocaleDateString();
        time.innerHTML = new Date().toLocaleTimeString();
        window.setInterval(function () {
          date.innerHTML = new Date().toLocaleDateString();
          time.innerHTML = new Date().toLocaleTimeString();
        }, 10000);
    };
}