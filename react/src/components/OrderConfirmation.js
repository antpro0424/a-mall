import React from 'react';

const OrderConfirmation = ({ orderNumber }) => {
  return (
    <div>
      <h2>Order Confirmation</h2>
      <p>Your order number is: {orderNumber}</p>
    </div>
  );
};

export default OrderConfirmation;
