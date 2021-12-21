import shared from"/pages/shared/shared.js"
export default () => {
  const content = document.querySelector(".content");

  return fetch("./pages/main/main.html")
    .then((response) => response.text())
    .then((mainHtml) => {
      content.innerHTML = mainHtml;
      shared();
      if(localStorage.getItem("user") != null && JSON.parse(localStorage.getItem("user")).roles.includes('ROLE_ADMIN')){
        fetchAdminContent();
      }
      else{
        fetchUserContent();
      }
    });
};

function fetchAdminContent(){
  let adminclasses = document.getElementsByClassName('admin-content');
  let userclasses = document.getElementsByClassName('user-content');
  for (var i = 0; i < adminclasses.length; i++) {
    adminclasses[i].style.display = "block";
  }
  for(var i = 0; i < userclasses.length; i++){
    userclasses[i].style.display = "none";
  }
}

function fetchUserContent(){
  let adminclasses = document.getElementsByClassName('admin-content');
  let userclasses = document.getElementsByClassName('user-content');
  for (var i = 0; i < adminclasses.length; i++) {
    adminclasses[i].style.display = "none";
  }
  for(var i = 0; i < userclasses.length; i++){
    userclasses[i].style.display = "block";
  }
}


