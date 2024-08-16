import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Button, Container, Row, Col, Alert } from 'react-bootstrap';

function ProductDetail() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/v0/items/${id}`)
            .then(response => {
                setProduct(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching product:', error);
                setError('Failed to load product details.');
                setLoading(false);
            });
    }, [id]);

    const handleAddToCart = () => {
        if (!product) return;

        const cartFormDTO = {
            itemId: product.id,
            num: 1,
            name: product.name,
            unitPrice: product.unitPrice,
            pictureUrls: product.pictureUrls
        };

        axios.post('/api/v0/carts', cartFormDTO)
            .then(response => {
                alert('Added to cart!');
            })
            .catch(error => console.error('Error adding item to cart:', error));
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!product) return <div>No product found.</div>;

    return (
        <Container>
            <Row>
                <Col md={6}>
                    <img 
                        src={product.pictureUrls ? product.pictureUrls : '/path/to/default/image.jpg'} 
                        alt={product.name} 
                        style={{ width: '100%' }} 
                    />
                </Col>
                <Col md={6}>
                    <h1>{product.name}</h1>
                    <p>{product.description}</p>
                    <h3>${product.unitPrice.toFixed(2)}</h3>
                    <Button variant="success" onClick={handleAddToCart}>Add to Cart</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default ProductDetail;
