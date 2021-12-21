import shared from"/pages/shared/shared.js"
export default (candidateId) => {
    const content = document.querySelector(".content");
    shared();

    fetch(`${window.apiUrl}/api/candidate/${candidateId}`, {
        method: "DELETE",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
          Authorization: "Bearer " + JSON.parse(localStorage.getItem("user")).accessToken,
        },
      })
        .then((response) => console.log(response))
        .then(()=>{
            window.router.navigate("/");
            window.alert("Succesfully Deleted");
        })
    
};