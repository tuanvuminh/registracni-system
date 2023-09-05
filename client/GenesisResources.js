// POST Create new user
document.getElementById("addNewUser").addEventListener("click", async () => {
  const nameOfUser = document.getElementById("nameOfUser").value;
  const surnameOfUser = document.getElementById("surnameOfUser").value;
  const personID = document.getElementById("personID").value;

  const userData = {
    name: nameOfUser,
    surname: surnameOfUser,
    personID: personID
  };

  const response = await fetch('http://localhost:8080/api/v1/user', {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData)
  });

  const apiResponse = await response.json();

  if (response.ok) {
    alert(apiResponse.message);
  } else {
    alert(apiResponse.message);
  }
});

// GET UsersNonDetailedInfo
document.getElementById("findButtonNonDetailed").addEventListener("click", async () => {
  const userId = document.getElementById("userId").value;
  const userDetailsElement = document.getElementById("userNonDetailed");

  try {
    const response = await fetch(`http://localhost:8080/api/v1/users/${userId}`);
    const user = await response.json();

    if (user != null) {
      userDetailsElement.innerHTML = `
          <div style="text-align: center;">
              <p style="font-family: Helvetica; font-weight: 500; font-size: 16px; display: inline-block; margin-right: 5px;">ID: ${user.id}</p>
              <p style="font-family: Helvetica; font-weight: 500; font-size: 16px; display: inline-block; margin-right: 5px;">Name: ${user.name}</p>
              <p style="font-family: Helvetica; font-weight: 500; font-size: 16px; display: inline-block; margin-right: 5px;">Surname: ${user.surname}</p>
          </div>
          `;
    } else {
      userDetailsElement.innerHTML = '<p style="font-family: Helvetica; font-size: 20px; font-weight: 500;">User was not found in database.</p>';
    }
  } catch (error) {
    userDetailsElement.innerHTML = '<p style="font-family: Helvetica; font-size: 20px; font-weight: 500;">User was not found in database.</p>';
  }
});

// GET UsersDetailedInfo
document.getElementById("findButtonDetailed").addEventListener("click", async () => {
  const userId = document.getElementById("userId").value;
  const userDetailsElement = document.getElementById("userDetailed");

  try {
    const response = await fetch(`http://localhost:8080/api/v1/users/${userId}?detail=true`);
    const user = await response.json();

    if (user != null) {
      userDetailsElement.innerHTML = `
          <div style="text-align: center;">
              <p style="font-family: Helvetica; font-weight: 300; font-size: 16px; line-height: 0px; display: inline-block; margin-right: 5px;">ID: ${user.id}</p>
              <p style="font-family: Helvetica; font-weight: 300; font-size: 16px; line-height: 0px; display: inline-block; margin-right: 5px;">Name: ${user.name}</p>
              <p style="font-family: Helvetica; font-weight: 300; font-size: 16px; line-height: 0px; display: inline-block; margin-right: 5px;">Surname: ${user.surname}</p>
              <p style="font-family: Helvetica; font-weight: 300; font-size: 16px; line-height: 0px; display: inline-block; margin-right: 5px;">Person ID: ${user.personID}<p>
              <p style="font-family: Helvetica; font-weight: 300; font-size: 16px; line-height: 0px; display: inline-block; margin-right: 5px; margin-bottom: 0px;">UUID:<p>
              <p style="font-family: Helvetica; font-weight: 300; font-size: 16px; line-height: 0px; display: inline-block; margin-right: 5px; margin-top: 0px;">${user.uuid}<p>
          </div>
          `;
    } else {
      userDetailsElement.innerHTML = '<p style="font-family: Helvetica; font-size: 20px; font-weight: 500;">User was not found in database.</p>';
    }
  } catch (error) {
    userDetailsElement.innerHTML = '<p style="font-family: Helvetica; font-size: 20px; font-weight: 500;">User was not found in database.</p>';
  }
});

// UPDATE User
document.getElementById("updateUser").addEventListener("click", async () => {
  const userIdToUpdate = document.getElementById("userIdToUpdate").value;
  const updatedName = document.getElementById("newName").value;
  const updatedSurname = document.getElementById("newSurname").value;

  const updatedUserData = {
    name: updatedName,
    surname: updatedSurname
  };

  try {
    const response = await fetch(`http://localhost:8080/api/v1/users/${userIdToUpdate}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(updatedUserData)
    });

    const apiResponse = await response.json();

    if (response.ok) {
      alert(apiResponse.message);
    } else {
      alert(apiResponse.message);
    }
  } catch (error) {
    alert("An error occurred: " + error.message);
  }
});

// DELETE User
document.getElementById("deleteUser").addEventListener("click", async () => {
  const userIdToDelete = document.getElementById("ID").value;

  try {
    const response = await fetch(`http://localhost:8080/api/v1/users/${userIdToDelete}`, {
      method: "DELETE"
    });

    const apiResponse = await response.json();

    if (response.ok) {
      alert(apiResponse.message);
    } else {
      alert(apiResponse.message);
    }
  } catch (error) {
    alert("An error occurred: " + error.message);
  }
});

// GET list of users
function openAllUsersInfo() {
  window.open("http://localhost:8080/api/v1/users", "_blank");
}
function openAllUsersDetailedInfo() {
  window.open("http://localhost:8080/api/v1/users?detail=true", "_blank");
}

// Animation
let circ = new CircleType(document.getElementById('circ'));
circ.raduis(360);