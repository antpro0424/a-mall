import React from 'react';

const Cart = ({ cart, proceedToCheckout }) => {
  // console.log(cart);
  const totalPrice = cart.reduce((total, cartItem) => {
    return total + (cartItem.unitPrice * cartItem.quantity);
  }, 0);
  return (
    <div>
      <h2>Your Cart</h2>
      <ul>
        {cart.map((cartItem, index) => (
          
          <li key={index}>
            
            {cartItem.name} - Quantity: {cartItem.quantity} 
          </li>
        ))}
      </ul>
      <h3>Total Price: ${totalPrice.toFixed(2)}</h3>
      <button onClick={proceedToCheckout}>
        Proceed to Checkout
      </button>
    </div>
  );
};

export default Cart;
