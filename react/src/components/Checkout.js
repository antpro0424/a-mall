import React, { useState } from 'react';
import axios from 'axios';

const Checkout = ({ cart, setOrderNumber }) => {
  const [paymentInfo, setPaymentInfo] = useState('');
  const [addressInfo, setAddressInfo] = useState('');

  const placeOrder = () => {

    const orderDetails = {
      cart,
      paymentInfo,
      addressInfo,
    };
    console.log(orderDetails)
    axios.post('/api/orders', orderDetails)
      .then(response => setOrderNumber(response.data.orderNumber))
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
