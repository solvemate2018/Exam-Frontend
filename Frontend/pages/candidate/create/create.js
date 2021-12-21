import shared from "/pages/shared/shared.js"
export default () => {
    const content = document.querySelector(".content");

    return fetch("./pages/candidate/create/create.html")
        .then((response) => response.text())
        .then((loginHtml) => {
            content.innerHTML = loginHtml;
            shared();
            generateContent();
        });
};

function generateContent() {
    const fullNameInput = document.querySelector("#full-name");
    const partyInput = document.querySelector("#party");
    const form = document.querySelector("form");

    fetch(`${window.apiUrl}/api/party`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + JSON.parse(localStorage.getItem("user")).accessToken,
        },
    })
        .then((response) => response.json())
        .then((data) => {
            data.forEach(party => {
                generateOption(partyInput, party);
            });
        })
        .then(() => {
            form.addEventListener("submit", (event) => createCandidate(event, fullNameInput, partyInput));
        })
        .catch((error) => {
            console.log(error);
        });
}

function generateOption(partyInput, party) {
    const option = document.createElement("option");
    option.setAttribute("value", party.id);
    option.innerText = party.name;

    partyInput.appendChild(option);
}

function createCandidate(event, fullNameInput, partyInput){
event.preventDefault();
console.log(fullNameInput);
console.log(partyInput);

event.preventDefault();
    fetch(`${window.apiUrl}/api/candidate/party/${partyInput.options[partyInput.selectedIndex].value}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + JSON.parse(localStorage.getItem("user")).accessToken,
        },
        body: JSON.stringify({
          fullName: fullNameInput.value,
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