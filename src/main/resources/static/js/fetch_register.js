
function setUser() {
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    let email = document.getElementById('email').value;
    let lastName = document.getElementById('lastName').value;
    let firstName = document.getElementById('firstName').value;
    let birthday = document.getElementById('birthday').value;

    fetch("http://localhost:8080/api/user", {
        method: "POST",
        body: JSON.stringify({
            username: username,
            password: password,
            email: email,
            lastName: lastName,
            firstName: firstName,
            birthday: birthday
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((res) => res.json())
        .then((data) => console.log(data))

}









