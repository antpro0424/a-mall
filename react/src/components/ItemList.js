

import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ItemList = ({ addToCart }) => {
  const [items, setItems] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8083/api/v0/items')
      .then(response => {
        // console.log(response.data.content);
        setItems(response.data.content);

      })
      .catch(error => console.error('Error fetching items:', error));
  }, []);

  // useEffect(() => {
  //   const mockData = [
  //     { id: 1, name: 'Apple', unitPrice: 1.5 },
  //     { id: 2, name: 'Banana', unitPrice: 1.2 },
  //     { id: 3, name: 'Orange', unitPrice: 2.0 },
  //     { id: 4, name: 'Mango', unitPrice: 2.5 },
  //   ];
  //
  //   setItems(mockData);
  // }, []);

  return (

 
    <div>
      <h2>Shopping List</h2>
      <ul>

        <table>
          <thead>
            <tr>
      
              <th>Item</th>
              <th>Unit Price</th>
              <th>Quantity</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {items.map(item => 
            
            (
              
              <tr key={item.id}>

                <td>{item.name}</td>
                <td>${item.unitPrice}</td>
                <td>
                  <input
                    type="number"
                    defaultValue={1}
                    min="1"
                    id={`quantity-${item.id}`}
                  />
                </td>
                <td>
                  <button onClick={() => {
                    // console.log(item);
                    return addToCart(item, document.getElementById(`quantity-${item.id}`).value)}}>
                    Add to Cart
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>


      </ul>
    </div>




  );
};

export default ItemList;

