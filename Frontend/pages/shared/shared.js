export default () => {
    const loginButton = document.querySelector("#loginButton");
    const logoutButton = document.querySelector("#logout");

    logoutButton.addEventListener("click", logout);

    checkForUser(loginButton, logoutButton);
  };

function checkForUser(loginButton, logoutButton){
    if(localStorage.getItem("user") != null){
        loginButton.style.display = "none";
        logoutButton.style.display = "block";
    }
    else{
        loginButton.style.display = "block";
        logoutButton.style.display = "none";
    }
}

function logout(){
    localStorage.removeItem("user");
}