import React from 'react';
import { Container, Row, Col, Carousel } from 'react-bootstrap';
import ProductList from '../components/ProductList';

function Home() {
    return (
        <Container>
            <Row>
                <Col>
                    <Carousel>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src="/assets/images/taobao.png"
                                alt="First slide"
                            />
                        </Carousel.Item>
                        {/* 其他轮播图 */}
                    </Carousel>
                </Col>
            </Row>
            <Row>
                <Col>
                    <h2>Popular Products</h2>
                    <ProductList />
                </Col>
            </Row>
        </Container>
    );
}

export default Home;
