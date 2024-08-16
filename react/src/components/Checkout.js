import React, { useState } from 'react';
import axios from 'axios';

const Checkout = ({ cart, setOrderNumber }) => {
  const [paymentInfo, setPaymentInfo] = useState('');
  const [addressInfo, setAddressInfo] = useState('');
    const totalPrice = cart.reduce((total, cartItem) => {
        return total + (cartItem.unitPrice * cartItem.quantity);
    }, 0);
  const placeOrder = () => {
      const cartById = cart.reduce((acc, item) => {
          acc[item.id] = item;
          return acc;
      }, {});
      // console.log(cartById)
    const orderDetails = {
        "customerId": "8f538b69-9b94-4464-b4b5-1278d8515d4d",
        "items":cartById,
        "payment": {
          "paymentType":"VISA",
          "paymentDetails": paymentInfo,
          "paymentStatus": "CREATED",
          "paymentAmount": 150.75,
          "paymentCurrency": "USD"

        },
        "address": {
            "zipCode":"95050",
            "detailAddress":addressInfo
        },
        "totalAmount": totalPrice.toFixed(2)

    };


    console.log(orderDetails)
    axios.post('http://localhost:8084/api/v0/orders', orderDetails)
      .then(response => {
          console.log(response.data.searchId)
          setOrderNumber(response.data.searchId)
      })
      .catch(error => console.error('Error placing order:', error));
  };

  return (
    <div>
      <h2>Checkout</h2>
      <label>
        Payment Info:
        <input
          type="text"
          value={paymentInfo}
          onChange={(e) => setPaymentInfo(e.target.value)}
        />
      </label>
      <br />
      <label>
        Address Info:
        <input
          type="text"
          value={addressInfo}
          onChange={(e) => setAddressInfo(e.target.value)}
        />
      </label>
      <br />
      <button onClick={placeOrder}>
        Place Order
      </button>
    </div>
  );
};

export default Checkout;
