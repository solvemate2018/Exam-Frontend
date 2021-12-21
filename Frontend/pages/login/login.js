import shared from"/pages/shared/shared.js"
export default () => {
    const content = document.querySelector(".content");
  
    return fetch("./pages/login/login.html")
      .then((response) => response.text())
      .then((loginHtml) => {
        content.innerHTML = loginHtml;
        shared();
        handleLoginFunctionality();
      });
  };

  function handleLoginFunctionality(){
      const form = document.querySelector("form");
      form.addEventListener("submit", (event) => {
        event.preventDefault();
        console.log(document.querySelector('#login').value);
        console.log(document.querySelector('#password').value);
    
        fetch(`${window.apiUrl}/api/auth/signin`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            username: document.querySelector('#login').value,
            password: document.querySelector('#password').value,
          }),
        })
          .then((response) => {
            if(response.status == "401"){
              window.alert("Invalid login request!");
            }
            return response.json()
          })
          .then((data) => {
            if (data.accessToken) {
              // Saving the JWT to local storage
              localStorage.setItem("user", JSON.stringify(data));
              // navigating to the users route. Using the global window.router
              window.router.navigate("/");
            }
          }).catch((error) => {
            console.error('Error:', error);
          });
      });
  }