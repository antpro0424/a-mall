import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Image, Button } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import axios from 'axios';

function ProductDetail() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        axios.get(`/api/v0/products/${id}`)
            .then(response => setProduct(response.data))
            .catch(error => console.error('Error fetching product:', error));
    }, [id]);

    if (!product) {
        return <div>Loading...</div>;
    }

    const addToCart = () => {
        // 将商品添加到购物车逻辑
        console.log('Added to cart:', product.id);
    };

    return (
        <Container>
            <Row>
                <Col md={6}>
                    <Image src={product.image} fluid />
                </Col>
                <Col md={6}>
                    <h2>{product.name}</h2>
                    <h4>${product.price}</h4>
                    <p>{product.description}</p>
                    <Button variant="success" onClick={addToCart}>Add to Cart</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default ProductDetail;
