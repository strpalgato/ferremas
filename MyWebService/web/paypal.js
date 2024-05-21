/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



const swalConfig = {
    heightAuto: false,
    icon: 'success',
};

document.addEventListener('DOMContentLoaded', function () {
    fetch('env.json')
        .then(response => response.json())
        .then(env => {
            window.env = env;
            console.log('Variables de entorno cargadas:', env);

            if (window.env && window.env.PAYPAL_CLIENT_ID) {
                paypal.Buttons({
                    createOrder: function (data, actions) {
                        return actions.order.create({
                            purchase_units: [{
                                amount: {
                                    value: '10.0' // Monto de la transacción
                                }
                            }]
                        });
                    },
                    onApprove: function (data, actions) {
                        return actions.order.capture().then(function (details) {
                            // Utiliza la configuración global de SweetAlert2
                            Swal.fire({
                                ...swalConfig,
                                title: 'Transacción completada',
                                text: 'Pago realizado por ' + details.payer.name.given_name,
                            });
                        });
                    }
                }).render('#paypal-button-container');
            } else {
                console.error('No se pudo cargar PayPal CLIENT ID desde las variables de entorno');
            }
        })
        .catch(error => console.error('Error cargando variables de entorno:', error));
});