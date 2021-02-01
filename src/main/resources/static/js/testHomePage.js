let count = 0

function flagChange(id) {
    let flagOff = document.getElementById(id);
    let flagOn = document.getElementById(id + "On");

    let submitBtn = document.getElementById("testSubmit")

    if (flagOff.style.display === "block") {
        flagOff.style.display = "none";
        flagOn.style.display = "block";
        count++;
        console.log(count)
    } else {
        flagOff.style.display = "block";
        flagOn.style.display = "none";
        count--;
        console.log(count)
    }

    if (count !== 0) {
        submitBtn.disabled = true;
        submitBtn.className = "test__btn__disabled"
    }else {
        submitBtn.disabled = false;
        submitBtn.className = "test__btn"
    }
}