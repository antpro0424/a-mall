import React, { useState, useEffect } from 'react';
import { Container, Row, Col, ListGroup, Button, Image } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import apiClient from '../services/api';  

function Cart() {
    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        // 获取购物车数据，使用 apiClient 代替 axios
        apiClient.get('/carts')
            .then(response => setCartItems(response.data))
            .catch(error => console.error('Error fetching cart items:', error));
    }, []);

    const removeFromCart = (id) => {
        // 删除购物车中的某个商品，使用 apiClient 代替 axios
        apiClient.delete(`/carts/${id}`)
            .then(() => {
                setCartItems(prevItems => prevItems.filter(item => item.id !== id));
            })
            .catch(error => console.error('Error removing item from cart:', error));
    };

    const updateCartItem = (cartFormDTO) => {
        // 更新购物车中的商品，使用 apiClient 代替 axios
        apiClient.put('/carts', cartFormDTO)
            .then(response => {
                // 处理成功后的逻辑，例如更新状态
            })
            .catch(error => console.error('Error updating cart item:', error));
    };

    const removeSelectedItems = (selectedIds) => {
        // 删除选中的商品，使用 apiClient 代替 axios
        apiClient.delete('/carts', { params: { ids: selectedIds } })
            .then(() => {
                setCartItems(prevItems => prevItems.filter(item => !selectedIds.includes(item.id)));
            })
            .catch(error => console.error('Error removing selected items from cart:', error));
    };

    const proceedToCheckout = () => {
        navigate('/checkout');
    };

    // 计算总价
    const totalPrice = cartItems.reduce((total, item) => {
        return total + item.unitPrice * item.num;
    }, 0);

    return (
        <Container>
            <h2>Your Cart</h2>
            {cartItems.length === 0 ? (
                <p>Your cart is empty</p>
            ) : (
                <>
                    <ListGroup variant="flush">
                        {cartItems.map(item => (
                            <ListGroup.Item key={item.id}>
                                <Row>
                                    <Col md={2}>
                                        <Image src={item.pictureUrls ? item.pictureUrls : '/assets/images/taobao.png'} fluid />
                                    </Col>
                                    <Col md={4}>
                                        <h5>{item.name}</h5>
                                    </Col>
                                    <Col md={2}>
                                        ${item.unitPrice.toFixed(2)}  {/* 显示单价 */}
                                    </Col>
                                    <Col md={2}>
                                        Quantity: {item.num}  {/* 显示数量 */}
                                    </Col>
                                    <Col md={2}>
                                        <Button
                                            variant="danger"
                                            onClick={() => removeFromCart(item.id)}
                                        >
                                            Remove
                                        </Button>
                                    </Col>
                                </Row>
                            </ListGroup.Item>
                        ))}
                    </ListGroup>

                    {/* 显示总价 */}
                    <h4 className="mt-4">Total Price: ${totalPrice.toFixed(2)}</h4>

                    <Button
                        variant="primary"
                        onClick={proceedToCheckout}
                        disabled={cartItems.length === 0}
                        className="mt-3"
                    >
                        Proceed to Checkout
                    </Button>
                </>
            )}
        </Container>
    );
}

export default Cart;
