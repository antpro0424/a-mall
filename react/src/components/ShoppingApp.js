import React, { useState } from 'react';
import ItemList from './ItemList';
import Cart from './Cart';
import Checkout from './Checkout';
import OrderConfirmation from './OrderConfirmation';

const ShoppingApp = () => {
  const [cart, setCart] = useState([]);
  const [checkout, setCheckout] = useState(false);
  const [orderNumber, setOrderNumber] = useState(null);

  const addToCart = (item, quantity) => {
    const updatedCart = [...cart];
    const existingItemIndex = updatedCart.findIndex(cartItem => cartItem.id === item.id);

    if (existingItemIndex >= 0) {
      updatedCart[existingItemIndex].quantity += parseInt(quantity, 10);
    } else {
      updatedCart.push({ ...item, quantity: parseInt(quantity, 10) });
    }

    setCart(updatedCart);
  };

  const proceedToCheckout = () => {
    setCheckout(true);
  };

  const placeOrder = (orderNumber) => {
    setOrderNumber(orderNumber);
    setCheckout(false);
    setCart([]);
  };

  return (
    <div>
      <h1>Shopping App</h1>
      {!checkout && !orderNumber && (
        <>
          <ItemList addToCart={addToCart} />
          <Cart cart={cart} proceedToCheckout={proceedToCheckout} />
        </>
      )}
      {checkout && !orderNumber && (
        <Checkout cart={cart} setOrderNumber={placeOrder} />
      )}
      {orderNumber && (
        <OrderConfirmation orderNumber={orderNumber} />
      )}
    </div>
  );
};

export default ShoppingApp;
