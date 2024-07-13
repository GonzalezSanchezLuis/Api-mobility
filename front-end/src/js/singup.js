export function singup(){
    let url = "http://localhost:8080/api/v1/signup"; 
    let form = document.getElementById("form");
  
    if (form) {
      form.addEventListener("submit", e => {
        e.preventDefault();
        let name = form.elements["name"].value;
        let email = form.elements["email"].value;
        let password = form.elements["password"].value;
  
        let user = { name, email, password };
        console.log(user);
  
        fetch(url, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(user)
        })
        .then(response => {
          if (!response.ok) {
            return response.text().then(text => { throw new Error(response.status + ": " + text); });
          }
          return response.json();
        })
        .then(data => {
          console.log('Success:', data);
          alert('User created successfully!');
          window.location.href = './dashboard.html';
        })
        .catch(error => {
          console.error('There was a problem with your fetch operation:', error);
          alert('Error: ' + error.message);
        });
      });
    }
}
    
  
