const tableBody = document.querySelector("#odontologosTable tbody");
//const apiURL = "http://localhost:8080";
const apiURL ="";
function fetchOdontologos() {
  
  // listando los odontologos

  fetch(`${apiURL}/odontologos`)
    .then((response) => response.json()) // se decodifica la data de la promesa
    .then((data) => {                    // se trabaja con la data decodificada en JSON
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        /**
               tr: table row (fila de tabla)
               th: table header (encabezado de tabla)
               tbody: table body (cuerpo de la tabla)
               td: table data (dato de tabla o celda de tabla)
        Estos elementos se utilizan para estructurar y organizar los datos dentro de una tabla en HTML.
        */

        row.innerHTML = `
                <td>${odontologo.id}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.numeroMatricula}</td>
                <td>
                  <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.numeroMatricula}')">Modificar</button>
                  <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
                </td>
              `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      let mensaje = `Error fetching data: ${error}`;
      // alert(mensaje);
      Swal.fire({
        title: '¡Error!',
        text: mensaje,
        icon: 'error',
        confirmButtonText: 'Aceptar'
      });
    });

  // modificar un odontologo

  modal.addEventListener("submit", function (event){
    event.preventDefault();

  });

  // eliminar un odontologo

  btnEliminar.addEventListener("submit", function (event) {
    
  })
}

fetchOdontologos();
