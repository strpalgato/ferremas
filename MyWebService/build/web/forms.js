/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// Función para mostrar el formulario correspondiente y ocultar los demás
function mostrarFormulario(formId) {
    var formularios = document.querySelectorAll('form');
    formularios.forEach(formulario => {
        if (formulario.id === formId) {
            formulario.classList.remove('hidden');
        } else {
            formulario.classList.add('hidden');
        }
    });
}

// Función para consultar el producto
function consultarProducto() {
    var productId = document.getElementById("productId").value;
    fetch('http://localhost:8080/MyWebService/webresources/rest/' + productId)
        .then(response => response.json())
        .then(data => {
            Swal.fire({
                heightAuto: false,
                icon: 'success',
                title: 'Producto encontrado',
                text: JSON.stringify(data),
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                heightAuto: false,
                icon: 'error',
                title: 'Error',
                text: 'Producto no encontrado',
            });
        });
}

function crearProducto() {
    var productName = document.getElementById("productName").value;
    var productPrice = parseFloat(document.getElementById("productPrice").value);
    var productStock = parseInt(document.getElementById("productStock").value);

    var newProduct = {
        nombre: productName,
        precio: productPrice,
        stock: productStock
    };

    fetch('http://localhost:8080/MyWebService/webresources/rest/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newProduct)
    })
    .then(response => response.text())
    .then(data => {
        Swal.fire({
            heightAuto: false,
            icon: 'success',
            title: 'Producto creado',
            text: data,
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            heightAuto: false,
            icon: 'error',
            title: 'Error',
            text: 'Error al agregar el producto',
        });
    });
}

// Función para obtener el producto a actualizar
function obtenerProductoParaActualizar() {
    var productId = document.getElementById("productIdToUpdate").value;
    fetch('http://localhost:8080/MyWebService/webresources/rest/' + productId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Producto no encontrado');
            }
            return response.json();
        })
        .then(data => {
            mostrarFormularioDeActualizacion(data);
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                heightAuto: false,
                icon: 'error',
                title: 'Error',
                text: 'Producto no encontrado',
            });
        });
}

// Función para mostrar el formulario de actualización con los datos del producto
function mostrarFormularioDeActualizacion(producto) {
    document.getElementById("updatedProductName").value = producto.nombre;
    document.getElementById("updatedProductPrice").value = producto.precio;
    document.getElementById("updatedProductStock").value = producto.stock;
    document.getElementById("updateProductForm").classList.remove('hidden');
}

// Función para actualizar el producto
function actualizarProducto() {
    var productId = document.getElementById("productIdToUpdate").value;
    var updatedProductName = document.getElementById("updatedProductName").value;
    var updatedProductPrice = parseFloat(document.getElementById("updatedProductPrice").value);
    var updatedProductStock = parseInt(document.getElementById("updatedProductStock").value);

    var updatedProduct = {
        id: parseInt(productId),
        nombre: updatedProductName,
        precio: updatedProductPrice,
        stock: updatedProductStock
    };

    fetch('http://localhost:8080/MyWebService/webresources/rest/' + productId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedProduct)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al actualizar el producto');
        }
        return response.text();
    })
    .then(data => {
        Swal.fire({
            heightAuto: false,
            icon: 'success',
            title: 'Producto actualizado',
            text: data,
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            heightAuto: false,
            icon: 'error',
            title: 'Error',
            text: 'Error al actualizar el producto',
        });
    });
}

function eliminarProducto() {
    var productId = document.getElementById("productIdToDelete").value;

    fetch('http://localhost:8080/MyWebService/webresources/rest/' + productId, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Producto no encontrado');
        }
        return response.text();
    })
    .then(data => {
        Swal.fire({
            heightAuto: false,
            icon: 'success',
            title: 'Producto eliminado',
            text: 'Producto eliminado exitosamente',
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire({
            heightAuto: false,
            icon: 'error',
            title: 'Error',
            text: 'Producto no encontrado',
        });
    });
}