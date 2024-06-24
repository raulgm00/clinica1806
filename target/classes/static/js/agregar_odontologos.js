const form = document.getElementById("agregarForm");
//const apiURL = "http://localhost:8080";
const apiURL ="";

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const nombre = document.getElementById("nombre").value;
  const apellido = document.getElementById("apellido").value;
  const matricula = document.getElementById("matricula").value;

  // llamando al endpoint de agregar

  fetch(`${apiURL}/odontologos`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ nombre, apellido, numeroMatricula: matricula }), // JavaScript a JSON
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // alert("Odontólogo agregado con éxito");
      Swal.fire({
        title: "Registro exitoso!",
        icon: "success"
      });
      form.reset(); // Resetear el formulario
    })
    .catch((error) => {
      //console.error("Error agregando odontólogo:", error);
      Swal.fire({
        title: '¡Error!',
        text: 'Por el momento el API esta fuera de linea, favor de intentar mas tarde',
        icon: 'error',
        confirmButtonText: 'Aceptar'
      });
    });
});
