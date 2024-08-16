import React, { useState } from 'react';
import { Card, Button, Modal, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import apiClient from '../services/api';

function ProductCard({ product }) {
    const navigate = useNavigate();
    const [showModal, setShowModal] = useState(false);
    const [quantity, setQuantity] = useState(1);

    const handleCardClick = () => {
        navigate(`/product/${product.id}`);
    };

    const handleAddToCartClick = (e) => {
        e.stopPropagation(); // Prevent triggering the card click event
        setShowModal(true);
    };

    const handleClose = () => {
        setShowModal(false);
    };

    const handleAddToCart = () => {
        const userId = localStorage.getItem('userId');  // 假设用户ID存储在localStorage中
    
        const cartFormDTO = {
            itemId: product.id,
            num: product.availableUnits,
            name: product.name,
            unitPrice: product.unitPrice,  // 假设你的前端 product 对象中有 price 属性
            pictureUrls: product.pictureUrls // 直接使用 product 对象中的 pictureUrls
        };
    
        apiClient.post('/carts', cartFormDTO)
            .then(response => {
                console.log(`Added ${quantity} of ${product.name} to cart.`);
                // 可以根据需求更新UI，比如展示一个成功提示
                setShowModal(false);
            })
            .catch(error => {
                console.error('Error adding item to cart:', error);
                // 处理错误，比如展示错误提示
            });
    };

    return (
        <>
            <Card className="mb-4" onClick={handleCardClick} style={{ cursor: 'pointer' }}>
                <Card.Img variant="top" src={product.pictureUrls? product.pictureUrls: '/assets/images/taobao.png'} alt={product.name} />
                <Card.Body>
                    <Card.Title>{product.name}</Card.Title>
                    <Card.Text>${product.unitPrice}</Card.Text>
                    <Card.Text>{product.availableUnits} in stock, order soon!</Card.Text>
                    <Button 
                        variant="success" 
                        onClick={handleAddToCartClick} 
                        onMouseDown={(e) => e.stopPropagation()}  // Prevent mousedown from triggering card click event
                    >
                        Add to Cart
                    </Button>
                </Card.Body>
            </Card>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{product.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div>
                        <img 
                            src={product.pictureUrls? product.pictureUrls: '/assets/images/taobao.png'} 
                            alt={product.name} 
                            style={{ width: '100%', marginBottom: '20px' }} 
                        />
                        <p>{product.description}</p>
                        <p>Price: ${product.unitPrice}</p>
                        <Form.Group controlId="quantity">
                            <Form.Label>Quantity</Form.Label>
                            <Form.Control 
                                type="number" 
                                value={quantity} 
                                min="1" 
                                onChange={(e) => setQuantity(Number(e.target.value))} 
                            />
                        </Form.Group>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleAddToCart}>
                        Add to Cart
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default ProductCard;
