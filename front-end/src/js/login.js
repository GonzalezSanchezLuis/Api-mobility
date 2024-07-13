
export function login() {
    let url = "http://localhost:8080/api/v1/auth/login";
    let login = document.getElementById("login");
  
    if (login) {
      login.addEventListener("submit", e => {
        e.preventDefault();
        
        let email = login.elements["email"].value;
        let password = login.elements["password"].value;
  
        let user = { email, password };
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
          console.log('Sending request with token:', data.token); 
          localStorage.setItem('token', data.token);
          window.location.href = './dashboard.html';
        })
        .catch(error => {
          console.error('There was a problem with your fetch operation:', error);
          alert('Error: ' + error.message);
        });
      });
    }
  }
  





  
    
  
