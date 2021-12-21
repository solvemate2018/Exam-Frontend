import shared from "/pages/shared/shared.js"
export default () => {
    const content = document.querySelector(".content");

    return fetch("./pages/party/create/create.html")
        .then((response) => response.text())
        .then((loginHtml) => {
            content.innerHTML = loginHtml;
            shared();
            generateContent();
        });
};

function generateContent() {
    const nameInput = document.querySelector("#party-name");
    const form = document.querySelector("form");

    form.addEventListener("submit", (event) => createParty(event, nameInput));
}


function createParty(event, nameInput){
    event.preventDefault();
    console.log(nameInput);
    
    event.preventDefault();
        fetch(`${window.apiUrl}/api/party`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + JSON.parse(localStorage.getItem("user")).accessToken,
            },
            body: JSON.stringify({
              name: nameInput.value,
            }),
          })
            .then((response) => console.log(response))
            .then(() => {
                window.router.navigate("/");
                window.alert("Succesfully Created");
            })
            .catch((error) => {
              console.error('Error:', error.me);
            });
    }