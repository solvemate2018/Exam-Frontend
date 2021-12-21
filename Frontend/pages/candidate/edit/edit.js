import shared from "/pages/shared/shared.js"
export default (candidateId) => {
    const content = document.querySelector(".content");

    return fetch("./pages/candidate/edit/edit.html")
        .then((response) => response.text())
        .then((loginHtml) => {
            content.innerHTML = loginHtml;
            shared();
            generateContent(candidateId);
        });
};
let candidate = undefined;
function generateContent(candidateId) {
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
        .then((data) => {
            candidate = getCandidate(candidateId, fullNameInput);
        })
        .then((data) => {
            form.addEventListener("submit", (event) => editCandidate(event, candidateId, fullNameInput, partyInput));
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

function getCandidate(candidateId, fullNameInput){
    fetch(`${window.apiUrl}/api/candidate/${candidateId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => response.json())
        .then((data) => {
            const candidate = data;
            fullNameInput.setAttribute("placeholder", candidate.fullName);
            return candidate
        })
        .catch((error) => {
            console.log(error);
        });
}

async function editCandidate(event, candidateId, fullNameInput, partyInput){
    event.preventDefault();

    if(fullNameInput.value === undefined || fullNameInput.value === ""){
        fullNameInput.value = fullNameInput.getAttribute("placeholder");
    }

    fetch(`${window.apiUrl}/api/candidate/${candidateId}/party/${partyInput.options[partyInput.selectedIndex].value}`, {
        method: "PUT",
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
            window.alert("Succesfully Updated");
        })
        .catch((error) => {
          console.error('Error:', error.me);
        });
}